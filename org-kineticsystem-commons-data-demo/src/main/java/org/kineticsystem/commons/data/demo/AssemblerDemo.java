/*
 * AssemblerDemo.java
 *
 * Created on 6 February 2006, 22.02
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

package org.kineticsystem.commons.data.demo;

// Java classes.

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

// Application classes.

import org.kineticsystem.commons.layout.*;
import org.kineticsystem.commons.data.model.*;

/**
 *
 * @author Giovanni Remigi
 * $Revision: 8 $
 */
public class AssemblerDemo extends JFrame implements ActionListener {
    
    /** Serial version number. */
    private static final long serialVersionUID = 1L;
    
    private static final int INITIAL_LIST_LENGTH = 10;
    
    private DefaultListModel model1;
    private DefaultListModel events;
    
    private JList list1;
    private JList list2;
    private JTextField xField;
    private JTextField yField;
    private JButton changeButton;
    private JButton resetButton;
    private JButton fireButton;
    
    private DefaultAssembler assembler;
    
    private int counter;
    
    private String command = "ADD";
    
    /** Listener list. */
    private EventListenerList listeners;
    
    /** Creates a new instance of AssemblerDemo */
    public AssemblerDemo() {
        
        listeners = new EventListenerList();
        
        counter = 0;
        assembler = new DefaultAssembler();
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        TetrisLayout layout = new TetrisLayout(4, 2);
        layout.setRowWeight(0, 0);
        layout.setRowWeight(1, 1);
        layout.setRowWeight(2, 0);
        layout.setRowWeight(3, 1);
        panel.setLayout(layout);
        
        Cell cell = new Cell();
        
        list1 = new JList();
        list2 = new JList();
        JList list3 = new JList();
        
        JScrollPane scroll1 = new JScrollPane(list1);
        JScrollPane scroll2 = new JScrollPane(list2);
        JScrollPane scroll3 = new JScrollPane(list3);
        
        panel.add(new JLabel("Synchronous list"), cell);
        panel.add(new JLabel("Asynchronous list"), cell);
        panel.add(scroll1, cell);
        panel.add(scroll2, cell);
        
        TetrisLayout buttonLayout = new TetrisLayout(6, 3);
        
        buttonLayout.setRowWeight(0, 0);
        buttonLayout.setRowWeight(1, 0);
        buttonLayout.setRowWeight(2, 0);
        buttonLayout.setRowWeight(3, 1);
        buttonLayout.setRowWeight(4, 0);
        buttonLayout.setRowWeight(5, 0);
        
        buttonLayout.setColWeight(0, 1);
        buttonLayout.setColWeight(1, 0);
        buttonLayout.setColWeight(2, 1);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(buttonLayout);
        
        JRadioButton addButton = new JRadioButton("ADD");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                command = "ADD";
            }
        });
        addButton.setSelected(true);
        JRadioButton delButton = new JRadioButton("DEL");
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                command = "DEL";
            }
        });
        JRadioButton modButton = new JRadioButton("MOD");
        modButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                command = "MOD";
            }
        });
        
        xField = new JTextField();
        xField.setText("0");
        yField = new JTextField();
        yField.setText("0");
        changeButton = new JButton("Apply change");
        resetButton = new JButton("Reset");
        
        ButtonGroup group = new ButtonGroup();
        group.add(addButton);
        group.add(delButton);
        group.add(modButton);

        changeButton.addActionListener(this);
        resetButton.addActionListener(this);
        
        Cell buttonCell = new Cell();
        
        buttonPane.add(addButton, buttonCell);
        buttonPane.add(new JLabel("x:"), buttonCell);
        buttonPane.add(xField, buttonCell);
        buttonPane.add(delButton, buttonCell);
        buttonPane.add(new JLabel("y:"), buttonCell);
        buttonPane.add(yField, buttonCell);
        buttonPane.add(modButton, buttonCell);
        buttonPane.add(new JPanel(), buttonCell);
        buttonPane.add(new JPanel(), buttonCell);
        
        buttonCell.setCols(3);
        buttonPane.add(new JPanel(), buttonCell);
        buttonPane.add(changeButton, buttonCell);
        buttonPane.add(resetButton, buttonCell);
        
        panel.add(new JLabel("Console"));
        panel.add(new JLabel("Event list"));
        
        panel.add(buttonPane, cell);
        
        TetrisLayout eventLayout = new TetrisLayout(2, 1);
        eventLayout.setRowWeight(0, 1);
        eventLayout.setRowWeight(1, 0);
        
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(eventLayout);
        
        fireButton = new JButton("Fire");
        fireButton.addActionListener(this);
        
        Cell eventCell = new Cell();
        eventPanel.add(scroll3, eventCell);
        eventPanel.add(fireButton, eventCell);

        panel.add(eventPanel, cell);
        
        setContentPane(panel);
        
        model1 = new DefaultListModel();
        list2.setModel(model1);
        
        ListDataListener listener1 = model1.getListDataListeners()[0];
        model1.removeListDataListener(listener1);
        listeners.add(ListDataListener.class, listener1);

        list1.setModel(model1);
        
        for (int i = 0; i < INITIAL_LIST_LENGTH; i++) {
            model1.addElement(new Integer(counter++));
        }
        
        events = new DefaultListModel();
        list3.setModel(events);
    }
    
    public void actionPerformed(ActionEvent event) {
        
        LinkedList<ActiveListEvent> internalEvents
            = new LinkedList<ActiveListEvent>();
        
        // Accessing private variables of DefaultAssembler by reflection.
        
        try {
            Field fields[] = DefaultAssembler.class.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                if ("events".equals(fields[i].getName())) {
                    fields[i].setAccessible(true);
                    java.util.List tmp = (java.util.List) fields[i]
                        .get(assembler);
                    for (Iterator iter = tmp.iterator(); iter.hasNext();) {
                        internalEvents.add((ActiveListEvent) iter.next());
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Object source = event.getSource();
        if (source == resetButton) {
            events.clear();
            internalEvents.clear();
            model1.clear();
            counter = 0;
            for (int i = 0; i < INITIAL_LIST_LENGTH; i++) {
                model1.addElement(new Integer(counter++));
            }
            
            return;
        }
        
        if (source == fireButton) {
            ActiveListEvent change = internalEvents.remove();
            if (change == null) {
                return;
            }
            
            int type = 0;
            switch (change.getType()) {
                case ListDataEvent.CONTENTS_CHANGED: {
                    
                    type = ActiveListEvent.CONTENTS_CHANGED;
                    ListDataEvent evt = new ListDataEvent(change.getSource(),
                        type, change.getX(), change.getY());
                    
                    Object[] list = listeners.getListenerList();
                    for (int i = list.length - 2; i >= 0; i -=2) {
                        if (list[i] == ListDataListener.class) {
                            ((ListDataListener) list[i+1]).contentsChanged(evt);
                        }
                    }
                    
                    break;
                }
                case ListDataEvent.INTERVAL_ADDED: {
                    
                    type = ActiveListEvent.INTERVAL_ADDED;
                    
                    ListDataEvent evt = new ListDataEvent(change.getSource(),
                        type, change.getX(), change.getY());
                    
                    Object[] list = listeners.getListenerList();
                    for (int i = list.length - 2; i >= 0; i -=2) {
                        if (list[i] == ListDataListener.class) {
                            ((ListDataListener) list[i+1]).intervalAdded(evt);
                        }
                    }
                    
                    break;
                }
                case ListDataEvent.INTERVAL_REMOVED: {
                    
                    type = ActiveListEvent.INTERVAL_REMOVED;
                    
                    ListDataEvent evt = new ListDataEvent(change.getSource(),
                        type, change.getX(), change.getY());
                    
                    Object[] list = listeners.getListenerList();
                    for (int i = list.length - 2; i >= 0; i -=2) {
                        if (list[i] == ListDataListener.class) {
                            ((ListDataListener) list[i+1]).intervalRemoved(evt);
                        }
                    }
                    
                    break;
                }
            }
            
            events.clear();
            for (ActiveListEvent e : internalEvents) {
                events.addElement(e);
            }
            return;
        }
        
        int index1 = Integer.parseInt(xField.getText());
        int index2 = Integer.parseInt(yField.getText());
        
        ActiveListEvent ale = new ActiveListEvent(this);
        ale.setX(index1);
        ale.setY(index2);
        
        if (source == changeButton) {
            
            if (command.equals("ADD")) {
                
                ale.setType(ActiveListEvent.INTERVAL_ADDED);
                System.out.println("model1.size: " + model1.size());
                for (int i = index1; i <= index2; i++) {
                    model1.add(i, new Integer(counter++));
                }
            
            } else if (command.equals("DEL")) {
                
                ale.setType(ActiveListEvent.INTERVAL_REMOVED);
                System.out.println("model1.size: " + model1.size());
                for (int i = index1; i <= index2; i++) {
                    model1.remove(index1);
                }
            
            } else if (command.equals("MOD")) {
            
                ale.setType(ActiveListEvent.CONTENTS_CHANGED);
                for (int i = index1; i <= index2; i++) {
                    model1.set(i, new Integer(counter++));
                }
            }
        }
        
        assembler.push(ale);
        
        events.clear();
        for (ActiveListEvent e : internalEvents) {
            events.addElement(e);
        }
    }
    
    public static void main(String[] args) {
        
        JFrame dialog = new AssemblerDemo();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = dialog.getPreferredSize();
        dialog.setLocation(screenSize.width/2 - (size.width/2),
            screenSize.height/2 - (size.height/2));
        
        dialog.setResizable(false);
        dialog.setVisible(true);
        dialog.setTitle("Assembler demo");
    }
}
