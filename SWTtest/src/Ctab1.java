/*
 * CoolBar example snippet: create a coolbar (relayout when resized)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 *
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import javafx.scene.control.ToolBar;
import jdk.nashorn.tools.Shell;

public class Ctab1 {

	public static void main( String[] args ) { 
		final Display display = Display.getDefault(); 
		final Shell shell = new Shell(); 
		shell.setLayout( new FillLayout() ); 

		CTabFolder tabFolder = new CTabFolder( shell, SWT.BORDER ); 
		tabFolder.setSingle( true ); 
		tabFolder.setSimple( false ); 
		tabFolder.setSelectionBackground( 
		display.getSystemColor(SWT.COLOR_WHITE ) ); 

		CTabItem tabItem = new CTabItem( tabFolder, SWT.NONE ); 
		tabItem.setText( "tabitem" ); 

		ToolBar t = new ToolBar( tabFolder, SWT.FLAT ); 
		ToolItem i = new ToolItem( t, SWT.PUSH ); 
		i.setText( "toolitems" ); 
		tabFolder.setTopRight( t, SWT.RIGHT ); 

		// !!!! 
		// Need to set height of tab to show toolbar 
		tabFolder.setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, 
		SWT.DEFAULT).y, tabFolder.getTabHeight())); 

		shell.open(); 
		while (!shell.isDisposed()) { 
		if (!display.readAndDispatch()) display.sleep(); 
		} 
		} 
}