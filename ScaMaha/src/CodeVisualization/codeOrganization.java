package CodeVisualization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import GraphViz.GraphViz;
import de.vogella.jdt.infos.handlers.SampleHandler;

public class codeOrganization {

	static int countClassNumber;
	static String Doc = "code";
	static int countAttributes;
	static int countMethods;
	static String InterfaceName;
	static BufferedWriter bw;

	static int countAttributeAccess;
	static int countParameters;
	static int countLocalVariables;
	static int countMethodInvocations;

	
	   public static void run() {
		try {

	         File inputFile = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+".xml");
	         
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);

            bw = new BufferedWriter(new FileWriter(("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"CodeOrg.dot")));
			bw.write("");

			Element rootNode = document.getRootElement();
			List<?> PackagesList = rootNode.getChildren("Packages");

			bw.write("digraph G {" + "\n");
			bw.write("rankdir=RL;" + "\n"); // "TB" "LR" "BT" "RL"
			bw.write("graph [fontsize=10 fontname=\"Verdana\" compound=true];" + "\n");
			bw.write("node [shape=record fontsize=10 fontname=\"Verdana\"];" + "\n");

			bw.write("subgraph cluster_" + document.getRootElement().getAttributeValue("ProjectName").replace(" ", "_")
					+ "{" + "\n");
			bw.write("label =" + document.getRootElement().getAttributeValue("ProjectName").replace(" ", "_") + ";"
					+ "\n");

			bw.write("style=filled;" + "\n");
			bw.write("fillcolor=azure1;" + "\n");

			for (int i = 0; i < PackagesList.size(); i++) {

				Element PackagesNode = (Element) PackagesList.get(i);
				List<?> PackageList = PackagesNode.getChildren("Package");

				for (int j = 0; j < PackageList.size(); j++) {

					Element PackageNode = (Element) PackageList.get(j);
					List<?> ClassesList = PackageNode.getChildren("Classes");

					bw.write("subgraph cluster_" + PackageNode.getAttributeValue("PackageName").replace(".", "_") + "{"
							+ "\n");
					bw.write("label =" + PackageNode.getAttributeValue("PackageName").replace(".", "_") + ";" + "\n");

					bw.write("style=filled;" + "\n");
					bw.write("fillcolor=yellow;" + "\n");
//				    yellow

					String superClass = "";

					String className = "";

					String isInterface = "";

					for (int k = 0; k < ClassesList.size(); k++) {

						Element ClassesNode = (Element) ClassesList.get(k);
						List<?> ClassList = ClassesNode.getChildren("Class");

						countClassNumber = 0;

						new HashSet<String>();

						for (int y = 0; y < ClassList.size(); y++) {

							countClassNumber++;

							Element ClassNode = (Element) ClassList.get(y);

							bw.write("subgraph cluster_" + ClassNode.getAttributeValue("ClassName") + "{" + "\n");
							bw.write("label =" + ClassNode.getAttributeValue("ClassName") + ";" + "\n");
							bw.write("style=filled;" + "\n");
							bw.write("fillcolor=bisque1;" + "\n");

							superClass = ClassNode.getAttributeValue("Superclass");
							className = ClassNode.getAttributeValue("ClassName");
							isInterface = ClassNode.getAttributeValue("isInterface");

							List<?> SuperInterfacesList = ClassNode.getChildren("SuperInterfaces");
							List<?> AttributesList = ClassNode.getChildren("Attributes");
							List<?> MethodsList = ClassNode.getChildren("Methods");

							for (int r = 0; r < SuperInterfacesList.size(); r++) {}

							for (int a = 0; a < AttributesList.size(); a++) {}

							for (int m = 0; m < MethodsList.size(); m++) {

								Element MethodsNode = (Element) MethodsList.get(m);
								List<?> MethodList = MethodsNode.getChildren("Method");

								countMethods = 0;

								for (int mm = 0; mm < MethodList.size(); mm++) {

									countMethods++;

									Element MethodNode = (Element) MethodList.get(mm);

									bw.write((ClassNode.getAttributeValue("ClassName")+MethodNode.getAttributeValue("MethodName")).hashCode()
											+ "[shape=underline,style=filled,fillcolor=cadetblue1,label=\""
											+ MethodNode.getAttributeValue("MethodName") + "\\n\"];" + "\n");

									
									List<?> ParametersList = MethodNode.getChildren("Parameters");
									List<?> LocalVariablesList = MethodNode.getChildren("LocalVariables");

									List<?> AttributeAccessesList = MethodNode.getChildren("AttributeAccesses");
									List<?> MethodInvocationsList = MethodNode.getChildren("MethodInvocations");

									for (int Ps = 0; Ps < ParametersList.size(); Ps++) {}

									for (int LVs = 0; LVs < LocalVariablesList.size(); LVs++) {}

									for (int AttAcs = 0; AttAcs < AttributeAccessesList.size(); AttAcs++) {}

									for (int MIs = 0; MIs < MethodInvocationsList.size(); MIs++) {}
								}

//								 if (countMethods ==0 || countAttributes ==0 || countMethods !=0 || countAttributes !=0) {
								if (countMethods == 0) {
									bw.write((PackageNode.getAttributeValue("PackageName")
											+ ClassNode.getAttributeValue("ClassName")).hashCode()
											+ "[shape=point, color=white];" + "\n");
								}
								
								bw.write("}" + "\n");

							}
						}
						
						
						
						if (countClassNumber == 0) {
							bw.write(PackageNode.getAttributeValue("PackageName").hashCode()
									+ "[shape=point, color=yellow];" + "\n");
						}

						

					}
					bw.write("}" + "\n");
				}
			}

			
			bw.write("}" + "\n");
			bw.write("}" + "\n");
			bw.close();

		}

		catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		String input = "E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"CodeOrg.dot";

		
		GraphViz gv = new GraphViz();
		gv.readSource(input);
		String type = "svg";
		File out = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+ "CodeOrg."+ type); 
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
		System.err.println("Done !!!!!");

	}
	// end of main method

}
