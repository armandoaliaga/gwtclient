package com.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwt.client.RowExpanderGrid.Images;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent.CellClickHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
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
	 
	public HimnosView(ArrayList<Himno> himnosgrid)
	{
		himnos=himnosgrid;
	}
	
	@Override
	public Widget asWidget() {
		if (panel == null) {
			
			ColumnConfig<Himno, Integer> numbercol = new ColumnConfig<Himno, Integer>(props.number(), 50, "Numero"); 
			ColumnConfig<Himno, String> nameCol = new ColumnConfig<Himno, String>(props.name(), 250, "Titulo");
			ColumnConfig<Himno, String> playcolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");
		    ColumnConfig<Himno, String> editcolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");
		    ColumnConfig<Himno, String> deletecolumn = new ColumnConfig<Himno, String>(props.aux(), 40, "");	
			
		    
		    //Button Play
		      final TextButtonCell buttonPlay = new TextButtonCell();
		      buttonPlay.setIconAlign(IconAlign.LEFT);
		      buttonPlay.setIcon(Images.INSTANCE.play());
		      buttonPlay.addSelectHandler(new SelectHandler() {
		 
		        @Override
		        public void onSelect(SelectEvent event) {
		          Context c = event.getContext();
		          int row = c.getIndex();
		          Himno p = store.get(row);	          
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
		          Himno p = store.get(row);	          	          
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
		    l.add(playcolumn);
		    l.add(editcolumn);
		    l.add(deletecolumn); 
		    ColumnModel<Himno> cm = new ColumnModel<Himno>(l);
		    store = new ListStore<Himno>(props.key());			   
			store.addAll(himnos);	
			
			
			
			
		      
		      
			panel = new ContentPanel();
			panel.setHeadingText("Himnos");
	      //panel.getHeader().setIcon(ExampleImages.INSTANCE.table());
			panel.setPixelSize(1141, 598);
			panel.addStyleName("margin-10");
			
			
			final HtmlEditor htmleditor= new HtmlEditor();			
			htmleditor.setEnableAlignments(false);
			htmleditor.setEnableColors(false);			
			htmleditor.setEnableFont(false);
			htmleditor.setEnableFontSize(false);
			htmleditor.setEnableFormat(false);
			htmleditor.setEnableLinks(false);
			htmleditor.setEnableLists(false);
			htmleditor.setEnableSourceEditMode(false);				
			htmleditor.setValue("<textarea rows='34' cols='92' disabled align='center' style='text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >Seleccione un himno para ver la letra.</textarea>");								
		
			final Grid<Himno> grid = new Grid<Himno>(store, cm);
		      grid.getView().setAutoExpandColumn(nameCol);
		      grid.setBorders(false);
		      grid.getView().setStripeRows(true);
		      grid.getView().setColumnLines(true);		     
		      
		      grid.addCellClickHandler(new CellClickHandler() {
				
				@Override
				public void onCellClick(CellClickEvent event) {
					Himno selected_himno = grid.getStore().get(event.getRowIndex());					
					htmleditor.setValue("<textarea rows='34' cols='92' disabled align='center' style='text-align: center;color:#0B0B61;vertical-align: middle; display: block;margin-left: auto;margin-right: auto;font-weight: bold' >"+selected_himno.getLyrics()+"</textarea>");					
				}
			});
			
			
			hlcontainer = new HorizontalLayoutContainer();
			hlcontainer.setBorders(false);		
			hlcontainer.add(grid, new HorizontalLayoutData(-1, 1));
			hlcontainer.add(htmleditor, new HorizontalLayoutData(1, 1));
			
			
			
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
	protected void Filtrar(String text) {		
		store.clear();			
		for (int i = 0; i < himnos.size(); i++) {
			if(himnos.get(i).getName().toLowerCase().contains(text.toLowerCase()))
				store.add(himnos.get(i));
		}
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
