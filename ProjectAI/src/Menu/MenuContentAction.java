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
								textWid.setEditable(false);
								if(canvas.getAction().isGlobalWid()) {
									btnWidth.setSelection(true);
								}
								
							}
							
							btnWidth.addListener(SWT.Selection, new Listener() {
								
								@Override
								public void handleEvent(Event event) {
									if(btnWidth.getSelection()) {
										textWid.setText(GlobalValue.widthOfAction);
										textWid.setEditable(false);
										canvas.getAction().setGlobalWid(true);
									}else {
										canvas.getAction().setGlobalWid(false);
										textWid.setEditable(true);

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
								textHei.setEditable(false);
								if(canvas.getAction().isGlobalHeight()) {
									btnHeight.setSelection(true);
								}
								
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
										canvas.getAction().setGlobalHeight(false);

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

						Text textPrec;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {

									if(isNumeric(textPrec.getText())) {
										if (canvas.getAction().isShownCond()) {
											canvas.getAction().setDefaultValuePrecLenght(false);
											canvas.getAction().setLengthPrecFromCm(Double.parseDouble(textPrec.getText()));
										} else {
											canvas.getAction().setStandardLengthPrecFromCm(Double.parseDouble(textPrec.getText()));
										}
										canvas.resizeParent();
										setVisible(false);
									}
									

								}
							};
						}

						@Override
						public void createContent() {
							label.setText("set the PrecLine-size of the action: " + canvas.getAction().getName());
							label.pack();
							composite.setLayout(new GridLayout(3, false));

							Button btnPrec;
							
							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(composite, SWT.BORDER);
								textPrec.setText(canvas.getAction().getLengthPrecInCm());
								btnPrec=new Button(composite, SWT.CHECK);
								btnPrec.setText("global");
								btnPrec.setVisible(false);
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the minimum lenght is: " + canvas.getAction().getLengthPrecInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(composite, SWT.BORDER);
								textPrec.setText(canvas.getAction().getStandardLengthPrecInCm());
								btnPrec=new Button(composite, SWT.CHECK);
								btnPrec.setText("global");
								btnPrec.setVisible(false);
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the default lenght is: " + canvas.getAction().getStandardLengthPrecInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);

							}
							
							if(canvas.getAction().isShownCond()) {
								if(GlobalValue.isLengthsOfPrecs) {
									btnPrec.setVisible(true);
									textPrec.setEditable(false);
									if(canvas.getAction().isGlobalPrec()) {
										btnPrec.setSelection(true);
									}
									
								}
							}else {
								btnPrec.setVisible(true);
								if(canvas.getAction().isGlobalEmptyPrec()) {
									btnPrec.setSelection(true);
									textPrec.setEditable(false);

								}
							}
							
							
							btnPrec.addListener(SWT.Selection, new Listener() {
								
								@Override
								public void handleEvent(Event event) {
									if(btnPrec.getSelection()) {
										if(canvas.getAction().isShownCond()) {
											textPrec.setText(GlobalValue.lengthsOfPrecs);
											textPrec.setEditable(false);
											canvas.getAction().setGlobalPrec(true);
										}else {
											textPrec.setText(GlobalValue.lengthsOfEmptyTasks);
											textPrec.setEditable(false);
											canvas.getAction().setGlobalEmptyPrec(true);;
											
										}
										
									}else {
										if(canvas.getAction().isShownCond()) {
											textPrec.setEditable(true);
											canvas.getAction().setGlobalPrec(false);
										}else {
											textPrec.setEditable(true);
											canvas.getAction().setGlobalEmptyPrec(false);
										}
										

									}
									
								}
							});
							
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

						Text textEff;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {
									if (isNumeric(textEff.getText())) {
										if (canvas.getAction().isShownCond()) {
											canvas.getAction().setDefaultValueEffLenght(false);
											canvas.getAction()
													.setLengthEffFromCm(Double.parseDouble(textEff.getText()));
										} else {
											canvas.getAction()
													.setStandardLengthEffFromCm(Double.parseDouble(textEff.getText()));

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
							Button btnEff;
							Composite c = composite;
							c.setLayout(new GridLayout(3, false));

							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textEff = new Text(c, SWT.BORDER);
								textEff.setText(canvas.getAction().getLengthEffInCm());
								btnEff=new Button(composite, SWT.CHECK);
								btnEff.setText("global");
								btnEff.setVisible(false);
								Label info = new Label(c, SWT.BORDER);
								info.setText("the minimum lenght is: " + canvas.getAction().getLengthEffInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textEff = new Text(c, SWT.BORDER);
								textEff.setText(canvas.getAction().getStandardLengthEffInCm());
								btnEff=new Button(composite, SWT.CHECK);
								btnEff.setText("global");
								btnEff.setVisible(false);
								Label info = new Label(c, SWT.BORDER);
								info.setText("the default lenght is: " +canvas.getAction().getStandardLengthEffInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);

							}
							
							if(canvas.getAction().isShownCond()) {
								if(GlobalValue.isLengthsOfEffs) {
									btnEff.setVisible(true);
									textEff.setEditable(false);
									if(canvas.getAction().isGlobalEff()) {
										btnEff.setSelection(true);
									}
									
								}
							}else {
								btnEff.setVisible(true);
								if(canvas.getAction().isGlobalEmptyEff()) {
									btnEff.setSelection(true);
									textEff.setEditable(false);

								}
							}
							
							
							btnEff.addListener(SWT.Selection, new Listener() {
								
								@Override
								public void handleEvent(Event event) {
									if(btnEff.getSelection()) {
										if(canvas.getAction().isShownCond()) {
											textEff.setText(GlobalValue.lengthsOfEffs);
											textEff.setEditable(false);
											canvas.getAction().setGlobalEff(true);	
										}else {
											textEff.setText(GlobalValue.lengthsOfEmptyTasks);
											textEff.setEditable(false);
											canvas.getAction().setGlobalEmptyEff(true);;
										}
									}else {
										if(canvas.getAction().isShownCond()) {
											
											textEff.setEditable(true);
											canvas.getAction().setGlobalEff(false);
										}else {
											textEff.setEditable(true);
											canvas.getAction().setGlobalEmptyEff(true);
										}
									


									}
									
								}
							});
							

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
