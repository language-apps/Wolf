/*
 * UndoRedoAuthors.java
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

package org.wolf.undoredo;

import org.wolf.data.Author;
import org.wolf.data.DictionaryData;

/** Class to undo and redo author changes */
public class UndoRedoAuthors extends UndoRedoData
{
    Author[] oldAuthors, newAuthors;

    public UndoRedoAuthors(Author[] oldAuthors, Author[] newAuthors)
    {  this.oldAuthors = oldAuthors;
       this.newAuthors = newAuthors;

    }
    public String redo(DictionaryData data)
    { data.setAuthors(newAuthors);
      return "";
    }

    public  String undo(DictionaryData data)
    {  data.setAuthors(oldAuthors);
       return "";
    }


}  // End of UndoRedoAuthors class
