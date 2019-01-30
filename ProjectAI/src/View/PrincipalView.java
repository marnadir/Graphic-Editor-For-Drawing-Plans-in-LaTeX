package View;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import org.eclipse.swt.widgets.Shell;

import GraphPart.GraphContent;
import Menu.MenuPrincipalView;

public class PrincipalView {

	private Shell shell;
	// private Composite child;
	private SashForm sashForm;
	private SashForm sashForm2;
	private DomainView domainView;
	private GraphContent contentAction;
	private ConsoleView consoleView;

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
		menuBar.fillMenu(domainView, contentAction);
		
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

		PlanView planView = new PlanView(sashForm2, SWT.PUSH);
		planView.setLayout();
		planView.createContent(domainView);
		

		consoleView = new ConsoleView(sashForm2, SWT.SCROLL_LINE);
		consoleView.setLayout();
		consoleView.createContent(domainView, contentAction, planView);
		
		
		
		planView.setPdfPreView(consoleView);


		shell.setMaximized(false);
	}


	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		File dirLog = new File(filepath + "/TDP" + "/dirLog");
		File dirLatex = new File(filepath + "/TDP" + "/dirLatex");

		// if the directory does not exist, create it
		if (!directory.exists()) {
			System.out.println("creating directory: " + directory.getName());
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
			System.out.println("creating directory: " + dirLog.getName());
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
