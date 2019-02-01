package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Action.Node;
import Dialog.IDialog;
import PlanPart.PlanContent;
import PlanPart.Oval;
import State.GoalStateCanvas;
import State.InitialStateCanvas;

public class PlanView  extends CTabFolder{
	
	ConsoleView consoleView;

	public PlanView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void setLayout() {
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setSimple(false);
		setUnselectedImageVisible(false);
		setUnselectedCloseVisible(false);
	}
	
	public void createContent(DomainView domainView) {
		CTabItem item = new CTabItem(this, SWT.CLOSE);
		PlanContent contentAction = new PlanContent(this, SWT.ALL);
		contentAction.addDndListener(domainView.getTreeAction());
		item.setControl(contentAction);
		setSelection(item);
		ArrayList<PlanContent> listOfPlan = new ArrayList<>();
		listOfPlan.add(contentAction);
		item.setText("Plan" + listOfPlan.size());

		ToolBar t = new ToolBar(this, SWT.ALL);
		ToolItem toolShow=new ToolItem(t, SWT.CHECK);
		Image icon = new Image(getDisplay(), "img/eye.png");
		toolShow.setImage(icon);
		toolShow.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
				for(Node node:updateNodeList) {
					ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
					Iterator<Oval> i = listOval.iterator();
					while (i.hasNext()) {
							Oval oval = i.next(); // must be called before you can call i.remove()
							if(oval!=null) {
//								if(oval.getNode()  instanceof Node) {
									oval.dispose();
									 i.remove();
									 contentAction.getOvalCounter().setListOval(listOval);
//								}
							}
							
					}
					if(toolShow.getSelection()) {
						node.getAction().setIsShownCond(true);
						node.pack();

					}else {
						node.getAction().setIsShownCond(false);
						node.pack();
					}
				}
				
				if(toolShow.getSelection()) {
					if(contentAction.getInitialStateCanvas()!=null) {
						contentAction.getInitialStateCanvas().setShownCond(true);
						contentAction.getInitialStateCanvas().pack();
					}
					if(contentAction.getGoalStateCanvas()!=null) {
						contentAction.getGoalStateCanvas().setShownCond(true);
						contentAction.getGoalStateCanvas().pack();
					}
					
				}else {
					if(contentAction.getInitialStateCanvas()!=null) {
						contentAction.getInitialStateCanvas().setShownCond(false);
						contentAction.getInitialStateCanvas().pack();
					}
					if(contentAction.getGoalStateCanvas()!=null) {
						contentAction.getGoalStateCanvas().setShownCond(false);
						contentAction.getGoalStateCanvas().pack();
					}
				}
				

			}
		} );
		
		
		ToolItem toolSetLenght=new ToolItem(t, SWT.PUSH);
		icon = new Image(getDisplay(), "img/setL.png");
		toolSetLenght.setImage(icon);
		toolSetLenght.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				IDialog d=new IDialog(getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
					Combo combo;
					Text lenghtPrec;
					Text lenghtEff;
					@Override
					public Listener getOkbtnListener() {
						Listener l;
						l = new Listener() {

							@Override
							public void handleEvent(Event event) {
								if(combo.getText().equalsIgnoreCase("action")) {
									if(isNumeric(lenghtPrec.getText())&& isNumeric(lenghtEff.getText())) {
										ArrayList<Node> updateNodeList = contentAction.getActionInPlan();
										if(updateNodeList !=null) {
											for(Node node:updateNodeList) {
												ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
												Iterator<Oval> i = listOval.iterator();
												while (i.hasNext()) {
														Oval oval = i.next(); // must be called before you can call i.remove()
														if(oval!=null) {
															if(oval.getNode()  instanceof Node) {
																oval.dispose();
																 i.remove();
																 contentAction.getOvalCounter().setListOval(listOval);
															}
														}
														
												}

												
												
												
												node.getAction().setLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));
												node.getAction().setStandardLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));

												node.getAction().setLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
												node.getAction().setStandardLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
												

												node.pack();
										}
										
											getDialog().dispose();

											
											
										}
									}
								}else {
									if(isNumeric(lenghtPrec.getText())) {
										if(	contentAction.getInitialStateCanvas()!=null) {
											
											ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
											Iterator<Oval> i = listOval.iterator();
											while (i.hasNext()) {
													Oval oval = i.next(); // must be called before you can call i.remove()
													if(oval.getStateCanvas()!=null) {
														if(oval.getStateCanvas()  instanceof InitialStateCanvas) {
															oval.dispose();
															i.remove();
															contentAction.getOvalCounter().setListOval(listOval);
														}
													}
													
											}
											
											
											

																						
											contentAction.getInitialStateCanvas().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentAction.getInitialStateCanvas().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentAction.getInitialStateCanvas().pack();
										}
										if(contentAction.getGoalStateCanvas()!=null) {
											if(	contentAction.getGoalStateCanvas()!=null) {
												
												ArrayList<Oval> listOval=contentAction.getOvalCounter().getListOval();
												for(int i=0;i<listOval.size();i++) {
													if(listOval.get(i).getStateCanvas()!=null) {
														if(listOval.get(i).getStateCanvas()  instanceof GoalStateCanvas) {
															listOval.get(i).dispose();
															listOval.remove(i);
															contentAction.getOvalCounter().setListOval(listOval);
														}
													}
												}
																							
												contentAction.getGoalStateCanvas().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentAction.getGoalStateCanvas().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentAction.getGoalStateCanvas().pack();
											}
										}
										getDialog().dispose();

									}
								}

							}
						};
						return l;
					}
					
					@Override
					public void createContent() {
						getLabel().setText("Set global lenght");
						this.getLabel().pack();
						Composite c = getComposite();
						c.setLayout(new GridLayout(1, false));
						Composite comp1 = new Composite(c, SWT.ALL);
						comp1.setLayout(new RowLayout(SWT.VERTICAL));
						Label l=new Label(comp1, SWT.ALL);
						l.setText("choose: ");
						combo = new Combo(comp1, SWT.PUSH);
						combo.setItems("Action","So/Goal State");
						combo.setText(combo.getItem(0));
						combo.pack();
						
						Composite comp2=new Composite(c, SWT.ALL);
						comp2.setLayout(new GridLayout(2, false));
						Label l2=new Label(comp2, SWT.ALL);
						l2.setText("Prec:");	
						lenghtPrec=new Text(comp2, SWT.BORDER);
						
						
						Label l3=new Label(comp2, SWT.ALL);
						l3.setText("Eff:");

						lenghtEff=new Text(comp2, SWT.BORDER);
						Label l4=new Label(comp2, SWT.ALL);
						l4.setText("Default value is 1.2cm");
						

						combo.addSelectionListener(new SelectionAdapter() {
							  public void widgetSelected(SelectionEvent e) {
									if(combo.getText().equalsIgnoreCase("action")) {	
										l3.setVisible(true);
										lenghtEff.setVisible(true);

										comp2.pack();
										getDialog().pack();

										
									}else {
										l2.setText("Cond:");
										l3.setVisible(false);
										lenghtEff.setVisible(false);
										getDialog().pack();

									}
							  }
						});
						
						
						this.getDialog().pack();
						
					}
				};
				
				d.createContent();
			}
		});
		
		ToolItem PDFPreview=new ToolItem(t,SWT.PUSH);
		icon = new Image(getDisplay(), "img/pdf.ico");
		PDFPreview.setImage(icon);
		
		PDFPreview.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				ConsoleViewDomain consoleViewDomain=consoleView.getConsoleViewDomain();
				consoleViewDomain.saveFile();
				
				ConsoleViewPlan consoleViewPlan=consoleView.getConsoleViewPlan();
				consoleViewPlan.saveFile();
				
				Process proc;
				try {
					ProcessBuilder pb = new ProcessBuilder("xdg-open"+consoleViewPlan.getFile().getAbsolutePath());
					pb.directory(consoleViewPlan.getDirPlan());
					pb.start();
					
//					String cmd1="cd "+consoleViewPlan.getDirPlan().getAbsolutePath();
//					System.out.println(cmd1);
//					proc=Runtime.getRuntime().exec(consoleViewPlan.getDirPlan().getAbsolutePath());
//					proc = Runtime.getRuntime().exec("pdflatex "+consoleViewPlan.getFile().getName());
//					proc.waitFor();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                        
			
				
				
				
			}
		});
		
		ToolItem i = new ToolItem(t, SWT.PUSH);
		i.setToolTipText("add a new Plan");
		 icon = new Image(getDisplay(), "img/add-documents.png");
		i.setImage(icon);
		i.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent var1) {

				CTabItem item2 = new CTabItem(getPlanView(), SWT.CLOSE);
				PlanContent contentAction = new PlanContent(getPlanView(), SWT.ALL);
				item2.setControl(contentAction);
				listOfPlan.add(contentAction);
				item2.setText("Plan" + listOfPlan.size());
				contentAction.addDndListener(domainView.getTreeAction());

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent var1) {
				// TODO Auto-generated method stub

			}
		});

		setTopRight(t, SWT.RIGHT);
		setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, SWT.DEFAULT).y, getTabHeight()));

	}
	
	public boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public PlanView getPlanView() {
		return this;
	}
	
	public void setPdfPreView(ConsoleView consoleView) {
		this.consoleView=consoleView;
	}
	
	public ArrayList<PlanContent> getAllPlan() {
		ArrayList<PlanContent> child=new ArrayList<>();
		Control[] control=getChildren();
		for(Control c:control) {
			if(c instanceof PlanContent) {
				child.add((PlanContent)c);
				
			}
		}
		return child;
	}
	
	public PlanContent getPlan() {
		PlanContent planContent=(PlanContent) getSelection().getControl();
		return planContent;
		
	}
	
	@Override
	protected void checkSubclass() {
		
	}
}
