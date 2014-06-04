package com.gwt.client;

import com.gwt.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.URL;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private static final String JSON_URL = GWT.getModuleBaseURL() + "sermones";
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		final TextBox asdf = new TextBox();
		String url = URL.encode(JSON_URL);		
		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		    try {
		    	Request request = builder.sendRequest(null, new RequestCallback() {
		        @Override
				public void onError(Request request, Throwable exception) {
		         // displayError("Couldn't retrieve JSON");
		        	 asdf.setText("me cago 1!");
		        }

		        @Override
				public void onResponseReceived(Request request, Response response) {
		          if (200 == response.getStatusCode()) {
		        	  JsArray<SermonData> a= JsonUtils.safeEval(response.getText());
		        	  asdf.setText(String.valueOf(a.get(0).getName_of_predicador()));		        	  
		          } else {
		            //displayError("Couldn't retrieve JSON (" + response.getStatusText()+ ")");
		        	  asdf.setText("me cago2" + response.getStatusText());
		          }
		        }
		      });
		    } catch (RequestException e) {
		      //displayError("Couldn't retrieve JSON");
		    	 asdf.setText("me cago 3!");
		    }
						
		    
		    
		 // Create a tree with a few items in it.
		    TreeItem root = new TreeItem();
		    root.setText("root");
		    root.addTextItem("item0");
		    root.addTextItem("item1");
		    root.addTextItem("item2");

		    // Add a CheckBox to the tree
		    TreeItem item = new TreeItem(new CheckBox("item3"));
		    root.addItem(item);

		    Tree t = new Tree();
		    t.addItem(root);

		    // Add it to the root panel.
		    RootPanel.get().add(t);
		    
		RootPanel.get("nameFieldContainer").add(asdf);
		
	}
}

