<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- EDIT/CREATE  TRIP -->

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<form:form action="announcement/user/create.do" modelAttribute="announcement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path = "rendezvous"/>
		
	
	<form:label path="title">
		<spring:message code="announcement.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br /><br/>
		
	<form:label path="description">
		<spring:message code="announcement.description" />:
	</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br /><br />
	
	
	<input type="submit" name="save" value="<spring:message code="announcement.save" />" />
	<input type="button" name="cancel"	value="<spring:message code="announcement.cancel" />
			"onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
	


</form:form>