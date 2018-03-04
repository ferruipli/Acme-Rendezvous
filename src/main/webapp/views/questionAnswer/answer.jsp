<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p> <spring:message code="question.answer"/> </p>

<form:form action="question/user/questionnaire.do" modelAttribute="questionnaireForm">

	<form:hidden path="questions"/>
	<form:hidden path="rendezvous"/>
	
	<jstl:if test="${not empty questionnaireForm.questions}">
		<jstl:forEach var="question" items="${questionnaireForm.questions}" varStatus="i">
			<form:label path="answers[${i.index}]">
		 		<h2> <jstl:out value="${questionnaireForm.questions[i.index].statement}"/> </h2> <br>
			</form:label>
			<form:input path="answers[${i.index}]"/>	
			<form:errors path="answers[${i.index}]" cssClass="error" />
			<br>
		</jstl:forEach>
	</jstl:if>

	<!-- Buttons -->
	
	<br>
	<acme:cancel code="question.label.cancel" url="rendezvous/display.do?rendezvousId=${questionnaireForm.rendezvous.id}"/>
	&nbsp;	
	<acme:submit name="finish" code="question.finish"/>
	
</form:form>