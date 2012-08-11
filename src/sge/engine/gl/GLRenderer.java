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
package sge.engine.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import sge.engine.node.Node;
import sge.engine.realism.SGEActivity;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public class GLRenderer implements Renderer {
	
	private Node rootNode = null;
	private sge.engine.realism.SGEActivity realism;
	public GLRenderer(SGEActivity realismAct){
		realism = realismAct;
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 2, 0);
		
		realism.update();
		if(rootNode != null){
			rootNode.draw(gl);
		}
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float ratio = (float)width/height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
		gl.glClearColor(0.0f, 0.9f, 0.9f, 1f);
		gl.glClearDepthf(1f);
		
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}
	
	public void setRootNode(Node node){
		rootNode = node;
	}
}
