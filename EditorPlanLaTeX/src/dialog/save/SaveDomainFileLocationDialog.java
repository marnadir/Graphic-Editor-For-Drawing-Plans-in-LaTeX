package dialog.save;

import java.io.File;
import java.util.ArrayList;

import javax.swing.text.PlainView;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

import Action.Action;
import View.DomainView;
import command.SaveDomainCommand;
/**
 * Dialog which allows to save the current domain.
 * @author nadir
 * */
public class SaveDomainFileLocationDialog extends FileDialog {

	String fileName;
	File fileLog;
	File directory;
	ArrayList<Action> updateActionListDomain;
	DomainView domainView;
	PlainView plainView;
	File dirDomain;
	File dirPlan;

	public SaveDomainFileLocationDialog(Shell parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public void createContent() {
		SaveDomainCommand command = new SaveDomainCommand();
		command.createDirectorDomain();
		String[] filterNames = new String[] { "*.txt", "All Files (*)" };
		String[] filterExtensions = new String[] { "*.txt", "*" };
		String filterPath = System.getProperty("user.home") + "/TDP" + "/dirDomain";
		String platform = SWT.getPlatform();
		if (platform.equals("win32")) {
			filterNames = new String[] { "Image Files", "All Files (*.*)" };
			filterExtensions = new String[] { "*.gif;*.png;*.bmp;*.jpg;*.jpeg;*.tiff", "*.*" };
			filterPath = "c:\\";
		}
		setFilterNames(filterNames);
		setFilterExtensions(filterExtensions);
		setFilterPath(filterPath);
		setFileName("domain");
		open();
		command.execute(getFileName(), domainView);

	}

	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}

	@Override
	protected void checkSubclass() {

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

}
