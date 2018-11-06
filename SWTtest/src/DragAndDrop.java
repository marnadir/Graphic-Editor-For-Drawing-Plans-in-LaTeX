import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;

public class DragAndDrop {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Plan Draw Tool");

		
		//Dialog of exit
		Listener exitListener = e -> {
			MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
			dialog.setText("Question");
			dialog.setMessage("Exit?");
			if (e.type == SWT.Close) e.doit = false;
			if (dialog.open() != SWT.OK) return;
			shell.dispose();
		};
		
		//dialog about
		Listener aboutListener = e -> {
			final Shell s = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			s.setText("About");
			GridLayout layout = new GridLayout(1, false);
			layout.verticalSpacing = 20;
			layout.marginHeight = layout.marginWidth = 10;
			s.setLayout(layout);
			Label label = new Label(s, SWT.NONE);
			label.setText("SWT and AWT Example.");
			Button button = new Button(s, SWT.PUSH);
			button.setText("OK");
			GridData data = new GridData();
			data.horizontalAlignment = GridData.CENTER;
			button.setLayoutData(data);
			button.addListener(SWT.Selection, event -> s.dispose());
			s.pack();
			Rectangle parentBounds = shell.getBounds();
			Rectangle bounds = s.getBounds();
			int x = parentBounds.x + (parentBounds.width - bounds.width) / 2;
			int y = parentBounds.y + (parentBounds.height - bounds.height) / 2;
			s.setLocation(x, y);
			s.open();
			while (!s.isDisposed()) {
				if (!display.readAndDispatch()) display.sleep();
			}
		};
		
		shell.addListener(SWT.Close, exitListener);
		
		
		//menu
		Menu mb = new Menu(shell, SWT.BAR);
		MenuItem fileItem = new MenuItem(mb, SWT.CASCADE);
		fileItem.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);
		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit\tCtrl+X");
		exitItem.setAccelerator(SWT.CONTROL + 'X');
		exitItem.addListener(SWT.Selection, exitListener);
		MenuItem aboutItem = new MenuItem(fileMenu, SWT.PUSH);
		aboutItem.setText("&About\tCtrl+A");
		aboutItem.setAccelerator(SWT.CONTROL + 'A');
		aboutItem.addListener(SWT.Selection, aboutListener);
		shell.setMenuBar(mb);

		//separator between menu and pannel
		Label separator2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		Composite comp = new Composite(shell, SWT.NONE | SWT.V_SCROLL);
        Canvas canvas = new Canvas(comp, SWT.ALL);
		
		
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Color color = display.getSystemColor(SWT.COLOR_CYAN);

				//e.gc.drawLine(20, 20, 500, 500);
				//e.gc.drawRectangle(0, 0, 50, 60);
				Rectangle rect=new Rectangle(0, 0, 60, 30);
				
				e.gc.drawRectangle(rect);

				//e.gc.drawLine(0, 40, 500, 500);
				//e.gc.drawLine(0, 0, 0, 1200);
				//e.gc.drawLine(0, 0, 1200, 0);
				
				

			}
		});
		Sash sash = new Sash(comp, SWT.VERTICAL);
		
		Composite comp2 = new Composite(comp, SWT.EMBEDDED);
		Label separator3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);


		//create domain panel
		
		Canvas canvas2=new Canvas(comp2, SWT.ALL);
		canvas2.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Color color = display.getSystemColor(SWT.COLOR_CYAN);

				//e.gc.drawLine(20, 20, 500, 500);
				//e.gc.drawRectangle(0, 0, 50, 60);
				Rectangle rect=new Rectangle(50, 40, 60, 30);
				
				e.gc.drawRectangle(rect);

				//e.gc.drawLine(0, 40, 500, 500);
				//e.gc.drawLine(0, 0, 0, 1200);
				//e.gc.drawLine(0, 0, 1200, 0);
				
				

			}
		});

		Label label=new Label(comp2, SWT.ALL);
		label.setText("Bella ciao");
		
		


		
		
		sash.addListener(SWT.Selection, e -> {
			if (e.detail == SWT.DRAG) return;
			GridData data = (GridData)canvas.getLayoutData();
			Rectangle trim = canvas.computeTrim(0, 0, 0, 0);
			data.widthHint = e.x - trim.width;
			comp.layout();
		});
		
		
		

		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 1;
		shell.setLayout(layout);
		GridData data;
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalIndent = 10;
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		data.horizontalSpan = 1;
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		separator2.setLayoutData(data);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 4;
		comp.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		separator3.setLayoutData(data);


		layout = new GridLayout(3, false);
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 1;
		comp.setLayout(layout);
		data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 200;
		canvas.setLayoutData(data);
		data = new GridData(GridData.FILL_VERTICAL);
		sash.setLayoutData(data);
		data = new GridData(GridData.FILL_BOTH);
		comp2.setLayoutData(data);

		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
