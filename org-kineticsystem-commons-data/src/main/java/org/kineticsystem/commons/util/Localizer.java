/*
 * Localizer.java
 *
 * Created on April 9, 2002, 5:10 PM
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

package org.kineticsystem.commons.util;

// Java classes.

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.KeyStroke;

// Apache commons classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * This is an utility with a set of localization methods.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public class Localizer {
    
    /** Logging framework. */
    private static Log logger = LogFactory.getLog(Localizer.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public constants.
     */
    
    /** This constants is used in debugging session. */
    public static final int DEBUG_MESSAGE_MODE = 0;

    public static final int DEFAULT_MESSAGE_MODE = 1; 
    
    /* /////////////////////////////////////////////////////////////////////////
     * Configuration properties.
     */
    
    /**
     * If set to <tt>DEBUG_MESSAGE_MODE</tt> any localization methods simply
     * return the searching key. If set to the default value
     * <tt>DEFAULT_MESSAGE_MODE</tt> any localization method returns the
     * requested localized resource.
     */
    private static int messageMode = DEFAULT_MESSAGE_MODE;
    
    /** The default localization context. */
    private static Locale locale = Locale.getDefault();
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /** 
     * Set the default localization context.
     * @param defaultLocale The new localization context.
     */
    public static void setLocale(Locale defaultLocale) {
        locale = defaultLocale;
    } 
    
    /** 
     * Return the default localization context.
     * @return The localization context.
     */
    public static Locale getLocale() {
        return locale;
    }
    
    /** 
     * Retrieve and return a localized string from a resource bundle using the
     * given searching key.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @return The localized string.
     */
    public static String localizeString(String resourceName, String key) {
        if (messageMode == DEBUG_MESSAGE_MODE) {
            return key;
        } else {
            return (String) doLocalize(resourceName, key);
        }
    }
    
    /** 
     * Retrieve and return a localized message from a resource bundle using the
     * given searching key.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @param messageArguments Input parameters to be localized inside the
     *     string. 
     * @return The localized message.
     */
    public static String localizeMessage(String resourceName, String key,
            Object[] messageArguments) {
        if (messageMode == DEBUG_MESSAGE_MODE) {
            return key;
        } else {
            String message = (String) doLocalize(resourceName, key);
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(Locale.getDefault());
            formatter.applyPattern(message);
            return formatter.format(messageArguments); 
        }
    }
    
    /** 
     * Retrieve and return a <tt>Mnemonic<tt> object from a resource bundle
     * using the given searching key. A mnemonic is an integer identifying a
     * keyboard key associated to the <tt>ALT</tt> key used in navigating menus.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @return The localized mnemonic.
     */
    public static Integer localizeMnemonic(String resourceName, String key) {
        return (Integer) doLocalize(resourceName, key);
    }
    
    /** 
     * Retrieve and return a <tt>KeyStroke<tt> object from a resource bundle
     * using the given searching key. A keystroke is an object identifying a
     * keyboard combination of keys.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @return The localized keystroke.
     */
    public static KeyStroke localizeShortcut(String resourceName, String key) {
        return (KeyStroke) doLocalize(resourceName, key);
    }
    
    /** 
     * Retrieve and return an object from a resource bundle using the given
     * searching key.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @return The localized object.
     */
    public static Object localize(String resourceName, String key) {
        return doLocalize(resourceName, key);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /** 
     * Helper method used to retrieve and return an object from a resource
     * bundle using the given searching key. It is used to avoid the problem of
     * fragile classes.
     * @param resourceName The resource name.
     * @param key The resource key.
     * @return The localized object.
     */ 
    private static Object doLocalize(String resourceName, String key) {
        Object localized = null;
        ResourceBundle resource = null;
        try {
            resource = ResourceBundle.getBundle(resourceName, locale);
        } catch (MissingResourceException ex) {
            logger.error(ex);
        }
        try {
            localized = resource.getObject(key);
        } catch (MissingResourceException ex) {
            logger.error(ex);
        }
        return localized;
    }    
}