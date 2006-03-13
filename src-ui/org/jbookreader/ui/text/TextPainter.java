package org.jbookreader.ui.text;

import java.io.PrintStream;

import org.jbookreader.formatengine.ITextFont;
import org.jbookreader.formatengine.ITextPainter;
import org.jbookreader.formatengine.StringDimensions;

/**
 * This class represents a simple painter over the console with width 80.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class TextPainter implements ITextPainter {
	
	/**
	 * Output device.
	 */
	private final PrintStream myOutput;

	private double myXPosition = 0;

	/**
	 * This constructs new <code>TextPainter</code> with specified
	 * <code>PrintStream</code> as the output device.
	 * @param output output stream
	 */
	public TextPainter(PrintStream output) {
		this.myOutput = output;
	}

	public void clear() {
		// do nothing.
	}

	public double getWidth() {
		return 80;
	}

	public double getHeight() {
		return Double.POSITIVE_INFINITY;
	}
	
	public double getXPosition() {
		return this.myXPosition;
	}

	public double getYPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	public StringDimensions calculateStringDimensions(String s, int start, int end, ITextFont font) {
		return new StringDimensions(1, 0,  end - start);
	}

	public void renderString(String s, int start, int end, ITextFont font) {
		this.myOutput.append(s, start, end);
		this.myXPosition += end-start;
	}

	public void addHorizontalStrut(double size) {
		while (size >= 0.5) {
			this.myOutput.append(' ');
			this.myXPosition += 1;
			size -= 1;
		}
	}

	public void flushString(double vStrut) {
		// FIXME: implement vertical spaces!
		this.myXPosition = 0;
		this.myOutput.println();
	}

	/**
	 * This closes the writer.
	 */
	public void close() {
		this.myOutput.close();
	}

	/**
	 * The w/a for the all font problems.
	 */
	private ITextFont myFont; 
	public ITextFont getFont(String name, int size) {
		if (this.myFont == null) {
			this.myFont = new ITextFont() {/*empty*/};
		}
		return this.myFont;
	}

}
