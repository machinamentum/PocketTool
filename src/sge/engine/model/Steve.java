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

/* 
 * SGE is a small rendering/game engine implementation I built for PocketTool.
 * It was never finished.
 * Many of its classes may or may not be implemented.
 * 
 */
package sge.engine.model;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.joshuahuelsman.pockettool.APKManipulation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Steve extends Model {
	Bitmap skin;
	Bitmap face;
	private float head[] = {
			
			1f, 2f, 1f,//front
			1f, 0f, 1f,
			-1f, 0f, 1f,
			-1f, 2f, 1f,
			
			1f, 2f, -1f,//back
			1f, 0f, -1f,
			-1f, 0f, -1f,
			-1f, 2f, -1f,
			
			
			1f,2f, 1f, //left side
			1f,0f, 1f,
			1f,0f, -1f,
			1f,2f, -1f,
			
			-1f,2f, -1f, //right side
			-1f,0f, -1f,
			-1f,0f, 1f,
			-1f,2f, 1f, 
			
			
			1f, 2f, -1f,//top
			1f, 2f, 1f,
			-1f, 2f, 1f,
			-1f, 2f, -1f,
			
			
			1f, 0f, -1f,//bottom
			1f, 0f, 1f,
			-1f, 0f, 1f,
			-1f, 0f, -1f,
			
			
			
			//torso
			1f, 0f, 0.5f,//front
			1f, -3f, 0.5f,
			-1f, -3f, 0.5f,
			-1f, 0f, 0.5f,
			
			1f, 0f, -0.5f,//back
			1f, -3f, -0.5f,
			-1f, -3f, -0.5f,
			-1f, 0f, -0.5f,
			
			-1f,0f, -0.5f, //right side
			-1f,-3f, -0.5f,
			-1f,-3f, 0.5f,
			-1f,0f, 0.5f, 
			
			1f,0f, 0.5f, //left side
			1f,-3f, 0.5f,
			1f,-3f, -0.5f,
			1f,0f, -0.5f, 
			
			1f, 0f, -0.5f,//top
			1f, 0f, 0.5f,
			-1f, 0f, 0.5f,
			-1f, 0f, -0.5f,
			
			1f, -3f, -0.5f,//top
			1f, -3f, 0.5f,
			-1f, -3f, 0.5f,
			-1f, -3f, -0.5f,
		};
		
		private byte indices[] = {
				//head
				0,1,2, 2,3,0, //front
				4,7,6, 6,5,4, //back
				10,9,8, 8,11,10, //left side
				12,15,14,14,13,12, //right
				16,17,18, 18,19,16,//top
				20,23,22, 22,21,20, //bottom
				
				//torso
				24,25,26, 26,27,24,//front
				28,31,30, 30,29,28,//back
				32,35,34, 34,33,32,//right side
				38,37,36, 36,39,38,//left side
				40,41,42, 42,43,40,//top
				44,47,46, 46,45,44,//bottom
		};
		
		FloatBuffer vfBuff;
		ByteBuffer isBuff;
		
		int[] textures = new int[1];
		
		float[] textCoords = {
				
				
				//head
				0.25f, 0.25f,//front
				0.25f, 0.5f,
				0.125f, 0.5f,
				0.125f,0.25f,
				
				0.5f, 0.25f,//back
				0.5f, 0.5f,
				0.375f, 0.5f,
				0.375f,0.25f,
			
				0.25f,0.25f,//left
				0.25f, 0.5f,
				0.375f, 0.5f,
				0.375f, 0.25f,
				
				0f,0.25f,//right
				0f, 0.5f,
				0.125f, 0.5f,
				0.125f, 0.25f,
			
				0.25f, 0.0f,//top
				0.25f, 0.25f,
				0.125f, 0.25f,
				0.125f,0.0f,
			
				0.375f, 0.0f,//bottom
				0.375f, 0.25f,
				0.25f, 0.25f,
				0.25f,0.0f,
				
				//torso
				0.4375f, 0.625f,//front
				0.4375f, 1f,
				0.3125f, 1f,
				0.3125f, 0.625f,
				
				0.5f, 0.625f,//back
				0.5f, 1f,
				0.625f, 1f,
				0.625f, 0.625f,
				
				0.25f, 0.625f,//right side
				0.25f, 1f,
				0.3125f, 1f,
				0.3125f, 0.625f,
				
				0.4375f, 0.625f,//left side
				0.4375f, 1f,
				0.5f, 1f,
				0.5f, 0.625f,
				
				0.4375f, 0.5f,//top
				0.4375f, 0.625f,
				0.3125f, 0.625f,
				0.3125f, 0.5f,
				
				0.5625f, 0.5f,//top
				0.5625f, 0.625f,
				0.4375f, 0.625f,
				0.4375f, 0.5f,
		};
		
		FloatBuffer textureBuffer;
		
		boolean initialize;
		
		public Steve(){
			ByteBuffer vBuff = ByteBuffer.allocateDirect(head.length * 4);
			vBuff.order(ByteOrder.nativeOrder());
			vfBuff = vBuff.asFloatBuffer();
			vfBuff.put(head);
			vfBuff.position(0);
			
			ByteBuffer iBuff = ByteBuffer.allocateDirect(indices.length * 2);
			iBuff.order(ByteOrder.nativeOrder());
			isBuff = iBuff;
			isBuff.put(indices);
			isBuff.position(0);
			
			ByteBuffer byteBuf = ByteBuffer.allocateDirect(textCoords.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			textureBuffer = byteBuf.asFloatBuffer();
			textureBuffer.put(textCoords);
			textureBuffer.position(0);
			loadDefaultTexture();
			initialize = true;
		}
		
		public void loadDefaultTexture(){
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			File texture = new File(APKManipulation.ptdir, "/temp/assets/mob/char.png");
			skin = BitmapFactory.decodeFile(texture.getAbsolutePath());
			//face = Bitmap.createBitmap(skin, 8, 8,8, 8);
			initialize = true;
		}
		
		@Override 
		public void draw(GL10 gl){
			
			super.draw(gl);
			if(initialize){
				gl.glDeleteTextures(1, textures, 0);
				gl.glGenTextures(1, textures, 0);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
		                   GL10.GL_TEXTURE_MAG_FILTER,
		                   GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
		                   GL10.GL_TEXTURE_MIN_FILTER,
		                   GL10.GL_LINEAR);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
						GL10.GL_REPEAT);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
						GL10.GL_REPEAT);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, skin, 0);
				skin.recycle();
				initialize = false;
			}
			
			
			gl.glFrontFace(GL10.GL_CW);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// Tell OpenGL where our texture is located.
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			// Tell OpenGL to enable the use of UV coordinates.
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			// Telling OpenGL where our UV coordinates are.
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vfBuff);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, isBuff);
			gl.glDisable(GL10.GL_CULL_FACE);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			// Disable the use of textures.
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
}
