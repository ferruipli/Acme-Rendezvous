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
	<form:hidden path="answers"/>
	<form:hidden path="currentQuestionNumber"/>

	<form:label path="text">
 		<h2> <spring:message code="question.title"/> <jstl:out value="${questionnaireForm.currentQuestionNumber+1}"/>/<jstl:out value="${numberOfQuestions}"/>: <jstl:out value="${questionnaireForm.questions[questionnaireForm.currentQuestionNumber].statement}"/> </h2> <br>
	</form:label>
	<form:input path="text"/>	
	<form:errors path="text" cssClass="error" />
	<br>

	<!-- Buttons -->
	
	<br>
	<acme:cancel code="question.cancel" url="welcome/index.do"/>&nbsp;
	
	<jstl:choose>
		<jstl:when test="${questionnaireForm.currentQuestionNumber+1 == numberOfQuestions}">
			<acme:submit name="finish" code="question.finish"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="next" code="question.next"/>
		</jstl:otherwise>
	</jstl:choose>
	
</form:form>