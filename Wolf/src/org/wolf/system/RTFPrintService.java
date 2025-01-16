package org.wolf.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.print.*;
import javax.print.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.wolf.application.RootDictionaryPanel;
import org.wolf.dialogs.ChooseFileDialog;

import javax.print.attribute.*;
import javax.print.attribute.standard.PrinterInfo;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterState;


/** Service to insert RTF output into the list of Printer Services */
public class RTFPrintService implements PrintService
{
   PrinterName printerName = new PrinterName("RTF Renderer", null);
   PrinterInfo printerInfo = new PrinterInfo("This Service writes RTF to disk", null);
   File file;
   JLabel label;
   boolean pageable = false;
   Environment env;
   
   /** Create instance of this print service 
 * @throws URISyntaxException */
   public RTFPrintService(Environment env)
   {
	   this.env = env;
       label = RootDictionaryPanel.getLabel();
       
       /** Don't register the service if this is a MAC because that OS  
        *  doesn't allow adding print to file services.
        *   
        */
       String osName = System.getProperty("os.name").toLowerCase();
       if (osName.contains("win")) 
       {
    	   PrintServiceLookup.registerService(this);
       }
   }
   
   public void setFile()
   {
	   String path = env.getPath(Environment.SAVE_DICT);
	   String fileName = ChooseFileDialog.getFileName();
	   if (fileName != null) 
		   path += "/" + fileName;
	   file = new File(path);
   }
   
   /** Get the file to direct the output */
   public File getFile()
   {
	   return file;
   }
   
   /** Activate or disable the print service */
   public void activate(boolean active)
   {
	   pageable = active;
   }
   
    
   /** Registers a listener for events on this PrintService. */
   public void	addPrintServiceAttributeListener(PrintServiceAttributeListener listener)
   {
	   return;
   }

   /** Creates and returns a PrintJob capable of handling data from any of the supported document flavors. */
   public DocPrintJob	createPrintJob()
   {
		try 
		{
 		    JFrame root = Environment.getRootFrame();
			ChooseFileDialog dialog = new ChooseFileDialog
			           (root, ChooseFileDialog.RTF);
			file = dialog.getSelectedFile();
		} catch (FileNotFoundException e) 
		{
			label.setText("Print Operation Canceled");
			return new RTFDocPrintJob(this, true);
		} 
		label.setText("Printing Active - please wait");
        return new RTFDocPrintJob(this, false);
   }
   
   /** Determines if two services are referring to the same underlying service. */
   public boolean	equals(Object obj)
   {
      return (obj instanceof RTFPrintService);
   }
   
   /** Gets the value of the single specified service attribute. */
   @SuppressWarnings("unchecked")
   public <T extends PrintServiceAttribute>T	getAttribute(Class<T> category)
   {  
	   String name = category.getSimpleName();
	   if (name.equals("PrinterMakeAndModel"))
		   return (T)printerName;
	   
	   if (name.equals("PrinterIsAcceptingJobs"))
		   return (T)PrinterIsAcceptingJobs.ACCEPTING_JOBS;
	   
	   if (name.equals("PrinterInfo"))
		   return (T)printerInfo;
	   
	   if (name.equals("PrinterState"))
		   return (T)PrinterState.IDLE;
	   
	   return null;
   }
   
   /** Obtains this print service's set of printer description attributes giving this Print Service's status. */
   public PrintServiceAttributeSet	getAttributes()
   {
      return null;
   }
   /** Determines this print service's default printing attribute value in the given category. */ 
   public Object	getDefaultAttributeValue(Class<? extends Attribute> category)
   {
	  String name = category.getSimpleName();
	  if (name.equals("Copies"))
		  return null;
	  if (name.equals("SheetCollate"))
		  return null;
	  if (name.equals("OrientationRequested"))
		  return null;
	  if (name.equals("Chromaticity"))
		  return null;
	  if (name.equals("PrintQuality"))
		  return null;
	  if (name.equals("Sides"))
		  return null;
	  if (name.equals("JobSheets"))
		  return null;
	  if (name.equals("JobPriority"))
		  return null;
	  if (name.equals("JobName"))
		  return null;
	  if (name.equals("RequestingUserName"))
		  return null;

      return null;
   }
   /** Returns a String name for this print service which may be used by applications to request a particular print service. */
   public String	getName()
   {
      return "Wolf Print to RTF";
   }
   
