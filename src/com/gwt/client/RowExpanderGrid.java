package com.gwt.client;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.rebind.MessageFormatParser.StaticArgChunk;
import com.google.gwt.media.client.Audio;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sun.xml.internal.ws.api.FeatureConstructor;

public class RowExpanderGrid implements IsWidget {
	 
	  private static final SermonProperties props = GWT.create(SermonProperties.class);
	  private ContentPanel panel;
	  private final ArrayList<Sermon> sermones;
	  private ListStore<Sermon> store;
	  final DeleteSermonServiceAsync deleteservice= GWT.create(DeleteSermonService.class);
	  private static final int COLUMN_FORM_WIDTH = 1100;
	  private VerticalPanel vp;
	  private VerticalLayoutContainer conn;
	  private int lastIdPlayed=0;
	  final UpdateSermonServiceAsync updateservice=GWT.create(UpdateSermonService.class);
	  private Audio audioReproductor;
	  {
		  audioReproductor = Audio.createIfSupported();	    	   
	  }
	  public RowExpanderGrid(ArrayList<Sermon> sermonesgrid)
	  {
		  sermones=sermonesgrid;
	  }	  
	  
	  public static native void DownloadAudio(String src) /*-{						  		 
		 $wnd.location.href = src;
		}-*/;
	  
