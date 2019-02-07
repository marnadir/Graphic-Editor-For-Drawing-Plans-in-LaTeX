import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessCommand {
	public static void main(String[] args) {


		try {

			Process process = Runtime.getRuntime().exec(new String[] { "bash", "-c",
					"cd /home/nadir/Desktop/Test/ && pdflatex test\\ .tex  -synctex=1 -interaction=nonstopmode" });

			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.println(result);

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

	}
}
