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

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Links extends Activity {
	
	private static final String TEXTURE_PACK_CENTRAL =
	"<a href=\"http://www.minecraftforum.net/topic/1059909-pocket-edition-texturepack-central\">Pocket Edition Texture Pack Central</a>";
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.links);
		
		TextView tv = (TextView)findViewById(R.id.linkstext);
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.append(Html.fromHtml(TEXTURE_PACK_CENTRAL));
	}
}
