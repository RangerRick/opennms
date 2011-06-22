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

package org.opennms.web.svclayer;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennms.netmgt.dao.db.JUnitConfigurationEnvironment;
import org.opennms.netmgt.dao.db.JUnitTemporaryDatabase;
import org.opennms.netmgt.dao.db.OpenNMSJUnit4ClassRunner;
import org.opennms.netmgt.model.DemandPoll;
import org.opennms.web.services.PollerService;
import org.opennms.web.svclayer.support.DefaultDemandPollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(OpenNMSJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:META-INF/opennms/applicationContext-dao.xml",
        "classpath*:/META-INF/opennms/component-dao.xml",
        "classpath*:/META-INF/opennms/component-service.xml",
        "classpath:org/opennms/web/svclayer/applicationContext-svclayer.xml",
        "classpath*:/META-INF/opennms/applicationContext-reportingCore.xml",
        "classpath:/META-INF/opennms/applicationContext-insertData-enabled.xml"
})
@JUnitConfigurationEnvironment
@JUnitTemporaryDatabase
public class DefaultPollServiceIntegrationTest {

    @Autowired
    private DemandPollService m_demandPollService;

    /**
     * this is a feature that has not been written yet
     */
    @Test
    @Ignore
    public void testPollMonitoredService() {
        PollerService api = createMock(PollerService.class);
        ((DefaultDemandPollService)m_demandPollService).setPollerAPI(api);

        DemandPoll poll = m_demandPollService.pollMonitoredService(1, "192.168.2.100", 1, 1);
        assertNotNull("DemandPoll should not be null", poll);
        assertTrue("Polled service addr doesn't match...", poll.getId() >= 1);
    }
}
