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
import org.eclipse.swt.widgets.Combo;
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
import Action.ICanvas;
import Action.Node;
import Dialog.IDialog;
import Dialog.InitializationVariableDialog;
import PlanPart.LinkCanvas;
import PlanPart.OrderConstrain;
import PlanPart.Oval;
import PlanPart.PlanContent;
import dialogMenuState.EditLayoutAction;

public class MenuContentAction implements MenuDetectListener {

	ICanvas canvas;


	public MenuContentAction(ICanvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(canvas);
		canvas.setMenu(m);

		if (canvas.getParent().getParent() instanceof PlanContent) {

			MenuItem c = new MenuItem(m, SWT.ALL);
			c.setText("Remove Action");
			c.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					if (canvas instanceof Node) {
						if (canvas.getParent().getParent() instanceof PlanContent) {
							PlanContent plan = (PlanContent) canvas.getParent().getParent();
							plan.getActionInPlan().remove(canvas);
							canvas.getParent().setVisible(false);
							for (Oval oval : canvas.getOvalList()) {
								plan.getOvalCounter().getListOval().remove(oval);
								oval.dispose();

							}
							canvas.setOvalList(new ArrayList<>());
							Action a = canvas.getAction();
							ArrayList<LinkCanvas> links = plan.getLink();
							ArrayList<LinkCanvas> linksToDelete = new ArrayList<>();
							for (LinkCanvas link : links) {
								if (link.getOval1().getNode().getAction().getName().equals(a.getName())
										|| link.getOval2().getNode().getAction().getName().equals(a.getName())) {

									link.setOval1(null);
									link.setOval2(null);
									linksToDelete.add(link);
								}
							}
							links.removeAll(linksToDelete);

							ArrayList<OrderConstrain> orderConstrains = plan.getOrds();
							ArrayList<OrderConstrain> orderConstrainsToDelete = new ArrayList<>();
							for (OrderConstrain orderConstrain : orderConstrains) {
								if (orderConstrain.getCond1().getAction().getName().equals(a.getName())
										|| orderConstrain.getCond2().getAction().getName().equals(a.getName())) {

									orderConstrainsToDelete.add(orderConstrain);
									orderConstrain.getParent().dispose();
								}
							}

							MessageBox messageBox = new MessageBox(canvas.getShell(), SWT.ICON_WARNING | SWT.OK);

							messageBox.setText("Message");
							messageBox.setMessage("Removed Action");
							messageBox.open();
							canvas.getParent().setVisible(false);
							return;
						}
					}

					MessageBox messageBox = new MessageBox(canvas.getShell(), SWT.ICON_WARNING | SWT.OK);

					messageBox.setText("Remove Action");
					messageBox.setMessage("Removed Action");
					messageBox.open();
					canvas.clearDisplay();
				}
			});

		}
		
		if ((canvas.getParent().getParent() instanceof PlanContent)) {
			
			PlanContent planContent=(PlanContent) canvas.getParent().getParent();
			if(actionHasVariable(canvas.getAction())) {
				MenuItem setvariable = new MenuItem(m, SWT.ALL);
				setvariable.setText("Set Variables");
				setvariable.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						
						if (actionHasVariable(canvas.getAction())) {
							InitializationVariableDialog dialog = new InitializationVariableDialog(
									canvas.getShell(), 
									SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
							dialog.setAction(canvas.getAction());
							dialog.setPlan(planContent);
							dialog.createContent();
							dialog.pack();
						}
						canvas.redraw();

					}
				});
			}
		
		}

		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem showC = new MenuItem(m, SWT.ALL);
			showC.setText("Show/Hide Cond.");
			showC.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownCond();
					canvas.redraw();

				}
			});

			MenuItem showN = new MenuItem(m, SWT.ALL);
			showN.setText("Show/Hide Name");
			showN.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					canvas.getAction().negateIsShownName();
					canvas.redraw();

				}
			});
		
			MenuItem editLayout = new MenuItem(m, SWT.ALL);
			editLayout.setText("Edit Layout");
			editLayout.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					EditLayoutAction dialog;
					dialog=new EditLayoutAction(m.getShell(), 
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER|SWT.RESIZE);
					dialog.setCanvas(canvas);
					dialog.createContent();
				}
			});
			

				
			MenuItem setSize = new MenuItem(m, SWT.CASCADE);
			setSize.setText("Set Size");

			Menu subMenu = new Menu(m);
			setSize.setMenu(subMenu);

			MenuItem boxSize = new MenuItem(subMenu, SWT.ALL);
			boxSize.setText("Size Box");
			boxSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER |SWT.RESIZE) {

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
							label.setText("Set the box-size of the action: " + canvas.getAction().getName());
							label.pack();
							mainComposite.setLayout(new GridLayout(3, false));

							Label lWidth = new Label(mainComposite, SWT.ALL);
							lWidth.setText("Width in cm: ");
							
							textWid = new Text(mainComposite, SWT.BORDER);
							textWid.setText(canvas.getAction().getWidthRectInCm());
							textWid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
							

							Button btnWidth=new Button(mainComposite, SWT.CHECK);
							btnWidth.setText("Global");
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
							

							Label lHeight = new Label(mainComposite, SWT.ALL);
							lHeight.setText("Height in cm: ");
							textHei = new Text(mainComposite, SWT.BORDER);
						    textHei.setText(canvas.getAction().getHeightRectInCm());
						    textHei.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

							
							Button btnHeight=new Button(mainComposite, SWT.CHECK);
							btnHeight.setText("Global");
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
			precSize.setText("Size Precondition Lines");
			precSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE) {

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
							label.setText("Set the precond. size of the action: " + canvas.getAction().getName());
							label.pack();
							mainComposite.setLayout(new GridLayout(3, false));

							Button btnPrec;
							
							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(mainComposite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(mainComposite, SWT.BORDER);
								textPrec.setText(canvas.getAction().getLengthPrecInCm());
								textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

								btnPrec=new Button(mainComposite, SWT.CHECK);
								btnPrec.setText("Global");
								btnPrec.setVisible(false);

							} else {
								Label lWidth = new Label(mainComposite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(mainComposite, SWT.BORDER);
								textPrec.setText(canvas.getAction().getStandardLengthPrecInCm());
								textPrec.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

								btnPrec=new Button(mainComposite, SWT.CHECK);
								btnPrec.setText("Global");
								btnPrec.setVisible(false);

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
			effSize.setText("Size Effect Lines");
			effSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					IDialog dialog = new IDialog(boxSize.getParent().getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE) {

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
									.setText("Set the effects size of the action: " + canvas.getAction().getName());
							label.pack();
							Button btnEff;
							Composite c = mainComposite;
							c.setLayout(new GridLayout(3, false));

							if (canvas.getAction().isShownCond()) {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textEff = new Text(c, SWT.BORDER);
								textEff.setText(canvas.getAction().getLengthEffInCm());
								textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

								btnEff=new Button(mainComposite, SWT.CHECK);
								btnEff.setText("Global");
								btnEff.setVisible(false);
							} else {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textEff = new Text(c, SWT.BORDER);
								textEff.setText(canvas.getAction().getStandardLengthEffInCm());
								textEff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

								btnEff=new Button(mainComposite, SWT.CHECK);
								btnEff.setText("Global");
								btnEff.setVisible(false);
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
		if(name.contains("(") || name.contains(",")) {
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
