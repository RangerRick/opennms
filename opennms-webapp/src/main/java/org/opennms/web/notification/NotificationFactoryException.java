/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2011 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.web.notification;

/**
 * This exception is used to indicate that the NotificationFactory had a problem
 *
 * @author <A HREF="mailto:jason@opennms.org">Jason Johns </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * @author <A HREF="mailto:jason@opennms.org">Jason Johns </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * @version 1.1.1.1
 * @since 1.8.1
 */
public class NotificationFactoryException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 2374905955140803820L;
    private Throwable rootCause;

    /**
     * <p>Constructor for NotificationFactoryException.</p>
     */
    public NotificationFactoryException() {
        super();
    }

    /**
     * <p>Constructor for NotificationFactoryException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public NotificationFactoryException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for NotificationFactoryException.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param rootCause a {@link java.lang.Throwable} object.
     */
    public NotificationFactoryException(String message, Throwable rootCause) {
        super(message);
        this.rootCause = rootCause;
    }

    /**
     * <p>Constructor for NotificationFactoryException.</p>
     *
     * @param rootCause a {@link java.lang.Throwable} object.
     */
    public NotificationFactoryException(Throwable rootCause) {
        super(rootCause.getLocalizedMessage());
        this.rootCause = rootCause;
    }

    /**
     * <p>Getter for the field <code>rootCause</code>.</p>
     *
     * @return a {@link java.lang.Throwable} object.
     */
    public Throwable getRootCause() {
        return rootCause;
    }
}
