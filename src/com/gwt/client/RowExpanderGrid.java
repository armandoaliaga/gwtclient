package com.gwt.client;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;

public class RowExpanderGrid implements IsWidget {
	 
	  private static final SermonProperties props = GWT.create(SermonProperties.class);
	  private ContentPanel panel;
	  private final ArrayList<Sermon> sermones;
	  private ListStore<Sermon> store;
	  final DeleteSermonServiceAsync deleteservice= GWT.create(DeleteSermonService.class);
	  
	  public RowExpanderGrid(ArrayList<Sermon> sermonesgrid)
	  {
		  sermones=sermonesgrid;
	  }
	  
	  @Override
	  public Widget asWidget() {
	    if (panel == null) {
	      final NumberFormat number = NumberFormat.getFormat("0.00");	      
	      RowExpander<Sermon> expander = new RowExpander<Sermon>(new AbstractCell<Sermon>() {
	        @Override
	        public void render(Context context, Sermon value, SafeHtmlBuilder sb) {
	          //sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b>" + value.getName() + "</p>");
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
	      /*ColumnConfig<Sermon, Date> lastTransCol = new ColumnConfig<Sermon, Date>(props.date(), 121, "Fecha (yyyy-mm-dd)");
	      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));*/
	 
	      
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
	          Info.display("Event Play", "The " + p.getId() + " was clicked.");	          	          
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
	          Info.display("Event Edit", "The " + p.getId() + " was clicked.");
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
	          final ConfirmMessageBox box = new ConfirmMessageBox("Mensaje", "Esta suguro de eliminar el sermon '"+p.getName()+"'?");
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
	      l.add(editcolumn);
	      l.add(deletecolumn);
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
	      
	      VerticalLayoutContainer con = new VerticalLayoutContainer();
	      con.setBorders(true);
	      con.add(toolBar, new VerticalLayoutData(1, -1));
	      con.add(grid, new VerticalLayoutData(1, 1));
	      
	      panel.setWidget(con);
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
				sermones.remove(i);	
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
		}	
}
