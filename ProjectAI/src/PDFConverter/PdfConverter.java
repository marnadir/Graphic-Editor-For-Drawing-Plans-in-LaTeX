package PDFConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class PdfConverter {

	String filePath;
	
	public PdfConverter(String string) {
		this.filePath=string;    

	}
	
	public void execute() {
		PDDocument document = null;
		File imageFile = null;

		try {
			document = PDDocument.load(new File(filePath));
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
				bim = pdfRenderer.renderImageWithDPI(page, 200, ImageType.RGB);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	    // suffix in filename will be used as the file format
    	    try {
    	    	
    	    	filePath=filePath.substring(0, filePath.length()-3);
    	    	filePath=filePath+"png";
//    	    	 imageFile = new File(filePath);
//    	    	 if(imageFile.exists()) {
//    	    		 imageFile.delete();
//    	    	 }
				ImageIOUtil.writeImage(bim, filePath, 300);
		       

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