   /** No User interface for this service. */
   public ServiceUIFactory	getServiceUIFactory()
   {
      return null;
   }
   
   /** Determines the printing attribute categories a client can specify when setting up a job for this print service. */
   public Class<?>[]	getSupportedAttributeCategories()
   {
      return null;
   }
   
   /** Determines the printing attribute values a client can specify in the given category when setting up a job for this print service. */ 
   public Object	getSupportedAttributeValues(Class<? extends Attribute> category, DocFlavor flavor, AttributeSet attributes)
   {
		  String name = category.getSimpleName();
		  if (name.equals("Copies"))
			  return null;
		  if (name.equals("MediaPrintableArea"))
			  return null;
	  
      return null;
   }
   
   /** Determines the print data formats a client can specify when setting up a job for this PrintService. */
   public DocFlavor[]	getSupportedDocFlavors()
   {
      return null;
   }
   
   /** Assume that all attributes are supported (like landscape, copies, etc. */
   public AttributeSet	getUnsupportedAttributes(DocFlavor flavor, AttributeSet attributes)
   {
	  if (attributes==null) return null;
	  System.out.println(attributes);
      return null;
   }
   
   /** This method should be implemented consistently with equals(Object). */
   public int	hashCode()
   {
      return "RTFPrintService".hashCode();
   }
   
   /** Determines whether a client can specify the given printing attribute category when setting up a job for this print service. */
   public boolean	isAttributeCategorySupported(Class<? extends Attribute> category)
   {
	  String name = category.getSimpleName();
	  if (name.equals("Destination"))
		  return false;
	  if (name.equals("PageRanges"))
		  return true;
	  if (name.equals("Copies"))
		  return false;
	  if (name.equals("SheetCollate"))
		  return false;
	  if (name.equals("Media"))
		  return false;
	  if (name.equals("OrientationRequested"))
		  return false;
	  if (name.equals("Chromaticity"))
		  return false;
	  if (name.equals("PrintQuality"))
		  return false;
	  if (name.equals("Sides"))
		  return false;
	  if (name.equals("JobSheets"))
		  return false;
	  if (name.equals("JobPriority"))
		  return false;
	  if (name.equals("JobName"))
		  return false;
	  if (name.equals("RequestingUserName"))
		  return false;
		  
      return false;
   }
   
   /** Determines whether a client can specify the given printing attribute value when setting up a job for this Print Service. */ 
   public boolean	isAttributeValueSupported(Attribute attrval, DocFlavor flavor, AttributeSet attributes)
   {  
	   String name = attrval.getName();
	   if (name.equals("page-ranges"))
		   return false;
	   if (name.equals("sheet-collate"))
		   return false;
	   if (name.equals("orientation-requested"))
		   return false;
      return false;
   }
   
   /** Determines if this print service supports a specific DocFlavor. */ 
   public boolean	isDocFlavorSupported(DocFlavor flavor)
   { 
	   if (flavor.equals(DocFlavor.SERVICE_FORMATTED.PAGEABLE))
		   return pageable; 
	   if (flavor.equals(DocFlavor.SERVICE_FORMATTED.PRINTABLE))
		   return true;
	   else
		   return false;
   }
   
   /** Removes the print-service listener from this print service.. */ 
   public void	removePrintServiceAttributeListener(PrintServiceAttributeListener listener)
   {
	   System.out.println("Remove Listener");
   }
    
}  // End of RTFPrintService class
