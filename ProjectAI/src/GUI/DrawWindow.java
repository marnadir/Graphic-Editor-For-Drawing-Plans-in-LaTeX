package GUI;



import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Action;
import DNDAaction.MyDropActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.LinkCanvas;
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
	private ArrayList<Action> updateActionList;



	
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
			private Composite compPoint;
			private LinkCanvas line;
			Label l1 = null;
			Label l2 = null;
			

			@Override
			public void handleEvent(Event event) {
			
				
				IDialog dialog=new IDialog(shell,SWT.DIALOG_TRIM) {
					
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l =new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								
								if(line != null) {
									if(!l1.getText().contains("Select the point") && !l2.getText().contains("Select the point")) {
										line.drawLine();
										l1.setText("First Cond. :" +"Select the point");
										l2.setText("Second Cond. :" +"Select the point");
										line.removelistener(l1, l2);
										
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
												
						
						Button archBtn=new Button(compButton, SWT.PUSH);
						archBtn.setText("draw arch");
						
						
						Button ordBtn=new Button(compButton,SWT.PUSH);
						ordBtn.setText("draw Ord");
						
						compPoint =new Composite(c, SWT.ALL);
						compPoint.setLayout(new GridLayout());								

						archBtn.addListener(SWT.Selection, new Listener() {

							@Override
							public void handleEvent(Event event) {

							
								
								if (compPoint.getChildren().length < 1) {
									 l1 = new Label(compPoint, SWT.ALL);
									l1.setText("First Cond. :" + "Select the point");

									l1.pack();

									l2 = new Label(compPoint, SWT.ALL);
									l2.setText("Second Cond. :" + "Select the point");

									l2.pack();
									compPoint.pack();
									getDialog().pack();
									
								}else {
									compPoint.setVisible(true);
									l1.setText("First Cond. :" + "Select the point");
									l2.setText("Second Cond. :" + "Select the point");

									
								}
							
								
								line=new LinkCanvas(contentAction);
								line.addlistener(l1,l2);

							}
						});

						ordBtn.addListener(SWT.Selection, new Listener() {
							
							@Override
							public void handleEvent(Event event) {

							
								compPoint.setVisible(false);
								line=null;
								getDialog().pack();
								
								
							}
						});
						
						
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
		console.setLayout(new GridLayout(2, true));
		
		
		
		Group consoleDomain=new Group(console, SWT.ALL);
		consoleDomain.setText("Domain");
		consoleDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		consoleDomain.setLayout(new GridLayout(1, true));
	    
		ToolBar toolBarDomain = new ToolBar(consoleDomain, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	   
		ToolItem updateTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
		updateTextDomain.setText("update");
	    Image icon = new Image(shell.getDisplay(), "img/refresh.png");
	    updateTextDomain.setImage(icon);
	    Text textDomain = new Text(consoleDomain, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
	    
	    textDomain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    textDomain.pack();
	    updateTextDomain.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				textDomain.setText(".....");
				if(createDomainView.getInitialState() != null) {
					createDomainView.getInitialState().generateLatexCode();
					textDomain.insert(createDomainView.getInitialState().getLatexCode());
				}
				if(createDomainView.getGoalState() != null) {
					createDomainView.getGoalState().generateLatexCode();
					textDomain.insert(createDomainView.getGoalState().getLatexCode());
				}
			
				
				updateActionList=createDomainView.getListAction();
				for(int i=0;i<updateActionList.size();i++) {
					updateActionList.get(i).generateLatexCode();
					textDomain.insert(updateActionList.get(i).getLatexCode());
				}
			
				
			}
		});
	    
	    ToolItem clearTextDomain = new ToolItem(toolBarDomain, SWT.PUSH);
	    clearTextDomain.setText("clear");
	    icon = new Image(shell.getDisplay(), "img/clear.ico");
	    clearTextDomain.setImage(icon);
	    clearTextDomain.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				textDomain.setText(".....");
				
			}
		});
	    
	   
	    
		
	    toolBarDomain.pack();
	    
	    
	    
		
		
		Group consolePlan=new Group(console, SWT.ALL);
		consolePlan.setText("Plan");
		consolePlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		consolePlan.setLayout(new GridLayout(1, true));

		
		ToolBar toolBarPlan = new ToolBar(consolePlan, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		   
		ToolItem updateTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
		updateTextPlan.setText("update");
	    icon = new Image(shell.getDisplay(), "img/refresh.png");
	    updateTextPlan.setImage(icon);
	    
	    
	    
	    ToolItem clearTextPlan = new ToolItem(toolBarPlan, SWT.PUSH);
	    clearTextPlan.setText("clear");
	    icon = new Image(shell.getDisplay(), "img/clear.ico");
	    clearTextPlan.setImage(icon);
	    
	    Text textPlan = new Text(consolePlan, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.READ_ONLY);
	    textPlan.insert("update data...");
	    textPlan.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    textPlan.pack();
		
	    toolBarPlan.pack();

		updateActionList=createDomainView.getListAction();

		DropTarget target = new DropTarget(contentAction, DND.DROP_MOVE | DND.DROP_COPY);
	    target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		target.addDropListener(new MyDropActionListener(PlanView, target,updateActionList));
		
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
