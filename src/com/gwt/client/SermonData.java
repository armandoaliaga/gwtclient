package com.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SermonData  extends JavaScriptObject {
	protected SermonData() {}                                              

	  public final native String getId() /*-{ return this.id; }-*/; 
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getName_of_predicador() /*-{ return this.name_of_predicador; }-*/;
}
