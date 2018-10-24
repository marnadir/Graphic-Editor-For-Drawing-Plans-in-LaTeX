/**
 * 
 */
package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author nadir
 *
 */
public class SplitTwoPane extends JSplitPane {
	
	/**
	 * 
	 */
	public SplitTwoPane(IPannel pannel1,IPannel pannel2) {
		
		this.setSize(600, 700);
		this.setDividerSize(0);
	    this.setDividerLocation(150);
	    this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
	    this.setLeftComponent(pannel1.getPannel());
	    this.setRightComponent(pannel2.getPannel());
        this.setBorder(BorderFactory.createLineBorder(Color.black));

	    
	}
	
	public JSplitPane getSplit() {
		return this;
	}
}
