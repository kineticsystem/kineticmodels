/*
 * ResourceLoader.java
 *
 * Created on April 29, 2002, 11:41 AM
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javax.swing.ImageIcon;

// Apache commons classes.

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is an utility class used to load application resources.
 * @author Remigi Giovanni
 * @version $Revision: 36 $
 */
public class ResourceLoader {
    
    /** Logging framework. */
    private static Log logger = LogFactory.getLog(ResourceLoader.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Return the url of the given resource.
     * @param path The resource path i.e
     *     <tt>org/kineticsystem/commons/mymodule/bundle/test.txt</tt>.
     * @return The resource url.
     */
    public static URL getResource(String path) {
        return ClassLoader.getSystemClassLoader().getResource(path);
    }
    
    /**
     * Return the string representing the content of a resource file specified
     * by the given path
     * @param path The resource path i.e
     *     <tt>org/kineticsystem/commons/mymodule/bundle/test.txt</tt>.
     * @return The resource file content.
     */
    public static String getStreamContent(String path) {  
        
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream inStream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(inStream);
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
        } catch (IOException ex) {
            logger.fatal(ex);
        }
        return buffer.toString();
    } 
    
    /** 
     * Return a resource as an input stream.
     * @param path The resource path i.e
     *     <tt>org/kineticsystem/commons/mymodule/bundle/test.txt</tt>.
     * @return The input stream representing the given resource.
     */
    public static InputStream getStream(String path) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }
    
    /** 
     * Return an imaged stored in the given resource.
     * @param path The resource path i.e
     *     <tt>org/kineticsystem/commons/mymodule/bundle/test.txt</tt>.
     * @return The image stored in the resource.
     */
    public static ImageIcon getIcon(String path) {
        return new ImageIcon(getResource(path));
    }
}