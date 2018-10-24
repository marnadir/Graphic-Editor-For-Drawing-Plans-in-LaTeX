/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import command.OpenCommand;
import command.SaveCommand;
import logic.CommandAction;
import logic.Commands;
import logic.ICommand;


/**
 * @author nadir
 *
 */
public class MenuBar extends JMenuBar {

	
	/**
	 * 
	 */
	private static final String serialVersionUID = "menuBar";
	JMenuBar menuBar;
	JMenu file,edit,help;
	JMenuItem New,OpenFile,Save,SaveAs,Exit;
	JMenuItem undo,redo;
	JMenuItem documentation,about;
		
	
	public MenuBar() {
		menuBar= new JMenuBar();
		menuBar.setVisible(true);
		
		file=new JMenu("File");
		menuBar.add(file);
		
		New= new JMenuItem("New",new ImageIcon("path for image"));
		
		file.add(New);
		
		//OpenFile= new JMenuItem("Open..",new ImageIcon("path for image"));
		OpenFile=new JMenuItem(createCommandButtonAction(ICommand.OPEN, null, OpenFile));
		file.add(OpenFile);
		
		Save= new JMenuItem(createCommandButtonAction(ICommand.SAVE, null, Save));
		file.add(Save);
		
		SaveAs= new JMenuItem(createCommandButtonAction(ICommand.SAVEAS, null, SaveAs));
		file.add(SaveAs);
		
		Exit= new JMenuItem(createCommandButtonAction(ICommand.EXIT, null,menuBar));
		file.add(Exit);
		
		
		
		
		
		edit=new JMenu("Edit");
		menuBar.add(edit);
		
		undo= new JMenuItem("Undo",new ImageIcon("path for image"));
		undo.setMnemonic(KeyEvent.VK_UNDO);
		edit.add(undo);
		
		redo= new JMenuItem("Redo",new ImageIcon("path for image"));
		redo.setMnemonic(KeyEvent.VK_RIGHT);
		edit.add(redo);
		
		
		
		help=new JMenu("Help");
		menuBar.add(help);
		
		documentation= new JMenuItem("Documentation",new ImageIcon("path for image"));
		help.add(documentation);
		
		about= new JMenuItem("AboutTool",new ImageIcon("path for image"));
		help.add(about);
		
	}
	
	  private Action createCommandButtonAction(ICommand command, Object parameter, JComponent target) {
		    Action action = new CommandAction(command, parameter, target);
		    action.putValue(Action.NAME, command.getName());
		    return action;
	 }
	
	
	  
	  
	public JMenuBar getMenuBar() {
		return menuBar; 
	}
	

	
}
