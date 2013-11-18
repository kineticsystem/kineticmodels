/*
 * SynchronizeAction.java
 *
 * Created on May 15, 2006, 10:49 PM
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

package org.kineticsystem.commons.data.view.actions;

// Java classes.

import java.awt.event.ActionEvent;
import javax.swing.Action;

// Application classes.

import org.kineticsystem.commons.data.controller.AdvancedAction;
import org.kineticsystem.commons.data.controller.DataNavigator;
import org.kineticsystem.commons.util.Localizer;

/**
 * Action connected to an editor controller and used to synchonize the modified
 * list with the database.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 * @see org.kineticsystem.commons.data.controller.DataNavigator
 */
public class SynchronizeAction extends AdvancedAction {
    
    /** Name of the action to synchonize the modified list with the database. */
    public static final String ACTION_NAME = "SynchronizeAction";
    
    /* /////////////////////////////////////////////////////////////////////////
     * Resources.
     */
    
    /** Resource bundle class. */
    private static final String NAVIGATOR_BUNDLE
        = "org.kineticsystem.commons.data.view.bundle.NavigatorBundle";

    /** Name of the package containing all requested images. */
    static final String NAVIGATOR_RESOURCE 
        = "org/kineticsystem/commons/data/view/images/";
    
    /* /////////////////////////////////////////////////////////////////////////
     * Variables.
     */
    
    /** The editor controller instance. */
    private final DataNavigator controller;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param controller The editor controller instance.
     */
    public SynchronizeAction(DataNavigator controller) {
        this.controller = controller;
//        putValue(Action.SMALL_ICON,
//            ResourceLoader.getIcon(NAVIGATOR_RESOURCE + "SaveAll16.gif"));
        putValue(Action.NAME, Localizer.localizeString(NAVIGATOR_BUNDLE,
            "SynchronizeAction"));
        putValue(Action.SHORT_DESCRIPTION,               
            Localizer.localizeString(NAVIGATOR_BUNDLE, 
                "SynchronizeAction_Description"));
        setEnabled(true);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * AbstractAction class implementation.
     */
    
    /** {@inheritDoc} */
    public void actionPerformed(ActionEvent actionEvent) {
        // Implement this method.
    }
}