/**
 * *****************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2011 The OpenNMS Group, Inc. OpenNMS(R) is Copyright (C)
 * 1999-2011 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OpenNMS(R). If not, see: http://www.gnu.org/licenses/
 *
 * For more information contact: OpenNMS(R) Licensing <license@opennms.org>
 * http://www.opennms.org/ http://www.opennms.com/
 ******************************************************************************
 */
package org.opennms.netmgt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennms.core.test.OpenNMSJUnit4ClassRunner;
import org.opennms.core.utils.BeanUtils;
import org.opennms.netmgt.dao.db.JUnitConfigurationEnvironment;
import org.opennms.netmgt.dao.db.JUnitTemporaryDatabase;
import org.opennms.netmgt.model.*;
import org.opennms.test.ThrowableAnticipator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@RunWith(OpenNMSJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:/META-INF/opennms/applicationContext-dao.xml",
    "classpath:/META-INF/opennms/applicationContext-databasePopulator.xml",
    "classpath:/META-INF/opennms/applicationContext-setupIpLike-enabled.xml",
    "classpath*:/META-INF/opennms/component-dao.xml"
})
@JUnitConfigurationEnvironment
@JUnitTemporaryDatabase(dirtiesContext = false)
public class MemoDaoTest implements InitializingBean {

    @Autowired
    private DistPollerDao m_distPollerDao;

    @Autowired
    private EventDao m_eventDao;

    @Autowired
    private NodeDao m_nodeDao;

    @Autowired
    private AlarmDao m_alarmDao;

//    @Autowired
//    private MemoDao m_memoDao;

    @Autowired
    private DatabasePopulator m_databasePopulator;

    private static boolean m_populated = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanUtils.assertAutowiring(this);
    }

    @BeforeTransaction
    public void setUp() {
        try {
            if (!m_populated) {
                m_databasePopulator.populateDatabase();
            }
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        } finally {
            m_populated = true;
        }
    }

    @Test
    @Transactional
    public void testAddMemoToExistingAlarm() {
        OnmsEvent event = new OnmsEvent();
        event.setEventLog("Y");
        event.setEventDisplay("Y");
        event.setEventCreateTime(new Date());
        event.setDistPoller(m_distPollerDao.load("localhost"));
        event.setEventTime(new Date());
        event.setEventSeverity(new Integer(7));
        event.setEventUei("uei://org/opennms/test/EventDaoTest");
        event.setEventSource("test");
        m_eventDao.save(event);

        OnmsNode node = m_nodeDao.findAll().iterator().next();

        OnmsAlarm alarm = new OnmsAlarm();

        alarm.setNode(node);
        alarm.setUei(event.getEventUei());
        alarm.setSeverityId(event.getEventSeverity());
        alarm.setFirstEventTime(event.getEventTime());
        alarm.setLastEvent(event);
        alarm.setCounter(1);
        alarm.setDistPoller(m_distPollerDao.load("localhost"));

        m_alarmDao.save(alarm);
        // It works we're so smart! hehe

        OnmsAlarm newAlarm = m_alarmDao.load(alarm.getId());
        assertEquals("uei://org/opennms/test/EventDaoTest", newAlarm.getUei());
        assertEquals(alarm.getLastEvent().getId(), newAlarm.getLastEvent().getId());
        
        
        final OnmsMemo memo = new OnmsMemo();
        memo.setBody("blabody");
        memo.setAuthor("tak");
        newAlarm.setStickyMemo(memo);
        
        m_alarmDao.update(alarm);
        
        assertNotNull(alarm.getStickyMemo().getId());
        assertNotNull(alarm.getStickyMemo().getUpdated());
        assertNotNull(alarm.getStickyMemo().getCreated());
        assertNotNull(alarm.getStickyMemo().getAuthor());
        assertNotNull(alarm.getStickyMemo().getBody());
    }

}