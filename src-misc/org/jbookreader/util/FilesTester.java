package org.jbookreader.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import junit.framework.Assert;

public class FilesTester {
	public static String compareReaders(Reader r1, Reader r2) throws IOException {
		LineNumberReader lr1 = new LineNumberReader(r1);
		LineNumberReader lr2 = new LineNumberReader(r2);

		for (;;) {
			String s1 = lr1.readLine();
			String s2 = lr2.readLine();
			
			if (s1 == null && s2 == null)
				return null;
			
			if (s1 == null)
				return "Expected EOF, but got " + s2;
			
			if (s2 == null)
				return "Expected '" + s1 + "' but got EOF";
			
			if (!s1.equals(s2)) {
				return "Expected '" + s1 + "' but got '" + s2 + "'"; 
			}
		}
	}
	
	public static void assertFileEqualsStream(File f1, File f2) {
		try {
			Reader r1 = new BufferedReader(new InputStreamReader(new FileInputStream(f1), "UTF-8"));
			Reader r2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2), "UTF-8"));
			String s = compareReaders(r1, r2);
			
			r1.close();
			r2.close();
		
			if (s != null)
				Assert.fail(s);
		} catch (IOException ioe) {
			throw new RuntimeException("Got IOException during file comparation", ioe);
		}
	}
}
