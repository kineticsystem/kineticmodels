/*
 * BeanRegExpFilter.java
 *
 * Created on 15 April 2006, 23.41
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

package org.kineticsystem.commons.data.model.bean;

// Java classes.

import java.util.regex.Pattern;

// Apache commons libraries.

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Application classes.

import org.kineticsystem.commons.data.model.mapping.Filter;

/**
 *
 * @author Giovanni Remigi
 * @version $Revision: 147 $
 */
public class BeanRegExpFilter implements Filter<Object> {
    
    /* /////////////////////////////////////////////////////////////////////////
     * Log framework.
     */
    
    /** Apache log framework. */
    private static Log logger = LogFactory.getLog(BeanRegExpFilter.class);
    
    /* /////////////////////////////////////////////////////////////////////////
     * Private properties.
     */
    
    /** Bean property to be used by the comparator. */
    private String[] propertyNames;

    /**
     * The pattern to be matched by the regular expression predicate.
     * @see java.util.regex.Pattern
     */
    private Pattern pattern;
    
    /* /////////////////////////////////////////////////////////////////////////
     * Constructors.
     */
    
    /**
     * Constructor.
     * @param propertyName The object property to be check with the regular
     *     expression.
     * @param regExp The regular expression.
     * @throws PatternSyntaxException
     */
    public BeanRegExpFilter(String propertyName, String regExp) {
        this.propertyNames = new String[] {propertyName};
        this.pattern = Pattern.compile(regExp);
    }
    
    /**
     * Constructor.
     * @param propertyNames The array of object properties to be check with the 
     *     regular expression. In at least one property value satisfies the
     *     regular expression, the whole object is accepted.
     * @param regExp The regular expression.
     * @throws PatternSyntaxException
     */
    public BeanRegExpFilter(String[] propertyNames, String regExp) {
        this.propertyNames = propertyNames;
        this.pattern = Pattern.compile(regExp);
    }
    
    /* /////////////////////////////////////////////////////////////////////////
     * Filter interface implementation.
     */
    
    /** {@inheritDoc} */
    public boolean evaluate(Object obj) {
        
        boolean result = false;
        try {
            for (int i = 0; i < propertyNames.length; i++) {
                Object propertyValue = PropertyUtils.getProperty(obj, 
                    propertyNames[i]);
                if (propertyValue instanceof String) {
                    String value = (String) propertyValue;
                    result = pattern.matcher(value).lookingAt();
                    if (result) {
                        continue;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
        return result;
    }
}
