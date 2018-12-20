package newHope;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

class DrawArcSWT {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.add(new JComponent() {
		    Point p1, p2; boolean first;
		    {
		        p1 = p2 = new Point();
		        setPreferredSize(new Dimension(400, 400));
		        addMouseListener(new MouseAdapter() {
		            public void mousePressed(java.awt.event.MouseEvent e) {
		                if (first) p1 = e.getPoint(); else p2 = e.getPoint();
		                first = !first;
		                repaint();
		            }
		        });
		    }

		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.fillRect(p1.x-1, p1.y-1, 3, 3); g.drawString("p1", p1.x, p1.y);   
		        g.fillRect(p2.x-1, p2.y-1, 3, 3); g.drawString("p2", p2.x, p2.y);

		        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
		        int diameter = (int) Math.round(p1.distance(p2));

		        Graphics2D g2d = (Graphics2D) g;
		        g2d.translate(p1.x, p1.y);
		        g2d.rotate(angle);
		        g2d.drawArc(0, -diameter/2, diameter, diameter, 0, 180);
		        g2d.fill(new Polygon(new int[] {0,10,-10}, new int[] {0,-10,-10}, 3));
		    }
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}