package org.jbookreader.ui.swing.painter;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jbookreader.formatengine.IRenderingObject;
import org.jbookreader.formatengine.RenderingDimensions;

/**
 * This is a class representing Swing/ImageIO images for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class ImageRenderingObject implements IRenderingObject {
	/**
	 * Corresponding painter.
	 */
	private final SwingBookPainter myPainter;
	/**
	 * Loaded image.
	 */
	private final BufferedImage myImage;
	/**
	 * Image dimensions
	 */
	private final RenderingDimensions myDimensions;

	/**
	 * This constructs the image for specified <code>painter</code> from
	 * data in <code>stream</code>.
	 * @param painter the painter to construct the image date.
	 * @param stream the stream with image data
	 * @throws IOException
	 */
	public ImageRenderingObject(SwingBookPainter painter, InputStream stream) throws IOException {		
		this.myPainter = painter;
		this.myImage = ImageIO.read(stream);
		if (this.myImage == null) {
			throw new RuntimeException("Can't load image");
		}
		this.myDimensions = new RenderingDimensions(this.myImage.getHeight(), 0, this.myImage.getWidth());
	}

	public RenderingDimensions getDimensions() {
		return this.myDimensions;
	}

	public boolean isGlue() {
		return false;
	}

	public void render() {
		this.myPainter.getGraphics().drawImage(
				this.myImage,
				new AffineTransform(1f, 0f, 0f, 1f, this.myPainter.getXCoordinate(), this.myPainter.getYCoordinate() - this.myDimensions.height),
				null);
	}
	
}
