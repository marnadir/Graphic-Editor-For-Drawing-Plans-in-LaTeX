package GUI;



import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import DNDAaction.MyDropActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.LineCanvas;
import command.ExitCommand;
import logic.IDialog;
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
	private GraphContent contentAction;
	
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
		
		MenuItem option=menuBar.createItem("&Option", SWT.CASCADE);
		IMenu menuOption=new IMenu(shell, SWT.DROP_DOWN);
		option.setMenu(menuOption);
		
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
		
		
		MenuItem showCond=new MenuItem(menuOption, SWT.PUSH);
		showCond.setText("Conditions in the Plan");
		
		Listener listenerShow=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				IDialog dialog=new IDialog(shell,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
					Composite compButton;
					
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l =new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								Control[] child=compButton.getChildren();
								for(int i=0;i<child.length;i++) {
									Button btn=(Button) child[i];
									if(btn.getSelection()) {
										System.out.println(btn.getText());
										getDialog().setVisible(false);
									}
								}
								
								
							}
						};
						return l;
					}
					
					@Override
					public void createContent() {
						getLabel().setText("Choice for Showing/Hiding conditions");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						compButton=new Composite(c, SWT.ALL);
						compButton.setLayout (new RowLayout (SWT.HORIZONTAL));
						
						Button showAllBtn=new Button(compButton, SWT.RADIO);
						showAllBtn.setText("ShowAll Conditions");
						
						Button hideAllBtn=new Button(compButton, SWT.RADIO);
						hideAllBtn.setText("HideAll Conditions");
						
						Button choiceUserBtn=new Button(compButton, SWT.RADIO);
						choiceUserBtn.setText("Depending on Choice");
						
						this.getDialog().pack();

						
					}
				};
				
				dialog.createContent();
			}
		};
		
		showCond.addListener(SWT.Selection, listenerShow);
		
		MenuItem menuLines=new MenuItem(menuOption, SWT.PUSH);
		menuLines.setText("Create Connection");
        Listener listenerLine=new Listener() {
			Composite compButton;

			@Override
			public void handleEvent(Event event) {
				IDialog dialog=new IDialog(shell,SWT.DIALOG_TRIM) {
					
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l =new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								
								Control[] child=compButton.getChildren();
								for(int i=0;i<child.length;i++) {
									Button btn=(Button) child[i];
									if(btn.getSelection()) {
											LineCanvas lineCanvas=new LineCanvas(contentAction);
											lineCanvas.addlistener();
										
										
									}
								}
									
								
								
							}
						};
						return l;
					}
					
					@Override
					public void createContent() {
						getLabel().setText("Create Connection");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						compButton=new Composite(c, SWT.ALL);
						compButton.setLayout (new RowLayout (SWT.VERTICAL));
												
						
						Button archBtn=new Button(compButton, SWT.TOGGLE);
						archBtn.setText("draw arch");
						
						
						Button ordBtn=new Button(compButton, SWT.TOGGLE);
						ordBtn.setText("draw Ord");
						
						
						this.getDialog().pack();

						
					}
				};
				
				dialog.createContent();
			}
		};
		
		menuLines.addListener(SWT.Selection, listenerLine);
		
	
		
		
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
		contentAction=new GraphContent(PlanView, SWT.ALL);
        item.setControl(contentAction);
        PlanView.setSelection(item);
 
		console=new Group(sashForm2, SWT.SCROLL_LINE);
		console.setText("Console");
		//console.setFont(boldFont);
		FormLayout Layout = new FormLayout();
		Layout.marginWidth = 5;
		Layout.marginHeight = 5;
		console.setLayout(Layout);

		DropTarget target = new DropTarget(contentAction, DND.DROP_MOVE | DND.DROP_COPY);
	    target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		target.addDropListener(new MyDropActionListener(PlanView, target));
		
		Display.getDefault().timerExec(100, new Runnable() {
	    @Override
	    public void run() {
	      //composite.redraw();
	    	contentAction.redraw();
	      // Run again - TODO add logic to stop after correct number of moves
	      Display.getDefault().timerExec(100, this);
	    }
	   });
		
		

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
