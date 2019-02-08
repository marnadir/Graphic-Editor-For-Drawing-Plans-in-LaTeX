package Draw2d;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.cairo.cairo_path_data_t;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class test1
{
   public static void main(String[] args) {
      new test1().run();
   }
   
   
   private void run() {
	   Shell shell = new Shell(new Display());
	   shell.setSize(365, 280);
	   shell.setText("Genealogy");
	   shell.setLayout(new GridLayout());

	   Canvas canvas = createDiagram(shell);
	   canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

	   Display display = shell.getDisplay();
	   shell.open();
	   while (!shell.isDisposed()) {
	      while (!display.readAndDispatch()) {
	         display.sleep();
	      }
	   }
	}
   
   private Canvas createDiagram(Composite parent) {

	   // Create a root figure and simple layout to contain
	   // all other figures
	   
	   
		Figure root = new Figure();
		root.setFont(parent.getFont());
		XYLayout layout = new XYLayout();
		root.setLayoutManager(layout);

		// Create a canvas to display the root figure
		Canvas canvas = new Canvas(parent, SWT.DOUBLE_BUFFERED);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		lws.setContents(root);

		IFigure andy = createPersonFigure("Andy");
		root.add(andy);
		layout.setConstraint(andy, new Rectangle(new Point(10, 10), andy.getPreferredSize()));
		
		IFigure betty = createPersonFigure("Betty");
		root.add(betty);
		layout.setConstraint(betty,
		new Rectangle(new Point(230, 10), betty.getPreferredSize()));
		
		
		
		
	   

//	   // Add the mother "Betty"

//
//	   // Add the son "Carl"
//	   IFigure carl = createPersonFigure("Carl");
//	   root.add(carl);
//	   layout.setConstraint(carl,
//	   new Rectangle(new Point(120, 120), carl.getPreferredSize()));
	   
	   
//	   IFigure marriage = createMarriageFigure();
//	   root.add(marriage,
//	      new Rectangle(new Point(145, 35),
//	      marriage.getPreferredSize()));
	   
//	   
//	   root.add(connect(andy, marriage));
//	   root.add(connect(betty, marriage));
//	   root.add(connect(carl, marriage));

	   return canvas;
	}
   
   
   
   private IFigure createPersonFigure(String name) {
	   RectangleFigure rectangleFigure = new RectangleFigure();
	   rectangleFigure.setBackgroundColor(ColorConstants.lightGray);
	   rectangleFigure.setLayoutManager(new ToolbarLayout());
	   rectangleFigure.setPreferredSize(100, 100);
	   rectangleFigure.add(new Label(name));
	   
	   
	   return rectangleFigure;
	   
	   
   }
   
   private IFigure createMarriageFigure() {
	   Rectangle r = new Rectangle(0, 0, 50, 50);
	   PolygonShape polygonShape = new PolygonShape();
	   polygonShape.setStart(r.getTop());
	   polygonShape.addPoint(r.getTop());
	   polygonShape.addPoint(r.getLeft());
	   polygonShape.addPoint(r.getBottom());
	   polygonShape.addPoint(r.getRight());
	   polygonShape.addPoint(r.getTop());
	   polygonShape.setEnd(r.getTop());
	   polygonShape.setFill(true);
	   polygonShape.setBackgroundColor(ColorConstants.lightGray);
	   polygonShape.setPreferredSize(r.getSize());
	   return polygonShape;
	}
   
   
   private Connection connect(IFigure figure1, IFigure figure2) {
	   PolylineConnection connection = new PolylineConnection();
	   
	   connection.setSourceAnchor(new ChopboxAnchor(figure1));
	   connection.setTargetAnchor(new ChopboxAnchor(figure2));
	   return connection;
	}
   
   
   
}
	   
	   
