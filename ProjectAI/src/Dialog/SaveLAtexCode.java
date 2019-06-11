package Dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import View.ConsoleViewDomain;
import View.ConsoleViewPlan;
import resourceLoader.ResourceLoader;

public class SaveLAtexCode extends FileDialog  {

	File dirLatex;
	File directory;
	File fileLatex; 
	ConsoleViewPlan consoleViewPlan;
	ConsoleViewDomain consoleViewDomain;
	boolean IsdomainLoad;


	
	
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
		if(open ()!=null) {
			createFilePlan(getFilterPath(),getFileName());;	
		}
		
		

		
		
		
		
		
		
	}

	
	public void createFilePlan(String filepath,String name) {
		

		fileLatex = new File(filepath,name);
		

		try {
				fileLatex.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		if (fileLatex.isFile()) {
			consoleViewPlan.updateView();
			WriteTextToFile(fileLatex,consoleViewPlan.getTextPlan().getText());
			try {
				copyTikzLibrary();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(saveDomainFile(fileLatex.getParentFile().getAbsolutePath())){
				IsdomainLoad=true;
			}else {
				IsdomainLoad=false;
				MessageBox messageBox = new MessageBox(consoleViewDomain.getShell(),
						SWT.ICON_WARNING |  SWT.OK);

				messageBox.setText("Warning");
				messageBox.setMessage("Domain not Present, please load the domain");
				messageBox.open();
			}
			
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
			boolean result = false;

			try {
				directory.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
			}
		}

		if (!dirLatex.exists()) {
			boolean result = false;

			try {
				dirLatex.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
			}
		}

	}
	
	public void copyTikzLibrary() throws IOException  {

		InputStream inputStream=ResourceLoader
				.load("TikzLibrary/tikzlibraryaiplans.code.tex");
		
		 byte[] buffer = new byte[inputStream.available()];
		 inputStream.read(buffer);
		
		File file2=new File(fileLatex.getParentFile(), "tikzlibraryaiplans.code.tex");
		OutputStream outStream = new FileOutputStream(file2);
		outStream.write(buffer);
		outStream.close();
		
	}
	
	public boolean saveDomainFile(String dir) {
	
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
					//\newcommand{\LengthsOfEmptyTasks}   {.35cm} has length 44
					if(consoleViewDomain.getTextDomain().getText().length()>45) {
						WriteTextToFile(file,consoleViewDomain.getTextDomain().getText());
						return true;
					}else {
						return false;
					}
				}
			}
			return false;
		

		
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

	
	
	
	public File getFileLatex() {
		return fileLatex;
	}
	
	

	public boolean isIsdomainLoad() {
		return IsdomainLoad;
	}

	@Override
	protected void checkSubclass() {
	
	}
}
