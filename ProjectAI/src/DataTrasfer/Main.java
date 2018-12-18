package DataTrasfer;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import Action.Action;

public class Main {
	 public static void main(String[] args) {
		    Display display = new Display();
		    Shell shell = new Shell(display);
		    shell.setLayout(new FillLayout());
		    final Label label1 = new Label(shell, SWT.BORDER | SWT.WRAP);
		    label1.setText("Drag Source for MyData[]");
		    final Label label2 = new Label(shell, SWT.BORDER | SWT.WRAP);
		   

		    
		    
		    DragSource source = new DragSource(label1, DND.DROP_COPY);
		    source.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		    
		    source.addDragListener(new DragSourceAdapter() {
		      public void dragSetData(DragSourceEvent event) {
		       
		    	MyType myType1 = new MyType();
		    	
		    	ArrayList<String> prec=new ArrayList<>();
		    	prec.add("ciao");
		    	prec.add("ciao2");
		    	
		    	
		    	ArrayList<String> eff=new ArrayList<>();
		    	eff.add("hello");
		    	
		    	Action action=new Action("first action", prec, eff);

		        myType1.name = action.getName();
		        myType1.prec=action.getPrec();
		        myType1.eff = action.getEffect();
		        
		    
		        event.data = new MyType[] { myType1};
		      }
		    });
		    
		    
		    DropTarget target = new DropTarget(label2, DND.DROP_COPY | DND.DROP_DEFAULT);
		    target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		    target.addDropListener(new DropTargetAdapter() {
		    	
		    	
		      public void dragEnter(DropTargetEvent event) {
		        if (event.detail == DND.DROP_DEFAULT) {
		          event.detail = DND.DROP_COPY;
		        }
		      }

		      public void dragOperationChanged(DropTargetEvent event) {
		        if (event.detail == DND.DROP_DEFAULT) {
		          event.detail = DND.DROP_COPY;
		        }
		      }

		      public void drop(DropTargetEvent event) {
		        if (event.data != null) {
		          MyType[] myTypes = (MyType[]) event.data;
		          if (myTypes != null) {
		            String string = "";
		            for (int i = 0; i < myTypes.length; i++) {
		              string += myTypes[i].name + " ";
		              
		              for(int j=0;j< myTypes[i].prec.size();j++) {
		            	  string +=myTypes[i].prec.get(j);
		              }
		              
		            }
		            label2.setText(string);
		          }
		        }
		      }

		    });
		    
		    
		    
		    
		    
		    shell.setSize(200, 200);
		    shell.open();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep();
		    }
		    display.dispose();
		  }
}
