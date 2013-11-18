/*
 * MoveForwardMouseAction.java
 *
 * Created on 19 May 2006, 16.30
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

import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.Action;

// Apache classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Application classes.

import org.kineticsystem.commons.data.controller.AdvancedAction;
import org.kineticsystem.commons.data.controller.Navigator;
import org.kineticsystem.commons.threads.Accelerator;
import org.kineticsystem.commons.util.Localizer;
import org.kineticsystem.commons.util.ResourceLoader;

/**
 *
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public class MoveForwardMouseAction extends AdvancedAction {
    
    /** Name of the action used to cancel an update, or an insertion. */
    public static final String ACTION_NAME = "MoveForwardMouseAction";
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Logging system. */
    private static Log logger = LogFactory.getLog(MoveForwardMouseAction.class);
    
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
    
    /**
     * The editor editor instance.
     */
    private final Navigator navigator;
    
    /** The thread uset to automatically move the list navigator. */
    private Accelerator mover;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param navigator The navigator instance.
     */
    public MoveForwardMouseAction(Navigator navigator) {
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
     * AdvancedAction class implementation.
     */

    /**
     * This method is called when the user press a mouse button on the button
     * connected to this action.
     * @param event The mouse event.
     */
    public void mousePressed(MouseEvent event) {

        if ((event.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            
            mover = new Accelerator() {
                
                public void execute() {

                    if (navigator.isMoveForwardEnabled()) {
                        try {
                            EventQueue.invokeAndWait(new Runnable() {
                                public void run() {
                                    navigator.moveNext();
                                }
                            });
                        } catch (Exception ex) {
                            logger.info(ex);
                        }
                    } else {
                        interrupt();
                    }
                }
            };
            mover.setPriority(Thread.NORM_PRIORITY);
            mover.start();
        }
    }

    /** 
     * This method is called when the user release a mouse button.
     * @param event The mouse event.
     */
    public void mouseReleased(MouseEvent event) {

        if ((event.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
            mover.interrupt();
            mover = null;
        }
    }
}