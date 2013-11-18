/*
 * ArrayUtils.java
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

/**
 * @author giovanni.remigi
 * @version $Revision: 9 $
 */
public class ArrayUtility {

    /** Private constructor. */
    private ArrayUtility() {
        
    }
    
    public static int[] remove(int[] array, int x, int y) {
        
        if ((x < 0) || (y >= array.length)) {
            throw new IndexOutOfBoundsException();
        }
        
        int intervalSize = y - x + 1;
        int[] newArray = new int[array.length - intervalSize];
        
        if (x > 0) {
            System.arraycopy(array, 0, newArray, 0, x - 1);
        }
        if (y < (array.length - 1)) {
            System.arraycopy(array, y + 1, newArray, 0, array.length - 1);
        }
        
        return newArray;
    }
    
    public static int[] add(int[] array, int index, int[] elements) {
        
        int[] newArray = new int[array.length + elements.length];
        
        return newArray;
    }
}