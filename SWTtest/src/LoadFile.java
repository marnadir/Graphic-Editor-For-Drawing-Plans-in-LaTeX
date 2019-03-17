

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class LoadFile {

  public static void main(String[] args) {
    Display display = new Display();
    final Shell shell = new Shell(display);

    FileDialog dlg = new FileDialog(shell, SWT.MULTI);
    String name;
    dlg.setFilterPath(System.getProperty("user.home")+"/TDP"+"/dirLog");
    name=dlg.open();
    System.out.println(name);
    display.dispose();

  }
}
