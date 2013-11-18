/*
 * CreateAction.java
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
import org.kineticsystem.commons.util.ResourceLoader;

/**
 * Action connected to an editor controller and used to create a new object.
 * 
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 * @see org.kineticsystem.commons.data.controller.DataNavigator
 */
public class CreateAction extends AdvancedAction {

    /** Name of the action used to create a new object. */
    public static final String ACTION_NAME = "CreateAction";
    
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
    public CreateAction(DataNavigator controller) {
        this.controller = controller;
        putValue(Action.SMALL_ICON,
            ResourceLoader.getIcon(NAVIGATOR_RESOURCE + "Add16.png"));
        putValue(Action.NAME, Localizer.localizeString(NAVIGATOR_BUNDLE,
            "NewAction"));
        putValue(Action.SHORT_DESCRIPTION,               
            Localizer.localizeString(NAVIGATOR_BUNDLE,
                "NewAction_Description"));
        setEnabled(false);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * AbstractAction class implementation.
     */
    
    /** {@inheritDoc} */
    public void actionPerformed(ActionEvent actionEvent) {
        controller.create();
    }
}