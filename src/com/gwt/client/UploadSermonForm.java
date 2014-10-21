package com.gwt.client;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;




import java.nio.file.attribute.FileAttribute;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class UploadSermonForm implements IsWidget {
	 
      final SubmitSermonServiceAsync submitservice= GWT.create(SubmitSermonService.class);
      final UploadDropboxServiceAsync dropboxservice= GWT.create(UploadDropboxService.class);
	  private static final int COLUMN_FORM_WIDTH = 1100;
	  private VerticalPanel vp;
	  private String path="";
	  private String namefile="";
	  private Boolean archivocargado=false;
	  public Widget asWidget() {
	    if (vp == null) {
	      vp = new VerticalPanel();
	      vp.setSpacing(10);
	      createColumnForm();	      
	    }
	    return vp;
	  }	 
	 
	  private void createColumnForm() {
	    FramedPanel panel = new FramedPanel();
	    panel.setHeadingText("Nuevo Sermon");
	    panel.setWidth(COLUMN_FORM_WIDTH);
	 
	    HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
	    panel.add(con, new MarginData(15));
	 
	    int cw = ((COLUMN_FORM_WIDTH - 30)/ 2) - 12;
	 
	    final TextField Name = new TextField();
	    Name.setAllowBlank(false);
	    Name.setWidth(cw);
	    con.add(new FieldLabel(Name, "Nombre"), new HtmlData(".name"));
	 
	    final TextField Name_of_predicador = new TextField();
	    Name_of_predicador.setAllowBlank(false);
	    Name_of_predicador.setWidth(cw);
	    con.add(new FieldLabel(Name_of_predicador, "Nombre del Predicador"), new HtmlData(".np"));
	 
	    final TextField serie = new TextField();
	    serie.setWidth(cw);
	    con.add(new FieldLabel(serie, "Serie"), new HtmlData(".serie"));	 	   
	 
	    final DateField Date = new DateField();
	    Date.setWidth(cw);	 
	    con.add(new FieldLabel(Date, "Fecha"), new HtmlData(".date"));
	 
	    final HtmlEditor a = new HtmlEditor();	   
	    a.setWidth(COLUMN_FORM_WIDTH - 25 - 30);
	    con.add(new FieldLabel(a, "Descripcccion"), new HtmlData(".editor"));
	    	    
	    final MultiUploader defaultUploader = new MultiUploader();
	    defaultUploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	    con.add(new FieldLabel(defaultUploader, "MP3 file"), new HtmlData(".file"));
	    
	    /*final FileUploadField file = new FileUploadField();	 
	    file.setWidth(cw);
	    con.add(new FieldLabel(file, "File"), new HtmlData(".file"));
	
	    file.addChangeHandler(new ChangeHandler() {
	    	 
	        @Override
	        public void onChange(ChangeEvent event) {
	          Info.display("File Changed", "You selected " + file.getValue());
	        }
	      });
	    file.setName("uploadedfile");
	    file.setAllowBlank(false);
	 	*/   
	    panel.addButton(new TextButton("Cancel",new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				Window.alert("Button Cancel");
			}
		}));	   
	    
	    panel.addButton(new TextButton("Submit",new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
			  if(archivocargado)
			  {				 			  			 
				  final AutoProgressMessageBox box = new AutoProgressMessageBox("En progreso", "Guardando sermon, por favor espere...");
	  	          box.setProgressText("Guardando...");
	  	          box.auto();		    	         
	  	          box.show();
					submitservice.SubmitService(Name.getText(), Name_of_predicador.getText(), serie.getText(), a.getValue(),Date.getCurrentValue(), new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String result) {
							 
								Name.setText("");
								Name_of_predicador.setText("");
								serie.setText("");
								a.setValue("");
								Date.setText("");
			    	          Timer t = new Timer() {
			    	            @Override
			    	            public void run() {		    	            	
			    	              Info.display("Mensaje", "Sermon guardado con exito!");
			    	              box.hide();
			    	            }
			    	          };
			    	          t.schedule(3000);
			    	          
			    	          dropboxservice.SubmitService(result, path, namefile, "S", new AsyncCallback<String>() {
								
								@Override
								public void onSuccess(String result) {
									Window.alert(result);
								}
								
								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									
								}
							});
						}
						
						@Override
						public void onFailure(Throwable caught) {
							 Info.display("Mensaje", "Error al guardar el sermon!");
							 box.hide();
						}
					});		
				}
			  else
			  {
				 Info.display("Mensaje"," Por favor, seleccione un archivo o aguarde a que termine el proceso de carga."); 
			  }
			}
		}));	    	    	 
		
		
	    // need to call after everything is constructed
	    List<FieldLabel> labels = FormPanelHelper.getFieldLabels(panel);
	    for (FieldLabel lbl : labels) {
	      lbl.setLabelAlign(LabelAlign.TOP);
	    }
	 
	    vp.add(panel);
	  }
	  
	  private native String getTableMarkup() /*-{
	    return [ '<table width=100% cellpadding=0 cellspacing=0>',
	        '<tr><td class=name width=50%></td><td class=np width=50%></td></tr>',
	        '<tr><td class=date></td><td class=serie></td></tr>',
	        '<tr><td class=file></td></tr>',	        
	        '<tr><td class=editor colspan=2></td></tr>', '</table>'	 
	    ].join("");
	  }-*/;	 	
	  
	  private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
	    public void onFinish(IUploader uploader) {
	      if (uploader.getStatus() == Status.SUCCESS) {	       
	        
	        // The server sends useful information to the client by default
	        UploadedInfo info = uploader.getServerInfo();
	        System.out.println("File name " + info.name);
	        System.out.println("File content-type " + info.ctype);
	        System.out.println("File size " + info.size);

	        // You can send any customized message and parse it 
	        System.out.println("Server message " + info.message);
	        
	        path=info.message;
	        namefile=info.name;
	        archivocargado=true;	        
	      }
	    }
	  };		  	 
}