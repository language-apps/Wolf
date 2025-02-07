/*
 *   class DictionaryPanels.java
 *
 *   @author  HarveyD
 *   Dan Harvey - Professor of Computer Science
 *   Southern Oregon University, 1250 Siskiyou Blvd., Ashland, OR 97520-5028
 *   harveyd@sou.edu
 *   @version 1.00
 *
 *   Copyright 2010, all rights reserved
 *
 * This software is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * To receive a copy of the GNU Lesser General Public write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.wolf.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.acorns.visual.ColorScheme;
import org.wolf.lib.Icons;
import org.wolf.system.Environment;

/** Create a panel with more than one dictionary panels. */
public class DictionaryPanels extends RootDictionaryPanel      
{
 	private static final long serialVersionUID = 1L;
	private DictionaryPanel  panel;
    private JPanel           thisPanel, center;
    
     /** Panel background color behind buttons */
    public static final int ICON = 30;

    public static final Color BACKGROUND  = new Color(200,200,200);
    /** Panel Dark background in scrollbar viewport */
    public static final Color FOREGROUND  = Color.WHITE;
	
    
    /** Creates a new instance of DictionaryPanels
     *
     * @param env Runtime environment class
     * @param number Number of panels to create (Only one supported at present)
     */
    public DictionaryPanels(Environment env)
    {   super(env);

        // Set the layout of this panel.
        thisPanel = this;
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);

        // Create the "DictionaryListeners" properties change listener
        // Remove previous "DictionaryListeners"
        String listener = "DictionaryListeners";
        PropertyChangeListener[] pcl 
            = Toolkit.getDefaultToolkit().getPropertyChangeListeners(listener);
        for (int i=0; i<pcl.length; i++)
        {   Toolkit.getDefaultToolkit().removePropertyChangeListener
                                                        (listener, pcl[0]); }
        // Add the new one.
        Toolkit.getDefaultToolkit().addPropertyChangeListener(listener, this);
 
        // Create array of dictionary panels and add them to the center.
        center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BACKGROUND);
        
        Color color = new Color(224,224,224);
        panel = new DictionaryPanel
                    (this, new ColorScheme(color, null), null);
        setButtonPanel(panel);
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        center.add(panel);
        center.add(Box.createVerticalStrut(10));
        add(center, BorderLayout.CENTER);
        
        JPanel north = panel.getNorthPanel();

        // Create the Help Button.
        JButton helpButton = null;
        ImageIcon icon = Icons.getImageIcon("help.png", 0);
        helpButton = new JButton(icon);
        helpButton.setPreferredSize(new Dimension(ICON+1, ICON+1));
        helpButton.setMinimumSize  (new Dimension(ICON+1, ICON+1));
        helpButton.setMaximumSize  (new Dimension(ICON+1, ICON+1));
        helpButton.setToolTipText("Get Help on How to Manage Dictionaries");
        helpButton.setBackground(BACKGROUND);
        HelpSet helpSet = env.getHelpSet();
        
        if (helpSet!=null)
        {  
        	HelpBroker helpBroker = helpSet.createHelpBroker("Main_Window");
            final ActionListener newListener = new CSH.DisplayHelpFromSource(helpBroker);
            helpButton.addActionListener(e -> {
          		Frame frame = Environment.getRootFrame();
          		Point point = frame.getLocation();
          		helpBroker.setLocation(point);
          		ActionEvent event = (ActionEvent)(e);
          	    newListener.actionPerformed(event);
            });
        }
        else
        {  helpButton.addActionListener(
                new ActionListener()
                {   public void actionPerformed(ActionEvent ae)
                    {  JOptionPane.showMessageDialog
                               (thisPanel, "Sorry, No Help is Available");
                    }
                });
        }
         
        // Add the components to the north panel.
        north.add(Box.createHorizontalGlue());
        if (helpButton!=null)    north.add(helpButton);
    }    
}   // End of DictionaryPanels class
