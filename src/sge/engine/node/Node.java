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
package sge.engine.node;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

import sge.engine.model.Model;

public class Node {
	private ArrayList<Node> children;
	private ArrayList<Model> models;
	public Node(){
		children = new ArrayList<Node>();
		models = new ArrayList<Model>();
	}
	
	public void attach(Model model){
		models.add(model);
	}
	
	public void attach(Node child){
		children.add(child);
	}
	
	
	private void drawChildren(GL10 gl){
		for(int i = 0; i < children.size(); i++){
			children.get(i).draw(gl);
		}
	}
	
	public void draw(GL10 gl){
		for(int i = 0; i < models.size(); i++){
			models.get(i).draw(gl);
		}
		drawChildren(gl);
	}
	
	public void rotate(float angle, float x, float y, float z){
		for(int i = 0; i < models.size(); i++){
			models.get(i).rotate(angle, x, y, z);
		}
		
	}
}
