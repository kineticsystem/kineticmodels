/*
 * MoveForwardAction.java
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

// Apache classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Application classes.

import org.kineticsystem.commons.data.controller.AdvancedAction;
import org.kineticsystem.commons.data.controller.Navigator;
import org.kineticsystem.commons.util.Localizer;
import org.kineticsystem.commons.util.ResourceLoader;

/**
 * Action connected to a navigator controller and used to to move forward to the
 * next of the controller list model.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 * @see org.kineticsystem.commons.data.controller.Navigator
 */
public class MoveForwardAction extends AdvancedAction {

    /** Name of the action used to move forward. */
    public static final String ACTION_NAME = "MoveForwardAction";
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Logging system. */
    private static Log logger = LogFactory.getLog(MoveForwardAction.class);
    
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
    
    /** The navigation controller instance. */
    private final Navigator navigator;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */

   /**
     * Constructor.
     * @param controller The navigator controller instance.
     */
    public MoveForwardAction(Navigator navigator) {
        this.navigator = navigator;
        putValue(Action.SMALL_ICON,
            ResourceLoader.getIcon(NAVIGATOR_RESOURCE + "Next16.png"));
        putValue(Action.NAME, Localizer.localizeString(NAVIGATOR_BUNDLE,
            "MoveForwardAction"));
        putValue(Action.SHORT_DESCRIPTION,               
            Localizer.localizeString(NAVIGATOR_BUNDLE,
                "MoveForwardAction_Description"));
        setEnabled(false);
    }

    /* /////////////////////////////////////////////////////////////////////////
     * AbstractAction class implementation.
     */
    
    /** {@inheritDoc} */
    public void actionPerformed(ActionEvent actionEvent) {
        navigator.moveNext();
    }
}