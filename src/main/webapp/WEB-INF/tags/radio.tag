<%--
 * checkbox.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" rtexprvalue="true" %>
<%@ attribute name="code" required="true" rtexprvalue="true" %>
<%@ attribute name="message1" required="true" rtexprvalue="true" %>
<%@ attribute name="message2" required="true" rtexprvalue="true" %>

<%-- Definition --%>

<div>
	<form:label path="${path}">
		<spring:message code="${code}" />:
	</form:label>
	<spring:message code="${message1}" var="message1Header" />
	<spring:message code="${message2}" var="message2Header" />
	<form:radiobutton path="${path}" value="1"/> <jstl:out value="${message1Header}" />
	<form:radiobutton path="${path}" value="0" /> <jstl:out value="${message2Header}" />
	
	<form:errors path="${path}" cssClass="error" />
</div>