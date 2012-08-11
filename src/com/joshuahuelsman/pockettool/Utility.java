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

public class Utility {
	public static long byteArrayToLong(byte[] b){
		
		return  + ((b[4] & 0xFF) << 32)
				+ ((b[3] & 0xFF) << 24)
                + ((b[2] & 0xFF) << 16)
                + ((b[1] & 0xFF) << 8)
                + (b[0] & 0xFF);
	}
	
	public static byte[] switchByteOrder(byte[] b){
		byte[] ret = new byte[b.length];
		
		for(int i = 1; i <= b.length; i++){
			ret[i - 1] = b[b.length - i];
		}
		
		return ret;
	}
	
	public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
	}

	public static final int byteArrayToInt(byte [] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
	}
}
