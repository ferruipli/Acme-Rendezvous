<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="service/manager/edit.do" modelAttribute="service">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="isCancelled"/>
	<form:hidden path="isRequested"/>
	
	<acme:textbox code="name" path="service.label.name"/>
	<acme:textbox code="description" path="service.label.description"/>
	<acme:textbox code="urlPicture" path="service.label.urlPicture"/>

	<!-- Buttons -->
	
	<acme:submit name="save" code="service.label.save"/>
	<jstl:if test="${service.id == 0}">
		<acme:submit name="delete" code="service.label.delete"/>
	</jstl:if>
	<acme:cancel url="service/manager/list.do" code="service.label.cancel"/>
	
</form:form>