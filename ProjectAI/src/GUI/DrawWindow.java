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
import Action.Node;
import DNDAaction.MyDropActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.LinkCanvas;
import GraphPart.OrderCondition;
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
	private ArrayList<Action> updateActionListDomain;
	private ArrayList<Node> updateNodeList;
	private ArrayList<LinkCanvas> updateLinkList;
	private ArrayList<OrderCondition> updateOrder;


	
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
        Listener listenerLink=new Listener() {
			Composite compButton;
			private Composite compPoint;
			
			private LinkCanvas link;
			private OrderCondition orderCond;
			Label l1 = null;
			
			Label l2 = null;
			String c1="....";
			String c2="....";

			@Override
			public void handleEvent(Event event) {
			
				
				IDialog dialog=new IDialog(shell,SWT.DIALOG_TRIM) {
					
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l =new Listener() {
							
							@Override
							public void handleEvent(Event event) {
								
								if(link != null) {
									if(!l1.getText().contains("Select the point") && !l2.getText().contains("Select the point")) {
										link.drawLine();
										contentAction.getLink().add(link);
										l1.setText("First Cond. :" +"Select the point");
										l2.setText("Second Cond. :" +"Select the point");
										link.removelistener(l1, l2);
										
									}
								}else if(orderCond !=null){
									if(!l2.getText().contains("null")) {
										orderCond.drawOrder();
										contentAction.getOrds().add(orderCond);
										orderCond.pack();
										c1 = "null";
										c2 = "null";
										l1.setText("ordering of actions");
										l2.setText(c1 + "<" + c2);										
										orderCond.removelistener(l2);
										
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

						compPoint = new Composite(c, SWT.ALL);
						compPoint.setLayout(new GridLayout());
						l1 = new Label(compPoint, SWT.ALL);
						l2 = new Label(compPoint, SWT.ALL);
						compPoint.setVisible(false);

						archBtn.addListener(SWT.Selection, new Listener() {

							@Override
							public void handleEvent(Event event) {

								orderCond=null;
								
								l1.setText("First Cond. :" + "Select the point");
								l1.pack();
								l2.setText("Second Cond. :" + "Select the point");
								l2.pack();
								compPoint.pack();
								getDialog().pack();
								compPoint.setVisible(true);
								

								link = new LinkCanvas(contentAction);
								link.addlistener(l1, l2);

							}
						});

						ordBtn.addListener(SWT.Selection, new Listener() {

							@Override
							public void handleEvent(Event event) {

								link = null;

								c1 = "null";
								c2 = "null";
								l1.setText("ordering of actions");
								l2.setText(c1 + "<" + c2);
								l2.pack();
								compPoint.pack();
								getDialog().pack();
								compPoint.setVisible(true);

								Composite comp = new Composite(contentAction, SWT.BORDER);
//								comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
//								comp.setLayout(new FillLayout());
								
								
								//sulla definizione di cio, ce qualcosa che mi turba!!
								comp.setSize(100,50);
								comp.setLocation(20, 30);
								//comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_RED));
								
								
								orderCond = new OrderCondition(comp);
								orderCond.addlistener(l2);
								
							}
						});

						this.getDialog().pack();

					}
					
					
					
					
				};
				
				dialog.createContent();
			}
		};
		
		menuLines.addListener(SWT.Selection, listenerLink);
		
	
		
		
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
	    
	    
	    /*domain*/
	    updateTextDomain.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				textDomain.setText("");
				if(createDomainView.getInitialState() != null) {
					createDomainView.getInitialState().generateLatexCode();
					textDomain.insert(createDomainView.getInitialState().getLatexCode());
				}
				if(createDomainView.getGoalState() != null) {
					createDomainView.getGoalState().generateLatexCode();
					textDomain.insert(createDomainView.getGoalState().getLatexCode());
				}
			
				
				updateActionListDomain=createDomainView.getListAction();
				for(int i=0;i<updateActionListDomain.size();i++) {
					updateActionListDomain.get(i).generateLatexCode();
					textDomain.insert(updateActionListDomain.get(i).getLatexCode());
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
	    /*Plan*/
	    updateTextPlan.addListener(SWT.Selection, new Listener() {
			
				@Override
				public void handleEvent(Event event) {
					textPlan.setText("");
									
					updateNodeList=contentAction.getActionInPlan();
					for(int i=0;i<updateNodeList.size();i++) {
						updateNodeList.get(i).generateLatexCode();
						textPlan.insert(updateNodeList.get(i).getLatexCode());
					}
				
					updateLinkList=contentAction.getLink();
					for(int i=0;i<updateLinkList.size();i++) {
						updateLinkList.get(i).generateLatexCode();
						textPlan.insert(updateLinkList.get(i).getLatexCode());
					}
					
					updateOrder=contentAction.getOrds();
					for(int i=0;i<updateOrder.size();i++) {
						updateOrder.get(i).generateLatexCode();
						textPlan.insert(updateOrder.get(i).getLatexCode());
					}
					
				}
			});
	    
	    
	 

		updateActionListDomain=createDomainView.getListAction();

		DropTarget target = new DropTarget(contentAction, DND.DROP_MOVE | DND.DROP_COPY);
	    target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		target.addDropListener(new MyDropActionListener(PlanView, target,updateActionListDomain));
		
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
