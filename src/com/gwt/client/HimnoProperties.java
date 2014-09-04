package com.gwt.client;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface HimnoProperties extends PropertyAccess<Himno> {
	  @Path("id")
	  ModelKeyProvider<Himno> key();
	  
	  ValueProvider<Himno, Integer> number();
	   		
	  ValueProvider<Himno, String> name();
	   
	  ValueProvider<Himno, String> lyrics();
	  
	  ValueProvider<Himno, String> aux();
	   
}
