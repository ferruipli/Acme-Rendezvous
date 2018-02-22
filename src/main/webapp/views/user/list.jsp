<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="users" id="user" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="user/profile.do?userId=${user.id}"> <spring:message code="user.link.profile"/> </a>
	</display:column>
	
	<spring:message code="user.table.name" var="title.name"/>
	<display:column property="name" title="title.name" sortable="true"/>
	
	<spring:message code="user.table.surname" var="title.surname"/>
	<display:column property="surname" title="title.surname" sortable="true"/>
</display:table>