<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul>
	<li> <spring:message code="actor.label.name"/>: <jstl:out value="${actor.name}"/> </li>
	<li> <spring:message code="actor.label.surname"/>: <jstl:out value="${actor.surname}"/> </li>
	<li> <spring:message code="actor.label.email"/>: <jstl:out value="${actor.email}"/> </li>
	<li> <spring:message code="actor.label.phone"/>: <jstl:out value="${actor.phoneNumber}"/> </li>
	<li> <spring:message code="actor.label.address"/>: <jstl:out value="${actor.postalAddress}"/> </li>
		 <spring:message code="actor.format.date" var="formatDate"/>
	<li> <spring:message code="actor.label.birth"/>: <fmt:formatDate value="${actor.birthdate}" pattern="${formatDate}"/> </li>
	<li> <spring:message code="actor.label.rsvpdRendezvouses"/>: <a href="rendezvous/listReserved.do?userId=${actor.id}"> <spring:message code="actor.link.rsvpdRendezvouses"/> </a> </li>
</ul>