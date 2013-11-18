/*
 * EditorListener.java
 *
 * Created on October 12, 2003, 6:25 PM
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

// Java classes.

import java.util.EventListener;

/**
 * Any object interested in the operations of an editor must implement this
 * interface.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 * @see org.kineticsystem.commons.data.controller.DataNavigator
 */
public interface DataNavigatorListener extends EventListener {
    
    /**
     * This method is called when an editor changes its internal state.
     * @param event Object containing information about the current state of an
     *     editor.
     */
    public void editorStateChanged(DataNavigatorEvent event);
}
