package Menu;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import PlanPart.LinkCanvas;
import PlanPart.Oval;
import PlanPart.PlanContent;
import View.IStateView;
import command.ChangeCondCommand;
import containerState.IContainerState;
import dialog.state.LineVsTextDialog;
import dialog.state.SetSizeStateDialog;
import so_goalState.GoalStateCanvas;
import so_goalState.IState;
import so_goalState.IStateCanvas;
import so_goalState.InitialStateCanvas;
/**
 * menu which is used to manage the initial/goal state
 * @author nadir
 * */


public class MenuContentState implements MenuDetectListener {

	IStateCanvas canvas;
	IState state;
	IStateView iStateView;
	PlanContent planContent;
	IContainerState containerState;

	public MenuContentState(Composite containerState) {
		
		if(containerState instanceof IStateView) {
			this.iStateView = (IStateView) containerState;
		}
		else if(containerState instanceof IContainerState) {
			this.containerState=(IContainerState) containerState;
		}
				
	}

	@Override
	public void menuDetected(MenuDetectEvent e) {
		
		Menu m=null;
		
		if(iStateView !=null) {
			this.canvas=iStateView.getIstateCanvas();
			this.state = canvas.getState();
			m = new Menu(canvas.getParent().getParent());
			iStateView.setMenu(m);
			canvas.setMenu(m);
			
		}else {
			this.canvas=containerState.getCanvas();
			this.state = canvas.getState();
			m = new Menu(canvas);
			canvas.setMenu(m);
		}
	

		addRemoveOption(m);
		addShowCondOption(m);
		addShowNameOption(m);
		addLineVsTextOption(m);
		addSetSizeOption(m);
	}

	
	
	private void updateViewPlaN() {
		if(canvas.getParent().getParent() instanceof PlanContent) {
			PlanContent planContent=(PlanContent)canvas.getParent().getParent();
			planContent.getPlanview().getConsoleView().getConsoleViewPlan().updateView();
		}
	}
	
	private void updateViewDomain() {
		if(canvas.getParent().getParent() instanceof PlanContent) {
			PlanContent planContent=(PlanContent)canvas.getParent().getParent();
			planContent.getPlanview().getConsoleView().getConsoleViewDomain().updateView();
		}
	}
	
	
	private void addRemoveOption(Menu m) {
		MenuItem c = new MenuItem(m, SWT.ALL);
		c.setText("Remove");
		c.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (canvas.getParent().getParent() instanceof PlanContent) {
					planContent = (PlanContent) canvas.getParent().getParent();
					
					
					if (canvas instanceof InitialStateCanvas) {
						planContent.setInitialStateCanvas(null);

					} else if (canvas instanceof GoalStateCanvas) {
						planContent.setGoalStateCanvas(null);
					}

					for (Oval oval : canvas.getOvalList()) {
						planContent.getOvalCounter().getListOval().remove(oval);
						oval.dispose();

					}
					canvas.setOvalList(new ArrayList<>());
					canvas.getParent().setVisible(false);
					
					ArrayList<LinkCanvas> links = planContent.getLink();
					ArrayList<LinkCanvas> linksToDelete = new ArrayList<>();
					
					for (LinkCanvas link : links) {
						if (link.getOval1().getStateCanvas() != null )  {
							if(link.getOval1().getStateCanvas().equals(canvas)) {
								link.setOval1(null);
								link.setOval2(null);
								linksToDelete.add(link);
							}
							
						}else if (link.getOval2().getStateCanvas()!=null)  {
							if(link.getOval2().getStateCanvas().equals(canvas)) {
								link.setOval1(null);
								link.setOval2(null);
								linksToDelete.add(link);
							}
							
						}
					
					}
					links.removeAll(linksToDelete);

				}
				updateViewPlaN();
				updateViewDomain();
				canvas.clearDisplay();

			}
		});

	}
	
	
	
	private void addLineVsTextOption(Menu m) {
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
	}
	
	private void addShowCondOption(Menu m) {
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem showC = new MenuItem(m, SWT.ALL);
			showC.setText("Show/Hide Cond.");
			showC.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					state.negateIsShownCond();
					canvas.redraw();

				}
			});
		}
	}
	
	
	private void addShowNameOption(Menu m) {
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {

			MenuItem changeCond = new MenuItem(m, SWT.ALL);
			changeCond.setText("Add/Remove Cond.");
			changeCond.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					ChangeCondCommand cmd = new ChangeCondCommand();
					cmd.execute(canvas);
					updateViewDomain();
				}
			});
		}
	}
	
	private void addSetSizeOption(Menu m) {
		if (!(canvas.getParent().getParent() instanceof PlanContent)) {
			MenuItem setSize = new MenuItem(m, SWT.CASCADE);
			setSize.setText("Set Size");
			setSize.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					SetSizeStateDialog dialog = new SetSizeStateDialog(canvas.getShell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
					dialog.setState(state);
					dialog.createContent();
					updateViewDomain();
				}
			});
		
		}
	}
	
	
	
	
}
