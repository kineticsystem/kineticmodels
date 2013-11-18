/*
 * EditorManager.java
 *
 * Created on 19 May 2006, 11.29
 *
 * Copyright (C) 2004 Remigi Giovanni
 * g.remigi@kineticsystem.org
 * www.kineticsystem.org
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package org.kineticsystem.commons.data.controller;

/**
 * This is the standard interface to implement by any component responsible for
 * the creation, the editing or the deletion of an object.
 * @author Giovanni Remigi
 * @version $Revision: 146 $
 * @see org.kineticsystem.commons.data.view.JEditorManager
 */
public interface Editor {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Control methods.
     */
    
    /**
     * This method should be called by <tt>Editor</tt> manager immediately
     * before creating a new object. If the method returns false no new object
     * is going to be created.
     * It can also be used to initialize a database transaction.
     * @return True if creation is available, false otherwise.
     */
    public boolean creationRequested();
    
    /**
     * This method is called before the insertion of a new object into the
     * underlying model. The method can be used to validate and create the new
     * new object before updating the model.
     * @return True if the object is valid, false otherwise.
     */
    public boolean creationChecked();
    
    /**
     * This method is called by an <tt>Editor</tt> manager immediately after a
     * new created object is inserted in the model. It can be used to commit a
     * transaction.
     */
    public void creationExecuted();
    
    /**
     * This method is called by an <tt>Editor</tt> manager when a creation of a
     * new object has been canceled. It can also be used to rollback a
     * transaction.
     */
    public void creationCancelled();
    
    /**
     * This method is called by an <tt>Editor</tt> manager immediately before
     * editing an object. If the method returns false the object cannot be
     * edited. It can also be used to start a database transaction.
     * @return True if edit is available, false otherwise.
     */
    public boolean changeRequested();
    
    /**
     * Validate an edited object before updating the underlying list. If the new
     * object is not valid the editor remains in the editing phase allowing the
     * user to correct the problem.
     * @return True if the object is valid, false otherwise.
     */
    public boolean changeChecked();
    
    /**
     * This method is called by an <tt>Editor</tt> manager immediately after an
     * object change. It can be used to commit a transaction.
     */
    public void changeExecuted();
    
    /**
     * This method is called by an <tt>Editor</tt> manager when a change has
     * been canceled. It can also be used to rollback a transaction.
     */
    public void changeCancelled();
    
    /**
     * This method is called by an <tt>Editor</tt> manager immediately before
     * removing an object. If the method returns false the object cannot be
     * deleted. It can also be used to start a database transaction.
     * @return True if removal is available, false otherwise.
     */
    public boolean removalRequested();
    
    /**
     * This method is called by an <tt>Editor</tt> manager immediately after an
     * object removal. It can be used to commit a transaction.
     */
    public void removalExecuted();
    
    /**
     * This method is called by an <tt>Editor</tt> manager when a removal has
     * been canceled. It can also be used to rollback a transaction.
     */
    public void removalCancelled();
    
    /* /////////////////////////////////////////////////////////////////////////
     * Getter and setter methods.
     */
    
    /**
     * Set the object do be displayed and modified.
     * @param obj The object do be displayed and modified (it can be null).
     */
    public void setObject(Object obj);
    
    /**
     * Return the object currently stored within the editor implementation. It
     * could be used to retrieve a new object or a modified one.
     * @return The object stored in the editor implementation (it can be null).
     */
    public Object getObject();
    
}