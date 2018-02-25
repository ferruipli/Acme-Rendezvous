<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


	<strong> <spring:message code="announcement.title"/>: </strong>
	<jstl:out value="${announcement.title}" />
<br/>
	<strong> <spring:message code="announcement.description"/>: </strong>
	<jstl:out value="${announcement.description}" />
<br/>

<strong> <spring:message code="announcement.moment"/>: </strong>
	<jstl:out value="${announcement.moment}" />
<br/>

<input type= "submit" name="delete" value = "<spring:message code="announcement.delete" />"
			onclick="return confirm('<spring:message code="announcement.confirm.delete" />')" />
