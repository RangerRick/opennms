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

package org.opennms.netmgt.poller.remote;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;


/**
 * DefaultTimeAdjustmentTest
 *
 * @author brozow
 */
public class DefaultTimeAdjustmentTest {
    private static final long m_jitter = 2;
    
    @Test
    public void testNoTimeDifference() {
       
        TimeAdjustment timeAdjustment = new DefaultTimeAdjustment();
        
        long time = System.currentTimeMillis();
        
        long adjusted = timeAdjustment.adjustTimeToMasterTime(time);
        
        assertEqualsWithin(time, adjusted, m_jitter);
    }
    
    @Test
    public void testServerTimeMatches() {
        
        TimeAdjustment timeAdjustment = new DefaultTimeAdjustment();
        
        timeAdjustment.setMasterTime(System.currentTimeMillis());
        
        long time = System.currentTimeMillis();
        
        long adjusted = timeAdjustment.adjustTimeToMasterTime(time);
        
        assertEqualsWithin(time, adjusted, m_jitter);

    }

    @Test
    public void testServerBehind() {
        
        TimeAdjustment timeAdjustment = new DefaultTimeAdjustment();
        
        timeAdjustment.setMasterTime(System.currentTimeMillis()-60000);
        
        long time = System.currentTimeMillis();
        
        long adjusted = timeAdjustment.adjustTimeToMasterTime(time);
        
        assertEqualsWithin(time-60000, adjusted, m_jitter);

    }

    @Test
    public void testServerAhead() {
        
        TimeAdjustment timeAdjustment = new DefaultTimeAdjustment();
        
        timeAdjustment.setMasterTime(System.currentTimeMillis()+60000);
        
        long time = System.currentTimeMillis();
        
        long adjusted = timeAdjustment.adjustTimeToMasterTime(time);
        
        assertEqualsWithin(time+60000, adjusted, m_jitter);

    }

    @Test
    public void testAssertEqualsWithin() {
        assertEqualsWithin(5, 7, 2);
        assertEqualsWithin(7, 5, 2);
        
        try {
            assertEqualsWithin(5, 8, 2);
        } catch (AssertionError e) {
            assertTrue(e != null);
        }
        
        try {
            assertEqualsWithin(8, 5, 2);
        } catch (AssertionError e) {
            assertTrue(e != null);
        }
    }

    private void assertEqualsWithin(final long a, final long b, final long distance) {
        boolean fail = false;
        if (a + distance < b) {
            fail = true;
        } else if (b + distance < a) {
            fail = true;
        }
        if (fail) {
            fail(String.format("%d and %d were not within %d of each other", a, b, distance));
        }
    }

}
