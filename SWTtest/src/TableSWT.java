
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableSWT {
	public static void main(String[] args) {
	    Display display = new Display();
	    /** create the new window */
	    Shell shell = new Shell(display);

	    /** adding the window title */
	    shell.setText("Table example");

	    /** add a layout of 1 columns */
	    shell.setLayout(new GridLayout(1, true));

	    /** setting up the window size */
	    shell.setSize(600, 400);

	    Table table = new Table(shell, SWT.BORDER);

	    TableColumn column1 = new TableColumn(table, SWT.LEFT);
	    TableColumn column2 = new TableColumn(table, SWT.LEFT);
	    TableColumn column3 = new TableColumn(table, SWT.LEFT);
	    column1.setText("First Name");
	    column2.setText("Last Name");
	    column3.setText("Address");
	    column1.setWidth(70);
	    column2.setWidth(70);
	    column3.setWidth(100);
	    table.setHeaderVisible(true);

	    TableItem item1 = new TableItem(table, SWT.NONE);
	    item1.setText(new String[] { "Alexavier", "Laubscher", "Madrid, Spain" });
	    TableItem item2 = new TableItem(table, SWT.NONE);
	    item2.setText(new String[] { "Caitlyn", "Kassin", "Paris, France" });
	    TableItem item3 = new TableItem(table, SWT.NONE);
	    item3.setText(new String[] { "Cornisha", "Laferte", "Rome, Italy" });
	    TableItem item4 = new TableItem(table, SWT.NONE);
	    item4.setText(new String[] { "Karmen", "Mcgrail", "Vienna, Austria" });

	    /** open the shell/window */
	    shell.open();

	    /** Loop to keep the application opened */
	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch()) {
	        display.sleep();
	        }
	    }
	    display.dispose();
	    }
  
}
