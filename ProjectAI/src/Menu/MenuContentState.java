package Menu;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import PlanPart.Oval;
import PlanPart.PlanContent;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialStateCanvas;
import command.ChangeCondCommand;
import dialogMenuState.LineVsTextDialog;
import dialogMenuState.SetSizeCondDialog;
import dialogMenuState.SetWidtHeiDialog;

public class MenuContentState implements MenuDetectListener {

	IStateCanvas canvas;
	IState state;

	public MenuContentState(IStateCanvas canvas) {
		this.canvas = canvas;
		this.state = canvas.getState();
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
				if (canvas.getParent().getParent() instanceof PlanContent) {
					PlanContent content = (PlanContent) canvas.getParent().getParent();
					if (canvas instanceof InitialStateCanvas) {
						content.setInitialStateCanvas(null);

					} else if (canvas instanceof GoalStateCanvas) {
						content.setGoalStateCanvas(null);
					}

					for (Oval oval : canvas.getOvalList()) {
						content.getOvalCounter().getListOval().remove(oval);
						oval.dispose();

					}
					canvas.setOvalList(new ArrayList<>());

				}
				//canvas.getParent().setVisible(false);
				canvas.clearDisplay();
			}
		});

		if (!(canvas.getParent().getParent() instanceof PlanContent)) {
			MenuItem vs = new MenuItem(m, SWT.ALL);
			vs.setText("Line vs Text");
			vs.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					LineVsTextDialog dialog = new LineVsTextDialog(canvas.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER|SWT.RESIZE);
					dialog.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

					dialog.setState(state);
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
			precSize.setText("Set Size Condition Lines");
			precSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					SetSizeCondDialog dialog = new SetSizeCondDialog(canvas.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
					dialog.setState(state);
					dialog.createContent();
				}
			});

			MenuItem width = new MenuItem(subMenu, SWT.ALL);
			width.setText("Set Width/Height");
			width.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					SetWidtHeiDialog dialog=new SetWidtHeiDialog(canvas.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
					dialog.setState(state);
					dialog.createContent();
					
				}
			});

		

		}

	}

}
