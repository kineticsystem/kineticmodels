/*
 * DefaultAssembler.java
 *
 * Created on 28 January 2006, 15.34
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

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This object is used to asynchronously collect events from an
 * <tt>ActiveList</tt>, pack and merge them in the right way and synchronously
 * perform all updates in one step when requested by the Swing Event-dispatching
 * Thread. This is the core of the KineticModels framework.
 * @author Giovanni Remigi
 * @version $Revision: 36 $
 */
public class DefaultAssembler implements ActiveListEventAssembler {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The list of events received from the list model. */
    private LinkedList<ActiveListEvent> events;
    
    /** List buffer used to store temporary events. */
    private List<ActiveListEvent> listBuffer;
    
    /**
     * When two events are merged together, this variable is set to the length
     * of the resulted adding event (equals to 0 if none).
     */
    private int adds;
    
    /**
     * When two events are merged together, this variable is set to the length
     * of the resulted deleting event (equals to 0 if none).
     */
    private int dels;
    
    /**
     * When two events are merged together, this variable is set to the length
     * of the resulted modifying event (equals to 0 if none).
     */
    private int mods;
    
    /**
     * Iterator used to iterate the event list and inserting new event when
     * necessary.
     */
    private ListIterator<ActiveListEvent> iter;
            
    /** Temporary variable representing and old event in the events list. */
    private ActiveListEvent oe;
    
