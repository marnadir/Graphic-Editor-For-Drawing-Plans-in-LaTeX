package DragDrop;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Test9 {
	
	public static Canvas canvas;
	public static Canvas canvas2;
	
	
    public static void main(String[] args) {
        // setup the SWT window
    	
    	
    	
    	
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setText("Photo Shuffler");

        // initialize a parent composite with a grid layout manager
        Composite parent = new Composite(shell, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth=20;
        parent.setLayout(gridLayout);

        // determine the path where the pictures are stored
        // initialize an array with the photograph names
        Group g1=new Group(parent, SWT.ALL);
        g1.setText("Group1");
        canvas=new Canvas(g1, SWT.ALL);
        canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {

				String s="sd"+parent.getChildren().length;
				e.gc.drawString(s, 10, 10);
			}
		});
        canvas.pack();
        
        
        Group g2=new Group(parent, SWT.ALL);
        g2.setText("Group2");
       canvas2=new Canvas(g2, SWT.ALL);
        canvas2.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {

			}
		});
        
        canvas2.pack();
        
        Control[] con=new Control[2];
        con[0]=canvas;
        con[1]=canvas2;
        
        for(int i=0;i<con.length;i++) {
        	
            DragSource source = new DragSource(con[i], DND.DROP_NONE);
            source.setTransfer(TextTransfer.getInstance()); // varargs are supported as of 4.7
            source.addDragListener(new MyDragSourceListener(parent, source));

            DropTarget target = new DropTarget(con[i], DND.DROP_NONE);
            target.setTransfer(new Transfer[] { TextTransfer.getInstance() }); // varargs are not yet supported see https://git.eclipse.org/r/#/c/92236         // add a drop listener
            target.addDropListener(new MyDropTargetListener(parent, target));        // add a drop listener
        }

        // show the SWT window
        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        // tear down the SWT window
        display.dispose();
    }

    private static class MyDragSourceListener extends DragSourceAdapter {

        private Composite parentComposite;
        private DragSource source;
        private Control[] c=new Control[2];

        /**
         * @param parentComposite - the composite that holds all pictures
         * @param source - the drag source
         *
         */
        public MyDragSourceListener(Composite parentComposite, DragSource source) {
            this.parentComposite = parentComposite;
            this.source = source;
            Control[] te=parentComposite.getChildren();
            for (int i = 0; i < te.length; i++) {
           	 	if(te[i] instanceof Group) {
           	 		Group g=(Group)te[i];
           	 		c[i]=g.getChildren()[0];
           	 	}
            	
            }
        }

        /**
         * The method computes the position / index of the source control
         * (label) in the children array of the parent composite. This index is
         * passed to the drop target using the data field of the drag source
         * event.
         */
        public void dragSetData(DragSourceEvent event) {
             for (int i = 0; i < c.length; i++) {
            	 
                if (c[i].equals(source.getControl())) {
                    event.data = new Integer(i).toString();
                    break;
                }
            }
        }

    }


public static class MyDropTargetListener extends DropTargetAdapter {

    private Composite parentComposite;
    private DropTarget target;
    private Control[] c=new Control[2];


    /**
     * @param parentComposite - the composite that holds all pictures
     * @param target - the drop target
     */
    public MyDropTargetListener(Composite parentComposite, DropTarget target) {
        this.parentComposite = parentComposite;
        this.target = target;
        Control[] te=parentComposite.getChildren();
        for (int i = 0; i < te.length; i++) {
       	 	if(te[i] instanceof Group) {
       	 		Group g=(Group)te[i];
       	 		c[i]=g.getChildren()[0];
       	 	}
        	
        }
    }

    /**
     * This method moves the dragged picture to the new position and shifts the
     * old picture to the right or left.
     */
    public void drop(DropTargetEvent event) {

        // retrieve the stored index
        int sourceIndex = Integer.valueOf(event.data.toString());

        // compute the index of target control
        Control targetControl = target.getControl();
        int targetIndex = -1;
        for (int i = 0; i < c.length; i++) {
            if (c[i].equals(targetControl)) {
                targetIndex = i;
                break;
            }
        }

        Control sourceControl = c[sourceIndex];
        // do not do anything if the dragged photo is dropped at the same
        // position
        if (targetIndex == sourceIndex)
            return;

        // if dragged from left to right
        // shift the old picture to the left
        if (targetIndex > sourceIndex) {
        	//targetControl.setParent((Composite) parentComposite.getChildren()[0]);
            sourceControl.moveBelow(targetControl);
     
        // if dragged from right to left
        // shift the old picture to the right
        } else
            sourceControl.moveAbove(targetControl);

        // repaint the parent composite
        parentComposite.requestLayout();
    }

}
}