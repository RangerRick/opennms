<%--
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

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="/includes/header.jsp" flush="false">
	<jsp:param name="title" value="Applications" />
	<jsp:param name="headTitle" value="Applications" />
	<jsp:param name="breadcrumb"
		value="<a href='admin/index.jsp'>Admin</a>" />
	<jsp:param name="breadcrumb" value="Applications" />
</jsp:include>

<h3>Applications</h3>

<table>
  <tr>
    <th>Delete</th>
    <th>Edit</th>
    <th>Application</th>
  </tr>
  <c:forEach items="${applications}" var="app">
	  <tr>
	    <td><a href="admin/applications.htm?removeApplicationId=${app.id}"><img src="images/trash.gif" alt="Delete Application"/></a></td>
	    <td><a href="admin/applications.htm?applicationid=${app.id}&edit=edit"><img src="images/modify.gif" alt="Edit Application"/></a></td>
	    <td><a href="admin/applications.htm?applicationid=${app.id}">${fn:escapeXml(app.name)}</a></td> 
  	  </tr>
  </c:forEach>
  <tr>
    <td></td>
    <td></td>
    <td>
      <form action="admin/applications.htm">
        <input type="textfield" name="newApplicationName" size="40"/>
        <input type="submit" value="Add New Application"/>
      </form>
  </tr>
</table>

<jsp:include page="/includes/footer.jsp" flush="false"/>
