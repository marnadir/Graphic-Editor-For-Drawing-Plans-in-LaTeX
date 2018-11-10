package GUI;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import command.ExitCommand;
import logic.IMenu;

public class DrawWindow {
	
	Shell shell;
	
	
	public DrawWindow(Shell shell) {
		this.shell=shell;
	}
	
	public void draw() {
		createMenuWindow();
		createContent();
	}
	
	public void createMenuWindow() {
		IMenu menuBar=new IMenu(shell,SWT.BAR);
		MenuItem fileItem=menuBar.createItem("&File",SWT.CASCADE); 
		IMenu menuFile=new IMenu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(menuFile);
		
		MenuItem saveItem= new MenuItem(menuFile, SWT.PUSH);
		saveItem.setText("&Save\tCtrl+S");
		saveItem.setAccelerator(SWT.CONTROL+'S');
		
		
		MenuItem saveAsItem=new MenuItem(menuFile, SWT.PUSH);
		saveAsItem.setText("&Save as...");

		MenuItem saveAllItem= new MenuItem(menuFile, SWT.PUSH);
		saveAllItem.setText("&Save All\tShift+Ctrl+S");
		saveAllItem.setAccelerator(SWT.SHIFT+SWT.CONTROL+'S');
		
		MenuItem exitItem= new MenuItem(menuFile, SWT.PUSH);
		exitItem.setText("&Exit");
		
		Listener listenerExit=new Listener() {	
			@Override
			public void handleEvent(Event event) {
				ExitCommand command=new ExitCommand();
				command.execute(shell, event);	
			}
		};
	
		exitItem.addListener(SWT.Selection, listenerExit);
		
		Listener listenerSave=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				
			}
		};
		
		shell.setMenuBar(menuBar);
		
		shell.addListener (SWT.Close, e -> {
			if (shell.getModified()) {
				MessageBox box = new MessageBox (shell, SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
				box.setText (shell.getText ());
				box.setMessage ("You have unsaved changes, do you want to exit?");
				e.doit = box.open () == SWT.OK;
			}
		});
	}
	
	public void createContent() {
		shell.setLayout(new FillLayout());
	    
	    SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
	
		ControlDomain controlDomain=new ControlDomain(sashForm);
		controlDomain.createOptionGruop();
		
		
//		Canvas canvas=new Canvas(domain, SWT.ALL);
//		canvas.addPaintListener(new PaintListener() {
//			
//			@Override
//			public void paintControl(PaintEvent e) {
//				// TODO Auto-generated method stub
//
//				//e.gc.drawLine(20, 20, 500, 500);
//				//e.gc.drawRectangle(0, 0, 50, 60);
//				Rectangle rect=new Rectangle(0, 0, 60, 30);
//				
//				e.gc.drawRectangle(rect);
//			}
//		});
//		
	    
		SashForm sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
	    CTabFolder folder = new CTabFolder (sashForm2, SWT.PUSH);
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		folder.setSimple(false);
		folder.setUnselectedImageVisible(false);
		folder.setUnselectedCloseVisible(false);
		

		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item ");
		Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("vaffanculo!!!");
		item.setControl(text);
		

		Group console=new Group(sashForm2, SWT.SCROLL_LINE);
		console.setText("Console");
		//console.setFont(boldFont);
		FormLayout Layout = new FormLayout();
		Layout.marginWidth = 5;
		Layout.marginHeight = 5;
		console.setLayout(Layout);

	    
	    
	    shell.setMaximized(false);
	}

}
