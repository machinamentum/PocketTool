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

class Skin {
	public Skin(String n, String p, boolean ex){
		name = n.substring(0, n.length()-4);
		path = p;
		extrn = ex;
	}
	String name;
	String path;
	boolean extrn;
}