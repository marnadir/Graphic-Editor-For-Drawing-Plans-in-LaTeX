package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	DomainView domainView;

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
		this.domainView=domainView;
		CTabItem item = new CTabItem(this, SWT.CLOSE);
		PlanContent contentPlan = new PlanContent(this, SWT.ALL);
		contentPlan.addDndListener(domainView.getTreeAction());
		item.setControl(contentPlan);
		setSelection(item);
		ArrayList<PlanContent> listOfPlan = new ArrayList<>();
		listOfPlan.add(contentPlan);
		item.setText("Plan" + listOfPlan.size());

		ToolBar t = new ToolBar(this, SWT.ALL);
		ToolItem toolShow=new ToolItem(t, SWT.CHECK);
		Image icon = new Image(getDisplay(), "img/eye.png");
		toolShow.setImage(icon);
		toolShow.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				ArrayList<Node> updateNodeList = contentPlan.getActionInPlan();
				for(Node node:updateNodeList) {
					ArrayList<Oval> listOval=contentPlan.getOvalCounter().getListOval();
//					Iterator<Oval> i = listOval.iterator();
//					while (i.hasNext()) {
//							Oval oval = i.next(); // must be called before you can call i.remove()
//							if(oval!=null) {
////								if(oval.getNode()  instanceof Node) {
//									oval.dispose();
//									 i.remove();
//									 contentAction.getOvalCounter().setListOval(listOval);
////								}
//							}
//							
//					}
					if(toolShow.getSelection()) {
						node.getAction().setIsShownCond(true);
						node.pack();
						node.redraw();

					}else {
						node.getAction().setIsShownCond(false);
						node.pack();
						node.redraw();
					}
					contentPlan.redraw();

				
				}

				
				if(toolShow.getSelection()) {
					if(contentPlan.getInitialStateCanvas()!=null) {
						contentPlan.getInitialStateCanvas().getState().setShownCond(true);
						contentPlan.getInitialStateCanvas().pack();
					}
					if(contentPlan.getGoalStateCanvas()!=null) {
						contentPlan.getGoalStateCanvas().getState().setShownCond(true);
						contentPlan.getGoalStateCanvas().pack();
					}
					
				}else {
					if(contentPlan.getInitialStateCanvas()!=null) {
						contentPlan.getInitialStateCanvas().getState().setShownCond(false);
						contentPlan.getInitialStateCanvas().pack();
					}
					if(contentPlan.getGoalStateCanvas()!=null) {
						contentPlan.getGoalStateCanvas().getState().setShownCond(false);
						contentPlan.getGoalStateCanvas().pack();
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
										ArrayList<Node> updateNodeList = contentPlan.getActionInPlan();
										if(updateNodeList !=null) {
											for(Node node:updateNodeList) {
												ArrayList<Oval> listOval=contentPlan.getOvalCounter().getListOval();
												Iterator<Oval> i = listOval.iterator();
												while (i.hasNext()) {
														Oval oval = i.next(); // must be called before you can call i.remove()
														if(oval!=null) {
															if(oval.getNode()  instanceof Node) {
																oval.dispose();
																 i.remove();
																 contentPlan.getOvalCounter().setListOval(listOval);
															}
														}
														
												}

												
												
												
												node.getAction().setLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));
												node.getAction().setStandardLengthPrecFromCm(Double.parseDouble(lenghtPrec.getText()));

												node.getAction().setLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
												node.getAction().setStandardLengthEffFromCm(Double.parseDouble(lenghtEff.getText()));
												

												node.pack();
										}
										
											dispose();

											
											
										}
									}
								}else {
									if(isNumeric(lenghtPrec.getText())) {
										if(	contentPlan.getInitialStateCanvas()!=null) {
											
											ArrayList<Oval> listOval=contentPlan.getOvalCounter().getListOval();
											Iterator<Oval> i = listOval.iterator();
											while (i.hasNext()) {
													Oval oval = i.next(); // must be called before you can call i.remove()
													if(oval.getStateCanvas()!=null) {
														if(oval.getStateCanvas()  instanceof InitialStateCanvas) {
															oval.dispose();
															i.remove();
															contentPlan.getOvalCounter().setListOval(listOval);
														}
													}
													
											}
											
											
											

																						
											contentPlan.getInitialStateCanvas().getState().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentPlan.getInitialStateCanvas().getState().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
											contentPlan.getInitialStateCanvas().pack();
										}
										if(contentPlan.getGoalStateCanvas()!=null) {
											if(	contentPlan.getGoalStateCanvas()!=null) {
												
												ArrayList<Oval> listOval=contentPlan.getOvalCounter().getListOval();
												for(int i=0;i<listOval.size();i++) {
													if(listOval.get(i).getStateCanvas()!=null) {
														if(listOval.get(i).getStateCanvas()  instanceof GoalStateCanvas) {
															listOval.get(i).dispose();
															listOval.remove(i);
															contentPlan.getOvalCounter().setListOval(listOval);
														}
													}
												}
																							
												contentPlan.getGoalStateCanvas().getState().setLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentPlan.getGoalStateCanvas().getState().setStandardLengthFromCm(Double.parseDouble(lenghtPrec.getText()));
												contentPlan.getGoalStateCanvas().pack();
											}
										}
										dispose();

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
										pack();

										
									}else {
										l2.setText("Cond:");
										l3.setVisible(false);
										lenghtEff.setVisible(false);
										pack();

									}
							  }
						});
						
						
						pack();
						
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
//				try {
//					ProcessBuilder pb = new ProcessBuilder("xdg-open"+consoleViewPlan.getFile().getAbsolutePath());
//					pb.directory(consoleViewPlan.getDirPlan());
//					pb.start();
//					
//					String cmd1="cd "+consoleViewPlan.getDirPlan().getAbsolutePath();
//					System.out.println(cmd1);
//					proc=Runtime.getRuntime().exec(consoleViewPlan.getDirPlan().getAbsolutePath());
//					proc = Runtime.getRuntime().exec("pdflatex "+consoleViewPlan.getFile().getName());
//					proc.waitFor();
//
//				} 
				
				try {

					String cmd1="cd "+consoleViewPlan.getDirPlan().getAbsolutePath();
					String cmd2="pdflatex LatexPlan\\ .tex  -synctex=1 -interaction=nonstopmode";
					String cmd3="xdg-open LatexPlan.pdf";
							
					Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c",
							cmd1+" && "+cmd2+" && "+cmd3 });

					process.waitFor();

					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					StringBuilder builder = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
						builder.append(System.getProperty("line.separator"));
					}
					String result = builder.toString();
					System.out.println(result);

				}
				
				
				
				catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());

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
	
	
	
	
	public DomainView getDomainView() {
		return domainView;
	}

	@Override
	protected void checkSubclass() {
		
	}
}
