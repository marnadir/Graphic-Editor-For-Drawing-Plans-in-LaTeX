package dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
/**
 * dialog implemented for management of the main shell window closing event
 * */
public class DialogAreUsure {

	private Shell shell;
	private String text;
	
	public DialogAreUsure(Shell shell,String text) {
		this.shell=shell;
		this.text=text;
	}

	public boolean getResult(Event e) {
		
		boolean result = false;
		MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
		dialog.setText("Question");
		dialog.setMessage(text);
		if(dialog.open() == SWT.OK) {
			return true;
		}else {
			e.doit=false;
		}
		
		return result;
	}

}
