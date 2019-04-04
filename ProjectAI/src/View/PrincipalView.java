package View;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import Menu.MenuPrincipalView;

public class PrincipalView {

	private Shell shell;
	// private Composite child;
	private SashForm sashForm;
	private SashForm sashForm2;
	private DomainView domainView;
	private ConsoleView consoleView;
	PlanView planView;
	PdfView pdfView;

	private File directory;

	public PrincipalView(Shell shell) {
		this.shell = shell;

	}

	public void draw() {
		
		createContent();
		createMenuWindow();
		createDirector();
	}

	public void createMenuWindow() {

		MenuPrincipalView menuBar=new MenuPrincipalView(shell, SWT.BAR);
		menuBar.fillMenu(domainView, planView);
		
	}

	public void createContent() {

		ScrolledComposite firstScroll = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		firstScroll.setLayout(new GridLayout(1, false));
		firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		sashForm = new SashForm(firstScroll, SWT.HORIZONTAL);

		firstScroll.setContent(sashForm);
		firstScroll.setExpandHorizontal(true);
		firstScroll.setExpandVertical(true);

		domainView = new DomainView(sashForm);
		domainView.createContent();

		sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
		sashForm.setWeights(new int[] { 1, 3 });

		planView = new PlanView(sashForm2, SWT.PUSH);
		planView.setLayout();
		planView.createContent(domainView);
		
		CTabFolder folder=new CTabFolder(sashForm2, SWT.ALL);
		
		CTabItem itemConsole = new CTabItem(folder, SWT.NONE);
		consoleView = new ConsoleView(folder, SWT.SCROLL_LINE);
		consoleView.setLayout();
		consoleView.createContent(domainView, planView);
		itemConsole.setText("Console");
		itemConsole.setControl(consoleView);
		folder.setSelection(itemConsole);
		
		
		CTabItem itemGlobal = new CTabItem(folder, SWT.NONE);
		GlobalOptionView globalOptionView=new GlobalOptionView(folder, SWT.SCROLL_LINE);
		globalOptionView.setLayout();
		globalOptionView.createContent();
		itemGlobal.setText("Global Option");
		itemGlobal.setControl(globalOptionView);
		globalOptionView.setDomainView(domainView);
		
		domainView.setGlobalOptionView(globalOptionView);
		
		CTabItem pdfItem = new CTabItem(folder, SWT.NONE);
		pdfItem.setText("Pdf View");
		pdfView=new PdfView(folder, SWT.ALL);
		pdfItem.setControl(pdfView);
		
		
		
		CTabItem positionItem=new CTabItem(folder, SWT.NONE);
		positionItem.setText("Position View");
		PositionInPlanView positionInPlanView=new PositionInPlanView(folder, SWT.ALL);
		positionInPlanView.setContentPlan(planView.getPlan());
		positionInPlanView.setLayout();
		positionInPlanView.createContent();
		positionItem.setControl(positionInPlanView);
		
		
		
		
		folder.setSelection(itemConsole);
		planView.setPdfPreView(consoleView);
		planView.setPdfView(pdfView);

		shell.setMaximized(false);
	}


	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		File dirLog = new File(filepath + "/TDP" + "/dirLog");
		File dirLatex = new File(filepath + "/TDP" + "/dirLatex");

		// if the directory does not exist, create it
		if (!directory.exists()) {
			System.out.println("creating directory: " + directory.getAbsolutePath());
			boolean result = false;

			try { 
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		if (!dirLog.exists()) {
			System.out.println("creating directory: " + dirLog.getAbsolutePath());
			boolean result = false;

			try {
				dirLog.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		if (!dirLatex.exists()) {
			System.out.println("creating directory: " + dirLatex.getName());
			boolean result = false;

			try {
				dirLatex.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

	}






}
