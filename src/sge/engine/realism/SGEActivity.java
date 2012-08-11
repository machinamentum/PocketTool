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
package sge.engine.realism;

import sge.engine.gl.GLView;
import sge.engine.model.Steve;
import sge.engine.node.Node;
import sge.engine.shapes.Cube;
import sge.engine.shapes.Triangle;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class SGEActivity extends Activity {
    /** Called when the activity is first created. */
	protected Node rootNode;
	Cube cube;
	Triangle tri;
	Steve steve;
	protected GLView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //LinearLayout lay = (LinearLayout)findViewById(R.id.linlay);
        view  = new GLView(this);
        //lay.addView(view);
        //setContentView(view);
        
        rootNode = new Node();
        steve = new Steve();
        rootNode.attach(steve);
        view.setRootNode(rootNode);
        //update();
    }
    float ang= 10;
    public void update(){ 
    	ang+=0.5f;
    	steve.translate(0, 0f, -6f);
    	steve.rotate(ang, 0, 1, 0);
    }
    
    public void useNewSkin(){
    	steve.loadDefaultTexture();
    }
    
}