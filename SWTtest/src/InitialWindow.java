import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InitialWindow {
	
	
  Display display = new Display();
  Shell shell = new Shell(display);
  SashForm sashForm;
  SashForm sashForm2;

  public InitialWindow() {
    
    shell.setLayout(new FillLayout());
    
    sashForm = new SashForm(shell, SWT.HORIZONTAL);
    
    Group domain=new Group(sashForm, SWT.SCROLL_LINE);
	Font boldFont = new Font( domain.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );
	domain.setText("Domain Graph");
	domain.setFont(boldFont);
	FormLayout ownerLayout = new FormLayout();
	ownerLayout.marginWidth = 5;
	ownerLayout.marginHeight = 5;
	domain.setLayout(ownerLayout);
    
    sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
    CTabFolder folder = new CTabFolder (sashForm2, SWT.PUSH);
	folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	folder.setSimple(false);
	folder.setUnselectedImageVisible(false);
	folder.setUnselectedCloseVisible(false);
	

	CTabItem item = new CTabItem(folder, SWT.CLOSE);
	item.setText("Item ");
	Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text.setText("vaffanculo!!!");
	item.setControl(text);
	

	Group console=new Group(sashForm2, SWT.SCROLL_LINE);
	console.setText("Console");
	console.setFont(boldFont);
	FormLayout Layout = new FormLayout();
	Layout.marginWidth = 5;
	Layout.marginHeight = 5;
	console.setLayout(Layout);

    
    
    shell.setMaximized(false);
    shell.open();
    //textUser.forceFocus();

    // Set up the event loop.
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        // If no more entries in event queue
        display.sleep();
      }
    }

    display.dispose();
  }


  public static void main(String[] args) {
    new InitialWindow();
  }
}