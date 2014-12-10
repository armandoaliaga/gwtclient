package com.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.client.RowExpanderGrid.Images;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class HimnosView implements IsWidget  {
	private ContentPanel panel;
	private HorizontalLayoutContainer hlcontainer;
	private static final HimnoProperties props = GWT.create(HimnoProperties.class);
	private ListStore<Himno> store;
	private final ArrayList<Himno> himnos;
	private VerticalLayoutContainer vlcontainer;
	final DeleteHimnoServiceAsync deleteservice= GWT.create(DeleteHimnoService.class);
	final UpdateHimnoServiceAsync updateservice= GWT.create(UpdateHimnoService.class);
	private CenterLayoutContainer clc;
	private VerticalPanel vp;
	private VerticalPanel vp3;
	private TextArea estrofa;
	private ContentPanel hp;
	private TextButton siguiente;
	private int n_estrofas;
	private ArrayList<String> estrofas=new ArrayList<>();
	private int last_selected=0;
	private static final int COLUMN_FORM_WIDTH = 1100;
	private HtmlEditor htmleditor;
	private Himno grid_selected_himno; 
	private HtmlEditor htmleditor_presentation;
	private int n_es_mostrar;
	private int indice;
	private Audio audioReproductor;
	{
		audioReproductor = Audio.createIfSupported();	    	   
	}
	public HimnosView(ArrayList<Himno> himnosgrid)
	{
		himnos=himnosgrid;
	}
    public static native void DownloadAudio(String src) /*-{						  		 
	 $wnd.location.href = src;
	}-*/;
	@Override
	public Widget asWidget() {
		if (panel == null) {
			
			ColumnConfig<Himno, Integer> numbercol = new ColumnConfig<Himno, Integer>(props.number(), 50, "Numero"); 
			ColumnConfig<Himno, String> nameCol = new ColumnConfig<Himno, String>(props.name(), 250, "Titulo");
			ColumnConfig<Himno, String> downloadcolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");
		    ColumnConfig<Himno, String> editcolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");
		    ColumnConfig<Himno, String> deletecolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");	
			
		    
		    //Button Download
		      final TextButtonCell buttonDownload = new TextButtonCell();
		      buttonDownload.setIconAlign(IconAlign.LEFT);
		      buttonDownload.setIcon(Images.INSTANCE.download());
		      buttonDownload.addSelectHandler(new SelectHandler() {
		 
		        @Override
		        public void onSelect(SelectEvent event) {
		          Context c = event.getContext();
		          int row = c.getIndex();
		          Himno p = store.get(row);	   
		          DownloadAudio(p.getShareableURL());
		          Info.display("Mensaje", "Iniciando descarga del himno: '"+p.getName()+"'");	      		          
		        }
		      });
		      downloadcolumn.setCell(buttonDownload);
		      
		      //Button Edit
		      TextButtonCell buttonEdit = new TextButtonCell();
		      buttonEdit.setIconAlign(IconAlign.LEFT);
		      buttonEdit.setIcon(Images.INSTANCE.edit());
		      buttonEdit.addSelectHandler(new SelectHandler() {
		 
		        @Override
		        public void onSelect(SelectEvent event) {
		          Context c = event.getContext();
		          int row = c.getIndex();
		          Himno p = store.get(row);	 		          
		          ChangeViewEdition(p);		          
		        }
		      });
		      editcolumn.setCell(buttonEdit);
		      
		      
		    //Button Delete
		      TextButtonCell buttonDelete = new TextButtonCell();
		      buttonDelete.setIconAlign(IconAlign.LEFT);
		      buttonDelete.setIcon(Images.INSTANCE.delete());
		      buttonDelete.addSelectHandler(new SelectHandler() {
		 
		        @Override
		        public void onSelect(SelectEvent event) {	          
		          Context c = event.getContext();
		          int row = c.getIndex();
		          final Himno p = store.get(row);
		          final ConfirmMessageBox box = new ConfirmMessageBox("Mensaje", "Esta seguro de eliminar el himno '"+p.getName()+"'?");
		          box.addDialogHideHandler(new DialogHideHandler() {
					
					@Override
					public void onDialogHide(DialogHideEvent event) {
						String answer = String.valueOf(event.getHideButton());
						if(answer=="YES")
						{
							final String id=String.valueOf(p.getId());
							deleteservice.DeleteService(id, new AsyncCallback<String>() {
								
								@Override
								public void onSuccess(String result) {
									Remover(p.getId());
									Info.display("Menasje",result);					
								}
								
								@Override
								public void onFailure(Throwable caught) {				
								}
					          });	       
						}
					}
				  });
		          box.show();		          	        	                     
		        }
		      });
		      deletecolumn.setCell(buttonDelete);
		    
		    
		    
		    
			List<ColumnConfig<Himno, ?>> l = new ArrayList<ColumnConfig<Himno, ?>>();
		    l.add(numbercol);
		    l.add(nameCol);
		    l.add(downloadcolumn);
		    
		    if(Cookies.getCookie("userid")==null)
            	Cookies.setCookie("userid","");
            String userid=Cookies.getCookie("userid");		            
            if(!userid.isEmpty())
            {
            	l.add(editcolumn);
            	l.add(deletecolumn);
            }
		    ColumnModel<Himno> cm = new ColumnModel<Himno>(l);
		    store = new ListStore<Himno>(props.key());			   
			store.addAll(himnos);	
			
			
			
			
		      
		      
			panel = new ContentPanel();
			panel.setHeadingText("Himnos");
			//panel.getHeader().setIcon(Images.INSTANCE.himnos());
			panel.setPixelSize(1141, 598);
			panel.addStyleName("margin-10");
			
			
			htmleditor= new HtmlEditor();			
			htmleditor.setEnableAlignments(false);
			htmleditor.setEnableColors(false);			
			htmleditor.setEnableFont(false);
			htmleditor.setEnableFontSize(false);
			htmleditor.setEnableFormat(false);
			htmleditor.setEnableLinks(false);
			htmleditor.setEnableLists(false);
			htmleditor.setEnableSourceEditMode(false);				
			htmleditor.setValue("<textarea rows='33' cols='92' disabled align='center' style='text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >Seleccione un himno para ver la letra.</textarea>");								
		
			final Grid<Himno> grid = new Grid<Himno>(store, cm);
		      grid.getView().setAutoExpandColumn(nameCol);
		      grid.setBorders(false);
		      grid.getView().setStripeRows(true);
		      grid.getView().setColumnLines(true);		     
		      
		      grid.addCellClickHandler(new CellClickHandler() {
				
				@Override
				public void onCellClick(CellClickEvent event) {
					grid_selected_himno = grid.getStore().get(event.getRowIndex());	
					PonerEnFormato(grid_selected_himno.getLyrics());									
				}
			});
			
			
			hlcontainer = new HorizontalLayoutContainer();
			hlcontainer.setBorders(false);		
			hlcontainer.add(grid, new HorizontalLayoutData(-1, 1));
			
			VerticalLayoutContainer vlcontainermp =new VerticalLayoutContainer();
			vlcontainermp.add(htmleditor,new VerticalLayoutData(-1, 1));
			vlcontainermp.add(new TextButton("Ver en modo presentacion",new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					if(grid_selected_himno!=null)
					{
						audioReproductor.setSrc(grid_selected_himno.getShareableURL());
						n_es_mostrar=0;
						indice=n_es_mostrar+1;
						LaunchFullScreen();
						Info.display("Mensaje","Modo presentacion activo");		
						final ScrollPanel con = (ScrollPanel) RootPanel.get("layout").getWidget(0);
						RootPanel.get("layout").remove(0);
						
						ContentPanel cp=new ContentPanel();												
						htmleditor_presentation= new HtmlEditor();			
						htmleditor_presentation.setEnableAlignments(false);
						htmleditor_presentation.setEnableColors(false);			
						htmleditor_presentation.setEnableFont(false);
						htmleditor_presentation.setEnableFontSize(false);
						htmleditor_presentation.setEnableFormat(false);
						htmleditor_presentation.setEnableLinks(false);
						htmleditor_presentation.setEnableLists(false);
						htmleditor_presentation.setEnableSourceEditMode(false);	
												
						final String[] estrofas= grid_selected_himno.getLyrics().split(">");						
						htmleditor_presentation.setValue("<textarea rows='14' cols='50' disabled align='center' style='font-size: 30pt; text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >\n \n "+indice+" \n"+estrofas[n_es_mostrar].replaceAll("<", "\n")+"</textarea>");
						cp.add(htmleditor_presentation);
						//cp.setPixelSize(1365, 730);
						cp.setPixelSize(Window.getClientWidth(), Window.getClientHeight()+50);					
						TextButton sig_estrofa=new TextButton("SIG");
						sig_estrofa.setIcon(Images.INSTANCE.sig_estrofa());
						sig_estrofa.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {							
								if(n_es_mostrar<estrofas.length-1)
								{
									n_es_mostrar++;
									indice=n_es_mostrar+1;
									htmleditor_presentation.setValue("<textarea rows='14' cols='50' disabled align='center' style='font-size: 30pt; text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >\n \n "+ indice+" \n"+estrofas[n_es_mostrar].replaceAll("<", "\n")+"</textarea>");
								}
							}
						});
						TextButton ant_estrofa=new TextButton("ANT");
						ant_estrofa.setIcon(Images.INSTANCE.ant_estrofa());
						ant_estrofa.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {
								if(n_es_mostrar>0)
								{
									n_es_mostrar--;
									indice=n_es_mostrar+1;
									htmleditor_presentation.setValue("<textarea rows='14' cols='50' disabled align='center' style='font-size: 30pt; text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >\n \n "+ indice +" \n"+estrofas[n_es_mostrar].replaceAll("<", "\n")+"</textarea>");
								}
							}
						});
						TextButton salir_modo=new TextButton("SALIR");
						salir_modo.setIcon(Images.INSTANCE.salir_modo());				
						salir_modo.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {	
								audioReproductor.setSrc(null);
								RootPanel.get("layout").remove(0);
								RootPanel.get("layout").add(con);
								ExitFullScreen();								
							}
						});
						
						TextButton audio_play=new TextButton("Play");
						audio_play.setIcon(Images.INSTANCE.play());				
						audio_play.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {							
								audioReproductor.play();						
							}
						});
						
						TextButton audio_pause=new TextButton("Pause");
						audio_pause.setIcon(Images.INSTANCE.pause());				
						audio_pause.addSelectHandler(new SelectHandler() {
							
							@Override
							public void onSelect(SelectEvent event) {							
								audioReproductor.pause();						
							}
						});
						
						cp.addButton(audio_play);
						cp.addButton(audio_pause);
						cp.addButton(ant_estrofa);
						cp.addButton(sig_estrofa);						
						cp.addButton(salir_modo);									
						RootPanel.get("layout").add(cp);																													
					}
					else
					{
						Info.display("Mensaje","Debe seleccionar un himno para poder ver en modo presentacion!");
					}
				}
			}), new VerticalLayoutData(1, -1));
			hlcontainer.add(vlcontainermp, new HorizontalLayoutData(1, 1));
			
			
			
			ToolBar toolBar = new ToolBar();
	        
		      final TextField search = new TextField();
		      search.setWidth(800);	     	     	     
		      search.addKeyUpHandler(new KeyUpHandler() {
				
				@Override
				public void onKeyUp(KeyUpEvent event) {				
					Filtrar(search.getText());
					if(store.size()==0)
						Info.display("Mensaje","Ninguna coincidencia encontrada");
				}
		     });
			
		      final FieldLabel seachlabel= new FieldLabel(search, "Buscar");
		      toolBar.add(seachlabel);
		      toolBar.add(search);
		      
		      vlcontainer = new VerticalLayoutContainer();
		      vlcontainer.setBorders(true);
		      vlcontainer.add(toolBar, new VerticalLayoutData(1, -1));
		      vlcontainer.add(hlcontainer, new VerticalLayoutData(1, 1));
			
			
			panel.setWidget(vlcontainer);
		}
		return panel;
	}
	
	public static native void LaunchFullScreen() /*-{		
		var element=$doc.documentElement; 
		if (element.requestFullScreen) {
		    element.requestFullScreen();
		  } else if(element.mozRequestFullScreen) {
		    element.mozRequestFullScreen();
		  } else if(element.webkitRequestFullScreen) {
		    element.webkitRequestFullScreen();
		  }
	}-*/;

	public static native void ExitFullScreen() /*-{		
	var element=$doc;
	   if (element.exitFullScreen) {
	    element.exitFullScreen();
	  } else if(element.mozCancelFullScreen) {
	    element.mozCancelFullScreen();
	  } else if(element.webkitCancelFullScreen) {
	    element.webkitCancelFullScreen();
	  }
	}-*/;
	
	protected void ChangeViewEdition(Himno p) {
		panel.setHeadingText("Editar himno");   
		estrofas.clear();
        vp = new VerticalPanel();
	    vp.setSpacing(10);
	    createColumnForm(p);	      		    						
		panel.setWidget(vp);		
	}

	private void createColumnForm(final Himno p) {
		 FramedPanel fpanel = new FramedPanel();		    
		    fpanel.setWidth(COLUMN_FORM_WIDTH);
		 
		    HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
		    fpanel.add(con, new MarginData(15));
		 
		    int cw = ((COLUMN_FORM_WIDTH - 30)/ 2) - 12;
		 

		    final TextField numero = new TextField();
		    numero.setAllowBlank(false);
		    numero.setWidth(cw);
		    numero.setText(String.valueOf(p.getNumber()));
		    con.add(new FieldLabel(numero, "Numero del Himno"), new HtmlData(".numero"));
		    
		    final TextField Name = new TextField();
		    Name.setAllowBlank(false);
		    Name.setWidth(cw);
		    Name.setText(p.getName());
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
		 	
		    final TextArea textarea=new TextArea();
		    textarea.setPixelSize(517, 100);
		    con.add(new FieldLabel(textarea,"Estrofa"),new HtmlData(".text"));
		    		    
		    
		    final ListBox s_estrofas=new ListBox();		  			
			String[] estrofas_lyrics=p.getLyrics().split(">");
			for (int i = 0; i < estrofas_lyrics.length; i++) {		
				if(estrofas_lyrics[i].length()>=80)
					s_estrofas.addItem(estrofas_lyrics[i].substring(0,80).replaceAll("<", " ")+"...");
				else
					s_estrofas.addItem(estrofas_lyrics[i].replaceAll("<", " ")+"...");
				estrofas.add((estrofas_lyrics[i].replaceAll("<", "\n")));
			}			
						
			textarea.setValue(estrofas.get(last_selected));
			s_estrofas.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {									 
					if(!estrofas.get(last_selected).equals(textarea.getValue()))
					{						
						estrofas.set(last_selected, textarea.getValue());
						if(textarea.getValue().length()>=80)
							s_estrofas.setItemText(last_selected, textarea.getValue().substring(0,80)+"...");							
						else
							s_estrofas.setItemText(last_selected, textarea.getValue()+"...");						
					}
					textarea.setValue(estrofas.get(s_estrofas.getSelectedIndex()));
					last_selected=s_estrofas.getSelectedIndex();
				}
			});
			
		    con.add(new FieldLabel(s_estrofas,"Estrofa para editar"),new HtmlData(".list")); 
		    		    		    		  
		    fpanel.addButton(new TextButton("Cancelar",new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					panel.setHeadingText("Himnos");
					panel.setWidget(vlcontainer);						
				}
			}));
		    
		    fpanel.addButton(new TextButton("Guardar Cambios",new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {						
					//actualizar el ultimo valor del textarea de una estrofa
					if(!estrofas.get(last_selected).equals(textarea.getValue()))					
						estrofas.set(last_selected, textarea.getValue());						
					//llamar al servicio
					updateservice.UpdateService(p.getId(), numero.getText(), Name.getText(), estrofas, new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String result) {
							Info.display("Mensaje",result);		
							ActualizarHimnos(p.getId(), numero.getText(), Name.getText(), estrofas);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}));	    	    	 
			
			
		    // need to call after everything is constructed
		    List<FieldLabel> labels = FormPanelHelper.getFieldLabels(fpanel);
		    for (FieldLabel lbl : labels) {
		      lbl.setLabelAlign(LabelAlign.TOP);
		    }
		 
		    vp.add(fpanel);
		
	}
	
	private void ActualizarHimnos(int id, String numero, String name, ArrayList<String> lyrics)
	{
		panel.setHeadingText("Himnos");
		panel.setWidget(vlcontainer);
	    store.clear();		   
		for (int i = 0; i < himnos.size(); i++) {
			if(himnos.get(i).getId()== id)
			{
				himnos.get(i).setNumber(Integer.parseInt(numero));
				himnos.get(i).setName(name);
				
				String s_lyrics="";
				for (int j = 0; j < lyrics.size(); j++) {
					s_lyrics=s_lyrics+lyrics.get(j).replace("\n", "<").replace("\r", "<")+">";
				}										
				himnos.get(i).setLyrics(s_lyrics);
				PonerEnFormato(s_lyrics);
				break;
			}
		}				
		store.addAll(himnos);			
	}
	 private native String getTableMarkup() /*-{
	    return [ '<table width=100% cellpadding=0 cellspacing=0>',
	        '<tr><td class=numero width=50%></td><td class=name width=50%></td></tr>',
	        '<tr><td class=file width=50%></td></tr><tr><td class=list width=50%></td></tr>',
	        '<tr><td class=text width=100%></td></tr>','</table>'	 
	    ].join("");
	  }-*/;	 

	protected void PonerEnFormato(String lyrics) {		
		String formateado="<textarea rows='33' cols='92' disabled align='center' style='text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' > \n";
		String[] estrofas=lyrics.split(">");
		for (int i = 0; i < estrofas.length; i++) {
			int n=i+1;
			formateado=formateado+n+"\n"+estrofas[i].replaceAll("<", "\n")+"\n \n";
		}
		formateado=formateado+"</textarea>";
		htmleditor.setValue(formateado);
	}

	protected void Filtrar(String text) {		
		store.clear();			
		for (int i = 0; i < himnos.size(); i++) {
			if(himnos.get(i).getName().toLowerCase().contains(text.toLowerCase()))
				store.add(himnos.get(i));
		}
	}
	
	public interface Images extends ClientBundle {
		public Images INSTANCE = GWT.create(Images.class);
		  
		  @Source("playmp.png")
		  ImageResource play();
		  
		  @Source("Edit.png")
		  ImageResource edit();
		  
		  @Source("Delete.png")
		  ImageResource delete();
		  
		  @Source("himnos.png")
		  ImageResource himnos();
		  
		  @Source("ant_estrofa.png")
		  ImageResource ant_estrofa();
		  
		  @Source("sig_estrofa.png")
		  ImageResource sig_estrofa();
		  
		  @Source("sali_modo.png")
		  ImageResource salir_modo();
		  
		  @Source("Download.png")
		  ImageResource download();
		  
		  @Source("pausemp.png")
		  ImageResource pause();
		}	
	
	protected void Remover(int id) {		
		store.clear();			
		for (int i = 0; i < himnos.size(); i++) {
			if(himnos.get(i).getId()== id)		
			{
				himnos.remove(i);
				break;
			}	
		}				
		store.addAll(himnos);			
	}

}
