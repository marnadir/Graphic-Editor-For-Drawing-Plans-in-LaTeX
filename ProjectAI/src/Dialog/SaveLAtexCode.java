package Dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import View.ConsoleViewDomain;
import View.ConsoleViewPlan;
import View.DomainView;

public class SaveLAtexCode extends FileDialog  {

	File dirLatex;
	File directory;
	File fileLatex; 
	ConsoleViewPlan consoleViewPlan;
	ConsoleViewDomain consoleViewDomain;


	
	
	public SaveLAtexCode(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public void createContent() {
		createDirector();
		String [] filterNames = new String [] {"All Files (*)"};
		String [] filterExtensions = new String [] {"*.tex", "*"};
		String filterPath = System.getProperty("user.home")+"/TDP"+"/dirLatex";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String [] {"Image Files", "All Files (*.*)"};
			filterExtensions = new String [] {"*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*"};
			filterPath = "c:\\";
		}
		setFilterNames (filterNames);
		setFilterExtensions (filterExtensions);
		setFilterPath (filterPath);
		setFileName ("PlanLatex");
		System.out.println ("Save to: " +open ());	
		createFilePlan(getFileName(),getFilterPath());

		
		
		
		
		
		
	}

	
	public void createFilePlan(String name,String filepath) {
		

		fileLatex = new File(filepath, name);
		
		if (!fileLatex.exists()) {
			try {
				fileLatex.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (fileLatex.isFile()) {
			consoleViewPlan.updateView();
			WriteTextToFile(fileLatex,consoleViewPlan.getTextPlan().getText());
			copyTikzLibrary();
			saveDomainFile(getFilterPath());
		}
		

	}
	
	public String getPathDirectory() {
		String file = null;
		
		return file;
	}
	
	
	public void createDirector() {
		String filepath = System.getProperty("user.home");
		directory = new File(filepath + "/TDP");
		dirLatex = new File(filepath + "/TDP" + "/dirLatex");

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
	
	public void copyTikzLibrary()  {
		File file=new File("TikzLibrary/tikzlibraryaiplans.code.tex");
		System.out.println(file.exists());
		
		File file2=new File(getFilterPath(), "tikzlibraryaiplans.code.tex");
		try {
			if(file.exists()) {
				file2.createNewFile();

			}
			Files.copy(file.toPath(), file2.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	public void saveDomainFile(String dir) {
	
			if (dir!=null) {
				File file = new File(dir, "tikzlibrarydomain.code.tex");
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (file.isFile()) {
					consoleViewDomain.updateView();
					WriteTextToFile(file,consoleViewDomain.getTextDomain().getText());
				}
			}
		

		
	}
	
	
	@Override
	public String open() {

		String fileName = null;

		boolean done = false;

		while (!done) {

			fileName = super.open();
			if (fileName == null) {

				done = true;
			} else {

				File file = new File(fileName);
				if (file.exists()) {

					MessageBox mb = new MessageBox(super.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

					mb.setMessage(fileName + " already exists. Do you want to replace it?");

					done = mb.open() == SWT.YES;
				} else {

					done = true;
				}
			}
		}
		return fileName;
	}
	
	public void WriteTextToFile(File file,String serObj) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(serObj);

		writer.close();
		
		
	}

	public void setConsoleViewPlan(ConsoleViewPlan consoleViewPlan) {
		this.consoleViewPlan = consoleViewPlan;
	}
	
	

	public void setConsoleViewDomain(ConsoleViewDomain consoleViewDomain) {
		this.consoleViewDomain = consoleViewDomain;
	}

	@Override
	protected void checkSubclass() {
	
	}
}
