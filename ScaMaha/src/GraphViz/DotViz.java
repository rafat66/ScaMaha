package GraphViz;

import java.io.File;



public class DotViz {

//	public static void main(String[] args) {
//	FMViz p = new FMViz();
//	p.start2();
//	}

	/**
	 * Read the DOT source from a file, convert to image and store the image in
	 * the file system.
	 */

	public static void start() {

		String input = "./ScaMaha/code.dot";

		GraphViz gv = new GraphViz();
		gv.readSource(input);
//		System.out.println(gv.getDotSource());

//		String type = "gif";
//		String type = "dot";
//		String type = "fig"; // open with xfig
//		String type = "pdf";
//		String type = "ps";
		String type = "svg"; // open with inkscape
//		String type = "png";
//		String type = "plain";

		File out = new File("./ScaMaha/CodeGraphViz."+ type); 

		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
		System.err.println("Done !!!!!!");
		
		}
}
