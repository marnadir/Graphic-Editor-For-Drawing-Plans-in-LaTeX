
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ScrollTest {

	public static void main(String[] args)
	{
	    Display display = new Display();
	    Shell shell = new Shell();
	    shell.setText("StackOverflow");
	    shell.setLayout(new GridLayout(1, false));

	    Group first = new Group(shell, SWT.NONE);
	    first.setText("Group 1");
	    first.setLayout(new GridLayout(1, false));
	    GridData firstData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    firstData.heightHint = 400;
	    first.setLayoutData(firstData);

	    ScrolledComposite firstScroll = new ScrolledComposite(first, SWT.V_SCROLL);
	    firstScroll.setLayout(new GridLayout(1, false));
	    firstScroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    Composite firstContent = new Composite(firstScroll, SWT.NONE);
	    firstContent.setLayout(new GridLayout(1, false));
	    firstContent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    for (int i = 0; i < 20; i++)
	    {
	        Text text = new Text(firstContent, SWT.BORDER);
	        text.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	    }

	    firstScroll.setContent(firstContent);
	    firstScroll.setExpandHorizontal(true);
	    firstScroll.setExpandVertical(true);
	    firstScroll.setMinSize(firstContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	    Group second = new Group(shell, SWT.NONE);
	    second.setText("Group 2");
	    second.setLayout(new GridLayout(1, false));
	    GridData secondData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    secondData.minimumHeight = 100;
	    second.setLayoutData(secondData);

	    Text text = new Text(second, SWT.BORDER | SWT.MULTI);
	    text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	    shell.pack();
	    shell.setSize(400, shell.getSize().y);
	    shell.open();

	    while (!shell.isDisposed())
	    {
	        if (!display.readAndDispatch())
	        {
	            display.sleep();
	        }
	    }
	    display.dispose();
	}
	
}
