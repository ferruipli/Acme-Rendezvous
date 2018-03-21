<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="users" id="user" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="actor/profile.do?actorId=${user.id}"> <spring:message code="actor.link.profile"/> </a>
	</display:column>
	
	<spring:message code="actor.label.name" var="titleName"/>
	<display:column property="name" title="${titleName}" sortable="true"/>
	
	<spring:message code="actor.label.surname" var="titleSurname"/>
	<display:column property="surname" title="${titleSurname}" sortable="true"/>
</display:table>