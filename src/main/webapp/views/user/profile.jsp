<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul>
	<li> <spring:message code="user.table.name"/>: <jstl:out value="${user.name}"/> </li>
	<li> <spring:message code="user.table.surname"/>: <jstl:out value="${user.surname}"/> </li>
	<li> <spring:message code="user.table.email"/>: <jstl:out value="${user.email}"/> </li>
	<li> <spring:message code="user.table.phone"/>: <jstl:out value="${user.phoneNumber}"/> </li>
	<li> <spring:message code="user.table.address"/>: <jstl:out value="${user.postalAddress}"/> </li>
	<li> <spring:message code="user.table.birth"/>: <jstl:out value="${user.birthdate}"/> </li>
	<li> <spring:message code="user.table.rsvpdRendezvouses"/>: <a href=""> <spring:message code="user.link.rsvpdRendezvouses"/> </a> </li>
</ul>