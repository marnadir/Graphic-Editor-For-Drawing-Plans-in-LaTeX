package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class Action {



	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	static Canvas canvas;
	static Composite contentCanvas;
	int max;
	boolean shownCond=true;
	
	public Action(String name,ArrayList<String> prec, ArrayList<String> eff) {
		this.name=name;
		this.prec=prec;
		this.effect=eff;
		
	}
	
	public void draw(Composite comp) {
		if (canvas != null) {
			if(!canvas.isDisposed()) {
				canvas.dispose();
			}
			canvas = new Canvas(comp, SWT.ALL);
		} else {
			contentCanvas = comp;
			canvas = new Canvas(comp, SWT.ALL);
			//canvas.layout();

		}

		
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				int num= comp.getChildren().length;
			
				
				int numPrec = prec.size();
				int numEff=effect.size();

				
				
				int startX = canvas.getLocation().x+100 ;
				int startY = canvas.getLocation().y +150;
				
			
				int heightRect=40;
				max=numPrec;
				if(numEff>numPrec) {
					max=numEff;
				}
				
				if(max>1) {
					heightRect=30+max*10;
				}
				
				int sizeString=name.length()*12;
				
				
				Rectangle rect=new Rectangle(startX, startY, sizeString,heightRect);
				
				
				e.gc.drawRectangle(rect);
				
				int l=rect.x+rect.width/6;
				e.gc.drawString(name, l, rect.y+rect.height/3);
				

				int posY = rect.y + 10;

				System.out.println(shownCond);

				for (int i = 0; i < numPrec; i++) {

					e.gc.drawLine(rect.x, posY, rect.x - 35, posY);
					if (shownCond) {
						String string = prec.get(i);
						e.gc.drawString(string, rect.x - (5 + string.length() * 10), posY - 20, false);
					}
					posY = posY + 30;
				}

				posY = rect.y + 10;
				for (int i = 0; i < numEff; i++) {

					int x = rect.x + rect.width;
					e.gc.drawLine(x, posY, x + 30, posY);
					if (shownCond) {

						String string = effect.get(i);
						e.gc.drawString(string, x + 10, posY - 20, false);
					}
					posY = posY + 30;

				}
				
			}
		});
		
		
		canvas.setSize(comp.getSize().x, comp.getSize().y);
		
		canvas.addMenuDetectListener(new MenuDetectListener() {
			
			@Override
			public void menuDetected(MenuDetectEvent e) {
				Menu m=new Menu(canvas);
				canvas.setMenu(m);
				
				MenuItem c=new MenuItem(m,SWT.ALL);
				c.setText("Clear");
				c.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						clearDisplay();
					}
				});
				
				MenuItem show=new MenuItem(m,SWT.ALL);
				show.setText("Show/Hide Cond...");
				show.addListener(SWT.Selection,new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						shownCond=!shownCond;
						canvas.redraw();
						
					}
				});
				
				}
		});
     
		
		
	}
	
	
	
	public String getName() {
		
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void clearDisplay() {

		if (canvas != null) {

			canvas.dispose();

		}
	}

	public ArrayList<String> getPrec() {
		return prec;
	}

	public void setPrec(ArrayList<String> prec) {
		this.prec = prec;
	}

	public ArrayList<String> getEffect() {
		return effect;
	}

	public void setEffect(ArrayList<String> effect) {
		this.effect = effect;
	}
}
