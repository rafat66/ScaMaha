package CodeVisualization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import GraphViz.GraphViz;
import de.vogella.jdt.infos.handlers.SampleHandler;

public class Invocation {

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

	     	static List<String> Methodnames = new ArrayList<>(); 
	     	
	     	public static void runInvocation() {
	     	
	     			try {
	     				   Methodnames();
	     				 
	     				   File inputFile = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+".xml");

	     			        SAXBuilder saxBuilder = new SAXBuilder();
	     			        Document document = saxBuilder.build(inputFile);
	     			        
	     		            bw = new BufferedWriter(new FileWriter(("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"Invocation.dot")));

	     					bw.write("");

	     					Element rootNode = document.getRootElement();
	     					List<?> PackagesList = rootNode.getChildren("Packages");

	     					bw.write("digraph G {" + "\n");
	     				    bw.write("rankdir=RL;" +"\n"); // "TB" "LR" "BT" "RL"
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


	     							for (int k = 0; k < ClassesList.size(); k++) {

	     								Element ClassesNode = (Element) ClassesList.get(k);
	     								List<?> ClassList = ClassesNode.getChildren("Class");

	     								countClassNumber = 0;

	     								new HashSet<String>();

	     								for (int y = 0; y < ClassList.size(); y++) {

	     									countClassNumber++;

	     									Element ClassNode = (Element) ClassList.get(y);
	     									 
	     									bw.write("subgraph cluster_" +ClassNode.getAttributeValue("ClassName")+"{" + "\n");
	     									bw.write("label =" + ClassNode.getAttributeValue("ClassName") + ";" + "\n");
	     									bw.write("style=filled;" + "\n");
	     									bw.write("fillcolor=bisque1;" + "\n");

	     									List<?> SuperInterfacesList = ClassNode.getChildren("SuperInterfaces");
	     									List<?> AttributesList = ClassNode.getChildren("Attributes");
	     									List<?> MethodsList = ClassNode.getChildren("Methods");

	     									for (int r = 0; r < SuperInterfacesList.size(); r++) {

	     										Element SuperInterfacesNode = (Element) SuperInterfacesList.get(r);
	     										List<?> SuperInterfaceList = SuperInterfacesNode.getChildren("Interface");

	     										for (int rr = 0; rr < SuperInterfaceList.size(); rr++) {

	     											Element InterfaceNode = (Element) SuperInterfaceList.get(rr);

	     										}

	     									}

	     									for (int a = 0; a < AttributesList.size(); a++) {

	     										Element AttributesNode = (Element) AttributesList.get(a);
	     										List<?> AttributeList = AttributesNode.getChildren("Attribute");

	     										countAttributes = 0;

	     										for (int aa = 0; aa < AttributeList.size(); aa++) {

	     											Element AttributeNode = (Element) AttributeList.get(aa);
	     											countAttributes++;

	     										}

	     									}

	     									for (int m = 0; m < MethodsList.size(); m++) {

	     										Element MethodsNode = (Element) MethodsList.get(m);
	     										List<?> MethodList = MethodsNode.getChildren("Method");

	     										countMethods = 0;

	     										
	     										for (int mm = 0; mm < MethodList.size(); mm++) {

	     											countMethods++;

	     											Element MethodNode = (Element) MethodList.get(mm);
	     											
	     											bw.write(MethodNode.getAttributeValue("MethodName").hashCode()+
	     											         "[shape=underline,style=filled,fillcolor=olivedrab1,label=\"" + 
	     											         MethodNode.getAttributeValue("MethodName")+"\\n\"];"+ "\n");


	     											List<?> MethodInvocationsList = MethodNode.getChildren("MethodInvocations");

	     											for (int MIs = 0; MIs < MethodInvocationsList.size(); MIs++) {

	     												Element MethodInvocationsNode = (Element) MethodInvocationsList.get(MIs);
	     												List<?> MethodInvocationList = MethodInvocationsNode
	     														.getChildren("MethodInvocation");

	     												countMethodInvocations = 0;
	     			                                   
	     												String InName="";
	     												for (int MI = 0; MI < MethodInvocationList.size(); MI++) {

	     													countMethodInvocations++;
	     													Element MethodInvocationNode = (Element) MethodInvocationList.get(MI);
	     													
//	     													System.out.println((ClassNode.getAttributeValue("ClassName")+MethodNode.getAttributeValue("MethodName")).hashCode());
//	     													System.out.println((MethodNode.getAttributeValue("MethodName")+MethodInvocationNode.getAttributeValue("MethodInvocationName")).hashCode());
	     													
//	     													System.out.println(MethodNode.getAttributeValue("MethodName").hashCode());
//	     													System.out.println(MethodInvocationNode.getAttributeValue("MethodInvocationName").hashCode());

	     													if (Methodnames.contains(MethodInvocationNode.getAttributeValue("MethodInvocationName"))) {
	     													bw.write(MethodNode.getAttributeValue("MethodName").hashCode()+
	     													"->"+
	     													MethodInvocationNode.getAttributeValue("MethodInvocationName").hashCode()+
	     													"[color=\"red;0.50:blue\"];\n");
	     													}
	     										
	     												}
	     											}
	     										}

	     										if (countMethods == 0) {
	     											bw.write((PackageNode.getAttributeValue("PackageName")
	     													+ ClassNode.getAttributeValue("ClassName")).hashCode()
	     													+ "[shape=point, color=bisque1];" + "\n");
	     										}
	     									}
	     									bw.write("}" + "\n"); 

	     								}

	     								if (countClassNumber == 0) {
	     									bw.write(PackageNode.getAttributeValue("PackageName").hashCode()
	     											+ "[shape=point, color=yellow];" + "\n");
	     								}

	     								bw.write("}" + "\n");

	     							}

	     						}

	     					}
	     			       
	     					bw.write("}" + "\n");
	     					bw.write("}" + "\n");
	     					bw.write("}" + "\n");
	     					bw.close();

	     				}

	     				catch (JDOMException e) {
	     					e.printStackTrace();
	     				} catch (IOException ioe) {
	     					ioe.printStackTrace();
	     				}

	     			
	    			String input = "E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"Invocation.dot";

	    			GraphViz gv = new GraphViz();
	    			gv.readSource(input);
	    			String type = "svg";
	    			File out = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+ "Invocation."+ type); 

