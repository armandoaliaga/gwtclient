package com.gwt.client;


import java.awt.Panel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.core.java.util.Collections;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.http.client.URL;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;


import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ibm.icu.text.MessagePatternUtil.ComplexArgStyleNode;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
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
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sun.java.swing.plaf.windows.resources.windows;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt implements IsWidget, EntryPoint {	
	private ArrayList<Sermon> sermonesgrid=null;	
	private ArrayList<Himno> himnosgrid=null;
	private static final String JSON_URL = GWT.getModuleBaseURL() + "sermones";
	private static final String JSON_URL_HIMNOS = GWT.getModuleBaseURL() + "himnos";
	final LoginServiceAsync loginservice= GWT.create(LoginService.class);

	  private ContentPanel lccenter;
	  private ToggleGroup toggleGroup = new ToggleGroup();	 
	  private ScrollPanel con;
	  private int cont=1;
	 
	  @Override
	  public Widget asWidget() {
	    if (con == null) {	    
	    cont=1;
	      con = new ScrollPanel();
	      con.setLayoutData(new MarginData(1));
	 
	      ContentPanel panel = new ContentPanel();
	      panel.setHeadingText("Help Church Menu");
	      panel.setPixelSize(1365, 633);
	 
	      BorderLayoutContainer border = new BorderLayoutContainer();
	      panel.setWidget(border);
	 
	      VBoxLayoutContainer lcwest = new VBoxLayoutContainer();
	      lcwest.addStyleName("x-toolbar-mark");
	      lcwest.setPadding(new Padding(5));
	      lcwest.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
	 
	      BorderLayoutData west = new BorderLayoutData(200);
	      west.setMargins(new Margins(5));
	 
	      border.setWestWidget(lcwest, west);
	 
	      lccenter = new ContentPanel();
	      lccenter.setHeaderVisible(false);
	      lccenter.add(new HTML(
	          "<h3 style=\"padding:10px;color:#556677;font-size:20px;\">Bienvenido seleccione su opcion</h3>"));
	 
	      MarginData center = new MarginData(new Margins(5));
	 
	      border.setCenterWidget(lccenter, center);
	 
	      BoxLayoutData vBoxData = new BoxLayoutData(new Margins(5, 5, 5, 5));
	      vBoxData.setFlex(8);
	 
	      lcwest.add(createToggleButton("Sermones", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	        	  final HBoxLayoutContainer c = new HBoxLayoutContainer();
		            c.setPadding(new Padding(5));
		            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		            c.setPack(BoxLayoutPack.CENTER);		            		            		            
		            
		            sermonesgrid = new ArrayList<>();
		            // llamada para recuperar los sermones
		            		      
		    		String url = URL.encode(JSON_URL);		
		    		 RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		    		    try {
		    		    	Request request = builder.sendRequest(null, new RequestCallback() {
		    		        @Override
		    				public void onError(Request request, Throwable exception) {
		    		         // displayError("Couldn't retrieve JSON");		    		        	
		    		        }

		    		        @Override
		    				public void onResponseReceived(Request request, Response response) {
		    		          if (200 == response.getStatusCode()) {
		    		        	  JsArray<SermonData> sermones= JsonUtils.safeEval(response.getText());		    		        	  		    		        	 		    		        	   		 		
		    		  		    
		    		        	  for (int i = 0; i < sermones.length(); i++) {		    		        		 
		    		        		  Sermon nsermon =new Sermon(sermones.get(i).getId(), sermones.get(i).getName(), sermones.get(i).getName_of_predicador(), sermones.get(i).getDescripcion(), sermones.get(i).getSerie(), sermones.get(i).getDurationSeg(),  sermones.get(i).getFecha(), sermones.get(i).getShareableUrl());
		    		        		  sermonesgrid.add(nsermon);		    		        		  
		    		        	    }
		    		        	  RowExpanderGrid a=new RowExpanderGrid(sermonesgrid);
		    			            c.add(a.asWidget());
		    		          } else {
		    		            //displayError("Couldn't retrieve JSON (" + response.getStatusText()+ ")");		    		        	  
		    		          }
		    		        }
		    		      });
		    		    } catch (RequestException e) {
		    		      //displayError("Couldn't retrieve JSON");		    		 
		    		    }		
		    		    
		    		    final AutoProgressMessageBox box = new AutoProgressMessageBox("En progreso", "Recuperando sermones, aguarde por favor...");
		    	          box.setProgressText("Cargando...");
		    	          box.auto();
		    	          box.show();
		    	         Timer t = new Timer() {
			    	            @Override
			    	            public void run() {		    	            	
			    	              Info.display("Mensaje", "Sermones cargados con exito!");
			    	              box.hide();
			    	            }
			    	          };
			    	          t.schedule(3000);   
	            addToCenter(c);
	          }
	        }
	      }), vBoxData);
	      
	      
	      lcwest.add(createToggleButton("Himnos", new ValueChangeHandler<Boolean>() {
		        @Override
		        public void onValueChange(ValueChangeEvent<Boolean> event) {
		          if (event.getValue()) {
		        	final HBoxLayoutContainer c = new HBoxLayoutContainer();
		            c.setPadding(new Padding(5));
		            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		            c.setPack(BoxLayoutPack.CENTER);
		 	        himnosgrid = new ArrayList<>();   
		 	        String url = URL.encode(JSON_URL_HIMNOS);		 	      
		    		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		    		
		    		try 
		    		{
	    		    	Request request = builder.sendRequest(null, new RequestCallback() {
	    		        @Override
	    				public void onError(Request request, Throwable exception) {
	    		         // displayError("Couldn't retrieve JSON");		    		        	
	    		        }

	    		        @Override
	    				public void onResponseReceived(Request request, Response response) {
	    		          if (200 == response.getStatusCode()) {
	    		        	  JsArray<HimnoData> himnos= JsonUtils.safeEval(response.getText());		    		        	  		    		        	 		    		        	   		 		
	    		  		    
	    		        	  for (int i = 0; i < himnos.length(); i++) {		    		        		  
	    		        		  Himno nhimno=new Himno(himnos.get(i).getId(),himnos.get(i).getNumber(), himnos.get(i).getName(), himnos.get(i).getLyrics(), himnos.get(i).getShareableUrl());    		        		  
	    		        		  himnosgrid.add(nhimno);		    		        		  
	    		        	    }    		        	  
	    		        	  HimnosView himnosview= new HimnosView(himnosgrid);
	    				      c.add(himnosview.asWidget());	        	      		        	 
	    		          } else {
	    		            //displayError("Couldn't retrieve JSON (" + response.getStatusText()+ ")");		    		        	  
	    		          }
	    		        }
	    		      });
	    		    } catch (RequestException e) {
	    		      //displayError("Couldn't retrieve JSON");		    		 
	    		    }	
		    	
		    		final AutoProgressMessageBox box = new AutoProgressMessageBox("En progreso", "Recuperando himnos, aguarde por favor...");
	   	          	box.setProgressText("Cargando...");
	   	          	box.auto();
	   	          	box.show();
	   	          	Timer t = new Timer() {
	    	            @Override
	    	            public void run() {		    	            	
	    	              Info.display("Mensaje", "Himnos cargados con exito!");
	    	              box.hide();
	    	            }
	   	          	};
	   	          	t.schedule(3000);	    		
		            addToCenter(c);
		          }
		        }
		      }), vBoxData);
	      
	      lcwest.add(createToggleButton("Nuevo Sermon", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	        	  
	    	      if(Cookies.getCookie("userid")==null)
	    	          	Cookies.setCookie("userid","");
	    	          String userid=Cookies.getCookie("userid");		            
	    	          if(!userid.isEmpty())
	    	          {

				            final AutoProgressMessageBox box = new AutoProgressMessageBox("En progreso", "cargando, aguarde por favor...");
				  	          box.setProgressText("Cargando...");
				  	          box.auto();
				  	          box.show();
				  	         Timer t = new Timer() {
				    	            @Override
				    	            public void run() {		    	            		    	              
				    	              box.hide();
				    	            }
				    	          };
				    	          t.schedule(3000);
			            HBoxLayoutContainer c = new HBoxLayoutContainer();
			            c.setPadding(new Padding(5));
			            c.setHBoxLayoutAlign(HBoxLayoutAlign.TOP);
			            
			            UploadSermonForm a= new UploadSermonForm();	         
			            c.add(a.asWidget()); 
			              
			            addToCenter(c);
	    	          }
	    	          else
	    	          {
	    	        	  Window.alert("Debe loguearse como administrador.");		
	    	          }
	          }
	        }
	      }), vBoxData);
	 
	     
	 
	      lcwest.add(createToggleButton("Nuevo Himno", new ValueChangeHandler<Boolean>() {
	        @Override
	        public void onValueChange(ValueChangeEvent<Boolean> event) {
	          if (event.getValue()) {
	        	  if(Cookies.getCookie("userid")==null)
    	          	Cookies.setCookie("userid","");
    	          String userid=Cookies.getCookie("userid");		            
    	          if(!userid.isEmpty())
    	          {
		            HBoxLayoutContainer c = new HBoxLayoutContainer();
		            c.setPadding(new Padding(5));
		            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		            c.setPack(BoxLayoutPack.CENTER);
		 
		            UploadHimnoForm a=new UploadHimnoForm();
		            c.add(a.asWidget());
		           
		           /* final Dialog simple = new Dialog();
		            simple.setHeadingText("Dialog Test");
		            simple.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
		            simple.setBodyStyleName("pad-text");
		            simple.add(new Label("un poco de texto jaja"));
		            simple.getBody().addClassName("pad-text");
		            simple.setHideOnButtonClick(true);
		            simple.setWidth(300);
		            simple.add(new Button("asdsa"));
		            simple.show();*/
		            addToCenter(c);
    	          }
    	          else
    	          {
    	        	  Window.alert("Debe loguearse como administrador.");		
    	          }
	          }
	        }
	      }), vBoxData);	
	      
	      lcwest.add(createToggleButton("Menu Login", new ValueChangeHandler<Boolean>() {
		        @Override
		        public void onValueChange(ValueChangeEvent<Boolean> event) {
		          if (event.getValue()) {
		            HBoxLayoutContainer c = new HBoxLayoutContainer();
		            c.setPadding(new Padding(5));
		            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		            c.setPack(BoxLayoutPack.CENTER);
		            		            
		            if(Cookies.getCookie("userid")==null)
		            	Cookies.setCookie("userid","");
		            String userid=Cookies.getCookie("userid");		            
		            if(userid.isEmpty())
		            {
		            
		            FramedPanel panel = new FramedPanel();
		            panel.setHeadingText("Log in");
		            panel.setWidth(350);
		            panel.setBodyStyle("background: none; padding: 15px");		        
		            
		            VerticalLayoutContainer p = new VerticalLayoutContainer();
		            panel.add(p);
			   		 final TextBox username=new TextBox();			   		 
			   		 username.setMaxLength(30);				   		
			   		 final PasswordTextBox pass=new PasswordTextBox();		
			   		 Button Login=new Button("Log in");
			   		 
			   		 Login.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							
							loginservice.Login(username.getValue(), pass.getValue(), new AsyncCallback<String>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}

								@Override
								public void onSuccess(String result) {
									if(result.isEmpty())
										Window.alert("Intento de ingreso insatisfactorio.");
									else									
									{
										String sessionID = result;
									    final long DURATION = 1000 * 60 * 60 * 24 * 14; //duration remembering login. 2 weeks in this example.
									    Date expires = new Date(System.currentTimeMillis() + DURATION);
									    Cookies.setCookie("userid", sessionID, expires, null, "/", false);	
									    Window.alert("Log in satisfactorio.");									    
									}
								}
							});
						}
			   		 });
			   		 
			   		 
			   		 p.add(new FieldLabel(username, "User Name"), new VerticalLayoutData(1, -1));
			   		 p.add(new FieldLabel(pass, "Password"), new VerticalLayoutData(1, -1));
			   		 p.add(Login, new VerticalLayoutData(1, -1));
			   		 
			   		 
		            com.sencha.gxt.widget.core.client.Window complex = new com.sencha.gxt.widget.core.client.Window();
		            complex.setMaximizable(true);		
		            complex.setHeadingText("Menu");		         
		            complex.setWidth(500);
		            complex.setHeight(200);
		            complex.add(panel);
		            
		            complex.show();
		            
		            }
		            else
		            {
		            	 FramedPanel panel = new FramedPanel();
		            	 panel.setHeadingText("Log out");
		            	 panel.setWidth(350);
		            	 panel.setBodyStyle("background: none; padding: 15px");		        
			            
		            	 VerticalLayoutContainer p = new VerticalLayoutContainer();
		            	 panel.add(p);
		            	 Button logout=new Button("Log out");
		            	 
		            	 logout.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								 Cookies.setCookie("userid","");	
								  Window.alert("Log out satisfactorio.");
								
							}
						});
		            	 p.add(logout, new VerticalLayoutData(1, -1));   			            	 		          
					   		 
		            	com.sencha.gxt.widget.core.client.Window complex = new com.sencha.gxt.widget.core.client.Window();
			            complex.setMaximizable(true);		
			            complex.setHeadingText("Menu");		         
			            complex.setWidth(500);
			            complex.setHeight(200);
			            complex.add(panel);
			            
			            complex.show();
		            }
		            
		            addToCenter(c);
		          }
		        }
		      }), vBoxData);	
	      lcwest.add(createToggleButton("", new ValueChangeHandler<Boolean>() {
		        @Override
		        public void onValueChange(ValueChangeEvent<Boolean> event) {
		          if (event.getValue()) {
		            HBoxLayoutContainer c = new HBoxLayoutContainer();
		            c.setPadding(new Padding(5));
		            c.setHBoxLayoutAlign(HBoxLayoutAlign.STRETCH);
		            c.setPack(BoxLayoutPack.CENTER);
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
	    button.setIconAlign(IconAlign.LEFT);	   
	    switch(cont)
	    {
	    case 1:
		    button.setIcon(Images.INSTANCE.logo());		   
		    cont++;
		    break;
	    case 2:
	    	button.setIcon(Images.INSTANCE.himnos());
		    cont++;
		    break;	
	    case 3:
	    	button.setIcon(Images.INSTANCE.addsermon());
		    cont++;
		    break;	
	    case 4:
		    button.setIcon(Images.INSTANCE.addhimno());		    
		    cont++;
		    break;	
	    case 5:
		    button.setIcon(Images.INSTANCE.menulogin());		    
		    cont++;
		    break;	
		default:
			//button.setIcon(Images.INSTANCE.default1());
			break;
	    }
	    return button;
	  }
	
	
	
	public void onModuleLoad() {
		 RootPanel.get("layout").add(asWidget());
	}
	
	public interface Images extends ClientBundle {
		public Images INSTANCE = GWT.create(Images.class);
		  
		  @Source("bible1.png")
		  ImageResource logo();
		  
		  @Source("addsermon.png")
		  ImageResource addsermon();
		  
		  @Source("menulogin.png")
		  ImageResource menulogin();
		  
		  @Source("addhimno.png")
		  ImageResource addhimno();
		  
		  @Source("himnos.png")
		  ImageResource himnos();
		}
}


