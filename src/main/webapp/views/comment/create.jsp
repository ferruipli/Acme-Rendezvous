<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- EDIT/CREATE  TRIP -->


<form:form action="comment/user/create.do" modelAttribute="comment">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="rendezvous"/>
	<form:hidden path="user"/>
	<form:hidden path="repliedComments"/>
	
	<form:label path="text">
		<spring:message code="comment.text" />:
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br /><br/>	
	
	<form:label path="urlPicture">
		<spring:message code="comment.urlPicture" />:
	</form:label>
	<form:input path="urlPicture" />
	<form:errors cssClass="error" path="urlPicture" />
	<br /><br/>	
	
	<acme:submit name="save" code="comment.save" />
	<acme:cancel url="rendezvous/user/list.do" code="comment.cancel"/>
	<br />
	


</form:form>