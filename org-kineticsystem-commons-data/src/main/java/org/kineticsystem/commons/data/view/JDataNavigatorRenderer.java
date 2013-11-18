/*
 * JEditorRenderer.java
 *
 * Created on July 10, 2004, 10:42 PM
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

package org.kineticsystem.commons.data.view;

// Java classes.

import javax.swing.JComponent;

// Application classes.

import org.kineticsystem.commons.data.controller.DataNavigatorEvent;
import org.kineticsystem.commons.data.controller.DataNavigatorListener;
import org.kineticsystem.commons.data.controller.NavigatorEvent;
import org.kineticsystem.commons.data.controller.NavigatorListener;

/**
 * Implement this class if you want to render some information related to
 * the selected object of a <tt>JNavigator</tt> message renderer.
 * @author Giovanni Remigi
 * @version $Revision: 149 $
 */
public abstract class JDataNavigatorRenderer extends JComponent
        implements NavigatorListener, DataNavigatorListener {
    
    /* /////////////////////////////////////////////////////////////////////////
     * NavigatorListener interface implementation.
     */
    
    /** Serial number. */
    private static final long serialVersionUID = -3019110992313999660L;

    /** {@inheritDoc} */
    public abstract void objectSelected(NavigatorEvent event);
    
    /* /////////////////////////////////////////////////////////////////////////
     * EditorListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public abstract void editorStateChanged(DataNavigatorEvent event);
}
