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

import javax.microedition.khronos.opengles.GL10;

public class Model {
	protected int USE_ROTATION = 0;
	protected int USE_TRANSLATION = 0;
	protected float rot_angle;
	protected float rot_x;
	protected float rot_y;
	protected float rot_z;
	protected float trans_x;
	protected float trans_y;
	protected float trans_z;
	public Model(){
		
	}
	
	
	public void draw(GL10 gl){
		gl.glLoadIdentity();
		if(USE_TRANSLATION == 1){
			gl.glTranslatef(trans_x, trans_y, trans_z);
		}
		if(USE_ROTATION == 1){
			gl.glRotatef(rot_angle, rot_x, rot_y, rot_z);
		}
		
	}
	
	public void rotate(float angle, float x, float y, float z){
		USE_ROTATION = 1;
		rot_angle = angle;
		rot_x = x;
		rot_y = y;
		rot_z = z;
	}
	
	public void translate(float x, float y, float z){
		USE_TRANSLATION = 1;
		trans_x = x;
		trans_y = y;
		trans_z = z;
	}
}
