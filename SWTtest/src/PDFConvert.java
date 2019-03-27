import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;



public class PDFConvert 
{
    public static void main (String [] args)
    {
    	
    	String pdfFilename="images/PlanLatex.pdf";
    	PDDocument document = null;
		try {
			document = PDDocument.load(new File(pdfFilename));
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	PDFRenderer pdfRenderer = new PDFRenderer(document);
    	for (int page = 0; page < document.getNumberOfPages(); ++page)
    	{ 
    	    BufferedImage bim = null;
			try {
				bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.RGB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	    // suffix in filename will be used as the file format
    	    try {
				ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) + ".png", 300);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	try {
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}