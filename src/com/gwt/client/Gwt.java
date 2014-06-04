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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.URL;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;



import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt implements IsWidget, EntryPoint {
	
	
	private static final String JSON_URL = GWT.getModuleBaseURL() + "sermones";
	
	
	
	
	private String button1Text = "Button 1";
	  private String button2Text = "Button 2";
	  private String button3Text = "Button 3";
	  private String button4Text = "Button 4";
	  private ContentPanel lccenter;
	  private ToggleGroup toggleGroup = new ToggleGroup();
	  private ScrollPanel con;
	 
	  @Override
	  public Widget asWidget() {
	    if (con == null) {
	      con = new ScrollPanel();
	      con.setLayoutData(new MarginData(10));
	 
	      ContentPanel panel = new ContentPanel();
	      panel.setHeadingText("Help Church Menu");
	      panel.setPixelSize(1200, 600);
	 
	      BorderLayoutContainer border = new BorderLayoutContainer();
	      panel.setWidget(border);
	 
	      VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
	      lcwest.addStyleName("x-toolbar-mark");
	      lcwest.setPadding(new Padding(5));
	      lcwest.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
	 
	      BorderLayoutData west = new BorderLayoutData(150);
	      west.setMargins(new Margins(5));
	 
	      border.setWestWidget(lcwest, west);
	 
	      lccenter = new ContentPanel();
	      lccenter.setHeaderVisible(false);
	      lccenter.add(new HTML(
	          "<p style=\"padding:10px;color:#556677;font-size:11px;\">Select a configuration on the left</p>"));
	 
	      MarginData center = new MarginData(new Margins(5));
	 
	      border.setCenterWidget(lccenter, center);
	 
	      BoxLayoutData vBoxData = new BoxLayoutData(new Margins(5, 5, 5, 5));
	      vBoxData.setFlex(1);
	 
	      lcwest.add(createToggleButton("Sermones", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
	 	        
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            flex.setFlex(1);
	            c.add(new Label(), flex);
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Multi-Spaced", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            flex.setFlex(1);
	            c.add(new Label(), flex);
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new Label(), flex);
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new Label(), flex);
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Align: top", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Align: middle", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Align: bottom", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.BOTTOM);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Align: stretch", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	 
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Align: stretchmax", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCHMAX);
	 
	            c.add(new TextButton(button1Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button2Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button3Text), new BoxLayoutData(new Margins(0, 5, 0, 0)));
	            c.add(new TextButton(button4Text), new BoxLayoutData(new Margins(0)));
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Flex: All even", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	 
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	 
	            BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            flex.setFlex(1);
	            c.add(new TextButton(button1Text), flex);
	            c.add(new TextButton(button2Text), flex);
	            c.add(new TextButton(button3Text), flex);
	 
	            BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
	            flex2.setFlex(1);
	            c.add(new TextButton(button4Text), flex2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Flex: ratio", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	 
	            BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            flex.setFlex(1);
	            c.add(new TextButton(button1Text), flex);
	            c.add(new TextButton(button2Text), flex);
	            c.add(new TextButton(button3Text), flex);
	 
	            BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
	            flex2.setFlex(3);
	            c.add(new TextButton(button4Text), flex2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Flex + Stretch", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
	 
	            BoxLayoutData flex = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            flex.setFlex(1);
	            c.add(new TextButton(button1Text), flex);
	            c.add(new TextButton(button2Text), flex);
	            c.add(new TextButton(button3Text), flex);
	 
	            BoxLayoutData flex2 = new BoxLayoutData(new Margins(0));
	            flex2.setFlex(3);
	            c.add(new TextButton(button4Text), flex2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Pack: start", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	            c.setPack(BoxLayoutPack.START);
	 
	            BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            c.add(new TextButton(button1Text), layoutData);
	            c.add(new TextButton(button2Text), layoutData);
	            c.add(new TextButton(button3Text), layoutData);
	 
	            BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));
	            c.add(new TextButton(button4Text), layoutData2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Pack: center", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	            c.setPack(BoxLayoutPack.CENTER);
	 
	            BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            c.add(new TextButton(button1Text), layoutData);
	            c.add(new TextButton(button2Text), layoutData);
	            c.add(new TextButton(button3Text), layoutData);
	 
	            BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));
	            c.add(new TextButton(button4Text), layoutData2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      lcwest.add(createToggleButton("Pack: end", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	            HBoxLayoutContainer c = new HBoxLayoutContainer();
	            c.setPadding(new Padding(5));
	            c.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
	            c.setPack(BoxLayoutPack.END);
	 
	            BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 5, 0, 0));
	            c.add(new TextButton(button1Text), layoutData);
	            c.add(new TextButton(button2Text), layoutData);
	            c.add(new TextButton(button3Text), layoutData);
	 
	            BoxLayoutData layoutData2 = new BoxLayoutData(new Margins(0));
	            c.add(new TextButton(button4Text), layoutData2);
	 
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	 
	      con.add(panel);
	    }
	    return con;
	  }
	 
	  private void addToCenter(Widget c) {
	    lccenter.clear();
	    lccenter.add(c);
	    lccenter.forceLayout();
	  }
	 
	  private ToggleButton createToggleButton(String name, ValueChangeHandler<Boolean> valueChangeHandler) {
	    ToggleButton button = new ToggleButton(name);
	    toggleGroup.add(button);
	    button.addValueChangeHandler(valueChangeHandler);
	    button.setAllowDepress(false);
	    return button;
	  }
	
	
	
	public void onModuleLoad() {
	
		
		/*final TextBox asdf = new TextBox();
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
		    
		RootPanel.get("nameFieldContainer").add(asdf);*/
		
		
		/*TextButton textButton = new TextButton("Verify GXT Works");
		RootPanel.get().add(textButton);
		textButton.addSelectHandler(new SelectHandler() {
		  @Override
		  public void onSelect(SelectEvent event) {
		    MessageBox messageBox = new MessageBox("GXT Works.");
		    messageBox.show();
		  }
		});*/
		 RootPanel.get("layout").add(asWidget());
	}
}

