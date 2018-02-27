<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 
 
 --%>
 
 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<a href = "legalTexts/termsAndConditions.do" >

<spring:message	code="master.termAndConditions" />

</a>
 | 
<a href = "legalTexts/legalData.do" >

<spring:message	code="master.legalData" />

</a>
 | 
<a href = "legalTexts/cookies.do" >

<spring:message	code="master.cookies" />

</a>

<br><br>
<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme Rendezvous Co., Inc.</b>