<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="rendezvous/user/edit.do" modelAttribute="rendezvous">
	
	<acme:textbox code="rendezvous.name" path="name" />
	<acme:textbox code="rendezvous.description" path="description" />
	<acme:textbox code="rendezvous.moment" path="moment" />
	
	<spring:message code="rendezvous.gpsCoordinates" var="gpsVar" />
	<p> <jstl:out value="${gpsVar}" /> </p>:
		<acme:textbox code="gps.latitude" path="latitude" />
		<acme:textbox code="gps.longitude" path="longitude" />
		
	<acme:checkbox code="rendezvous.finalMode" path="finalMode" message1="finalMode.true" message2="finalMode.false" />
	<acme:checkbox code="rendezvous.adultOnly" path="adultOnly" message1="finalMode.true" message2="finalMode.false" />
	<acme:textbox code="rendezvous.urlPicture" path="urlPicture" />
	<acme:select code="rendezvous.similarOnes" path="similarOnes" items="rendezvouses" itemLabel="name" />
	
	<acme:submit name="save" code="rendezvous.save" />
	<jstl:if test="${rendezvous.id != 0}">
		<acme:submit name="delete" code="rendezvous.delete" />
	</jstl:if>
	<acme:cancel code="rendezvous.cancel" url="rendezvous/user/list.do" />
</form:form>