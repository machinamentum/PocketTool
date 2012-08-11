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
package sge.engine.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import sge.engine.model.Model;



public class Cube extends Model {
		private float vertices[] = {
			1f, 1f, -1f,
			1f, -1f, -1f,
			-1f, -1f, -1f,
			-1f, 1f, -1f,
			
			1f, 1f, 1f,
			1f, -1f, 1f,
			-1f, -1f, 1f,
			-1f, 1f, 1f
		};
		
		private short indices[] = {
				3,4,0,  0,4,1,  3,0,1,
				3,7,4,  7,6,4,  7,3,6,
				3,1,2,  1,6,2,  6,3,2,
				1,4,5,  5,6,1,  6,5,4
		};
		
		FloatBuffer vfBuff;
		ShortBuffer isBuff;
		
		public Cube(){
			ByteBuffer vBuff = ByteBuffer.allocateDirect(vertices.length * 4);
			vBuff.order(ByteOrder.nativeOrder());
			vfBuff = vBuff.asFloatBuffer();
			vfBuff.put(vertices);
			vfBuff.position(0);
			
			ByteBuffer iBuff = ByteBuffer.allocateDirect(indices.length * 2);
			iBuff.order(ByteOrder.nativeOrder());
			isBuff = iBuff.asShortBuffer();
			isBuff.put(indices);
			isBuff.position(0);
		}
		
		@Override 
		public void draw(GL10 gl){
			super.draw(gl);
			gl.glFrontFace(GL10.GL_CW);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glCullFace(GL10.GL_BACK);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vfBuff);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, isBuff);
			gl.glDisable(GL10.GL_CULL_FACE);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		
}
