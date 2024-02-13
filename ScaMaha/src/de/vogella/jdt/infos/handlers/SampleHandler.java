// by Ra'fat Ahmad AL-msie'deen  2012

package de.vogella.jdt.infos.handlers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import CodeVisualization.Inheritance;
import CodeVisualization.Invocation;
import CodeVisualization.codeOrganization;
import visitors.AssignmentVisitor;
import visitors.AttributeAccessVisitor;
import visitors.CommentVisitor;

import visitors.LineCounterVisitor;
import visitors.MethodInvocationVisitor;
import visitors.TypeDeclarationVisitor;
import visitors.VariableDeclarationFragmentVisitor;



public class SampleHandler extends AbstractHandler {
	


public static String ProjectName;

@SuppressWarnings({ "unchecked", "unused" })

public Object execute(ExecutionEvent event) throws ExecutionException {
	    
	    long time1 = System.currentTimeMillis();
	    
	    deleteAllDocuments();
	    
		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		DirectoryDialog fileDialog = new DirectoryDialog(HandlerUtil.getActiveShell(event));
		String directory = fileDialog.open();
		IProject[] projects = root.getProjects();
		
		
		for (IProject project : projects) {
			
			 int packageCounter=0;
			 int classCounter=0;
			 int attributeCounter=0;
			 int methodCounter=0;
			 int commentCounter=0;
			 int lineCounter=0;
			 int localVarCounter=0;
			 
			 int inheritanceCounter=0;
			 int MethodInvocationCounter=0;
			 int AttributeAccessCounter=0;
			 
			 String packageStr="";
			 String classStr="";
			 String attributeStr="";
			 String methodStr="";
			 String commentStr="";
			 String lineStr="";
			 String localVarStr="";
			 
			 String inheritanceStr="";
			 String MethodInvocationStr="";
			 String AttributeAccessStr="";
			 
			try {
				if (project.isNatureEnabled("org.eclipse.jdt.core.javanature") && project.isOpen()) {
					IJavaProject javaProject = JavaCore.create(project);
					Element xmlroot = new Element("Project");
					Element xmlcodeMetrics = new Element("Project");
					
					Document doc = new Document(xmlroot);
					Document codeMetricsDoc = new Document(xmlcodeMetrics);
					
					xmlroot.setAttribute("ProjectName", javaProject.getElementName());
					xmlcodeMetrics.setAttribute("ProjectName", javaProject.getElementName());
					ProjectName=project.getName();
					
					Element xmlPackages = new Element("Packages");
					Element xmlMetrics = new Element("Metrics");
					
					xmlroot.addContent(xmlPackages);
					xmlcodeMetrics.addContent(xmlMetrics);
					
					Element xmlMetricLOC = new Element("LinesOfCode");
					Element xmlMetricNOP = new Element("NumberOfPackages");
					Element xmlMetricNOC = new Element("NumberOfClasses");
					Element xmlMetricNOA = new Element("NumberOfAttributes");
					Element xmlMetricNOM = new Element("NumberOfMethods");
					Element xmlMetricNOCo = new Element("NumberOfComments");
					Element xmlMetricNOLv = new Element("NumberOfLocalVariables");
					
					Element xmlMetricNOIn = new Element("NumberOfInheritances");
					Element xmlMetricNOI = new Element("NumberOfInvocations");
					Element xmlMetricNOAc = new Element("NumberOfAccesses");
					
					xmlMetrics.addContent(xmlMetricLOC);
					xmlMetrics.addContent(xmlMetricNOP);
					xmlMetrics.addContent(xmlMetricNOC);
					xmlMetrics.addContent(xmlMetricNOA);
					xmlMetrics.addContent(xmlMetricNOM);
					xmlMetrics.addContent(xmlMetricNOCo);
					xmlMetrics.addContent(xmlMetricNOLv);
					
					xmlMetrics.addContent(xmlMetricNOIn);
					xmlMetrics.addContent(xmlMetricNOI);
					xmlMetrics.addContent(xmlMetricNOAc);
					
					for (IPackageFragment packageFragment : javaProject.getPackageFragments()) {
						
						
						if (packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE && !packageFragment.getElementName().equals("")) {
							Element xmlPackage = new Element("Package");
							
							xmlPackages.addContent(xmlPackage);
						
							
							xmlPackage.setAttribute("PackageName", packageFragment.getElementName());
							packageCounter++;
							
					
							
							Element xmlClasses = new Element("Classes");
							xmlPackage.addContent(xmlClasses);
							for (ICompilationUnit compilationUnit : packageFragment.getCompilationUnits()) {
								   
									ASTParser parser = ASTParser.newParser(AST.JLS4);
									parser.setSource(compilationUnit);
									parser.setKind(ASTParser.K_COMPILATION_UNIT);
									
									parser.setResolveBindings(true);
									parser.setBindingsRecovery(true);
									
									final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
									TypeDeclarationVisitor typeDeclarationVisitor = new TypeDeclarationVisitor();
									cu.accept(typeDeclarationVisitor);


									for (TypeDeclaration typeDeclaration : typeDeclarationVisitor.getTypes()) {
										Element xmlClass = new Element("Class");
										xmlClasses.addContent(xmlClass);
									
										xmlClass.setAttribute("ClassName", typeDeclaration.getName().getFullyQualifiedName());
										classCounter++;
										
										
										// Count the lines of code
										LineCounterVisitor LineCounterDeclarationFragmentVisitor = new LineCounterVisitor();
										cu.accept(LineCounterDeclarationFragmentVisitor);								        
								        // Get the total lines of code
								        int linesOfCode = LineCounterDeclarationFragmentVisitor.getLinesOfCode();
								        
//								        System.err.println("Total lines of code: " + linesOfCode);					
								        lineCounter +=linesOfCode;
								        
								        
										
										if (Modifier.isPublic(typeDeclaration.resolveBinding().getModifiers())) {
											xmlClass.setAttribute("classAccessLevel", "public");
										} else
										if (Modifier.isProtected(typeDeclaration.resolveBinding().getModifiers())) {
											xmlClass.setAttribute("classAccessLevel", "protected");
										} else
										if (Modifier.isPrivate(typeDeclaration.resolveBinding().getModifiers())) {
											xmlClass.setAttribute("classAccessLevel", "private");
										}				
										xmlClass.setAttribute("isInterface",String.valueOf(typeDeclaration.isInterface()));
																			
										if (typeDeclaration.resolveBinding().getSuperclass()!=null) {
											xmlClass.setAttribute("Superclass",typeDeclaration.resolveBinding().getSuperclass().getName());
											inheritanceCounter++;
										}
										xmlClass.setAttribute("DeclaredPackage", typeDeclaration.resolveBinding().getPackage().getName());
										Element xmlInterfaces = new Element("SuperInterfaces");
										xmlClass.addContent(xmlInterfaces);
										
										for (ITypeBinding itf : typeDeclaration.resolveBinding().getInterfaces()) {
											Element xmlInterface = new Element("Interface");
											xmlInterfaces.addContent(xmlInterface);
											xmlInterface.setAttribute("InterfaceName",itf.getName());
										}
										
										String packageName0 = packageFragment.getElementName();
										String packageName =packageName0.replace(".", "/");

										Element xmlComments = new Element("Comments");								
										xmlClass.addContent(xmlComments);	
																		
										String str ="E:/Workspace/"+javaProject.getElementName()+"/src"+"/"+packageName+"/"+typeDeclaration.getName().getFullyQualifiedName()+ ".java";
//										System.err.println(str);
										
										File file = new File(str);
										if (file.exists()) {
										String converted = readFileToString(str);
										
										for (Comment comment : (List<Comment>) cu.getCommentList()) {
										comment.accept(new CommentVisitor(cu, converted));
										Element xmlComment = new Element("Comment");
										xmlComments.addContent(xmlComment);
										int start = comment.getStartPosition();
										int end = start + comment.getLength();	
										
										if (start >= 0 && end <= converted.length() && start <= end) {
										String commentT = converted.substring(start, end);
//										System.err.println(commentT);
										
										String commentText= commentT.toString().replaceAll("[^a-zA-Z0-9]", " ").trim();
										xmlComment.setAttribute("CommentText", commentText);
										commentCounter++;
										
//										System.err.println(commentText);
//										System.err.println(commentCounter);
										}else {    
										// Handle the case of invalid indices, e.g., by logging an error message or taking appropriate action.
										System.err.println("Invalid indices for substring extraction.");
										}

										}
										
										}else {
											// File doesn't exist, handle the error
								            System.out.println("The system cannot find the file specified.");

										}
										
										
										
										Element xmlFields = new Element("Attributes");
										xmlClass.addContent(xmlFields);							
										for (FieldDeclaration fieldDeclaration : typeDeclaration.getFields()) {		
											for (VariableDeclarationFragment variable : (List<VariableDeclarationFragment>)fieldDeclaration.fragments()) {
												Element xmlField = new Element("Attribute");
												xmlFields.addContent(xmlField);
												xmlField.setAttribute("AttributeName", variable.getName().getFullyQualifiedName());		
												attributeCounter++;
												
												IVariableBinding variableBinding = variable.resolveBinding();
												if (variableBinding!=null){
												if (Modifier.isPublic(variable.resolveBinding().getModifiers())) {
													xmlField.setAttribute("AttributeAccessLevel", "public");
												} else
												if (Modifier.isProtected(variable.resolveBinding().getModifiers())) {
													xmlField.setAttribute("AttributeAccessLevel", "protected");
												} else
												if (Modifier.isPrivate(variable.resolveBinding().getModifiers())) {
													xmlField.setAttribute("AttributeAccessLevel", "private");
												}
												else xmlField.setAttribute("AttributeAccessLevel", "public");
												
												xmlField.setAttribute("AttributeType", variable.resolveBinding().getType().getName());
												xmlField.setAttribute("isStaticAttribute",String.valueOf(Modifier.isStatic(variable.resolveBinding().getModifiers())));
											}
										}
										}			
										

										Element xmlMethods = new Element("Methods");
										xmlClass.addContent(xmlMethods);
										for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
											Element xmlMethod = new Element("Method");
											xmlMethods.addContent(xmlMethod);
											xmlMethod.setAttribute("MethodName",methodDeclaration.getName().getFullyQualifiedName());
											methodCounter++;
											
										if (Modifier.
												isPublic(methodDeclaration
												.resolveBinding()
												.getModifiers())) {
											xmlMethod.setAttribute(
													"MethodAccessLevel",
													"public");
										} else if (Modifier
												.isProtected(methodDeclaration
														.resolveBinding()
														.getModifiers())) {
											xmlMethod.setAttribute(
													"MethodAccessLevel",
													"protected");
										} else if (Modifier
												.isPrivate(methodDeclaration
														.resolveBinding()
														.getModifiers())) {
											xmlMethod.setAttribute(
													"MethodAccessLevel",
													"private");
										} else {
											xmlMethod.setAttribute(
													"MethodAccessLevel",
													"null");
										}
											
										
										xmlMethod.setAttribute("MethodReturnType",methodDeclaration.resolveBinding().getReturnType().getName());
										
										xmlMethod.setAttribute("isStaticMethod",String.valueOf(Modifier.isStatic(methodDeclaration.resolveBinding().getModifiers())));

										xmlMethod.setAttribute("DeclaredClass", methodDeclaration.resolveBinding().getDeclaringClass().getName());
																				
								
											Element xmlParameters = new Element("Parameters");
											
											xmlParameters.setAttribute("NumberOfParameters", String.valueOf(methodDeclaration.parameters().size()));
											xmlMethod.addContent(xmlParameters);
											
											for (SingleVariableDeclaration singleVariableDeclaration : (List<SingleVariableDeclaration>)methodDeclaration.parameters()) {
												Element xmlParameter = new Element("Parameter");
												xmlParameters.addContent(xmlParameter);
												
												xmlParameter.setAttribute("ParameterName", singleVariableDeclaration.getName().getFullyQualifiedName());
												xmlParameter.setAttribute("ParameterType", singleVariableDeclaration.resolveBinding().getType().getName());
											}

											
											Element xmlVariableDeclarations = new Element("LocalVariables");
											xmlMethod.addContent(xmlVariableDeclarations);
											VariableDeclarationFragmentVisitor variableDeclarationFragmentVisitor = new VariableDeclarationFragmentVisitor();
											methodDeclaration.accept(variableDeclarationFragmentVisitor);
											for (VariableDeclarationFragment variableDeclarationFragment : variableDeclarationFragmentVisitor.getVariables()) {
											    if(variableDeclarationFragment.resolveBinding()!=null){
											    variableDeclarationFragment.resolveBinding().getName();
												Element xmlVariableDeclaration = new Element("LocalVariable");
												xmlVariableDeclarations.addContent(xmlVariableDeclaration);
												
												xmlVariableDeclaration.setAttribute("LocalVariableName", variableDeclarationFragment.getName().toString());
												xmlVariableDeclaration.setAttribute("LocalVariableType", variableDeclarationFragment.resolveBinding().getType().getName());
												localVarCounter++;
												
//												xmlVariableDeclaration.setAttribute("DeclaredMethod", variableDeclarationFragment.resolveBinding().getDeclaringMethod().getName());
//												xmlVariableDeclaration.setAttribute("DeclaredClass", variableDeclarationFragment.resolveBinding().getDeclaringMethod().getDeclaringClass().getName());

											    }
											}

											Element xmlFieldDeclarations = new Element("AttributeAccesses");
											xmlMethod.addContent(xmlFieldDeclarations);
											AttributeAccessVisitor fieldDeclarationFragmentVisitor = new AttributeAccessVisitor();
											methodDeclaration.accept(fieldDeclarationFragmentVisitor);
											for (SimpleName simpleName : fieldDeclarationFragmentVisitor.getFields()) {
												
												IVariableBinding variableBinding = (IVariableBinding)simpleName.resolveBinding();
												
												if (variableBinding != null){ 
												if(!variableBinding.getName().toString().contains("out")){
													
												Element xmlFieldDeclaration = new Element("AttributeAccess");
												xmlFieldDeclarations.addContent(xmlFieldDeclaration);
																		
												xmlFieldDeclaration.setAttribute("AttributeAccessName", variableBinding.getName().toString());
												
												AttributeAccessCounter++;
												
												xmlFieldDeclaration.setAttribute("AttributeAccessType", variableBinding.getType().getName());
												xmlFieldDeclaration.setAttribute("HowIsItUsed", simpleName.getParent().toString());
												xmlFieldDeclaration.setAttribute("AccessedIn", methodDeclaration.getName().getFullyQualifiedName());

												 }
												}
												}
											
											Element xmlMethodInvocations = new Element("MethodInvocations");
											xmlMethod.addContent(xmlMethodInvocations);
											
											MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
											methodDeclaration.accept(methodInvocationVisitor);
											for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {
												Element xmlMethodInvocation = new Element("MethodInvocation");
												xmlMethodInvocations.addContent(xmlMethodInvocation);
												
												xmlMethodInvocation.setAttribute("MethodInvocationName", methodInvocation.getName().toString());
												
												MethodInvocationCounter++;

												xmlMethodInvocation.setAttribute("Arguments", methodInvocation.arguments().toString());
												xmlMethodInvocation.setAttribute("InvokedBy", methodDeclaration.getName().getFullyQualifiedName());
												
											}
											
																		
											Element xmlMethodAssignment = new Element("MethodAssignments");
											xmlMethod.addContent(xmlMethodAssignment);
											
											AssignmentVisitor methodAssignmentVisitor = new AssignmentVisitor();
											methodDeclaration.accept(methodAssignmentVisitor);
											for (Assignment methodAssignment : methodAssignmentVisitor.getAssignments()) {
												Element xmlMethodAssignmentExpression= new Element("Assignment");
												xmlMethodAssignment.addContent(xmlMethodAssignmentExpression);
												xmlMethodAssignmentExpression.setAttribute("LeftHandSide", methodAssignment.getLeftHandSide().toString());
												xmlMethodAssignmentExpression.setAttribute("RightHandSide", methodAssignment.getRightHandSide().toString());
											}

											Element xmlMethodExceptions = new Element("MethodExceptions");
											xmlMethod.addContent(xmlMethodExceptions);
											for (ITypeBinding exceptions : methodDeclaration.resolveBinding().getExceptionTypes()) {
												Element xmlMethodException = new Element("Exception");
												xmlMethodExceptions.addContent(xmlMethodException);
												xmlMethodException.setAttribute("ExceptionType", exceptions.getName());	
											}
						
										}
									}	
							}
					
						}
					}

					
					XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
					if (directory!=null) {
						outputter.output(doc, new FileOutputStream(directory+"/"+project.getName()+".xml"));
					}	
					
					lineStr=String.valueOf(lineCounter);
					packageStr= String.valueOf(packageCounter);
					classStr=String.valueOf(classCounter);
					attributeStr=String.valueOf(attributeCounter);
					methodStr=String.valueOf(methodCounter);
					commentStr=String.valueOf(commentCounter);
					localVarStr=String.valueOf(localVarCounter);
					
					inheritanceStr=String.valueOf(inheritanceCounter);
					MethodInvocationStr=String.valueOf(MethodInvocationCounter);
					AttributeAccessStr=String.valueOf(AttributeAccessCounter);
				    
					xmlMetricLOC.setAttribute("LOC", lineStr);
					xmlMetricNOP.setAttribute("NOP", packageStr);
					xmlMetricNOC.setAttribute("NOC", classStr);
					xmlMetricNOA.setAttribute("NOA", attributeStr);
					xmlMetricNOM.setAttribute("NOM", methodStr);
					xmlMetricNOCo.setAttribute("NOCo", commentStr);
					xmlMetricNOLv.setAttribute("NOLv", localVarStr);
					
					xmlMetricNOIn.setAttribute("NOIn", inheritanceStr);
					xmlMetricNOI.setAttribute("NOI", MethodInvocationStr);
					xmlMetricNOAc.setAttribute("NOAc", AttributeAccessStr);
					
					
					XMLOutputter outputter2 = new XMLOutputter(Format.getPrettyFormat());
					if (directory!=null) {
						outputter2.output(xmlcodeMetrics, new FileOutputStream(directory+"/"+project.getName()+"-codeMetrics"+".xml"));
					}	
				}
			
				
			} 
			
			
			catch (FileNotFoundException e) {
			e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JavaModelException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
//			catch (NullPointerException e) {
//				e.printStackTrace();
//			}
		}
		
//		return null;
		
		new Inheritance();
		Inheritance.Read();
		
		new codeOrganization();
		codeOrganization.run();
		
		new Invocation();
		Invocation.runInvocation();
		
	    System.out.println("==[The execution time in ms]=========================================");
		long time2 = System.currentTimeMillis();
		System.out.println("The execution time in ms is equal to: "+(time2-time1));
		System.out.println("=====================================================================");

		
		return null;
	}


	// read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();
		return fileData.toString();
	}

	public static void deleteAllDocuments() {
		
		File file = new File("E:\\Workspace\\ScaMaha\\ScaMaha");      
	       String[] myFiles;    
	           if(file.isDirectory()){
	               myFiles = file.list();
	               for (int i=0; i<myFiles.length; i++) {
	                   File myFile = new File(file, myFiles[i]); 
	                   myFile.delete();
	               }
	            }
	}
}
	