	    			gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
	    			System.err.println("Done !!!!!");	
	     		
	     }	
	     			
	     	
	     public static void Methodnames() {

	     	try {
	     		
	     		File inputFile = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+".xml");
	     		SAXBuilder saxBuilder = new SAXBuilder();

	     		Document document = saxBuilder.build(inputFile);

	     		Element rootNode = document.getRootElement();
	     		List<?> PackagesList = rootNode.getChildren("Packages");

	     		for (int i = 0; i < PackagesList.size(); i++) {

	     			Element PackagesNode = (Element) PackagesList.get(i);
	     			List<?> PackageList = PackagesNode.getChildren("Package");

	     			for (int j = 0; j < PackageList.size(); j++) {

	     				Element PackageNode = (Element) PackageList.get(j);
	     				List<?> ClassesList = PackageNode.getChildren("Classes");

	     				for (int k = 0; k < ClassesList.size(); k++) {

	     					Element ClassesNode = (Element) ClassesList.get(k);
	     					List<?> ClassList = ClassesNode.getChildren("Class");

	     					for (int y = 0; y < ClassList.size(); y++) {

	     						Element ClassNode = (Element) ClassList.get(y);

	     						List<?> SuperInterfacesList = ClassNode.getChildren("SuperInterfaces");
	     						List<?> AttributesList = ClassNode.getChildren("Attributes");
	     						List<?> MethodsList = ClassNode.getChildren("Methods");


	     						for (int m = 0; m < MethodsList.size(); m++) {

	     							Element MethodsNode = (Element) MethodsList.get(m);
	     							List<?> MethodList = MethodsNode.getChildren("Method");

	     							countMethods = 0;

	     							for (int mm = 0; mm < MethodList.size(); mm++) {
	     								
	     								countMethods++;
	     								
	     								Element MethodNode = (Element) MethodList.get(mm);
	     								
	     								Methodnames.add(MethodNode.getAttributeValue("MethodName"));


	     						}
	     					}
	     				}
	     			}
	     		}
	     			
	     		}
	     		
	     	}
	     	catch (JDOMException e) {
	     		e.printStackTrace();
	     	} catch (IOException ioe) {
	     		ioe.printStackTrace();
	     	}	
	}
	// end of main method
}
