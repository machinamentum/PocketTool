/*******************************************************************************
 * Copyright (c) 2012 Joshua Huelsman.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Joshua Huelsman - Initial implementation.
 *******************************************************************************/
package com.joshuahuelsman.pockettool;

import java.io.File;


/**
 * QuickPatch class. Placeholder for QuickPatch format support.
 * This class will contain a parser, loader, and patching
 * function specific to QuickPatch. 
 * NOTE: Class must comply with current standards of
 * QuickPatch. Including on-the-flying options for 
 * patches.
 * @author Joshua Huelsman
 *
 */
public class QuickPatch {
	
	
	public static QuickPatch load(File file)
	{
		return null;
	}
	
	private void parse(byte[] data)
	{
		
	}
	
	public enum QPTag {
		TITLE("title", QPTagType.STRING),
		VERSION("version", QPTagType.NUMBER),
		AUTHOR("author", QPTagType.STRING),
		TARGET("target", QPTagType.STRING),
		ROOT("root", QPTagType.BOOLEAN),
		INITIAL("initial", QPTagType.OBJECT),
		OPTIONS("options", QPTagType.OBJECT);
		
		private String name;
		private QPTagType type;
		QPTag(String name, QPTagType type)
		{
			this.name = name;
			this.type = type;
		}
		
		public String getName() {
			return name;
		}
		
		public QPTagType getType() {
			return type;
		}
		
		public enum QPTagType {
			STRING,
			NUMBER,
			ARRAY,
			BOOLEAN,
			OBJECT
		}
	}
}
