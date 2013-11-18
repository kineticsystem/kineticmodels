/*
 * TableModelAdapter.java
 *
 * Created on 2 March 2006, 21.38
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

package org.kineticsystem.commons.data.model.swing.adapters;

// Java classes.

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

// Application classes.

import org.kineticsystem.commons.data.model.ActiveList;

/**
 * This object is used to adapt the <tt>TableModel</tt> interface to the
 * <tt>DataList</tt> interface so that it can be used by a <tt>JTable</tt>
 * component.
 * @author Giovanni Remigi
 * @version $Revision: 145 $
 */
public class TableModelAdapter implements TableModel {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private variables.
     */
    
    /** The adaptee. */
    protected ActiveList<?> adaptee;
    
    /**
     * The object used to format data to be displayed by the table component.
     */
    protected TableStructure structure;
    
    /**
     * Component used to transform <tt>ActiveListEvent</tt> events to
     * <tt>TableModelEvent</tt> events. It receives events from the
     * <tt>DataList</tt> adaptee, transforms and sends them to all registered
     * <tt>TableModelListener</tt> listeners.
     */
    private TableModelListenerDispatcher dispatcher;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors and initializing methods.
     */
    
    /** Default constructor. */
    public TableModelAdapter(ActiveList<?> adaptee) {
        this.adaptee = adaptee;
        this.structure = null;
        init();
    }
    
    /** Default constructor. */
    public TableModelAdapter(ActiveList<?> adaptee, TableStructure structure) {
        this.adaptee = adaptee;
        this.structure = structure;
        init();
    }
    
    /** Initializing method. */
    private void init() {
        if (adaptee == null) {
            throw new NullPointerException("Adaptee cannot be null!");
        }
        dispatcher = new TableModelListenerDispatcher(this);
        this.adaptee.addActiveListListener(dispatcher);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * AbstractTableModel abstract methods implementation.
     */
    
    /** {@inheritDoc} */
    public void addTableModelListener(TableModelListener listener) {
        dispatcher.addTableModelListener(listener);
    }
    
    /** {@inheritDoc} */
    public void removeTableModelListener(TableModelListener listener) {
        dispatcher.removeTableModelListener(listener);
    }
    
    /** {@inheritDoc} */
    public int getRowCount() {
        return adaptee.size();
    }
    
    /** {@inheritDoc} */
    public int getColumnCount() {
        if (structure != null) {
            return structure.getColumnCount();
        } else {
            return 1;
        }
    }
    
    /** {@inheritDoc} */
    public Object getValueAt(int row, int column) {
        Object obj = adaptee.get(row);
        if (structure != null) {
            return structure.getColumnValue(obj, column);
        } else {
            return obj;
        }
    }
    
    /** {@inheritDoc} */
    public void setValueAt(Object obj, int row, int column) {
        // TODO: implement this method.
    }
    
    /** {@inheritDoc} */
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    /** {@inheritDoc} */
    public Class<?> getColumnClass(int column) {
        return Object.class;
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * AbstractTableModel method overriding.
     */
    
    /** {@inheritDoc} */
    public String getColumnName(int columnIndex) {
        if (structure != null) {
            return structure.getColumnLabel(columnIndex);
        } else {
            return "";
        }
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Public methods.
     */
    
    /**
     * Return the table structure.
     * @return The table structure.
     * @see TableStructure
     */
    public TableStructure getStructure() {
        return structure;
    }
    
    /**
     * Set the table format.
     * @param structure The table structure.
     * @see TableStructure
     */
    public void setStructure(TableStructure structure) {
        
        this.structure = structure;
        dispatcher.fireTableChanged(new TableModelEvent(this,
            TableModelEvent.HEADER_ROW));
    }
}