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

package org.opennms.netmgt.scriptd.jmx;

/**
 * <p>Scriptd class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public class Scriptd implements ScriptdMBean {
    /**
     * <p>init</p>
     */
    public void init() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.init();
    }

    /**
     * <p>start</p>
     */
    public void start() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.start();
    }

    /**
     * <p>stop</p>
     */
    public void stop() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        scriptd.stop();
    }

    /**
     * <p>getStatus</p>
     *
     * @return a int.
     */
    public int getStatus() {
        org.opennms.netmgt.scriptd.Scriptd scriptd = org.opennms.netmgt.scriptd.Scriptd.getInstance();
        return scriptd.getStatus();
    }

    /**
     * <p>status</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String status() {
        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }

    /**
     * <p>getStatusText</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStatusText() {
        return org.opennms.core.fiber.Fiber.STATUS_NAMES[getStatus()];
    }
}
