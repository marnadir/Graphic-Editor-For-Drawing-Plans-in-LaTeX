/**
 * 
 */
package command;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


import logic.ICommand;

/**
 * @author nadir
 *
 */

public class OpenCommand implements ICommand  {

	private final String name = "Open...";
	private JFrame JFrame;


	public OpenCommand() {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logic.ICommand#canExecute(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logic.ICommand#execute(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void execute(Object var1, Object var2) {
		
			JPanel p = new JPanel();
			this.JFrame=(JFrame)var1;
			Container cp = this.JFrame.getContentPane();
			cp.add(p, BorderLayout.SOUTH);
			p = new JPanel();
			p.setLayout(new GridLayout(2, 1));
			cp.add(p, BorderLayout.NORTH);
			
			JFileChooser c = new JFileChooser();
			// Demonstrate "OpenCommand" dialog:
			int rVal = c.showSaveDialog(JFrame);
			if (rVal == JFileChooser.APPROVE_OPTION) {
			}
			if (rVal == JFileChooser.CANCEL_OPTION) {

			}

		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logic.ICommand#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
