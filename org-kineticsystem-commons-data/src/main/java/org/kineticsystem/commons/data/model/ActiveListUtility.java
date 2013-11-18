/*
 * ActiveListUtility.java
 *
 * Created on 17 April 2006, 15.21
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

package org.kineticsystem.commons.data.model;

// Java classes.

import java.util.ArrayList;
import java.util.List;

/**
 * <tt>ActiveListEvent<tt> and <tt>ActiveList<tt> related utilities.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class ActiveListUtility {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Return the difference of two <tt>ActiveList</tt> in terms of events.
     * The destination list can always be obtained by the source list using
     * at most two events: an add or remove event and a modify event.
     * @param oldSize The size of the source list.
     * @param newSize The size of the destination list.
     * @param source The <tt>ActiveList</tt> we want to use as event source.
     */
    public static List<ActiveListEvent> difference(int oldSize, int newSize,
            ActiveList<?> source) {
        
        List<ActiveListEvent> events = new ArrayList<ActiveListEvent>();
        
        int adds = Math.max(0, newSize - oldSize);
        int dels = Math.max(0, oldSize - newSize);
        int mods = Math.min(oldSize, newSize);

        if (adds > 0) {
            ActiveListEvent event = new ActiveListEvent(source);
            event.setX(0);
            event.setY(adds - 1);
            event.setType(ActiveListEvent.INTERVAL_ADDED);
            events.add(event);
        }
        if (dels > 0) {
            ActiveListEvent event = new ActiveListEvent(source);
            event.setX(0);
            event.setY(dels - 1);
            event.setType(ActiveListEvent.INTERVAL_REMOVED);
            events.add(event);
        }
        if (mods > 0) {
            ActiveListEvent event = new ActiveListEvent(source);
            event.setX(adds);
            event.setY(adds + mods - 1);
            event.setType(ActiveListEvent.CONTENTS_CHANGED);
            events.add(event);
        }
        
        return events;
    }
    
    /**
     * Transform the destination list into the source list using the given list
     * of events.
     * @param source The source list.
     * @param dest The destination list to be synchronized.
     * @param events All events necessary to transform the destination list into
     *     the source list.
     */
    public static <T> void synchronize(ActiveList<T> source, ActiveList<T> dest,
            List<ActiveListEvent> events) {
        
        for (ActiveListEvent event : events) {
            synchronizeHelper(source, dest, event);
        }
    }
    
    /**
     * Execute the given event on the destination list reading all necessary
     * information from the source list.
     * @param source The source list.
     * @param dest The destination list to be modified.
     * @param event The event to be executed on the destination list.
     */
    public static <T> void synchronize(ActiveList<T> source, ActiveList<T> dest,
            ActiveListEvent event) {
        
        synchronizeHelper(source, dest, event);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /**
     * Execute the given event on the destination list reading all necessary
     * information from the source list. This private method is used to avoid
     * fragile classes.
     * @param source The source list.
     * @param dest The destination list to be modified.
     * @param event The event to be executed on the destination list.
     */
    private static <T> void synchronizeHelper(ActiveList<T> src,
            ActiveList<T> dst, ActiveListEvent event) {
        
        int type = event.getType();
        int x = event.getX();
        int y = event.getY();
        int size = y - x + 1;
        
        switch (type) {
            case ActiveListEvent.CONTENTS_CHANGED: {
                for (int i = 0; i < size; i++) {
                    dst.set(x + i, src.get(x + i));
                }
                break;
            }
            case ActiveListEvent.INTERVAL_ADDED: {
                for (int i = 0; i < size; i++) {
                    dst.add(x, src.get(y - i));
                }
                break;
            }
            case ActiveListEvent.INTERVAL_REMOVED: {
                for (int i = 0; i < size; i++) {
                    dst.remove(x);
                }
                break;
            }
        }   
    }
}
