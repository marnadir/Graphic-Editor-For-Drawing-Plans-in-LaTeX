package Menu;


import java.util.ArrayList;

import org.bouncycastle.tsp.GenTimeAccuracy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import Action.Action;
import Action.GlobalValue;
import Action.ICanvasNode;
import Action.Node;
import Dialog.IDialog;
import Dialog.InitializationVariableDialog;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;

public class MenuContentAction implements MenuDetectListener {

	ICanvasNode canvas;


	public MenuContentAction(ICanvasNode canvas) {
		this.canvas = canvas;
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(canvas);
		canvas.setMenu(m);

		MenuItem c = new MenuItem(m, SWT.ALL);
		c.setText("Remove Action");
		c.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(canvas instanceof Node) {
					if(canvas.getParent().getParent() instanceof PlanContent) {
						PlanContent plan=(PlanContent)canvas.getParent().getParent();
						plan.getActionInPlan().remove(canvas);
						canvas.getParent().setVisible(false);
						for (Oval oval : canvas.getOvalList()) {
							plan.getOvalCounter().getListOval().remove(oval);
							oval.dispose();

						}
						canvas.setOvalList(new ArrayList<>());
						Action a=canvas.getAction();
						ArrayList<LinkCanvas> links=plan.getLink();
						ArrayList<LinkCanvas> linksToDelete=new ArrayList<>();
						for(LinkCanvas link:links) {
							if(link.getOval1().getNode().getAction().getName().equals(a.getName()) || 
									link.getOval2().getNode().getAction().getName().equals(a.getName())) {
								
								link.setOval1(null);
								link.setOval2(null);
								linksToDelete.add(link);
							}
						}
						links.removeAll(linksToDelete);
						
						ArrayList<OrderConstrain> orderConstrains=plan.getOrds();
						ArrayList<OrderConstrain> orderConstrainsToDelete=new ArrayList<>();
						for(OrderConstrain orderConstrain:orderConstrains) {
							if(orderConstrain.getCond1().getAction().getName().equals(a.getName()) || 
									orderConstrain.getCond2().getAction().getName().equals(a.getName())) {
								
								orderConstrainsToDelete.add(orderConstrain);
								orderConstrain.getParent().dispose();
							}
						}

						
						
						
						MessageBox messageBox = new MessageBox(canvas
								.getShell(),
								SWT.ICON_WARNING |  SWT.OK);

						messageBox.setText("Message");
						messageBox.setMessage("Removed Action");
						messageBox.open();
						canvas.clearDisplay();
						return;
					}
				}
				
				MessageBox messageBox = new MessageBox(canvas
						.getShell(),
						SWT.ICON_WARNING |  SWT.OK);

				messageBox.setText("Remove Action");
				messageBox.setMessage("Removed Action");
				messageBox.open();
				canvas.clearDisplay();
			}
		});
		
		
		
		if ((canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem setvariable = new MenuItem(m, SWT.ALL);
			setvariable.setText("Set-Variables");
			setvariable.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					
					if (actionHasVariable(canvas.getAction())) {
						InitializationVariableDialog dialog = new InitializationVariableDialog(
								canvas.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
						dialog.setAction(canvas.getAction());
						dialog.createContent();
						dialog.pack();
					}
					canvas.redraw();

				}
			});
		}

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
			
			
			
			MenuItem frame = new MenuItem(m, SWT.CASCADE);
			frame.setText("Frame");
			Menu subM = new Menu(m);
			frame.setMenu(subM);
			
			MenuItem form = new MenuItem(subM, SWT.CASCADE);
			form.setText("Border Color");
			Menu subForm = new Menu(m);
			form.setMenu(subForm);
			MenuItem black = new MenuItem(subForm, SWT.ALL);
			black.setText("Black");
			
			MenuItem white = new MenuItem(subForm, SWT.ALL);
			white.setText("White");
			
			
			black.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsborder(true);
					canvas.getAction().setDefaultAction(false);
					canvas.redraw();
					
				}
			});
			
			white.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsborder(false);
					canvas.getAction().setDefaultAction(false);
					canvas.redraw();

				}
			});
			
			MenuItem menufett = new MenuItem(subM, SWT.CASCADE);
			menufett.setText("Fat Border");
			Menu subfett = new Menu(m);
			menufett.setMenu(subfett);
			MenuItem normal = new MenuItem(subfett, SWT.ALL);
			normal.setText("Normal");
			normal.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsFett(false);
					canvas.getAction().setDefaultAction(false);
					canvas.redraw();
					
				}
			});
			
			MenuItem fett = new MenuItem(subfett, SWT.ALL);
			fett.setText("Fat");
			fett.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setIsFett(true);
					canvas.getAction().setDefaultAction(false);
					canvas.redraw();
					
				}
			});
			
			

			MenuItem menuEckig = new MenuItem(subM, SWT.CASCADE);
			menuEckig.setText("Corner");
		
			Menu subeckig = new Menu(m);
			menuEckig.setMenu(subeckig);
			MenuItem round = new MenuItem(subeckig, SWT.ALL);
			round.setText("Round");
			round.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setBorderIsSquare(false);
					canvas.getAction().setDefaultAction(false);

					canvas.redraw();
					
				}
			});
			
			MenuItem square = new MenuItem(subeckig, SWT.ALL);
			square.setText("Square");
			
			square.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setBorderIsSquare(true);
					canvas.getAction().setDefaultAction(false);

					canvas.redraw();
					
				}
			});
			
			
			MenuItem menuDefault = new MenuItem(subM, SWT.CASCADE);
			menuDefault.setText("Set as Default Action");
			menuDefault.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					canvas.getAction().setDefaultAction(true);
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
								if(canvas.getAction().isGlobalWid()) {
									btnWidth.setSelection(true);
									textWid.setEditable(false);

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
								if(canvas.getAction().isGlobalHeight()) {
									btnHeight.setSelection(true);	
									textHei.setEditable(false);

									
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
							
							

//							Label info = new Label(composite, SWT.BORDER);
//							info.setText(
//									"the default size is: " + canvas.getAction().getWidthRectInCm() + "cm x" + canvas.getAction().getHeightRectInCm()+"cm");
//							GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
//							gridData.horizontalSpan = 2;
//							info.setLayoutData(gridData);
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
//								Label info = new Label(composite, SWT.BORDER);
//								info.setText("the minimum lenght is: " + canvas.getAction().getLengthPrecInCm()+"cm");
//								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
//								gridData.horizontalSpan = 2;
//								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(composite, SWT.BORDER);
								textPrec.setText(canvas.getAction().getStandardLengthPrecInCm());
								btnPrec=new Button(composite, SWT.CHECK);
								btnPrec.setText("global");
								btnPrec.setVisible(false);
//								Label info = new Label(composite, SWT.BORDER);
//								info.setText("the default lenght is: " + canvas.getAction().getStandardLengthPrecInCm()+"cm");
//								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
//								gridData.horizontalSpan = 2;
//								info.setLayoutData(gridData);

							}
							
							if(canvas.getAction().isShownCond()) {
								if(GlobalValue.isLengthsOfPrecs) {
									btnPrec.setVisible(true);
									if(canvas.getAction().isGlobalPrec()) {
										btnPrec.setSelection(true);
										textPrec.setEditable(false);

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
//								Label info = new Label(c, SWT.BORDER);
//								info.setText("the minimum lenght is: " + canvas.getAction().getLengthEffInCm()+"cm");
//								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
//								gridData.horizontalSpan = 2;
//								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textEff = new Text(c, SWT.BORDER);
								textEff.setText(canvas.getAction().getStandardLengthEffInCm());
								btnEff=new Button(composite, SWT.CHECK);
								btnEff.setText("global");
								btnEff.setVisible(false);
//								Label info = new Label(c, SWT.BORDER);
//								info.setText("the default lenght is: " +canvas.getAction().getStandardLengthEffInCm()+"cm");
//								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
//								gridData.horizontalSpan = 2;
//								info.setLayoutData(gridData);

							}
							
							if(canvas.getAction().isShownCond()) {
								if(GlobalValue.isLengthsOfEffs) {
									btnEff.setVisible(true);
									if(canvas.getAction().isGlobalEff()) {
										btnEff.setSelection(true);								
										textEff.setEditable(false);

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
	
	private boolean actionHasVariable(Action a) {
		boolean result=false;
		String name=a.getName();
		if(name.contains("(")&& name.contains(",")) {
			result=true;
		}
		
		return result;
	
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
