package com.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;

public class HimnoData extends JavaScriptObject{
	protected HimnoData() {}                                              

	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native int getNumber() /*-{ return this.number; }-*/; 
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getLyrics() /*-{ return this.lyrics; }-*/;	
	  public final native String getShareableUrl() /*-{ return this.shareableURL; }-*/;
}
