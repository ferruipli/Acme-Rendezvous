<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="announcement/administrator/display.do" modelAttribute="announcement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="rendezvous" >
		<spring:message code="announcement.rendezvous"/>:
	</form:label>
		<jstl:out value="${announcement.rendezvous.name}" />
	
	<form:label path="title">
		<spring:message code="announcement.title" />:
	</form:label>
		<jstl:out value="${announcement.title}" />
	<br /><br/>
	
	<form:label path="description">
		<spring:message code="announcement.description" />:
	</form:label>
		<jstl:out value="${announcement.description}" />
	<br /><br />
	
	<form:label path="moment" >
		<spring:message code="announcement.moment"/>:
	</form:label>
		<jstl:out value="${announcement.moment}" />
	<br /><br />
	
	
	<input type= "submit" name="delete" value = "<spring:message code="announcement.delete" />"
			onclick="return confirm('<spring:message code="announcement.confirm.delete" />')" />
	</form:form>
	