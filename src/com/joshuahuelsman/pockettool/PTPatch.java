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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import android.content.Context;
import android.widget.Toast;

public class PTPatch {
	public final static byte[] magic = { (byte) 0xff, 0x50, 0x54, 0x50 };
	public final static byte[] op_codes = { (byte) 0xaa, (byte) 0xdd, (byte) 0xee };
	private byte[] patch_array;
	private int count;
	private String location;
	public String name;
	private Context con;
	Header mHeader;
	public PTPatch(Context c, String loc){
		con = c;
		location = loc;
		mHeader = new Header();
	}
	class Header {
		byte[] magic = new byte[4];
		int minecraft_ver;
		int num_patches;
		byte[] indices;
	}
	
	public void loadPatch() throws IOException{
		File patchf = new File(location);
		patch_array = new byte[(int) patchf.length()];
		InputStream is = new FileInputStream(patchf);
		is.read(patch_array);
		is.close();
		mHeader.minecraft_ver = getMinecraftVersion();
		mHeader.num_patches = getNumPatches();
		mHeader.indices = getIndices();
		count = 0;
	}
	
	public int getMinecraftVersion(){
		return patch_array[4];
	}
	
	public int getNumPatches(){
		return patch_array[5];
	}
	
	public byte[] getIndices(){
		byte[] ret = new byte[mHeader.num_patches * 4];
		for(int i = 0; i < (mHeader.num_patches * 4); i++){
			ret[i] = patch_array[i + 6];
		}
		return ret;
	}
	
