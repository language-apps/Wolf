package org.wolf.system;

import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.print.Doc;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAttributeListener;
import javax.print.event.PrintJobListener;
import javax.swing.JLabel;

import org.wolf.application.RootDictionaryPanel;
import org.wolf.data.DictionaryData;

public class RTFDocPrintJob implements DocPrintJob
{
	RTFPrintService printService;
	boolean canceled;
	JLabel label;
	
	public RTFDocPrintJob(RTFPrintService printService, boolean canceled)
	{
		this.printService = printService;
		this.canceled = canceled;
	    label = RootDictionaryPanel.getLabel();
	}

	@Override
	public PrintService getPrintService() 
	{
		return printService;
	}

	@Override
	public PrintJobAttributeSet getAttributes() 
	{
		return null;
	}

	@Override
	public void addPrintJobListener(PrintJobListener listener) 
	{
		System.out.println("addPrintJobListener");
	}

	@Override
	public void removePrintJobListener(PrintJobListener listener) 
	{
		System.out.println("RemovePrintListener");
	}

	@Override
	public void addPrintJobAttributeListener(PrintJobAttributeListener listener, PrintJobAttributeSet attributes) 
	{
		System.out.println("AddAttributeListener");
	}

	@Override
	public void removePrintJobAttributeListener(PrintJobAttributeListener listener)
	{
		System.out.println("RemoveJobAttributeListener");
	}

	@Override
	public void print(Doc doc, PrintRequestAttributeSet attributes) throws PrintException 
	{ 
		if (canceled)
		{
			label.setText("Operation Cancelled");
			return;
		}
		
		File file = printService.getFile();
        PropertyChangeListener[] pcl 
        = Toolkit.getDefaultToolkit().getPropertyChangeListeners
                                         ("DictionaryListeners");
        RootDictionaryPanel dictionaryPanelProperties = (RootDictionaryPanel)pcl[0];
        DictionaryData dictionary = dictionaryPanelProperties.getDictionaryData();
        
        try
        {
    		dictionary.exportXML(file);
        }
        catch (Exception e) 
        {
        	label.setText("Print Operation Failed");
        }
		label.setText("Operation Complete");
	}
}	// End of RTFDocPrint class
