/*
 * JBookReader - Java FictionBook Reader
 * Copyright (C) 2006 Dmitry Baryshkov
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.jbookreader.ui.swing.painter;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.jbookreader.book.bom.INode;
import org.jbookreader.formatengine.objects.AbstractInlineRenderingObject;

/**
 * This is a class representing Swing/ImageIO images for the FE.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
class ImageRenderingObject extends AbstractInlineRenderingObject {
	/**
	 * Loaded image.
	 */
	private final BufferedImage myImage;

	/**
	 * This constructs the image for specified <code>painter</code> from
	 * data in <code>stream</code>.
	 * @param painter the painter to construct the image date.
	 * @param node the node containing the image
	 * @param stream the stream with image data
	 * @throws IOException in case of I/O problems
	 */
	public ImageRenderingObject(SwingBookPainter painter, INode node, InputStream stream) throws IOException {
		super(painter, node);
//		this.myImage = null;
		this.myImage = ImageIO.read(stream);
		if (this.myImage == null) {
			throw new RuntimeException("Can't load image");
		}
		setHeight(this.myImage.getHeight());
		setWidth(this.myImage.getWidth());
	}

	@Override
	public void renderInline() {
		SwingBookPainter painter = (SwingBookPainter) this.getPainter();
		painter.getGraphics().drawImage(
				this.myImage,
				new AffineTransform(1f, 0f, 0f, 1f,
					this.getPainter().getXCoordinate(),
					this.getPainter().getYCoordinate() - this.getHeight()),
				null);
		this.getPainter().addHorizontalStrut(this.getWidth());
	}

	@Override
	public boolean isSplittable() {
		return true;
	}
	
}
