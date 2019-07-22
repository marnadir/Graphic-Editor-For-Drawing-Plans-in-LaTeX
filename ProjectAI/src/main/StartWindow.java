package main;

import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import View.PrincipalView;
import dialog.DialogAreUsure;
/**
 * Principal view of the tool
 * @author nadir
 * */
public class StartWindow {

	
	public void start() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(display.getClientArea().width, display.getClientArea().height);
		shell.setText("Graphical Editor For Drawing Plan");
		shell.setLayout(new FillLayout());

		PrincipalView principalView = new PrincipalView(shell);
		principalView.draw();

		shell.open();
		
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				DialogAreUsure dialog=new DialogAreUsure(shell, "Are you sure?");
				dialog.getResult(event);
			}
		});
		
		LoadLastDialog loadDialog=new LoadLastDialog(shell, "Load the last autosave?");
		loadDialog.load(principalView.getPlanView());
		
		
		Timer timer=new Timer();
		SaveStatePlan exe=new SaveStatePlan();
		exe.setDomainView(principalView.getPlanView().getDomainView());
		timer.schedule(exe, 0,2000);

	
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	
	
		
		
	}
	
	public static void main(String[] args) {
			new StartWindow().start();
		
	}
}
