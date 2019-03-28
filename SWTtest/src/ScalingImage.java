import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ScalingImage {
	private static float scale = 1;
	private static Font  font;

	public static void main(String[] args)
	{
	    final Display display = new Display();
	    final Shell shell = new Shell(display);
	    shell.setText("StackOverflow");
	    shell.setLayout(new FillLayout());

	    final Image image = new Image(display, "images/icon.png");

	    final Canvas canvas = new Canvas(shell, SWT.NONE);
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

	                font = new Font(display, data);

	                event.gc.setFont(font);
	            }

	            Rectangle bounds = image.getBounds();
	            event.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 0, 0, (int) (bounds.width * scale), (int) (bounds.height * scale));
	            event.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
	            event.gc.drawText("Some text here", (int)(10 * scale), (int)(10 * scale), true);
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
	    canvas.addListener(SWT.Dispose, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event)
	        {
	            if (!image.isDisposed())
	                image.dispose();
	            if (!font.isDisposed())
	                font.dispose();
	        }
	    });

	    shell.pack();
	    shell.setSize(400, 200);
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
