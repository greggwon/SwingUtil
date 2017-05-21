package org.wonderly.swing;

import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

/**
<pre>
Copyright (c) 1997-2006, Gregg Wonderly
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
    * The name of the author may not be used to endorse or promote
      products derived from this software without specific prior
      written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
</pre>
 *  An Icon object for use on JToolBar components.
 *
 *  @author Gregg Wonderly <a href="mailto:gregg.wonderly@pobox.com">gregg.wonderly@pobox.com</a>
 */
public class ToolIcon extends ImageIcon {
	static int sz = 2;
	Icon icon;
	public ToolIcon( URL u ) {
		super(u);
		if( super.getIconWidth() > sz )
			sz = super.getIconWidth();
		if( super.getIconHeight() > sz )
			sz = super.getIconHeight();
	}
	public int getIconWidth() {
		return sz;
	}
	public int getIconHeight() {
		return sz;
	}
	public void paintIcon( Component c, Graphics g, int x, int y ) {
//			new Throwable().printStackTrace();
		int cx = x;
		int cy = y;
		int hx = super.getIconWidth();
		int hy = super.getIconHeight();
		if( hx < sz )
			cx += (sz - hx)/2;
		if( hy < sz )
			cy += (sz - hy)/2;
		super.paintIcon( c, g, cx, cy );
	}
}