package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class PdfView  extends Composite{

	
	Image myImage;
	Label myLabel;
	private static float scale = 1;
	private static Font  font;
	Canvas canvas;
	Image image;
	
	public PdfView(Composite parent, int style) {
		super(parent, style);
	    setLayout(new FillLayout());

		// TODO Auto-generated constructor stub
	}
	
	public void draw(String imagePath) {
		
//		if(myLabel!=null) {
//			myLabel.dispose();
//		}
//		myImage = new Image( getDisplay(),imagePath );
//		myLabel = new Label( this, SWT.NONE );
//		//accentrare
//		myLabel.setLocation(0, 0);
//		myLabel.setImage( myImage );
//		myLabel.pack();
//		pack();
		image=new Image( getDisplay(),imagePath );

		if(canvas==null) {
			
			canvas = new Canvas(this, SWT.ALL);
			
		    canvas.addListener(SWT.Paint, new Listener()
		    {
		        private int initialFontSize = -1;

		        @Override
		        public void handleEvent(Event event)
		        {
		            Font tempFont = event.gc.getFont();
		            FontData data = tempFont.getFontData()[0];

		            if (initialFontSize == -1)
		                initialFontSize = tempFont.getFontData()[0].getHeight();
		            else
		            {
		                if(font != null && !font.isDisposed())
		                    font.dispose();

		                data.setHeight((int)(initialFontSize * scale));

		                font = new Font(getDisplay(), data);

		                event.gc.setFont(font);
		            }

		            Rectangle bounds = image.getBounds();
		            event.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 0, 0, (int) (bounds.width * scale), (int) (bounds.height * scale));
		            canvas.setSize(image.getBounds().width,image.getBounds(). height);
		        }
		    });
		    
		    canvas.addListener(SWT.MouseWheel, new Listener()
		    {
		        @Override
		        public void handleEvent(Event event)
		        {
		            if (event.count > 0)
		                scale += .2f;
		            else
		                scale -= .2f;

		            scale = Math.max(scale, 0);

		            canvas.redraw();
		        }
		    });
//		    canvas.addListener(SWT.Dispose, new Listener()
//		    {
//		        @Override
//		        public void handleEvent(Event event)
//		        {
//		            if (!image.isDisposed())
//		                image.dispose();
//		            if (!font.isDisposed())
//		                font.dispose();
//		        }
//		    });

		}
		canvas.redraw();
//		canvas = new Canvas(this, SWT.ALL);
		
		
		
	}

	
}
