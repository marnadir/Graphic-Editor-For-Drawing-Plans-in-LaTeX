package GUI;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import DND.MyDragSourceListener;
import DND.MyDropTargetListener;
import DataTrasfer.MyTransfer;
import command.ExitCommand;
import logic.ContentAction;
import logic.IMenu;

public class DrawWindow  {
	
	private Shell shell;
	//private Composite child;
	private IMenu menuBar;
	private SashForm sashForm;
	private Group domainGroup; 
	private SashForm sashForm2;
	private CTabFolder PlanView;
	private Group console;
	private CreateDomainView createDomainView;
	
	public DrawWindow(Shell shell) {
		this.shell=shell;
		
	}
	
	
	public void draw() {
		createMenuWindow();
		createContent();
	}
	
	
	public void createMenuWindow() {
		
				
	

		
		menuBar=new IMenu(shell,SWT.BAR);
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
	    
		ScrolledComposite firstScroll = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
	    firstScroll.setLayout(new GridLayout(1, false));
	    firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

//	    Composite firstContent = new Composite(firstScroll, SWT.NONE);
//	    firstContent.setLayout(new GridLayout(1, false));
//	    firstContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    sashForm = new SashForm(firstScroll, SWT.HORIZONTAL);

	    
	    firstScroll.setContent(sashForm);
	    firstScroll.setExpandHorizontal(true);
	    firstScroll.setExpandVertical(true);
		
		
	    
	    
	    
		createDomainView=new CreateDomainView(sashForm);
		createDomainView.createContent();
	
		
		sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
		
		sashForm.setWeights(new int[] {1,3});
		

		

	    PlanView = new CTabFolder (sashForm2, SWT.PUSH);
		PlanView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		PlanView.setSimple(false);
		PlanView.setUnselectedImageVisible(false);
		PlanView.setUnselectedCloseVisible(false);
		

		CTabItem item = new CTabItem(PlanView, SWT.CLOSE);
		item.setText("Item ");
		ContentAction contentAction=new ContentAction(PlanView, SWT.ALL);
        item.setControl(contentAction);
        
        PlanView.setSelection(item);
        
		

		console=new Group(sashForm2, SWT.SCROLL_LINE);
		console.setText("Console");
		//console.setFont(boldFont);
		FormLayout Layout = new FormLayout();
		Layout.marginWidth = 5;
		Layout.marginHeight = 5;
		console.setLayout(Layout);

	    
//	   DropTarget target = new DropTarget(contentAction, DND.DROP_MOVE | DND.DROP_COPY);
//	   target.setTransfer(new Transfer[] { TextTransfer.getInstance() }); // varargs are not yet supported see https://git.eclipse.org/r/#/c/92236         // add a drop listener
//	   target.addDropListener(new MyDropTargetListener(contentAction, target));
		
		   
//		DragSource source = new DragSource(console, DND.DROP_NONE);
//		final TextTransfer textTransfer = TextTransfer.getInstance();
//		final FileTransfer fileTransfer = FileTransfer.getInstance();
//		Transfer[] types = new Transfer[] { fileTransfer, textTransfer };
//		source.setTransfer(types); // varargs are supported as of 4.7
//		source.addDragListener(new MyDragSourceListener(source));

		
		
		
		
		DropTarget target = new DropTarget(contentAction, DND.DROP_MOVE | DND.DROP_COPY);
	    target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		target.addDropListener(new MyDropTargetListener(PlanView, target));
		
		
		
		
	    shell.setMaximized(false);
	}

	
	
	
	

	public Shell getShell() {
		return shell;
	}

	public IMenu getMenuBar() {
		return menuBar;
	}

	public SashForm getSashForm() {
		return sashForm;
	}

	public Group getDomainGroup() {
		return domainGroup;
	}

	public SashForm getSashForm2() {
		return sashForm2;
	}

	public CTabFolder getPlanView() {
		return PlanView;
	}

	public Group getConsole() {
		return console;
	}
	
	
	
	
}
