


import org.eclipse.draw2d.CoordinateListener;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import javafx.scene.input.MouseDragEvent;


public class CTabForPlan{

	public static void main(String[] args) {
		Display display = new Display();

		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		
		final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		folder.setSimple(false);
		folder.setUnselectedImageVisible(false);
		folder.setUnselectedCloseVisible(false);

		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item ");
		Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("vaffanculo!!!");
		item.setControl(text);
		
		folder.setMinimizeVisible(true);
		folder.setMaximizeVisible(true);
		

		ToolBar t = new ToolBar( folder, SWT.FLAT ); 
		ToolItem i = new ToolItem( t, SWT.PUSH ); 
		i.setToolTipText("add a new Plan");
	    Image icon = new Image(shell.getDisplay(), "add-documents.png");
		i.setImage(icon); 
		i.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent var1) {

				CTabItem item2 = new CTabItem(folder, SWT.CLOSE);
				item2.setText("plan2");
			
				Color color = display.getSystemColor(SWT.COLOR_CYAN);

				
				FigureCanvas canvas = new FigureCanvas(folder, SWT.NONE);
				
				Composite comp=new Composite(canvas, SWT.BORDER);

				
				RoundedRectangle fig=new RoundedRectangle();
				
				Point point=new Point();
				point.setLocation(50, 50);
				
				
				
				Label label=new Label();
				label.setVisible(true);
				label.setText("Goto()");
				
				
				
				fig.setLocation(point);
				fig.setSize(122, 80);
				
				point.setLocation(fig.getClientArea().getCenter().x, fig.getClientArea().getCenter().y);	
				label.setLocation(point);
				label.setSize(50, 50);
				
				
				//getLine sx
				
				int x,y;
				
				int tempX=fig.getClientArea().x;
				
				int tempy=fig.getClientArea().y+fig.getClientArea().width;
				Point end=new Point(tempX,tempy/2);
				Point start=new Point(tempX-30,tempy/2);
				
				
				
				Polyline pol=new Polyline();
				pol.setLineWidth(2);
				pol.setStart(start);
				pol.setEnd(end);
				canvas.getViewport().add(pol);
			    
				fig.add(label);
				
				
				setDragDrop(canvas);
				
				canvas.getViewport().add(fig);
				item2.setControl(canvas);
				
				

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent var1) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		folder.setTopRight( t, SWT.RIGHT ); 
		
		folder.setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, 
				SWT.DEFAULT).y, folder.getTabHeight())); 
		
		
	
		folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void minimize(CTabFolderEvent event) {
				folder.setMinimized(true);
				folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				shell.layout(true);
			}

			@Override
			public void maximize(CTabFolderEvent event) {
				folder.setMaximized(true);
				folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
				shell.layout(true);
			}

			@Override
			public void restore(CTabFolderEvent event) {
				folder.setMinimized(false);
				folder.setMaximized(false);
				folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
				shell.layout(true);
			}
			@Override
			public void showList(CTabFolderEvent event) {
				// TODO Auto-generated method stub
				super.showList(event);
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	public static void setDragDrop (final Composite comp) {
		Transfer[] types = new Transfer[] {LocalSelectionTransfer.getTransfer()};
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final DragSource source = new DragSource (comp, operations);
		source.setTransfer(types);
		source.addDragListener (new DragSourceListener () {
			Point selection;
			@Override
			public void dragStart(DragSourceEvent e) {
				if(comp instanceof FigureCanvas) {
					FigureCanvas canvas=(FigureCanvas)comp;
					selection = canvas.getViewport().getViewLocation();
					e.doit = selection.x != selection.y;
				}
				
			}

			@Override
			public void dragSetData(DragSourceEvent e) {
				if (comp instanceof FigureCanvas) {
					FigureCanvas canvas = (FigureCanvas) comp;
					selection = canvas.getViewport().getViewLocation();
					e.data = canvas.getViewport();
				}

			}
			@Override
			public void dragFinished(DragSourceEvent e) {
				if (e.detail == DND.DROP_MOVE) {
					if (comp instanceof FigureCanvas) {
						FigureCanvas canvas = (FigureCanvas) comp;
						canvas.getViewport().setViewLocation(selection);
					}
					
				}
				selection = null;
			}
		});

		DropTarget target = new DropTarget(comp, operations);
		target.setTransfer(types);
		target.addDropListener (new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				
				//text.append((String) event.data);
			}
		});
	}
	
	
}






