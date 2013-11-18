/*
 * JEditorDefaultRenderer.java
 *
 * Created on July 10, 2004, 9:14 PM
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

import java.awt.BorderLayout;
import javax.swing.JTextField;

// Application classes.

import org.kineticsystem.commons.data.controller.DataNavigator;
import org.kineticsystem.commons.data.controller.DataNavigatorEvent;
import org.kineticsystem.commons.data.controller.Navigator;
import org.kineticsystem.commons.data.controller.NavigatorEvent;
import org.kineticsystem.commons.util.Localizer;

/**
 *
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
@SuppressWarnings("serial")
public class JDataNavigatorDefaultRenderer extends JDataNavigatorRenderer {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constants.
     */

    /** Resource bundle class. */
    private static final String NAVIGATOR_BUNDLE
       = "org.kineticsystem.commons.data.view.bundle.NavigatorBundle";
    
    /* /////////////////////////////////////////////////////////////////////////
     * GUI components.
     */
    
    /** Rendering objects. */
    private JTextField renderer;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public JDataNavigatorDefaultRenderer() {
        this.setLayout(new BorderLayout());
        renderer = new JTextField();
        renderer.setFocusable(false);
        add(renderer, BorderLayout.CENTER);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * NavigatorListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public void objectSelected(NavigatorEvent event) {

        Navigator navigator = (Navigator) event.getSource();
        Object obj = navigator.getSelectedObject();
        int position = navigator.getPosition() + 1;
        int count = navigator.getSize();
        
        if (obj == null) {
            renderer.setText(Localizer.localizeString(NAVIGATOR_BUNDLE, 
                "NoObjectFound"));
        } else {
            renderer.setText(Localizer.localizeMessage(NAVIGATOR_BUNDLE, 
                "NavigatorMessage",
                new Object[] {new Integer(position), new Integer(count)})
            );
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * EditorListener interface implementation.
     */
    
    /** {@inheritDoc} */
    public void editorStateChanged(DataNavigatorEvent event) {
        switch(event.getState()) {
            case DataNavigator.EMPTY_STATE: {
                // Do nothing.
                break;
            }
            case DataNavigator.DEFAULT_STATE: {
                // Do nothing.
                break;
            }
            case DataNavigator.CREATION_STATE: {
                renderer.setText(Localizer.localizeString(NAVIGATOR_BUNDLE,
                    "CreateObjectMessage"));
                break;
            }
            case DataNavigator.EDITING_STATE: {
                renderer.setText(Localizer.localizeString(NAVIGATOR_BUNDLE,
                    "ModifyObjectMessage"));
                break;
            }
        }
    }
}