package com.gwt.client;


import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.info.Info;

public class UploadHimnoForm implements IsWidget {
	final SubmitHimnoServiceAsync submithimno= GWT.create(SubmitHimnoService.class);
	private ContentPanel panel;
	private CenterLayoutContainer clc;
	
	private static final int COLUMN_FORM_WIDTH = 1100;
	private VerticalPanel vp;
	private VerticalPanel vp3;
	private TextArea estrofa;
	private ContentPanel hp;
	private TextButton siguiente;
	private int n_estrofas;
	private ArrayList<String> estrofas;
	private int n;
	@Override
	public Widget asWidget() {
			
		
	      
		if (panel == null) {
		panel = new ContentPanel();
        panel.setBodyStyle("padding: 6px");
        panel.setHeadingText("Nuevo Himno");
		
        vp = new VerticalPanel();
	    vp.setSpacing(10);
	    createColumnForm();	      
		    
		
		hp=new ContentPanel();
		hp.setBodyStyle("padding: 6px");
		hp.setHeadingText("Estrofa 1");
		
		estrofa = new TextArea();
		estrofa.setPixelSize(500, 100);
		estrofa.setAllowBlank(false);
		siguiente=new TextButton("Siguiente");
		hp.add(estrofa);	
		hp.addButton(siguiente);	
		
		clc=new CenterLayoutContainer();
		clc.add(hp);		
		
		vp3= new VerticalPanel();
		vp3.add(vp);		
		
		panel.add(vp3);				
		}
		return panel;
	}	
	
	private void createColumnForm() {
	    FramedPanel panel = new FramedPanel();
	    panel.setHeadingText("Nuevo Himno");
	    panel.setWidth(COLUMN_FORM_WIDTH);
	 
	    HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
	    panel.add(con, new MarginData(15));
	 
	    int cw = ((COLUMN_FORM_WIDTH - 30)/ 2) - 12;
	 

	    final TextField numero = new TextField();
	    numero.setAllowBlank(false);
	    numero.setWidth(cw);
	    con.add(new FieldLabel(numero, "Numero del Himno"), new HtmlData(".numero"));
	    
	    final TextField Name = new TextField();
	    Name.setAllowBlank(false);
	    Name.setWidth(cw);
	    con.add(new FieldLabel(Name, "Nombre"), new HtmlData(".name"));
	 	 	  
	    
	    final FileUploadField file = new FileUploadField();	 
	    file.setWidth(cw);
	    con.add(new FieldLabel(file, "File"), new HtmlData(".file"));
	
	    file.addChangeHandler(new ChangeHandler() {
	    	 
	        @Override
	        public void onChange(ChangeEvent event) {
	          Info.display("File Changed", "You selected " + file.getValue());
	        }
	      });
	    file.setName("uploadedfile");
	    //file.setAllowBlank(false);
	 	   
	   
	    final ListBox numeroEstrofas = new ListBox(false);				      
	      for (int i = 0; i < 10; i++) {
	        numeroEstrofas.addItem(i+1+"");		        
	      }
	    con.add(new FieldLabel(numeroEstrofas, "Numero de Estrofas"), new HtmlData(".ne"));  	   
	    
	    panel.addButton(new TextButton("Continuar",new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {			
				numero.disable();
				Name.disable();
				file.disable();
				numeroEstrofas.setEnabled(false);
				vp3.add(clc);		
				n_estrofas=Integer.parseInt(numeroEstrofas.getValue(numeroEstrofas.getSelectedIndex()));
				estrofas=new ArrayList<>();	
				estrofas.clear();
				hp.setHeadingText("Estrofa 1");
				n= estrofas.size()+1;
				siguiente.addSelectHandler(new SelectHandler() {
					
					@Override
					public void onSelect(SelectEvent event) {
						if(estrofas.size()< n_estrofas-1)
						{
							if(!estrofa.getText().isEmpty())
							{
								estrofas.add(estrofa.getText());
								n=estrofas.size()+1;
								//Window.alert(n+"");
								hp.setHeadingText("Estrofa "+n);
								estrofa.setText("");													
							}	
							else
							{
								Info.display("Mensaje","La estrofa no puede estrar vacia.");								
							}
						}
						else
						{
							estrofas.add(estrofa.getText());
							submithimno.SubmitService(numero.getText(), Name.getText(), estrofas, new AsyncCallback<String>() {
								
								@Override
								public void onSuccess(String result) {
									estrofas.clear();
									vp3.remove(1);
									numero.enable();
									Name.enable();
									file.enable();									
									numeroEstrofas.setEnabled(true);
									numero.setText("");
									Name.setText("");									
									estrofa.setText("");
									estrofas.clear();
									Info.display("Mensaje",result);
									//Window.alert(result);									
								}
								
								@Override
								public void onFailure(Throwable caught) {	
									Window.alert(caught+"");
								}
							});						
						}
					}
				});												
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
	        '<tr><td class=numero width=50%></td><td class=name width=50%></td></tr>',
	        '<tr><td class=file width=50%></td></tr><tr><td class=ne width=50%></td></tr>','</table>'	 
	    ].join("");
	  }-*/;	 	  	 
	  	  
}
