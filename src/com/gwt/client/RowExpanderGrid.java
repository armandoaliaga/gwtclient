package com.gwt.client;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.RowExpander;

public class RowExpanderGrid implements IsWidget {
	 
	  private static final SermonProperties props = GWT.create(SermonProperties.class);
	  private ContentPanel panel;
	  private final ArrayList<Sermon> sermones;
	  public RowExpanderGrid(ArrayList<Sermon> sermonesgrid)
	  {
		  sermones=sermonesgrid;
	  }
	  
	  @Override
	  public Widget asWidget() {
	    if (panel == null) {
	      final NumberFormat number = NumberFormat.getFormat("0.00");
	      final String desc = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.";
	 
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
	 
	      ColumnConfig<Sermon, Date> lastTransCol = new ColumnConfig<Sermon, Date>(props.date(), 121, "Fecha");
	      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));
	 
	      List<ColumnConfig<Sermon, ?>> l = new ArrayList<ColumnConfig<Sermon, ?>>();
	      l.add(expander);
	      l.add(nameCol);
	      l.add(symbolCol);
	      l.add(lastCol);
	      l.add(changeCol);
	      l.add(lastTransCol);
	      ColumnModel<Sermon> cm = new ColumnModel<Sermon>(l);
	 
	      ListStore<Sermon> store = new ListStore<Sermon>(props.key());	      
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
	      panel.setWidget(grid);
	    }
	 
	    return panel;
	  }
}
