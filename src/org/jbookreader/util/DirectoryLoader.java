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
/*
 * Copyright (c) 2006 Dmitry Baryshkov
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the author may be used to endorse or promote
 *    products derived from this software without specific prior written
 *    permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package org.jbookreader.util;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.IOException;

/**
 * This is the very simple <code>ClassLoader</code>. It's main purpose is loading classes
 * from specified directory.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class DirectoryLoader extends ClassLoader {

	/**
	 * This is a very simple byte-buffer.
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	private static class Buffer {
		/**
		 * The backing array;
		 */
		byte[] array;
		/**
		 * The length of contents in the array.
		 */
		int length;
	}

	/**
	 * This is very simple filter. It accepts only names ending with ".class".
	 * 
	 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
	 *
	 */
	private static class ClassNameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".class");
		}
	}

	/**
	 * Returns a byte array with file contents.
	 * @param file the file to read.
	 * @return the contents of the file.
	 * @throws IOException in case of I/O problem.
	 */
	@SuppressWarnings("synthetic-access")
	private byte[] readFile(File file) throws IOException {
		InputStream input = new BufferedInputStream(new FileInputStream(file));

		List<Buffer> blist = new ArrayList<Buffer>();
		int totalLength = 0;

		while (true) {
			Buffer b = new Buffer();
			b.array = new byte[1024];
			b.length = input.read(b.array);

			if (b.length <= 0) {
				break;
			}

			blist.add(b);
			totalLength += b.length;
		}

		byte[] result = new byte[totalLength];
		int offset = 0;

		for (Buffer b: blist) {
			for (int i = 0; i < b.length; i++) {
				result[offset++] = b.array[i];
			}
		}

		return result;
	}

	/**
	 * This loads all classes from specified directory and returns them.
	 * @param directory the directory to load classes from
	 * @return an array with loaded classes.
	 * @throws IOException in case of I/O error.
	 */
	@SuppressWarnings("synthetic-access")
	public Class<?>[] loadClasses(File directory) throws IOException {
		File[] classFiles = directory.listFiles(new ClassNameFilter());
		Class<?>[] classes = new Class<?>[classFiles.length];

		int i = 0;

		for (File f: classFiles) {
			byte[] data = readFile(f);
			Class<?> fclass = defineClass(null, data, 0, data.length);
			resolveClass(fclass);
			classes[i++] = fclass;
		}

		return classes;
	}
	
}
