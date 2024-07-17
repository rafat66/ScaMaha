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


public class Inheritance {
	
   static int count;
   static String Doc = "code";
   static int countAttributes;
   static int countMethods;
   static String InterfaceName;
   static BufferedWriter bw;

   static int countAttributeAccess;
   static int countParameters;
   static int countLocalVariables;
   static int countMethodInvocations;
   

   public static void Read() {
	
      try {
    	  
         File inputFile = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+".xml");

         SAXBuilder saxBuilder = new SAXBuilder();
         Document document = saxBuilder.build(inputFile);
         
         bw = new BufferedWriter(new FileWriter(("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"Inheritance.dot")));

         bw.write("");
		 
         Element rootNode = document.getRootElement();
		 List<?> PackagesList = rootNode.getChildren("Packages");
		 
		 bw.write("digraph G {" +"\n");
		 
		 bw.write("graph [fontsize=10 fontname=\"Verdana\" compound=true];"+"\n");
		 bw.write("node [shape=record fontsize=10 fontname=\"Verdana\"];"+"\n");
		 
	     bw.write("subgraph cluster_" + document.getRootElement().getAttributeValue("ProjectName").replace(" ", "_") + "{" + "\n");
	     bw.write("label ="+ document.getRootElement().getAttributeValue("ProjectName").replace(" ", "_")+";" + "\n");
	     
	     bw.write("style=filled;"+ "\n");
	     bw.write("fillcolor=azure2;"+ "\n");
	     

		 for (int i = 0; i < PackagesList.size(); i++) {
			 
				Element PackagesNode = (Element) PackagesList.get(i);
				List<?> PackageList = PackagesNode.getChildren("Package");
				
				
				
				for (int j = 0; j < PackageList.size(); j++) {
					
					Element PackageNode = (Element) PackageList.get(j);
					List<?> ClassesList = PackageNode.getChildren("Classes");
										
					bw.write("subgraph cluster_" + PackageNode.getAttributeValue("PackageName").replace(".", "_")+ "{" + "\n");
				    bw.write("label ="+ PackageNode.getAttributeValue("PackageName").replace(".", "_")+";" + "\n");
					
				    bw.write("style=filled;"+ "\n");
				    bw.write("fillcolor=bisque1;"+ "\n");
//				    yellow
				    
				    String superClass = "";
				    
					String className = "";
					
					String isInterface="";
					
					for (int k = 0; k < ClassesList.size(); k++) {
						
						Element ClassesNode = (Element) ClassesList.get(k);
						List<?> ClassList = ClassesNode.getChildren("Class");
						
						count=0;
						
						new HashSet<String>();
						
						
						for (int l = 0; l < ClassList.size(); l++) {
							

							count++;

							Element ClassNode = (Element) ClassList.get(l);

//							System.out.println(ClassNode.getAttributeValue("ClassName"));
						    
						    bw.write(ClassNode.getAttributeValue("ClassName").hashCode()  
							        + "[shape=note,style=filled,fillcolor=white,label=\"" + ClassNode.getAttributeValue("ClassName")+"\\n\"];"+ "\n");

//							        + "[shape=note,style=filled,fillcolor=white,label=\"" + "{"+ ClassNode.getAttributeValue("ClassName")+"\\n}\"];"+ "\n");

							
						    bw.write("style=filled;"+ "\n");
						    bw.write("fillcolor=bisque1;"+ "\n");
//						    yellow
						    
						    superClass=ClassNode.getAttributeValue("Superclass");
						    className=ClassNode.getAttributeValue("ClassName");
						    isInterface=ClassNode.getAttributeValue("isInterface");
						   
//						    System.out.println(ClassNode.getAttributeValue("isInterface"));
//							System.out.println(ClassNode.getAttributeValue("superClass"));

//							lightblue
//						    if (!isInterface.contains("true") && !superClass.contains("Object")){
//						    bw.write(ClassNode.getAttributeValue("Superclass").hashCode()  
//							        + "[shape=record,style=filled,fillcolor=white,label=\"" + "{"+ ClassNode.getAttributeValue("Superclass")+"\\n}\"];"+ "\n");
//						    }
							
						    
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
							
							countAttributes =0;
							
							for (int aa = 0; aa < AttributeList.size(); aa++) {
								
							Element AttributeNode = (Element) AttributeList.get(aa);
							countAttributes++;
							
							}
							
							}
							                         
	
							for (int m = 0; m < MethodsList.size(); m++) {
								
							
								Element MethodsNode = (Element) MethodsList.get(m);
								List<?> MethodList = MethodsNode.getChildren("Method");
								
								countMethods =0;
																
								for (int mm = 0; mm < MethodList.size(); mm++) {
									
								countMethods++;	
								
								Element MethodNode = (Element) MethodList.get(mm);
								  
								List<?> ParametersList = MethodNode.getChildren("Parameters");
								List<?> LocalVariablesList = MethodNode.getChildren("LocalVariables"); 
								 
								List<?> AttributeAccessesList = MethodNode.getChildren("AttributeAccesses");
								List<?> MethodInvocationsList = MethodNode.getChildren("MethodInvocations");

								
                                for (int Ps = 0; Ps < ParametersList.size(); Ps++) {
									
									Element ParametersNode = (Element) ParametersList.get(Ps);
									List<?> ParameterList = ParametersNode.getChildren("Parameter");
									
									countParameters =0;
									
									for (int p = 0; p < ParameterList.size(); p++) {
										
									countParameters++;	
									Element ParameterNode = (Element) ParameterList.get(p);
									
								
									}}
								
								
                                    for (int LVs = 0; LVs < LocalVariablesList.size(); LVs++) {
									
									Element LocalVariablesNode = (Element) LocalVariablesList.get(LVs);
									List<?> LocalVariableList = LocalVariablesNode.getChildren("LocalVariable");
									
									countLocalVariables =0;
									
									for (int lv = 0; lv < LocalVariableList.size(); lv++) {
										
									countLocalVariables++;	
									Element countLocalVariableNode = (Element) LocalVariableList.get(lv);
									
								
									}}
								
								
								
								
								for (int AttAcs = 0; AttAcs < AttributeAccessesList.size(); AttAcs++) {
									
									Element AttributeAccessesNode = (Element) AttributeAccessesList.get(AttAcs);
									List<?> AttributeAccessList = AttributeAccessesNode.getChildren("AttributeAccess");
									
									countAttributeAccess =0;
									
									for (int AttA = 0; AttA < AttributeAccessList.size(); AttA++) {
										
									countAttributeAccess++;	
									Element AttributeAccessNode = (Element) AttributeAccessList.get(AttA);
							
									}}
								
								
                                    for (int MIs = 0; MIs < MethodInvocationsList.size(); MIs++) {
									
									Element MethodInvocationsNode = (Element) MethodInvocationsList.get(MIs);
									List<?> MethodInvocationList = MethodInvocationsNode.getChildren("MethodInvocation");
									
									countMethodInvocations =0;
									
									for (int MI = 0; MI < MethodInvocationList.size(); MI++) {
										
									countMethodInvocations++;
									
									Element MethodInvocationNode = (Element) MethodInvocationList.get(MI);
									

								
									}
									}
                                    
                                    if (countAttributeAccess ==0 && countMethodInvocations ==0) {
//   									 bw.write((PackageNode.getAttributeValue("PackageName")+ClassNode.getAttributeValue("ClassName")).hashCode()+"[shape=point, color=lightblue];"+ "\n");
   									 }
                                    

   								}
                                    
								 if (countMethods ==0 || countAttributes ==0 || countMethods !=0 || countAttributes !=0) {
//									 bw.write((PackageNode.getAttributeValue("PackageName")+ClassNode.getAttributeValue("ClassName")).hashCode()+"[shape=point, color=white];"+ "\n");
									 }
								}
						}
						
					    if (count ==0) {bw.write(PackageNode.getAttributeValue("PackageName").hashCode()+"[shape=point, color=bisque1];"+ "\n");}
					
					    bw.write("}"+ "\n");

					}

				}
				
				
				
		 }
		 
		    FindDependencies();
		    bw.write("}"+ "\n");
			bw.write("}"+ "\n");
			bw.write("}"+ "\n");
		    bw.close();
		 

		 
      }
      

      catch(JDOMException e){
         e.printStackTrace();
      }catch(IOException ioe){
         ioe.printStackTrace();
      }
      
		String input = "E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+"Inheritance.dot";

		GraphViz gv = new GraphViz();
		gv.readSource(input);
		String type = "svg";
		File out = new File("E:\\Workspace\\ScaMaha\\ScaMaha\\"+SampleHandler.ProjectName+ "Inheritance."+ type); 

		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
		System.err.println("Done !!!!!");	    
      
      
      
   }
   // end of main method
   
   
   
   
   
   public static void FindDependencies(){ 

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

					    String superClass = "";
						String className = "";
						String isInterface="";
						
						for (int k = 0; k < ClassesList.size(); k++) {
							Element ClassesNode = (Element) ClassesList.get(k);
							List<?> ClassList = ClassesNode.getChildren("Class");
							
							for (int l = 0; l < ClassList.size(); l++) {
								Element ClassNode = (Element) ClassList.get(l);
//								System.out.println(ClassNode.getAttributeValue("ClassName"));	
								
							    superClass=ClassNode.getAttributeValue("Superclass");
							    className=ClassNode.getAttributeValue("ClassName");
							    isInterface=ClassNode.getAttributeValue("isInterface");
							    
							if(!isInterface.contains("true") && !superClass.contains("Object")){
								
								bw.write(ClassNode.getAttributeValue("Superclass").hashCode()  
							    + "[shape=note,style=filled,fillcolor=white,label=\"" + ClassNode.getAttributeValue("Superclass")+"\\n\"];"+ "\n");
								bw.write(className.hashCode() + "->" + superClass.hashCode()+ "[color=\"red;0.50:blue\"]" +";\n");
								
							}
							
							}
							
						}
					}
					
			 }
	   }
	   catch(JDOMException e){
	         e.printStackTrace();
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
   }
   
 
}


