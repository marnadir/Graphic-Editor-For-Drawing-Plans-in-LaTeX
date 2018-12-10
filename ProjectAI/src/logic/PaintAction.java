package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

public class PaintAction {

	public Action action;
	public Composite compositeContent;
	int max;
	boolean shownCond = false;
	boolean shownName = true;
	int widthRect;
	int lengthPrec;
	int lengthEff;
	int heightRect = 40;
	int standardLengthEff=30; //Standard lenght of effect line 
	int standardLengthPrec=30;
	boolean defaultValuePrecLenght=true;
	boolean defaultValueEffLenght=true;
	public Canvas canvas;
	
	public PaintAction(Action action,Composite composite) {
		this.action=action;
		this.compositeContent=composite;
	}
	
	public void draw() {
		if (canvas != null) {
			if (!canvas.isDisposed()) {
				canvas.dispose();
			}
			canvas = new Canvas(compositeContent, SWT.ALL);
		} else {
			canvas = new Canvas(compositeContent, SWT.ALL);
			// canvas.layout();

		}
		widthRect = action.getName().length() * 12;
		
		
		canvas.addPaintListener(new PaintListener() {

			Canvas canvas=getCanvas();
			
			@Override
			public void paintControl(PaintEvent e) {

				int numPrec = action.getPrec().size();
				int numEff = action.getEffect().size();

				int startX = canvas.getLocation().x + 100;
				int startY = canvas.getLocation().y + 150;

				max = numPrec;
				if (numEff > numPrec) {
					max = numEff;
				}

				if (max > 1) {
					heightRect = 30 + max * 10;
				}

				
				Rectangle rect = new Rectangle(startX, startY, widthRect, heightRect);
				e.gc.drawRectangle(rect);

				if (shownName) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}

				int posY = rect.y + 10;
				
				
				int avergWidth = (int) e.gc.getFontMetrics().getAverageCharacterWidth();

				if(defaultValuePrecLenght) {
					lengthPrec=getLenght(action.getPrec())*avergWidth;
				}
				
				for (int i = 0; i < numPrec; i++) {

					if (shownCond) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(rect.x, posY, rect.x - (lengthPrec+12), posY);
						e.gc.drawString(string, rect.x - (8 + lengthPrec), posY - 20, false);				
						e.gc.setLineWidth(4);
						e.gc.drawOval( rect.x - (13 + lengthPrec), posY-2, 5, 5);
						e.gc.setLineWidth(0);
						System.out.println("qwsfd");
									
					} else {
						e.gc.drawLine(rect.x, posY, rect.x - standardLengthPrec, posY);	
						e.gc.setLineWidth(4);
						e.gc.drawOval( rect.x - (standardLengthPrec+4), posY-2, 5, 5);
						e.gc.setLineWidth(0);
						

					}

					posY = posY + 30;
				}

				posY = rect.y + 10;
				if(defaultValueEffLenght) {
					lengthEff=getLenght(action.getEffect())*avergWidth;
				}
				for (int i = 0; i < numEff; i++) {
					int x = rect.x + rect.width;

					if (shownCond) {
						String string = action.getEffect().get(i); 
						e.gc.drawLine(x, posY, x + lengthEff+15, posY);
						e.gc.drawString(string, x + 10, posY - 20, false);
						e.gc.setLineWidth(4);
						e.gc.drawOval(x + (16 + lengthEff), posY-2, 5, 5);
						e.gc.setLineWidth(0);
						
					}else {
							
						e.gc.drawLine(x, posY, x + standardLengthEff, posY);
						e.gc.setLineWidth(4);
						e.gc.drawOval(x+standardLengthEff+1, posY-2, 5, 5);
						e.gc.setLineWidth(0);
						//e.gc.drawString("dafd", x, posY);
						

					}

					posY = posY + 30;

				}

			}
		});

		canvas.setSize(compositeContent.getSize().x, compositeContent.getSize().y);

		canvas.addMenuDetectListener(new MenuDetectListener() {

			Canvas canvas=getCanvas();
			
			@Override
			public void menuDetected(MenuDetectEvent e) {
				Menu m = new Menu(canvas);
				canvas.setMenu(m);

				MenuItem c = new MenuItem(m, SWT.ALL);
				c.setText("Clear");
				c.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						clearDisplay();
					}
				});

				MenuItem showC = new MenuItem(m, SWT.ALL);
				showC.setText("Show/Hide Cond...");
				showC.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						shownCond = !shownCond;
						canvas.redraw();

					}
				});

				MenuItem showN = new MenuItem(m, SWT.ALL);
				showN.setText("Show/Hide Name..");
				showN.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						shownName = !shownName;
						canvas.redraw();

					}
				});

				MenuItem setSize = new MenuItem(m, SWT.CASCADE);
				setSize.setText("Set Size...");

				Menu subMenu = new Menu(m);
				setSize.setMenu(subMenu);

			
				
				
				MenuItem boxSize = new MenuItem(subMenu, SWT.ALL);
				boxSize.setText("Size Box");
				boxSize.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

							Text textWid;
							Text textHei;

							@Override
							public Listener getOkbtnListener() {
								return new Listener() {

									@Override
									public void handleEvent(Event event) {
										widthRect = Integer.parseInt(textWid.getText());
										heightRect = Integer.parseInt(textHei.getText());
										getDialog().setVisible(false);

									}
								};
							}

							@Override
							public void createContent() {
								this.getLabel().setText("set the Box-size: " + action.getName());
								this.getLabel().pack();
								Composite c = getComposite();
								c.setLayout(new GridLayout(2, false));

								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Width: ");
								textWid = new Text(c, SWT.BORDER);
								textWid.setText(Integer.toString(widthRect));
								textWid.setLayoutData(new GridData(40, 20));


								Label lHeight = new Label(c, SWT.ALL);
								lHeight.setText("Height: ");
								textHei = new Text(c, SWT.BORDER);
								textHei.setText(Integer.toString(heightRect));
								textHei.setLayoutData(new GridData(40, 20));

								Label info = new Label(c, SWT.BORDER);
								info.setText("the default size is: " + widthRect + "x" + heightRect);
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
								this.getDialog().pack();

							}
						};
						dialog.createContent();
					}
				});
				
				
				MenuItem precSize = new MenuItem(subMenu, SWT.ALL);
				precSize.setText("Size Precondition lines");
				precSize.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

							Text textWid;

							@Override
							public Listener getOkbtnListener() {
								return new Listener() {

									@Override
									public void handleEvent(Event event) {
										
										if (shownCond) {
											defaultValuePrecLenght = false;
											lengthPrec = Integer.parseInt(textWid.getText());
										} else {
											standardLengthPrec = Integer.parseInt(textWid.getText());

										}
										
										getDialog().setVisible(false);

									}
								};
							}

							@Override
							public void createContent() {
								this.getLabel().setText("set the PrecLine-size of the action: " + action.getName());
								this.getLabel().pack();
								Composite c = getComposite();
								c.setLayout(new GridLayout(2, false));

								if(shownCond) {
									Label lWidth = new Label(c, SWT.ALL);
									lWidth.setText("Lenght: ");
									textWid = new Text(c, SWT.BORDER);
									textWid.setText(Integer.toString(lengthPrec));
									Label info = new Label(c, SWT.BORDER);
									info.setText("the minimum lenght is: " + lengthPrec);	
									GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
									gridData.horizontalSpan = 2;
									info.setLayoutData(gridData);
								}else {
									Label lWidth = new Label(c, SWT.ALL);
									lWidth.setText("Lenght: ");
									textWid = new Text(c, SWT.BORDER);
									textWid.setText(Integer.toString(standardLengthPrec));
									Label info = new Label(c, SWT.BORDER);
									info.setText("the default lenght is: " + 35);	
									GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
									gridData.horizontalSpan = 2;
									info.setLayoutData(gridData);
											
								}
								
								
								this.getDialog().pack();
								
								

							}
						};
						dialog.createContent();
					}
				});
				

				MenuItem effSize = new MenuItem(subMenu, SWT.ALL);
				effSize.setText("Size Effect lines");
				effSize.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						IDialog dialog = new IDialog(boxSize.getParent().getShell()) {

							Text textWid;

							@Override
							public Listener getOkbtnListener() {
								return new Listener() {

									@Override
									public void handleEvent(Event event) {
										
										if (shownCond) {
											defaultValueEffLenght = false;
											lengthEff = Integer.parseInt(textWid.getText());
										} else {
											standardLengthEff = Integer.parseInt(textWid.getText());

										}
										
										getDialog().setVisible(false);

									}
								};
							}

							@Override
							public void createContent() {
								this.getLabel().setText("set the EffectLine-size of the action: " + action.getName());
								this.getLabel().pack();
								Composite c = getComposite();
								c.setLayout(new GridLayout(2, false));

								if(shownCond) {
									Label lWidth = new Label(c, SWT.ALL);
									lWidth.setText("Lenght: ");
									textWid = new Text(c, SWT.BORDER);
									textWid.setText(Integer.toString(lengthEff));
									Label info = new Label(c, SWT.BORDER);
									info.setText("the minimum lenght is: " + lengthEff);	
									GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
									gridData.horizontalSpan = 2;
									info.setLayoutData(gridData);
								}else {
									Label lWidth = new Label(c, SWT.ALL);
									lWidth.setText("Lenght: ");
									textWid = new Text(c, SWT.BORDER);
									textWid.setText(Integer.toString(standardLengthEff));
									Label info = new Label(c, SWT.BORDER);
									info.setText("the default lenght is: " + 30);	
									GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
									gridData.horizontalSpan = 2;
									info.setLayoutData(gridData);
											
								}
								
								
								this.getDialog().pack();
								
								

							}
						};
						dialog.createContent();
					}
				});
				
				

			}
		});
	}
	
	public int getLenght(ArrayList<String> conds) {

		int lenght = 0;
		if (conds.size() > 0) {
			String stringa = conds.get(0);
			lenght=stringa.length();
			for (String cond : conds) {
				if (cond.length() > stringa.length()) {
					stringa = cond;
					lenght = cond.length();
				}
			}
		}
		return lenght;
	}
	
	public void clearDisplay() {
		if (canvas != null) {
			canvas.dispose();
		}
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
	
	
}
