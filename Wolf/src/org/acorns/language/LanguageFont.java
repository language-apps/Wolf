/*
 * LanguageFont.java
 *
 *   @author  HarveyD
 *   @version 3.00 Beta
 *
 *   Copyright 2007-2015, all rights reserved
 */
package org.acorns.language;
 
import java.awt.Font;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class LanguageFont implements Cloneable, Serializable
{
	//7943735110496738736L original Wolf version
   private static final long serialVersionUID = 1L; 
   private String name;                // Name of the font.
   private int    size;                // Size of the font.
   private String language;            // Font language.
   private transient Font font = null; // Actual font.

   
   /** Constructor to instantiate the font and its parameters
    * @param name The name of the font family
    * @param size The size of this font
    * @param language The language that this font applies to
    */
     public LanguageFont(String name, int size, String language)
     {
        this.name     = name;
        this.size     = size;
        this.language = language;
        
        if (name!=null && name.equals("Default")) name = null;
        this.font     =  getFont();
        this.name     =  font.getName();
     }
   
   /** Get the name of the font family */
   public String getLanguage() { return language; }
   
   /** Get the language font object */
   public final Font   getFont()     
   {  if (font == null) font = new Font(name, Font.PLAIN, size);
      if (font == null) font = new Font(null, Font.PLAIN, 12);
      return font;
   }

   /** String to create a string representing this object
    */
   static String blanks = "                             ";
   public @Override String toString()  {  return toString(null); }
   
   public String toString(String languageCode)
   {
       String nameStr = (getFont().getName() + blanks);
       nameStr = nameStr.substring(0,blanks.length());
       
       String sizeStr = ("  " + getFont().getSize());
       sizeStr = sizeStr.substring(sizeStr.length()-2, sizeStr.length());
       
       String languageStr = "";
       if (languageCode!=null && languageCode.length()==3)
       {  languageStr = languageCode + " "; }
       languageStr += (language + blanks).substring(0, blanks.length());
       
       return nameStr + " " + sizeStr + " " + languageStr;     
   }
   
   /** Extract the toString() formatted string data
    *  @param s toString formatted data
    *  @return a trimmed string array three long. [0] = font name, [1] = font size, [2] = language
    */
   public static String[] extractToString(String s)
   {  String[] extract = new String[3];
      extract[0] = s.substring(0, blanks.length());
      extract[1] = s.substring(blanks.length()+1, blanks.length()+3);
      extract[2] = s.substring(blanks.length()+4);
      for (int i=0; i<3; i++)
      {   extract[i] = extract[i].replaceAll("^\\s+", "");
          extract[i] = extract[i].replaceAll("\\s+$", "");
      }
      return extract;       
   }
	
   /** Method to create a clone of this object */
   public @Override Object clone()
   {  try 
      {   LanguageFont result = (LanguageFont) super.clone();
          return result;
      } catch (CloneNotSupportedException e) 
      { JOptionPane.showMessageDialog
                (null, "Could not clone LanguageFont object"); 
      }
      return null;
  }
   
} // End of LanguageFont class