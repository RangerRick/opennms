/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2011 The OpenNMS Group, Inc.
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

package org.opennms.features.poller.remote.gwt.client.remoteevents;


/**
 * <p>GeocodingUpdatingRemoteEvent class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public class GeocodingUpdatingRemoteEvent implements MapRemoteEvent {
	private int m_count = 0;
	private int m_size = 0;

	/**
	 * <p>Constructor for GeocodingUpdatingRemoteEvent.</p>
	 */
	public GeocodingUpdatingRemoteEvent() {}

	/**
	 * <p>Constructor for GeocodingUpdatingRemoteEvent.</p>
	 *
	 * @param count a int.
	 * @param size a int.
	 */
	public GeocodingUpdatingRemoteEvent(final int count, final int size) {
		m_count = count;
		m_size = size;
	}

	/** {@inheritDoc} */
	public void dispatch(final MapRemoteEventHandler presenter) {
//		Window.alert("updating geocoding: " + m_count + "/" + m_size);
	}

	/**
	 * <p>toString</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
	    return "GeocodingUpdatingRemoteEvent[count=" + m_count + ",size=" + m_size + "]";
	}
}
