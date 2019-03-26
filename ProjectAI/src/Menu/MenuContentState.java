package Menu;

 
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import Action.GlobalValue;
import Dialog.IDialog;
import PlanPart.Oval;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialStateCanvas;
import command.ChangeCondCommand;

public class MenuContentState implements MenuDetectListener {

	IStateCanvas canvas;
	IState state;
	

	public MenuContentState(IStateCanvas canvas) {
		this.canvas = canvas;
		this.state=canvas.getState();
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
				if(canvas.getParent().getParent() instanceof PlanContent) {
					PlanContent content=(PlanContent)canvas.getParent().getParent();
					if(canvas instanceof InitialStateCanvas) {
						content.setInitialStateCanvas(null);

					}else if(canvas instanceof GoalStateCanvas){
						content.setGoalStateCanvas(null);
					}

					
					for (Oval oval : canvas.getOvalList()) {
						content.getOvalCounter().getListOval().remove(oval);
						oval.dispose();

					}
					canvas.setOvalList(new ArrayList<>());
				
					
				}
				canvas.getParent().setVisible(false);
				canvas.clear();
			}
		});

		
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {
			MenuItem vs = new MenuItem(m, SWT.ALL);
			vs.setText("Line vs Text");
			vs.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {

					IDialog dialog = new IDialog(canvas.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
						Composite compButton;
						Composite textButton;
						Button btnText;
						Text text;

						@Override
						public Listener getOkbtnListener() {
							Listener l;
							l = new Listener() {

								@Override
								public void handleEvent(Event event) {
									if (btnText.getSelection()) {
										state.setText(text.getText());
										
										state.setIsText(true);
									} else {
										state.setIsText(false);
									}

									dispose();
								}
							};
							return l;
						}

						@Override
						public void createContent() {
							label.setText("Line vs Text");
							this.label.pack();
							composite.setLayout(new GridLayout(1, false));
							compButton = new Composite(composite, SWT.ALL);
							compButton.setLayout(new RowLayout(SWT.HORIZONTAL));

							btnText = new Button(compButton, SWT.RADIO);
							btnText.setText("Text");

							Button btnLine = new Button(compButton, SWT.RADIO);
							btnLine.setText("Line");

							textButton = new Composite(composite, SWT.ALL);
							textButton.setLayout(new GridLayout(2, false));

							Label l = new Label(textButton, SWT.ALL);
							l.setText("Name of state:");

							text = new Text(textButton, SWT.BORDER);
							text.setText("state");
							text.setSize(20, 10);
						
							btnText.setSelection(true);


							btnText.addListener(SWT.Selection, new Listener() {

								@Override
								public void handleEvent(Event event) {
									textButton.setVisible(true);

								}
							});

							btnLine.addListener(SWT.Selection, new Listener() {

								@Override
								public void handleEvent(Event event) {
									textButton.setVisible(false);

								}
							});

							pack();

						}
					};

					dialog.createContent();

				}
			});

		}
		
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem showC = new MenuItem(m, SWT.ALL);
			showC.setText("Show/Hide Cond...");
			showC.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					state.negateIsShownCond();
					canvas.redraw();

				}
			});

			MenuItem changeCond = new MenuItem(m, SWT.ALL);
			changeCond.setText("Add/Remove Cond...");
			changeCond.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					ChangeCondCommand cmd = new ChangeCondCommand();
					cmd.execute(canvas);

				}
			});

			MenuItem setSize = new MenuItem(m, SWT.CASCADE);
			setSize.setText("Set Size...");

			Menu subMenu = new Menu(m);
			setSize.setMenu(subMenu);

			MenuItem precSize = new MenuItem(subMenu, SWT.ALL);
			precSize.setText("Size Condition lines");
			precSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					IDialog dialog = new IDialog(canvas.getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {

						Text textPrec;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {

									if (state.isShownCond()) {
										state.setDefaultValue(false);
										if(isNumeric(textPrec.getText())) {
											state.setLengthFromCm(Double.parseDouble(textPrec.getText()));
											setVisible(false);

										}
									} else {
										if(isNumeric(textPrec.getText())) {
											state.setStandardLengthFromCm(Double.parseDouble(textPrec.getText()));
											setVisible(false);

										}
									}
									// canvas.resizeParent();

								}
							};
						}

						@Override
						public void createContent() {
							this.label.setText("set the Conditions-size  ");
							this.label.pack();
							composite.setLayout(new GridLayout(3, false));
							 Button btnPrec;

							


							if (state.isShownCond()) {
								Label lPrec = new Label(composite, SWT.ALL);
								lPrec.setText("Lenght in cm: ");
								textPrec = new Text(composite, SWT.BORDER);
								textPrec.setText(state.getLengthCondInCm());
								btnPrec=new Button(composite, SWT.CHECK);
								btnPrec.setText("global");
								btnPrec.setVisible(false);
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the minimum lenght is: " + state.getLengthCondInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(composite, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textPrec = new Text(composite, SWT.BORDER);
								textPrec.setText((state.getLengthCondInCm()));
								btnPrec=new Button(composite, SWT.CHECK);
								btnPrec.setText("global");
								btnPrec.setVisible(false);
								Label info = new Label(composite, SWT.BORDER);
								info.setText("the default lenght is: " + "1.4cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);

							}
							
							if(state.isShownCond()) {
								if(GlobalValue.isLengthsOfConds) {
									btnPrec.setVisible(true);
									textPrec.setEditable(false);
									if(state.isGlobalCond()) {
										btnPrec.setSelection(true);
									}
									
								}
							}else {
								btnPrec.setVisible(true);
								if(state.isGlobalEmpty()) {
									btnPrec.setSelection(true);
									textPrec.setEditable(false);

								}
							}

							btnPrec.addListener(SWT.Selection, new Listener() {

								@Override
								public void handleEvent(Event event) {
									if (btnPrec.getSelection()) {
										if (state.isShownCond()) {
											textPrec.setText(GlobalValue.lengthsOfConds);
											textPrec.setEditable(false);
											state.setGlobalCond(true);
										} else {
											textPrec.setText(GlobalValue.lengthsOfEmptyTasks);
											textPrec.setEditable(false);
											state.setGlobalEmpty(true);
											

										}

									} else {
										if (state.isShownCond()) {
											textPrec.setEditable(true);
											state.setGlobalCond(false);
										} else {
											textPrec.setEditable(true);
											state.setGlobalEmpty(false);
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
