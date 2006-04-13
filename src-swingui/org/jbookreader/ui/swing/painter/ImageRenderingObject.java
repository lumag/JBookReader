package org.jbookreader.ui.swing.painter;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.AbstractRenderingObject;

/**
 * This is a class representing Swing/ImageIO images for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class ImageRenderingObject extends AbstractRenderingObject {
	/**
	 * Loaded image.
	 */
	private final BufferedImage myImage;

	/**
	 * This constructs the image for specified <code>painter</code> from
	 * data in <code>stream</code>.
	 * @param painter the painter to construct the image date.
	 * @param node TODO
	 * @param stream the stream with image data
	 * @throws IOException
	 */
	public ImageRenderingObject(SwingBookPainter painter, INode node, InputStream stream) throws IOException {
		super(painter, node);
		this.myImage = ImageIO.read(stream);
		if (this.myImage == null) {
			throw new RuntimeException("Can't load image");
		}
		setHeight(this.myImage.getHeight());
		setWidth(this.myImage.getWidth());
	}

	public void render() {
		SwingBookPainter painter = (SwingBookPainter) this.getPainter();
		painter.getGraphics().drawImage(
				this.myImage,
				new AffineTransform(1f, 0f, 0f, 1f,
					this.getPainter().getXCoordinate(),
					this.getPainter().getYCoordinate() - this.getHeight()),
				null);
	}
	
}
