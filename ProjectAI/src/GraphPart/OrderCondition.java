package GraphPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import LaTex.LaTexGeneratorAction;
import LaTex.LaTexGeneratorNode;

public class OrderCondition extends Canvas{
 
	GraphContent canvasContainer;
	Node cond1=null;
	Node cond2=null;
	Composite c1;
	Composite c2;
	Composite parent;
	String latexCode;

	
	public OrderCondition(Composite parent) {
		super(parent, SWT.ALL);
		this.canvasContainer=(GraphContent)parent.getParent();
		this.parent=parent;

		//parent.pack();
	}
	
	public void addlistener(Label l1) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
			
		}
	}
	
	public void removelistener(Label l1) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
		}
	}
	
	public Listener addOrdCond(Label l1,Composite comp) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (cond1 == null) {
					for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {
						
						canvasContainer.getActionInPlan().get(i).getClientArea();
						
						if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x,event.y))){
							c1=comp;

							cond1=canvasContainer.getActionInPlan().get(i);
							l1.setText(cond1.getAction().getName()+"<"+".....");
							l1.pack();
							
						}	
					}
				} else if (cond2 == null) {
					for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

						canvasContainer.getActionInPlan().get(i).getClientArea();

						if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
							c2=comp;

							cond2 = canvasContainer.getActionInPlan().get(i);
							l1.setText(cond1.getAction().getName() + "<" + cond1.getAction().getName());
							l1.pack();

						}
					}
				}
			}
		};
			
			return l;
	}
	
	public void drawOrder() {
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				e.gc.setLineWidth(1);

				Point p = new Point(cond1.getBounds().x + cond1.getBounds().width, cond1.getBounds().y - 20);
				Point p1 = c1.getParent().toControl(c1.toDisplay(p.x, p.y));

				p = new Point(cond2.getBounds().x, cond2.getBounds().y - 20);
				Point p2 = c2.getParent().toControl(c2.toDisplay(p.x, p.y));

//				Path path = new Path(canvasContainer.getDisplay());
//				path.moveTo((float) (p1.x), (float) (p1.y));
//				if (p1.y > p2.y) {
//					path.quadTo(p1.y, p2.x, p2.x, p2.y);
//				} else {
//					path.quadTo(p2.x, p1.y, p2.x, p2.y);
//
//				}
//				path.quadTo(p2.x, p1.y, p2.x, p2.y);
//				e.gc.drawPath(path);

				//parent.setSize(p2.x - p1.x, 40);
				parent.setLocation(p1.x, p1.y - 30);
				
				Image img = new Image(parent.getDisplay(), "img/ord.png");
//				img.setBackground(canvasContainer.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
				//e.gc.drawImage(img, 0, 0);
				e.gc.drawImage(img, 0, 0, img.getImageData().width, img.getImageData().height, 0, 0, p2.x- p1.x, 40);
				
			
			    img.dispose();

				
				//parent.layout();
			    //parent.pack();
				
			    
			    
			}
		});
	
		
		

	}

	public Node getCond1() {
		return cond1;
	}

	public Node getCond2() {
		return cond2;
	}
	
	public void generateLatexCode() {
		LaTexGeneratorNode generator=new LaTexGeneratorNode();
		latexCode=generator.getLatexOrderCodePlan(this);
	
		
	}

	public String getLatexCode() {
		return latexCode;
	}
	
}
