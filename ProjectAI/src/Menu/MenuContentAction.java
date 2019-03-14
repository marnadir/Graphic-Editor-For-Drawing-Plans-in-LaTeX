package Menu;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import Action.GlobalValue;
import Action.ICanvasAction;
import Action.Node;
import Dialog.IDialog;
import PlanPart.Oval;
import PlanPart.PlanContent;

public class MenuContentAction implements MenuDetectListener {

	ICanvasAction canvas;


	public MenuContentAction(ICanvasAction canvas) {
		this.canvas = canvas;
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(canvas);
		canvas.setMenu(m);

		MenuItem c = new MenuItem(m, SWT.ALL);
		c.setText("Clear");
		c.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(canvas instanceof Node) {
					if(canvas.getParent().getParent() instanceof PlanContent) {
						PlanContent contentAction=(PlanContent)canvas.getParent().getParent();
						contentAction.getActionInPlan().remove(canvas);
						canvas.getParent().setVisible(false);
						for (Oval oval : canvas.getOvalList()) {
							contentAction.getOvalCounter().getListOval().remove(oval);
							oval.dispose();

						}
						canvas.setOvalList(new ArrayList<>());
						canvas.clearDisplay();
						return;
					}
				}
				canvas.clearDisplay();
			}
		});

		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem showC = new MenuItem(m, SWT.ALL);
			showC.setText("Show/Hide Cond...");
			showC.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownCond();
					canvas.redraw();

				}
			});

			MenuItem showN = new MenuItem(m, SWT.ALL);
			showN.setText("Show/Hide Name..");
			showN.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownName();
					canvas.redraw();

				}
			});

			
			MenuItem fillColor= new MenuItem(m, SWT.CASCADE);
			fillColor.setText("fillColor");
			
			Menu subMe = new Menu(m);
			fillColor.setMenu(subMe);
			
			MenuItem cyan=new MenuItem(subMe, SWT.ALL);
			cyan.setText("Cyan");
			
			
			cyan.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsFillColor(true);	
					canvas.getAction().setColorString("cyan");;
					canvas.redraw();

				}
			});
			
		
			
			MenuItem yellow=new MenuItem(subMe, SWT.ALL);
			yellow.setText("Yellow");
			
			yellow.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsFillColor(true);	
					canvas.getAction().setColorString("yellow");;
					canvas.redraw();

				}
			});
			
			
			
			MenuItem none=new MenuItem(subMe, SWT.ALL);
			none.setText("None");
			
			none.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsFillColor(false);
					canvas.redraw();

				}
			});
			
			
			
			
			
			MenuItem form = new MenuItem(m, SWT.CASCADE);
			form.setText("form");
			Menu subM = new Menu(m);
			form.setMenu(subM);
			MenuItem black = new MenuItem(subM, SWT.ALL);
			black.setText("Black");
			
			MenuItem white = new MenuItem(subM, SWT.ALL);
			white.setText("White");
			
			
			black.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setForm(true);
					canvas.redraw();
					
				}
			});
			
			white.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setForm(false);
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
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {

						Text textWid;
						Text textHei;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {
									if(isNumeric(textWid.getText())&& isNumeric(textHei.getText())) {
										canvas.getAction().setWidthRectFromCm(Double.parseDouble(textWid.getText()));
										canvas.getAction().setHeightRectFromCm(Double.parseDouble(textHei.getText()));
										canvas.getAction().setDefaultValueWid(false);
										canvas.getAction().setDefaultValueHeig(false);

										setVisible(false);
										canvas.resizeParent();
									}
								

								}
							};
						}

						@Override
						public void createContent() {
							label.setText("set the Box-size: " + canvas.getAction().getName());
							label.pack();
							composite.setLayout(new GridLayout(3, false));

							Label lWidth = new Label(composite, SWT.ALL);
							lWidth.setText("Width in cm: ");
							
							textWid = new Text(composite, SWT.BORDER);
							textWid.setText(canvas.getAction().getWidthRectInCm());
							textWid.setLayoutData(new GridData(40, 20));
							

							Button btnWidth=new Button(composite, SWT.CHECK);
							btnWidth.setText("global");
							btnWidth.setVisible(false);
							

							
							if(GlobalValue.isWidthOfAction) {
								btnWidth.setVisible(true);
								
							}
							
							btnWidth.addListener(SWT.Selection, new Listener() {
								
								@Override
								public void handleEvent(Event event) {
									if(btnWidth.getSelection()) {
										textWid.setText(GlobalValue.widthOfAction);
										textWid.setEditable(false);
										canvas.getAction().setGlobalWid(true);
									}else {
										textWid.setEditable(true);
										canvas.getAction().setGlobalWid(false);

									}
									
								}
							});
							

							Label lHeight = new Label(composite, SWT.ALL);
							lHeight.setText("Height in cm: ");
							textHei = new Text(composite, SWT.BORDER);
						    textHei.setText(canvas.getAction().getHeightRectInCm());
							textHei.setLayoutData(new GridData(40, 20));
							
							Button btnHeight=new Button(composite, SWT.CHECK);
							btnHeight.setText("global");
							btnHeight.setVisible(false);
							

							
							if(GlobalValue.isHeightOfAction) {
								btnHeight.setVisible(true);
								
							}
							
							btnHeight.addListener(SWT.Selection, new Listener() {
								
								@Override
								public void handleEvent(Event event) {
									if(btnHeight.getSelection()) {
										textHei.setText(GlobalValue.heightOfAction);
										textHei.setEditable(false);
										canvas.getAction().setGlobalHeight(true);
									}else {
										textHei.setEditable(true);
										canvas.getAction().setGlobalHeight(true);

									}
									
								}
							});
							
							

							Label info = new Label(composite, SWT.BORDER);
							info.setText(
									"the default size is: " + canvas.getAction().getWidthRectInCm() + "cm x" + canvas.getAction().getHeightRectInCm()+"cm");
							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
							gridData.horizontalSpan = 2;
							info.setLayoutData(gridData);
							pack();

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
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {

						Text textWid;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {

									if(isNumeric(textWid.getText())) {
										if (canvas.getAction().isShownCond()) {
											canvas.getAction().setDefaultValuePrecLenght(false);
											canvas.getAction().setLengthPrecFromCm(Double.parseDouble(textWid.getText()));
										} else {
											canvas.getAction().setStandardLengthPrecFromCm(Double.parseDouble(textWid.getText()));
										}
										canvas.resizeParent();
										setVisible(false);
									}
									

								}
							};
						}

						@Override
						public void createContent() {
							label
									.setText("set the PrecLine-size of the action: " + canvas.getAction().getName());
							label.pack();
							composite.setLayout(new GridLayout(2, false));

							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(composite, SWT.BORDER);
								textWid.setText(canvas.getAction().getLengthPrecInCm());
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the minimum lenght is: " + canvas.getAction().getLengthPrecInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(composite, SWT.BORDER);
								textWid.setText(canvas.getAction().getStandardLengthPrecInCm());
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the default lenght is: " + canvas.getAction().getStandardLengthPrecInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);

							}

							pack();

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
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {

						Text textWid;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {
									if (isNumeric(textWid.getText())) {
										if (canvas.getAction().isShownCond()) {
											canvas.getAction().setDefaultValueEffLenght(false);
											canvas.getAction()
													.setLengthEffFromCm(Double.parseDouble(textWid.getText()));
										} else {
											canvas.getAction()
													.setStandardLengthEffFromCm(Double.parseDouble(textWid.getText()));

										}
										canvas.resizeParent();

										setVisible(false);
									}
								}
							};
						}

						@Override
						public void createContent() {
							label
									.setText("set the EffectLine-size of the action: " + canvas.getAction().getName());
							label.pack();
							Composite c = composite;
							c.setLayout(new GridLayout(2, false));

							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(c, SWT.BORDER);
								textWid.setText(canvas.getAction().getLengthEffInCm());
								Label info = new Label(c, SWT.BORDER);
								info.setText("the minimum lenght is: " + canvas.getAction().getLengthEffInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(c, SWT.BORDER);
								textWid.setText(canvas.getAction().getStandardLengthEffInCm());
								Label info = new Label(c, SWT.BORDER);
								info.setText("the default lenght is: " +canvas.getAction().getStandardLengthEffInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);

							}

							pack();

						}
					};
					dialog.createContent();
				}
			});
		}
	}
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
