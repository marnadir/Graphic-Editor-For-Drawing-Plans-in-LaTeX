package DragDrop;


import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Test7 {

  public static void main(String[] args) {

    Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());
    Canvas c1=new Canvas(shell, SWT.BORDER);
    PaintListener paintListener= new PaintListener() {
		
		@Override
		public void paintControl(PaintEvent e) {
			e.gc.drawString("saasd", 10, 10);
			
		}
		
	};
    c1.addPaintListener(paintListener);

    Canvas c2 = new Canvas(shell, SWT.BORDER);
    Listener [] ls=c1.getListeners(SWT.Paint);
    
 
    
    
    setDragDrop(c1);
    setDragDrop(c2);
    shell.setSize(200, 200);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

  public static void setDragDrop(final Canvas canvas) {

    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
    int operations = DND.DROP_COPY | DND.DROP_LINK;
    

    final DragSource source = new DragSource(canvas, operations);
    source.setTransfer(types);
    source.addDragListener(new DragSourceListener() {
      public void dragStart(DragSourceEvent event) {
       // event.doit = (label.getText().length() != 0);
      }

      public void dragSetData(DragSourceEvent event) {
       // event.data = label.getText();
    	  event.data=canvas.getData();
      }

      public void dragFinished(DragSourceEvent event) {
//        if (event.detail == DND.DROP_MOVE)
//          label.setText("");
      }
    });

    DropTarget target = new DropTarget(canvas, operations);
    target.setTransfer(types);
    target.addDropListener(new DropTargetAdapter() {
      public void drop(DropTargetEvent event) {
        if (event.data == null) {
          event.detail = DND.DROP_NONE;
          return;
        }
        canvas.setData(event.data);
      }
    });
  }
}