    /** Temporary variable representing the new inserted event. */
    private ActiveListEvent ne;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /** Default constructor. */
    public DefaultAssembler() {
        events = new LinkedList<ActiveListEvent>();
        listBuffer = new LinkedList<ActiveListEvent>();
        adds = 0;
        dels = 0;
        mods = 0;
        iter = null;
        oe = null;
        ne = null;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * ActiveListEventAssembler interface implementation.
     */
    
    /**
     * Return false is the assembler doesn't contain any event, false otherwise.
     * @return False is the assembler doesn't contain any event, false
     *     otherwise.
     */
    public boolean isEmpty() {
        return events.isEmpty();
    }
    
    /**
     * Get the first event of the list.
     * @return The first event of the list (if any).
     */
    public ActiveListEvent pop() {
        ActiveListEvent event = null;
        if (!events.isEmpty()) {
            event = events.remove();
        }
        return event;
    }
    
    /**
     * Add the given event to the list.
     * @param event The event to be packed inside the list.
     * @throws IllegalArgumentException if event x coordinate is greater than
     *     event y coordinate.
     */
    public void push(ActiveListEvent event) {
        
        if (event.getX() > event.getY()) {
            throw new IllegalArgumentException("Cannot accept given event: "
                + "x coordinate must be less than or equal to y coordinate!");
        }

        ne = event;
        iter = events.listIterator(events.size());

        // Propagate the new event throw the event list.

        while (iter.hasPrevious() && (ne != null)) {

            oe = iter.previous();

            /*
             * Evaluate common variables: see external documentation on
             * DefaultAssembler.
             */

            int oldChangeX = oe.getX();
            int oldChangeY = oe.getY();

            int newChangeX = ne.getX();
            int newChangeY = ne.getY();

            int changesMinX = Math.min(oldChangeX, newChangeX);
            int changesMaxY = Math.max(oldChangeY, newChangeY);

            int changesRShift = newChangeY - oldChangeY;
            int changesLShift = newChangeX - oldChangeX;

            int changesRDist = newChangeX - oldChangeY - 1;
            int changesLDist = oldChangeX - newChangeY - 1;

            int oldChangeLength = oldChangeY - oldChangeX + 1;
            int newChangeLength = newChangeY - newChangeX + 1;

            int changesSpan = changesMaxY - changesMinX + 1;
            int changesOverlapt = changesSpan - Math.abs(changesRShift)
                - Math.abs(changesLShift);

            boolean overlapped = (changesLDist < 0) && (changesRDist < 0);
            boolean adjacent = (changesLDist == 0) || (changesRDist == 0);

            // Check all possible cases.

            if ((ne.getType() == ActiveListEvent.INTERVAL_ADDED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_ADDED)) {

                // ADD method after ADD method.

                 if (changesRDist > 0) {
                    insert();
                } else if (changesLShift < 0) {
                    translate(newChangeLength);
                } else {
                     
                    adds = oldChangeLength + newChangeLength;
                    dels = 0;
                    mods = 0;
                    
                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.INTERVAL_ADDED)
                    && (oe.getType() == ActiveListEvent.CONTENTS_CHANGED)) {

                // ADD method after MOD method.

                if (changesRDist > 0) {
                    insert();
                } else if (changesLShift < 0) {
                    translate(newChangeLength);
                } else {
                    
                    adds = newChangeLength;
                    mods = oldChangeLength;
                    dels = 0;

                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.INTERVAL_ADDED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_REMOVED)) {

                // ADD method after DEL method.  

                if (changesLShift > 0) {
                    insert();
                } else if (changesLShift < 0) {
                    translate(newChangeLength);
                } else {
                    
                    adds = Math.max(newChangeLength - oldChangeLength, 0);
                    dels = Math.max(oldChangeLength - newChangeLength, 0);
                    mods = changesOverlapt;

                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.CONTENTS_CHANGED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_ADDED)) {

                // MOD method after ADD method.

                if (changesRDist >= 0) {
                    insert();
                } else if (adjacent || overlapped) {

                    adds = oldChangeLength;
                    dels = 0;
                    mods = newChangeLength - changesOverlapt;
                    
                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.CONTENTS_CHANGED)
                    && (oe.getType() == ActiveListEvent.CONTENTS_CHANGED)) {

                // MOD method after MOD method.

                if (changesRDist > 0) {
                    insert();
                } else if (adjacent || overlapped) {

                    adds = 0;
                    dels = 0;
                    mods = changesMaxY - changesMinX + 1;
                    
                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.CONTENTS_CHANGED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_REMOVED)) {

                // MOD method after DEL method.

                if (changesLShift >= 0) {
                    insert();
                } else if ((changesLDist <= 0) &&  (changesLShift < 0)) {

                    adds = 0;
                    dels = oldChangeLength;
                    mods = newChangeLength;

                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.INTERVAL_REMOVED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_ADDED)) {

                // DEL method after ADD method.

                if (changesRDist > 0) {
                    insert();
                } else if (changesLDist > 0) {
                    translate(-newChangeLength);
                } else {

                    adds = Math.max(oldChangeLength - newChangeLength, 0);
                    dels = Math.max(newChangeLength - oldChangeLength, 0);
                    mods = oldChangeLength - adds - changesOverlapt;

                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.INTERVAL_REMOVED)
                    && (oe.getType() == ActiveListEvent.CONTENTS_CHANGED)) {

                // DEL method after MOD method.

                if (changesRDist > 0) {
                    insert();
                } else if (changesLDist > 0) {
                    translate(-newChangeLength);
                } else {

                    adds = 0;
                    dels = newChangeLength;
                    mods = oldChangeLength - changesOverlapt;

                    merge(changesMinX);
                }

            } else if ((ne.getType() == ActiveListEvent.INTERVAL_REMOVED)
                    && (oe.getType() == ActiveListEvent.INTERVAL_REMOVED)) {

                // DEL method after DEL method.

                if (changesLShift > 0) {
                    insert();
                } else if (changesLDist > 0) {
                    translate(-newChangeLength);
                } else {

                    adds = 0;
                    dels = oldChangeLength + newChangeLength;
                    mods = 0;
                    
                    merge(changesMinX);
                }
            }
        }

        // Insert the given object.

        if (ne != null) {
            events.addFirst(ne);
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private methods.
     */
    
    /** Insert the new event into the events list. */
    private void insert() {
        iter.next();
        iter.add(ne);
        ne = null;
    }
    
    /**
     * Translate the current list event by the given length.
     * @param translation The translation length.
     */
    private void translate(int translation) {
        oe.setX(oe.getX() + translation);
        oe.setY(oe.getY() + translation);
    }
    
    /**
     * Merge the new event and the current list event.
     * @param start 
     */
    private void merge(int start) {
        
        if (adds > 0) {
            ActiveListEvent e = new ActiveListEvent(ne.getSource());
            e.setX(start);
            e.setY(start + adds - 1);
            e.setType(ActiveListEvent.INTERVAL_ADDED);
            listBuffer.add(e);
        }
        if (dels > 0) {
            ActiveListEvent e = new ActiveListEvent(ne.getSource());
            e.setX(start);
            e.setY(start + dels - 1);
            e.setType(ActiveListEvent.INTERVAL_REMOVED);
            listBuffer.add(e);
        }
        if (mods > 0) {
            ActiveListEvent e = new ActiveListEvent(ne.getSource());
            e.setX(start + adds);
            e.setY(start + adds + mods - 1);
            e.setType(ActiveListEvent.CONTENTS_CHANGED);
            listBuffer.add(e);
        }

        if (listBuffer.isEmpty()) { // No events.
            ne = null;
            iter.remove();
        } else if (listBuffer.size() == 1) { // DEL, ADD or MOD event.
            ne = listBuffer.remove(0);
            iter.remove();      
        } else if (listBuffer.size() == 2) { // MOD event.
            ne = listBuffer.remove(0);
            iter.set(listBuffer.remove(0));
        }
    }
}