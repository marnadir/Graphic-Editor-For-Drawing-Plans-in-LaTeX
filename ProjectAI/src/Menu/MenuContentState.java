package Menu;

 
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

import Dialog.IDialog;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialStateCanvas;
import command.ChangeCondCommand;

public class MenuContentState implements MenuDetectListener {

	IStateCanvas canvas;
	

	public MenuContentState(IStateCanvas canvas) {
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
				canvas.clear();
				if(canvas.getParent().getParent() instanceof PlanContent) {
					PlanContent content=(PlanContent)canvas.getParent().getParent();
					if(canvas instanceof InitialStateCanvas) {
						content.setInitialStateCanvas(null);

					}else if(canvas instanceof GoalStateCanvas){
						content.setGoalStateCanvas(null);
					}
				}
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
										canvas.setText(text.getText());
										canvas.setIsText(true);
									} else {
										canvas.setIsText(false);
									}

									getDialog().dispose();
								}
							};
							return l;
						}

						@Override
						public void createContent() {
							getLabel().setText("Line vs Text");
							this.getLabel().pack();
							Composite c = getComposite();
							c.setLayout(new GridLayout(1, false));
							compButton = new Composite(c, SWT.ALL);
							compButton.setLayout(new RowLayout(SWT.HORIZONTAL));

							btnText = new Button(compButton, SWT.RADIO);
							btnText.setText("Text");

							Button btnLine = new Button(compButton, SWT.RADIO);
							btnLine.setText("Line");

							textButton = new Composite(c, SWT.ALL);
							textButton.setLayout(new RowLayout(SWT.HORIZONTAL));

							Label l = new Label(textButton, SWT.ALL);
							l.setText("set the text:");

							text = new Text(textButton, SWT.BORDER);
							text.setText("init");
							text.setSize(20, 10);

							btnText.setSelection(true);

							// textButton.setVisible(false);

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

							this.getDialog().pack();

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
					canvas.negateIsShownCond();
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

						Text textWid;

						@Override
						public Listener getOkbtnListener() {
							return new Listener() {

								@Override
								public void handleEvent(Event event) {

									if (canvas.isShownCond()) {
										canvas.setDefaultValue(false);
										if(isNumeric(textWid.getText())) {
											canvas.setLengthFromCm(Double.parseDouble(textWid.getText()));
											getDialog().setVisible(false);

										}
									} else {
										if(isNumeric(textWid.getText())) {
											canvas.setStandardLengthFromCm(Double.parseDouble(textWid.getText()));
											getDialog().setVisible(false);

										}
									}
									// canvas.resizeParent();

								}
							};
						}

						@Override
						public void createContent() {
							this.getLabel().setText("set the PrecLine-size of the So ");
							this.getLabel().pack();
							Composite c = getComposite();
							c.setLayout(new GridLayout(2, false));

							if (canvas.isShownCond()) {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(c, SWT.BORDER);
								textWid.setText(canvas.getLengthCondInCm());
								Label info = new Label(c, SWT.BORDER);
								info.setText("the minimum lenght is: " + canvas.getLengthCondInCm()+"cm");
								GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
								gridData.horizontalSpan = 2;
								info.setLayoutData(gridData);
							} else {
								Label lWidth = new Label(c, SWT.ALL);
								lWidth.setText("Lenght in cm: ");
								textWid = new Text(c, SWT.BORDER);
							
								textWid.setText((canvas.getLengthCondInCm()));
								Label info = new Label(c, SWT.BORDER);
								info.setText("the default lenght is: " + "1.4cm");
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
