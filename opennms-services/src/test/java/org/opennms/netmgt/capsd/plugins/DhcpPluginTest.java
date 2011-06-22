/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2008-2011 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.capsd.plugins;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opennms.core.utils.InetAddressUtils;
import org.opennms.netmgt.dhcpd.Dhcpd;


/**
 * @author thedesloge
 *
 */
public class DhcpPluginTest {
    
    private Dhcpd m_dhcpd;
    
    @Before
    public void setup() throws Exception{
        m_dhcpd = Dhcpd.getInstance();
        m_dhcpd.init();
        m_dhcpd.start();
        
    }
    
    @After
    public void tearDown(){
        m_dhcpd.stop();
    }
    
    @Ignore
    @Test
    public void testPlugin() throws MarshalException, ValidationException, IOException {
        DhcpPlugin plugin = new DhcpPlugin();
        assertTrue(plugin.isProtocolSupported(InetAddressUtils.addr("172.20.1.1")));
    }
    
}
