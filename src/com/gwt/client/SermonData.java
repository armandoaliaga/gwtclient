package com.gwt.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SermonData  extends JavaScriptObject {
	protected SermonData() {}                                              

	  public final native int getId() /*-{ return this.id; }-*/; 
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getName_of_predicador() /*-{ return this.name_of_predicador; }-*/;
	  public final native String getDescripcion() /*-{ return this.descripcion; }-*/;
	  public final native String getSerie() /*-{ return this.serie; }-*/;
	  public final native int getDurationSeg() /*-{ return this.durationseg; }-*/;	
	  public final native String getFecha() /*-{ return this.fecha; }-*/;
}
