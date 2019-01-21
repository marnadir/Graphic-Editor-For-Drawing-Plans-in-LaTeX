package Menu;

 
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import Dialog.IDialog;
import GraphPart.GraphContent;
import State.ChangeCondCommand;
import State.IStateCanvas;

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
			}
		});

		if (!(canvas.getParent().getParent() instanceof GraphContent)) {

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
			precSize.setText("Size Precondition lines");
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
										canvas.setLengthFromCm(Double.parseDouble(textWid.getText()));
									} else {
										canvas.setStandardLengthFromCm(Double.parseDouble(textWid.getText()));
									}
									// canvas.resizeParent();
									getDialog().setVisible(false);

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

}