	public void checkMagic(){
		if(patch_array[0] == magic[0]){
			if(patch_array[1] == magic[1]){
				if(patch_array[2] == magic[2]){
					if(patch_array[3] == magic[3]){
						Toast.makeText(con, R.string.magic_ok, Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
		}
		Toast.makeText(con, R.string.magic_error, Toast.LENGTH_SHORT).show();
	}
	
	public void checkMinecraftVersion(){
		if(patch_array[4] == (byte)APKManipulation.minever){
			//Toast.makeText(con, "Patch Compatible!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//Toast.makeText(con, "Patch is Incompatible!", Toast.LENGTH_SHORT).show();
	}
	
	public int getNextAddr(){
		byte[] i = new byte[4];
		i[0] = mHeader.indices[(count*4)];
		i[1] = mHeader.indices[(count*4)+1];
		i[2] = mHeader.indices[(count*4)+2];
		i[3] = mHeader.indices[(count*4)+3];
		
		int index = byteArrayToInt(i);
		
		byte[] b = new byte[4];
		b[0] = patch_array[index];
		b[1] = patch_array[index + 1];
		b[2] = patch_array[index + 2];
		b[3] = patch_array[index + 3];
		return byteArrayToInt(b);
	}
	
	public int getCurrentIndex(){
		byte[] i = new byte[4];
		i[0] = mHeader.indices[(count*4)];
		i[1] = mHeader.indices[(count*4)+1];
		i[2] = mHeader.indices[(count*4)+2];
		i[3] = mHeader.indices[(count*4)+3];
		
		int index = byteArrayToInt(i);
		return index;
	}
	
	public byte[] getNextData(){
		byte[] array = new byte[getDataLength()];
		int index = getCurrentIndex();
		int i;
		int i2 = 0;
		for( i = 0; i < getDataLength(); i++){
			array[i2] = patch_array[i + (index + 4)];
			i2++;
		}
		
		return array;
	}
	
	public int getDataLength(){
		int start = 0;
		int end = 0;
		byte[] i = new byte[4];
		i[0] = mHeader.indices[(count*4)];
		i[1] = mHeader.indices[(count*4)+1];
		i[2] = mHeader.indices[(count*4)+2];
		i[3] = mHeader.indices[(count*4)+3];
		
		if(count != (mHeader.num_patches - 1)){
			byte[] i2 = new byte[4];
			i2[0] = mHeader.indices[((count+1)*4)];
			i2[1] = mHeader.indices[((count+1)*4)+1];
			i2[2] = mHeader.indices[((count+1)*4)+2];
			i2[3] = mHeader.indices[((count+1)*4)+3];
			end = byteArrayToInt(i2);
		}else{
			end = patch_array.length;
		}
		start = (byteArrayToInt(i) + 4);
		
		return (end - start);
		
	}
	
	public void applyPatch(File f) throws IOException{
		
		byte[] barray = new byte[(int) f.length()];
		
		InputStream is = new FileInputStream(f);
		is.read(barray);
		is.close();
		ByteBuffer buf = ByteBuffer.wrap(barray);
		for(count = 0; count < mHeader.num_patches; count++){
			buf.position(getNextAddr());
			buf.put(getNextData());
		}
		
		f.delete();
		OutputStream os = new FileOutputStream(f);
		os.write(buf.array());
		os.close();
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
	
	public static byte[] readPatch(String patch)
			throws IOException {
		File patchf = new File(patch);
		byte[] ret = new byte[(int) patchf.length()];
		InputStream is = new FileInputStream(patch);
		is.read(ret, 0, ret.length);
		is.close();
		return ret;
	}
	
	public static void diff(String oldf, String newf, File out) throws IOException{
		byte[] oldData = readPatch(oldf);
		byte[] newData = readPatch(newf);
		
		if(oldData.length != newData.length){
			//System.out.println("Error: The new file's length does not match the old file's length. Aborting...");
			return;
		}
		
		out.delete();
		OutputStream os = new FileOutputStream(out);
		writeMagic(os);
		writeVersionCode(0, os);
		
		byte[][] patchData;
		
		int numPatches = 0;
		for(int i = 0; i < oldData.length; i++){
			if(oldData[i] != newData[i]){
				numPatches++;
				while(oldData[i] != newData[i]){
					i++;
				}
				
			}
		}
		System.out.println("Number of Patches: " + numPatches);
		writeNumberPatches(numPatches, os);
		patchData = new byte[numPatches][];
		int index = 0;
		int[] address = new int[numPatches];
		for(int i2 = 0; i2 < numPatches; i2++){
			for(int i = index; i < oldData.length; i++){
				if(oldData[i] != newData[i]){
					address[i2] = i;
					int i3 = 0;
					while(oldData[i] != newData[i]){
						i++;
						i3++;
					}
					patchData[i2] = new byte[i3 + 4];
					System.out.println("Length of patch " + i2 + ": " + (i3 + 4));
					index = i;
					
					break;
				}
			}
		}
		
		index = 0;
		for(int i2 = 0; i2 < numPatches; i2++){
			
			for(int i = index; i < oldData.length; i++){
				if(oldData[i] != newData[i]){
					
					byte[] addr = intToByteArray(i);
					patchData[i2][0] = addr[0];
					patchData[i2][1] = addr[1];
					patchData[i2][2] = addr[2];
					patchData[i2][3] = addr[3];
					while(oldData[i] != newData[i]){
						i++;
					}
					index = i;
					break;
				}
			}
		}
		
		for(int i2 = 0; i2 < numPatches; i2++){
			for(int i = 0; i < (patchData[i2].length - 4); i++){
				patchData[i2][i + 4] = newData[address[i2] + i];
			}
		}
		
		os.write((generateIndices(patchData)));
		for(int i = 0; i < numPatches; i++){
			os.write(patchData[i]);
		}
		os.close();
		
	}
	
	public static byte[] generateIndices(int num, byte[][] patchData) {
		byte[] ret = new byte[num * 4];
		int headerSize = (6 + (num * 4));

		int bloat = 0;

		for (int i = 0; i < num; i++) {
			int temp = headerSize;
			byte[] data = new byte[4];
			if (i == 0) {
				bloat += patchData[i].length - 5;
				data = intToByteArray(temp);
				ret[i * 4] = data[0];
				ret[(i * 4) + 1] = data[1];
				ret[(i * 4) + 2] = data[2];
				ret[(i * 4) + 3] = data[3];
			} else {
				temp += bloat;
				data = intToByteArray(temp);
				ret[i * 4] = data[0];
				ret[(i * 4) + 1] = data[1];
				ret[(i * 4) + 2] = data[2];
				ret[(i * 4) + 3] = data[3];
				bloat += patchData[i].length - 5;
			}
		}
		
		
		
		return ret;
	}
	
	public static byte[] generateIndices(byte[][] patchData) {
		byte[] ret = new byte[patchData.length * 4];
		int headerSize = (6 + (patchData.length * 4));

		int bloat = 0;

		for (int i = 0; i < patchData.length; i++) {
			int temp = headerSize;
			byte[] data = new byte[4];
			if (i == 0) {
				bloat += patchData[i].length;
				data = intToByteArray(temp);
				ret[i * 4] = data[0];
				ret[(i * 4) + 1] = data[1];
				ret[(i * 4) + 2] = data[2];
				ret[(i * 4) + 3] = data[3];
			} else {
				temp += bloat;
				data = intToByteArray(temp);
				ret[i * 4] = data[0];
				ret[(i * 4) + 1] = data[1];
				ret[(i * 4) + 2] = data[2];
				ret[(i * 4) + 3] = data[3];
				bloat += patchData[i].length;
			}
		}
		
		
		
		return ret;
	}
	public static void writeMagic(OutputStream os) throws IOException {
		byte[] magic = { (byte) 0xFF, 0x50, 0x54, 0x50 };
		os.write(magic);
	}

	public static void writeVersionCode(int vc, OutputStream os)
			throws IOException {
		os.write(vc);
	}

	public static void writeNumberPatches(int num, OutputStream os)
			throws IOException {
		os.write(num);
	}
}
