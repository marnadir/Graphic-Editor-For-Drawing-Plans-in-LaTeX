package PlanPart;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

import LaTex.LaTexGeneratorNode;

public class LinkCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5962744944510049128L;
	Point p1 = null;
	Point p2 = null;

	Oval oval1 = null;
	Oval oval2 = null;

	boolean isRight;

	Composite c1;
	Composite c2;

	PlanContent canvasContainer;

	String latexCode;

	public LinkCanvas(PlanContent parent) {
		this.canvasContainer = parent;

		// TODO Auto-generated constructor stub
	}

	public void setLine(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void addlistener(Label l1, Label l2, Button btn) {

		for (int i = 0; i < canvasContainer.getChildren().length; i++) {
			if (!(canvasContainer.getChildren()[i] instanceof Button)) {
				Composite comp = (Composite) canvasContainer.getChildren()[i];
				comp.setEnabled(true);
				if (comp instanceof Oval) {
					// comp.addListener(SWT.Selection, addLink(l1, l2, comp, btn));
					Oval oval = (Oval) comp;

				}
			}

		}
		canvasContainer.addListener(SWT.MouseDoubleClick, addLink(l1, l2, btn));

	}

	public Listener addLink(Label l1, Label l2, Button btn) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (l1.isDisposed() || l2.isDisposed()) {
					return;
				}

				if (!(l1.getText().contains("ordering"))) {
					if (oval1 == null) {
						int d = canvasContainer.getOvalCounter().getListOval().size();
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p = canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11)
									&& (p.y - 11 < event.y && event.y < p.y + 11)) {

								oval1 = canvasContainer.getOvalCounter().getListOval().get(i);
//								c1 = comp;
								l1.setText("First Cond. :" + oval1.getCond());
								l1.pack();

							}
						}
					} else if (oval2 == null) {
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p = canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11)
									&& (p.y - 11 < event.y && event.y < p.y + 11)) {
								if (canvasContainer.getOvalCounter().getListOval().get(i).getNode() != oval1
										.getNode()) {
									oval2 = canvasContainer.getOvalCounter().getListOval().get(i);
//									c2 = comp;
									l2.setText("Second Cond. :" + oval2.getCond());
									l2.pack();
								}
//								drawLine();

							}
						}
					}
				}

			}
		};

		return l;
	}

	public void drawLine() {
		canvasContainer.addPaintListener(getListener());

	}

	public void removeL() {
		canvasContainer.removePaintListener(getListener());
	}

	public void addClearListener() {
		canvasContainer.addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {

				Menu m = new Menu(canvasContainer);
				canvasContainer.setMenu(m);
				 
				 Point pMenu=((Control)e.widget).toControl(e.x, e.y);
				
				
				canvasContainer.getActionInPlan().get(0).getCursor();



				MenuItem c = new MenuItem(m, SWT.CHECK);
				c.setText("Clear Link");

				c.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						

						
						ArrayList<LinkCanvas> links=canvasContainer.getLink();
						for(int i=0;i<links.size();i++) {
							Point p1 = links.get(i).getOval1().getP();
							Point p2 = links.get(i).getOval2().getP();
							System.out.println(pMenu+" "+p1);
							
							if ((p1.x - 11 < pMenu.x && pMenu.x < p1.x + 11)
									&& (p1.y - 11 < pMenu.y && pMenu.y < p1.y + 11)) {
								links.get(i).setOval1(null);
								links.get(i).setOval1(null);
								canvasContainer.getLink().remove(links.get(i));
								MessageBox messageBox = new MessageBox(canvasContainer.getShell(),
										SWT.ICON_WARNING |  SWT.OK);

								messageBox.setText("Clear Link");
								messageBox.setMessage("Link removed");
								messageBox.open();
								
							}
							
							if ((p2.x - 11 < pMenu.x && pMenu.x < p2.x + 11)
									&& (p2.y - 11 < pMenu.y && pMenu.y < p2.y + 11)) {
								links.get(i).setOval1(null);
								links.get(i).setOval1(null);
								canvasContainer.getLink().remove(links.get(i));
								MessageBox messageBox = new MessageBox(canvasContainer.getShell(),
										SWT.ICON_WARNING |  SWT.OK);

								messageBox.setText("Clear Link");
								messageBox.setMessage("Link removed");
								messageBox.open();
								
							}
						}

					}
				});

			}
		});
	}

	public PaintListener getListener() {
		PaintListener p;
		addClearListener();

		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				e.gc.setLineWidth(1);
				if (oval1 != null && oval2 != null) {
					Point p = oval1.getP();
					p1 = p;
					p = oval2.getP();
					p2 = p;

					if (p1.y < p2.y + 7 && p1.y > p2.y + 1) {

						int distance = (int) Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));

						e.gc.drawArc(p1.x, p1.y - (distance / 6), distance, distance / 3, 0, 180);
						isRight = false;

					} else if (p1.y < p2.y - 1 && p1.y > p2.y - 7) {

						int distance = (int) Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
						e.gc.drawArc(p1.x, p1.y - (distance / 6), distance, distance / 3, 0, -180);
						isRight = true;

					} else {
						e.gc.setLineWidth(1);
						Path path = new Path(canvasContainer.getDisplay());
						path.moveTo((float) (p1.x), (float) (p1.y));
						Point temp1 = p1;
						Point temp2 = p2;

						if (p1.y > p2.y) {
							Point temp = null;
							temp = temp1;
							temp1 = temp2;
							temp2 = temp;
							path.quadTo(temp1.x, temp2.y, temp1.x, temp1.y);
							isRight = true;

						} else {
							path.quadTo(temp2.x, temp1.y, temp2.x, temp2.y);
							isRight = false;

						}

						e.gc.drawPath(path);

					}
				}
				canvasContainer.update();
				canvasContainer.setRedraw(true);
				canvasContainer.layout();
				removeL();
			}

		};
		return p;
	}

	public Oval getOval1() {
		return oval1;
	}

	public Oval getOval2() {
		return oval2;
	}

	public void generateLatexCode() {
		LaTexGeneratorNode generator = new LaTexGeneratorNode();
		latexCode = generator.getLatexLinkCodePlan(this);

	}

	public String getLatexCode() {
		return latexCode;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setOval1(Oval oval1) {
		this.oval1 = oval1;
	}

	public void setOval2(Oval oval2) {
		this.oval2 = oval2;
	}
	
	

}
