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

package org.opennms.netmgt.importer.specification;

import org.opennms.netmgt.config.modelimport.Asset;
import org.opennms.netmgt.config.modelimport.Category;
import org.opennms.netmgt.config.modelimport.Interface;
import org.opennms.netmgt.config.modelimport.ModelImport;
import org.opennms.netmgt.config.modelimport.MonitoredService;
import org.opennms.netmgt.config.modelimport.Node;

/**
 * <p>ImportVisitor interface.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public interface ImportVisitor {
    
    /**
     * <p>visitModelImport</p>
     *
     * @param mi a {@link org.opennms.netmgt.config.modelimport.ModelImport} object.
     */
    public void visitModelImport(ModelImport mi);
    /**
     * <p>completeModelImport</p>
     *
     * @param modelImport a {@link org.opennms.netmgt.config.modelimport.ModelImport} object.
     */
    public void completeModelImport(ModelImport modelImport);
    /**
     * <p>visitNode</p>
     *
     * @param node a {@link org.opennms.netmgt.config.modelimport.Node} object.
     */
    public void visitNode(Node node);
    /**
     * <p>completeNode</p>
     *
     * @param node a {@link org.opennms.netmgt.config.modelimport.Node} object.
     */
    public void completeNode(Node node);
    /**
     * <p>visitInterface</p>
     *
     * @param iface a {@link org.opennms.netmgt.config.modelimport.Interface} object.
     */
    public void visitInterface(Interface iface);
    /**
     * <p>completeInterface</p>
     *
     * @param iface a {@link org.opennms.netmgt.config.modelimport.Interface} object.
     */
    public void completeInterface(Interface iface);
    /**
     * <p>visitMonitoredService</p>
     *
     * @param svc a {@link org.opennms.netmgt.config.modelimport.MonitoredService} object.
     */
    public void visitMonitoredService(MonitoredService svc);
    /**
     * <p>completeMonitoredService</p>
     *
     * @param svc a {@link org.opennms.netmgt.config.modelimport.MonitoredService} object.
     */
    public void completeMonitoredService(MonitoredService svc);
    /**
     * <p>visitCategory</p>
     *
     * @param category a {@link org.opennms.netmgt.config.modelimport.Category} object.
     */
    public void visitCategory(Category category);
    /**
     * <p>completeCategory</p>
     *
     * @param category a {@link org.opennms.netmgt.config.modelimport.Category} object.
     */
    public void completeCategory(Category category);
    /**
     * <p>visitAsset</p>
     *
     * @param asset a {@link org.opennms.netmgt.config.modelimport.Asset} object.
     */
    public void visitAsset(Asset asset);
    /**
     * <p>completeAsset</p>
     *
     * @param asset a {@link org.opennms.netmgt.config.modelimport.Asset} object.
     */
    public void completeAsset(Asset asset);

}
