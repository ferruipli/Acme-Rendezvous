<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="rsvp/user/cancel.do" modelAttribute="rsvp">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="name" >
		<spring:message code="rsvp.rendezvous.name"/>:
	</form:label>
		<jstl:out value="${rsvp.rendezvous.name}" />
	
	<form:label path="description">
		<spring:message code="rsvp.rendezvous.description" />:
	</form:label>
		<jstl:out value="${rsvp.rendezvous.description}" />
	<br /><br/>
	
	<form:label path="moment">
		<spring:message code="rsvp.rendezvous.moment" />:
	</form:label>
		<jstl:out value="${rsvp.rendezvous.moment}" />
	<br /><br />
	
	
	<input type= "submit" name="cancel" value = "<spring:message code="rsvp.cancel" />"
			onclick="return confirm('<spring:message code="rsvp.confirm.cancel" />')" />
	</form:form>
	