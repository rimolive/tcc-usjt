package br.usjt.tcc.utils.lwjgl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

/**
 * A utility class to load textures for JOGL. This source is based on a texture
 * that can be found in the Java Gaming (www.javagaming.org) Wiki. It has been
 * simplified slightly for explicit 2D graphics use.
 * 
 * OpenGL uses a particular image format. Since the images that are loaded from
 * disk may not match this format this loader introduces a intermediate image
 * which the source image is copied into. In turn, this image is used as source
 * for the OpenGL texture.
 *
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class TextureLoader {
	
	/** A tabela de texturas que foram carregadas aqui */
	private HashMap<String, Texture> table = new HashMap<String, Texture>();
	
	/** O modelo de coloracao incluindo o alpha para a imagem GL */
	private ColorModel glAlphaColorModel;
	
	/** O modelo de coloracao da imagem GL */
	private ColorModel glColorModel;

	/**
	 * Cria um novo texture loader baseado no painel do jogo
	 */
	public TextureLoader() {
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
				new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 },
				false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
	}

	/**
	 * Cria um novo ID para a textura
	 *
	 * @return O novo ID da textura
	 */
	private int createTextureID() {
		IntBuffer tmp = createIntBuffer(1);
		GL11.glGenTextures(tmp);
		return tmp.get(0);
	}

	/**
	 * Carrega uma textura
	 *
	 * @param resourceName
	 *            A localizacao do recurso a ser carregado
	 * @return A textura carregada
	 * @throws IOException
	 *             Indica uma falha ao acessar o recurso
	 */
	public Texture getTexture(String resourceName) throws IOException {
		Texture tex = (Texture) table.get(resourceName);

		if (tex != null) {
			return tex;
		}

		tex = getTexture(resourceName, GL11.GL_TEXTURE_2D, GL11.GL_RGBA, GL11.GL_LINEAR, GL11.GL_LINEAR);

		table.put(resourceName, tex);

		return tex;
	}

	/**
	 * Load a texture into OpenGL from a image reference on disk.
	 *
	 * @param resourceName
	 *            The location of the resource to load
	 * @param target
	 *            The GL target to load the texture against
	 * @param dstPixelFormat
	 *            The pixel format of the screen
	 * @param minFilter
	 *            The minimising filter
	 * @param magFilter
	 *            The magnification filter
	 * @return The loaded texture
	 * @throws IOException
	 *             Indicates a failure to access the resource
	 */
	public Texture getTexture(String resourceName, int target, int dstPixelFormat, int minFilter, int magFilter)
			throws IOException {
		int srcPixelFormat = 0;

		// create the texture ID for this texture
		int textureID = createTextureID();
		Texture texture = new Texture(target, textureID);

		// bind this texture
		GL11.glBindTexture(target, textureID);

		BufferedImage bufferedImage = loadImage(resourceName);
		texture.setWidth(bufferedImage.getWidth());
		texture.setHeight(bufferedImage.getHeight());

		if (bufferedImage.getColorModel().hasAlpha()) {
			srcPixelFormat = GL11.GL_RGBA;
		} else {
			srcPixelFormat = GL11.GL_RGB;
		}

		// convert that image into a byte buffer of texture data
		ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);

		if (target == GL11.GL_TEXTURE_2D) {
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
		}

		// produce a texture from the byte buffer
		GL11.glTexImage2D(target, 0, dstPixelFormat, get2Fold(bufferedImage.getWidth()),
				get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL11.GL_UNSIGNED_BYTE, textureBuffer);

		return texture;
	}

	/**
	 * Get the closest greater power of 2 to the fold number
	 * 
	 * @param fold
	 *            The target number
	 * @return The power of 2
	 */
	private int get2Fold(int fold) {
		int ret = 2;
		while (ret < fold) {
			ret *= 2;
		}
		return ret;
	}

	/**
	 * Converte a imagem bufferizada em uma textura
	 *
	 * @param bufferedImage
	 *            A imagem a ser convertida em textura
	 * @param texture
	 *            A textura a ser armazenada
	 * @return Um buffer contendo o dado
	 */
	private ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture) {
		ByteBuffer imageBuffer = null;
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = 2;
		int texHeight = 2;

		// find the closest power of 2 for the width and height
		// of the produced texture
		while (texWidth < bufferedImage.getWidth()) {
			texWidth *= 2;
		}
		while (texHeight < bufferedImage.getHeight()) {
			texHeight *= 2;
		}

		texture.setTextureHeight(texHeight);
		texture.setTextureWidth(texWidth);

		// create a raster that can be used by OpenGL as a source
		// for a texture
		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
		}

		// copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, null);

		// build a byte buffer from the temporary image
		// that be used by OpenGL to produce a texture.
		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();

		return imageBuffer;
	}

	/**
	 * Carrega um dado recurso como um objeto BufferedImage
	 * 
	 * @param ref
	 *            A localizacao do recurso a ser carregado
	 * @return O objeto BufferedImage carregado
	 * @throws IOException
	 *             Indica uma falha ao encontrar o recurso
	 */
	private BufferedImage loadImage(String ref) throws IOException {
		URL url = TextureLoader.class.getClassLoader().getResource(ref);

		if (url == null) {
			throw new IOException("Nao foi possivel encontrar: " + ref);
		}

		BufferedImage bufferedImage = ImageIO
				.read(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(ref)));

		return bufferedImage;
	}

	/**
	 * Cria um buffer de numeros inteiros para armazenar numeros especificos
	 *
	 * @param size
	 *            Quantos numeros inteiros ira conter
	 * @return O buffer de numeros inteiros criado
	 */
	protected IntBuffer createIntBuffer(int size) {
		ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
		temp.order(ByteOrder.nativeOrder());

		return temp.asIntBuffer();
	}
}