	  @Override
	  public Widget asWidget() {
	    if (panel == null) {
	      final NumberFormat number = NumberFormat.getFormat("0.00");	      
	      RowExpander<Sermon> expander = new RowExpander<Sermon>(new AbstractCell<Sermon>() {
	        @Override
	        public void render(Context context, Sermon value, SafeHtmlBuilder sb) {
	          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Descripcion:</b> " + value.getDescription());	          
	        }
	      });	 	     			      	      
	      
	      ColumnConfig<Sermon, String> nameCol = new ColumnConfig<Sermon, String>(props.name(), 100, "Nombre");
	      ColumnConfig<Sermon, String> symbolCol = new ColumnConfig<Sermon, String>(props.name_of_predicador(), 230, "Predicador");
	      ColumnConfig<Sermon, String> lastCol = new ColumnConfig<Sermon, String>(props.serie(), 121, "Serie");
	 
	      ColumnConfig<Sermon, Integer> changeCol = new ColumnConfig<Sermon, Integer>(props.duration(), 120, "Duracion");
	      changeCol.setCell(new AbstractCell<Integer>() {
	 
	        @Override
	        public void render(Context context, Integer value, SafeHtmlBuilder sb) {
	          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
	          String v = number.format(value);
	          sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + v + "'>" + v + "</span>");
	        }
	      });
	 
	      ColumnConfig<Sermon, String> lastTransCol = new ColumnConfig<Sermon, String>(props.date(), 130, "Fecha (yyyy-mm-dd)");
	      ColumnConfig<Sermon, String> playcolumn = new ColumnConfig<Sermon, String>(props.play(), 40, "");
	      ColumnConfig<Sermon, String> editcolumn = new ColumnConfig<Sermon, String>(props.play(), 40, "");
	      ColumnConfig<Sermon, String> deletecolumn = new ColumnConfig<Sermon, String>(props.play(), 40, "");
	      ColumnConfig<Sermon, String> downloadcolumn = new ColumnConfig<Sermon, String>(props.play(), 40, "");
	      ColumnConfig<Sermon, String> pausecolumn = new ColumnConfig<Sermon, String>(props.play(), 40, "");
	      /*ColumnConfig<Sermon, Date> lastTransCol = new ColumnConfig<Sermon, Date>(props.date(), 121, "Fecha (yyyy-mm-dd)");
	      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));*/
	 
	      
	      //Button Download
	      final TextButtonCell buttonDownload = new TextButtonCell();
	      buttonDownload.setIconAlign(IconAlign.LEFT);
	      buttonDownload.setIcon(Images.INSTANCE.download());
	      buttonDownload.addSelectHandler(new SelectHandler() {
	 
	        @Override
	        public void onSelect(SelectEvent event) {
	          Context c = event.getContext();
	          int row = c.getIndex();
	          Sermon p = store.get(row);	
	          DownloadAudio(p.getShareableURL());
	          Info.display("Mensaje","Iniciando descarga de audio del sermon '"+p.getName()+"'");		          
	        }
	      });
	      downloadcolumn.setCell(buttonDownload);
	      
	      //Button Pause
	      final TextButtonCell buttonPause = new TextButtonCell();
	      buttonPause.setIconAlign(IconAlign.LEFT);
	      buttonPause.setIcon(Images.INSTANCE.pause());
	      buttonPause.addSelectHandler(new SelectHandler() {
	 
	        @Override
	        public void onSelect(SelectEvent event) {
	          Context c = event.getContext();
	          int row = c.getIndex();
	          Sermon p = store.get(row);	
	          if(lastIdPlayed!=0)
	          {
	        	  if(p.getId()==lastIdPlayed)
	        		  audioReproductor.pause();
	          }
	        }
	      });
	      pausecolumn.setCell(buttonPause);
	      
	 
	      //Button Play
	      final TextButtonCell buttonPlay = new TextButtonCell();
	      buttonPlay.setIconAlign(IconAlign.LEFT);
	      buttonPlay.setIcon(Images.INSTANCE.play());
	      buttonPlay.addSelectHandler(new SelectHandler() {
	 
	        @Override
	        public void onSelect(SelectEvent event) {
	          Context c = event.getContext();
	          int row = c.getIndex();
	          Sermon p = store.get(row);	
	          if(lastIdPlayed!=p.getId() || audioReproductor.hasEnded())
	          	audioReproductor.setSrc(p.getShareableURL());	          
	          audioReproductor.play();
	          lastIdPlayed=p.getId();
	          Info.display("Mensaje", "'"+p.getName()+"' esta siendo reproducida.");		        
	        }
	      });
	      playcolumn.setCell(buttonPlay);
	      
	      //Button Edit
	      TextButtonCell buttonEdit = new TextButtonCell();
	      buttonEdit.setIconAlign(IconAlign.LEFT);
	      buttonEdit.setIcon(Images.INSTANCE.edit());
	      buttonEdit.addSelectHandler(new SelectHandler() {
	 
	        @Override
	        public void onSelect(SelectEvent event) {
	          Context c = event.getContext();
	          int row = c.getIndex();
	          Sermon p = store.get(row);	          	          
	          
	          vp = new VerticalPanel();
		      vp.setSpacing(10);
		      createColumnForm(p.getId(),p.getName(),p.getName_of_predicador(),p.getDescription(),p.getSerie(),p.getDuration(),p.getDate());	
		      panel.setWidget(vp);
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
	          final Sermon p = store.get(row);
	          final ConfirmMessageBox box = new ConfirmMessageBox("Mensaje", "Esta seguro de eliminar el sermon '"+p.getName()+"'?");
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
	      
	      List<ColumnConfig<Sermon, ?>> l = new ArrayList<ColumnConfig<Sermon, ?>>();
	      l.add(expander);
	      l.add(nameCol);
	      l.add(symbolCol);
	      l.add(lastCol);
	      l.add(changeCol);
	      l.add(lastTransCol);
	      l.add(playcolumn);	 
	      l.add(pausecolumn);
	      l.add(downloadcolumn);
	     
	      if(Cookies.getCookie("userid")==null)
          	Cookies.setCookie("userid","");
          String userid=Cookies.getCookie("userid");		            
          if(!userid.isEmpty())
          {		      
		      l.add(editcolumn);
		      l.add(deletecolumn);
          }
	      
          ColumnModel<Sermon> cm = new ColumnModel<Sermon>(l);
	 
	      store = new ListStore<Sermon>(props.key());	      
		  store.addAll(sermones);	      
	 
	      panel = new ContentPanel();
	      panel.setHeadingText("Sermones");
	      //panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
	      panel.setPixelSize(1141, 598);
	      panel.addStyleName("margin-10");
	 
	      final Grid<Sermon> grid = new Grid<Sermon>(store, cm);
	      grid.getView().setAutoExpandColumn(nameCol);
	      grid.setBorders(false);
	      grid.getView().setStripeRows(true);
	      grid.getView().setColumnLines(true);
	 
	      expander.initPlugin(grid);	
	      
	      
	      //Buscador
	      
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
	      
	      conn = new VerticalLayoutContainer();
	      conn.setBorders(true);
	      conn.add(toolBar, new VerticalLayoutData(1, -1));
	      conn.add(grid, new VerticalLayoutData(1, 1));
	      
	      panel.setWidget(conn);
	    }	    
	 
	    return panel;
	  }

	protected void Filtrar(String text) {		
		store.clear();			
		for (int i = 0; i < sermones.size(); i++) {
			if(sermones.get(i).getName().toLowerCase().contains(text.toLowerCase()))
				store.add(sermones.get(i));
		}
	}
	
	protected void Remover(int id) {		
		store.clear();			
		for (int i = 0; i < sermones.size(); i++) {
			if(sermones.get(i).getId()== id)		
			{
				sermones.remove(i);
				break;
			}	
		}				
		store.addAll(sermones);			
	}
	public interface Images extends ClientBundle {
		public Images INSTANCE = GWT.create(Images.class);
		  
		  @Source("Play.png")
		  ImageResource play();
		  
		  @Source("Edit.png")
		  ImageResource edit();
		  
		  @Source("Delete.png")
		  ImageResource delete();
		  
		  @Source("Download.png")
		  ImageResource download();
		  
		  @Source("Pause.png")
		  ImageResource pause();
		}	

	 private void createColumnForm(final int id, String name, String name_predicador, String descripcion, String seriee, int duracion, String date) {
		    FramedPanel panell = new FramedPanel();
		    panell.setHeadingText("Nuevo Sermon");
		    panell.setWidth(COLUMN_FORM_WIDTH);
		 
		    HtmlLayoutContainer con = new HtmlLayoutContainer(getTableMarkup());
		    panell.add(con, new MarginData(15));
		 
		    int cw = ((COLUMN_FORM_WIDTH - 30)/ 2) - 12;
		 
		    final TextField Name = new TextField();
		    Name.setAllowBlank(false);
		    Name.setWidth(cw);
		    Name.setText(name);
		    con.add(new FieldLabel(Name, "Nombre"), new HtmlData(".name"));
		 
		    final TextField Name_of_predicador = new TextField();
		    Name_of_predicador.setAllowBlank(false);
		    Name_of_predicador.setWidth(cw);
		    Name_of_predicador.setText(name_predicador);
		    con.add(new FieldLabel(Name_of_predicador, "Nombre del Predicador"), new HtmlData(".np"));
		 
		    final TextField serie = new TextField();
		    serie.setWidth(cw);
		    serie.setText(seriee);
		    con.add(new FieldLabel(serie, "Serie"), new HtmlData(".serie"));	 	   
		 
		    final DateField Date = new DateField();
		    Date.setWidth(cw);	 
		    Date.setText(date);
		    con.add(new FieldLabel(Date, "Fecha"), new HtmlData(".date"));
		 
		    final HtmlEditor a = new HtmlEditor();	   
		    a.setWidth(COLUMN_FORM_WIDTH - 25 - 30);
		    a.setValue(descripcion);
		    con.add(new FieldLabel(a, "Descripcccion"), new HtmlData(".editor"));
		    
		    final FileUploadField file = new FileUploadField();	 
		    file.setWidth(cw);
		    con.add(new FieldLabel(file, "File"), new HtmlData(".file"));
		   /* file.addChangeHandler(new ChangeHandler() {
		    	 
		        @Override
		        public void onChange(ChangeEvent event) {
		          Info.display("File Changed", "You selected " + file.getValue());
		        }
		      });
		    file.setName("uploadedfile");
		    file.setAllowBlank(false);*/
		 	   
		    panell.addButton(new TextButton("Cancel",new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					panel.setWidget(conn);
				}
			}));
		    panell.addButton(new TextButton("Submit",new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {									
				  final AutoProgressMessageBox box = new AutoProgressMessageBox("En progreso", "Guardando sermon, por favor espere...");
	  	          box.setProgressText("Guardando...");
	  	          box.auto();		    	         
	  	          box.show();
	  	          
	  	          	updateservice.UpdateService(id, Name.getText(), Name_of_predicador.getText(), serie.getText(), a.getValue(),Date.getCurrentValue(), new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(final String result) {
							 Timer t = new Timer() {
				    	            @Override
				    	            public void run() {		    	            	
				    	              Info.display("Mensaje", "Sermon editado con exito!");
				    	              box.hide();
				    	            }
				    	          };
				    	          t.schedule(2500);				    	        
				    	          ActualizarSermones(id, Name.getText(), Name_of_predicador.getText(), serie.getText(), a.getValue(),result);
							
						}
										

						@Override
						public void onFailure(Throwable caught) {
							Info.display("Mensaje", "Error al editar el sermon!");
							 box.hide();
						}
					});					
				}
			}));	    	    	 
			
			
		    // need to call after everything is constructed
		    List<FieldLabel> labels = FormPanelHelper.getFieldLabels(panell);
		    for (FieldLabel lbl : labels) {
		      lbl.setLabelAlign(LabelAlign.TOP);
		    }
		 
		    vp.add(panell);
		  }
		  
		  private native String getTableMarkup() /*-{
		    return [ '<table width=100% cellpadding=0 cellspacing=0>',
		        '<tr><td class=name width=50%></td><td class=np width=50%></td></tr>',
		        '<tr><td class=date></td><td class=serie></td></tr>',
		        '<tr><td class=file></td></tr>',	        
		        '<tr><td class=editor colspan=2></td></tr>', '</table>'	 
		    ].join("");
		  }-*/;	 			
			
		  private void ActualizarSermones(int id, String name,String name_predicador, String serie, String descripcion,String date) { 
			  panel.setWidget(conn);
			    store.clear();			
				for (int i = 0; i < sermones.size(); i++) {
					if(sermones.get(i).getId()== id)
					{
						sermones.get(i).setName(name);
						sermones.get(i).setName_of_predicador(name_predicador);
						sermones.get(i).setSerie(serie);
						sermones.get(i).setDescription(descripcion);
						sermones.get(i).setDate(date);
						break;
					}
				}				
				store.addAll(sermones);					
			}
}
