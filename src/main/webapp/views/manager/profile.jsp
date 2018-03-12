<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<ul>
	<li> <spring:message code="manager.label.name"/>: <jstl:out value="${manager.name}"/> </li>
	<li> <spring:message code="manager.label.surname"/>: <jstl:out value="${manager.surname}"/> </li>
	<li> <spring:message code="manager.label.email"/>: <jstl:out value="${manager.email}"/> </li>
	<li> <spring:message code="manager.label.phone"/>: <jstl:out value="${manager.phoneNumber}"/> </li>
	<li> <spring:message code="manager.label.address"/>: <jstl:out value="${manager.postalAddress}"/> </li>
		 <spring:message code="manager.format.date" var="formatDate"/>
	<li> <spring:message code="manager.label.birth"/>: <fmt:formatDate value="${manager.birthdate}" pattern="${formatDate}"/> </li>
	
</ul>