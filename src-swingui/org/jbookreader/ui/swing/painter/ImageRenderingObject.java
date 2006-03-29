package org.jbookreader.ui.swing.painter;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jbookreader.formatengine.model.IRenderingObject;
import org.jbookreader.formatengine.model.RenderingDimensions;

class ImageRenderingObject implements IRenderingObject {
	private final SwingBookPainter myPainter;
	private final BufferedImage myImage;
	private final RenderingDimensions myDimensions;

	public ImageRenderingObject(SwingBookPainter painter, InputStream stream) throws IOException, ImageLoadingException {		
		this.myPainter = painter;
		this.myImage = ImageIO.read(stream);
		if (this.myImage == null) {
			throw new ImageLoadingException();
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
