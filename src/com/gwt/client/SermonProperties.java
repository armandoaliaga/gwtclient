package com.gwt.client;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface SermonProperties extends PropertyAccess<Sermon>{
	  @Path("id")
	  ModelKeyProvider<Sermon> key();
	   		
	  ValueProvider<Sermon, String> name();
	   
	  ValueProvider<Sermon, String> name_of_predicador();
	   
	  ValueProvider<Sermon, String> description();
	   
	  ValueProvider<Sermon, String> serie();
	   
	  ValueProvider<Sermon, Integer> duration();
	   
	  ValueProvider<Sermon, String> date();
	 
}
