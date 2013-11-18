/*
 * JarLoader.java
 *
 * Created on June 18, 2003, 7:20 PM
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

// Classi Java.

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Load the content of all jar files at the specified directory. After that it
 * is possible to instantiate an embedded class using the <tt>loadClass</tt>
 * method.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public class JarLoader {
    
    /** The class loader. */
    private static URLClassLoader loader;    
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Ritorna l'istanza della classe utilizzata per caricare i file jar
     * presenti nella directory specificata con il metodo
     * <code>initialize</code>.
     */
    public static final ClassLoader getInstance() {
        return loader;
    }
    
    /** 
     * Inizializzazione della classe.
     * @param directory La directory in cui sono presenti i file jar da
     *        caricare. Vengono caricati solo i file con estensione ".jar".
     */
    public static final void initialize(File directory) throws Exception {
        
        // Ricava l'elenco dei file presenti nella directory specificata.
        
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                String name = file.getName();
                if (name.endsWith(".jar")) {
                    return true;
                }
                return false;
            }
        };
        
        File[] files = directory.listFiles(filter);
        URL[] jars = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            jars[i] = files[i].toURL();
        }
        
        // Instantiate the class loader.
        
        loader = new URLClassLoader(jars);
    }
    
    /**
     * Return the class object of the specified class name (i.e.
     * "com.eclipsewelcome.MyClass").
     * @param className Full class name.
     * @return The class object.
     */
    public static final Class loadClass(String className)
            throws ClassNotFoundException{
        return loader.loadClass(className);
    }
}