/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2007-2011 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.statsd;

import java.util.Date;
import java.util.SortedSet;

import org.opennms.netmgt.model.AttributeStatistic;

/**
 * <p>StandardOutputReportPersister class.</p>
 *
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 * @version $Id: $
 */
public class StandardOutputReportPersister implements ReportPersister {
    /** {@inheritDoc} */
    public void persist(ReportInstance report) {
        System.out.println("Top " + report.getCount() + " " + report.getAttributeMatch() + " data sources on resources of type " + report.getResourceTypeMatch() + " from " + new Date(report.getStartTime()) + " to " + new Date(report.getEndTime()));
        SortedSet<AttributeStatistic> top = report.getResults();
        for (AttributeStatistic stat : top) {
            System.out.println(stat.getAttribute().getResource().getId() + "/" + stat.getAttribute().getName() + ": " + stat.getStatistic());
        }
        System.out.println("");
    }
}
