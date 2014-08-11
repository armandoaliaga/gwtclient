package com.gwt.client;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.LocalizableResource.Key;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class RowExpanderGrid implements IsWidget {
	 
	  private static final SermonProperties props = GWT.create(SermonProperties.class);
	  private ContentPanel panel;
	  private final ArrayList<Sermon> sermones;
	  private ListStore<Sermon> store;
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
	          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Reproducir:</b>  <button>Play</button> ");
	        }
	      });
	 
	      ColumnConfig<Sermon, String> nameCol = new ColumnConfig<Sermon, String>(props.name(), 200, "Nombre");
	      ColumnConfig<Sermon, String> symbolCol = new ColumnConfig<Sermon, String>(props.name_of_predicador(), 270, "Predicador");
	      ColumnConfig<Sermon, String> lastCol = new ColumnConfig<Sermon, String>(props.serie(), 190, "Serie");
	 
	      ColumnConfig<Sermon, Integer> changeCol = new ColumnConfig<Sermon, Integer>(props.duration(), 120, "Duracion");
	      changeCol.setCell(new AbstractCell<Integer>() {
	 
	        @Override
	        public void render(Context context, Integer value, SafeHtmlBuilder sb) {
	          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
	          String v = number.format(value);
	          sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + v + "'>" + v + "</span>");
	        }
	      });
	 
	      ColumnConfig<Sermon, String> lastTransCol = new ColumnConfig<Sermon, String>(props.date(), 121, "Fecha (yyyy-mm-dd)");
	      /*ColumnConfig<Sermon, Date> lastTransCol = new ColumnConfig<Sermon, Date>(props.date(), 121, "Fecha (yyyy-mm-dd)");
	      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));*/
	 
	      List<ColumnConfig<Sermon, ?>> l = new ArrayList<ColumnConfig<Sermon, ?>>();
	      l.add(expander);
	      l.add(nameCol);
	      l.add(symbolCol);
	      l.add(lastCol);
	      l.add(changeCol);
	      l.add(lastTransCol);
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
				ActualizarGrid(search.getText());
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

	protected void ActualizarGrid(String text) {		
		store.clear();			
		for (int i = 0; i < sermones.size(); i++) {
			if(sermones.get(i).getName().toLowerCase().contains(text.toLowerCase()))
				store.add(sermones.get(i));
		}
	}
}
