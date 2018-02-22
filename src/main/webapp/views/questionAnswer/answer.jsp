<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<p> <spring:message code="question.answer"/> </p>

<form:form action="" modelAttribute="answer">

	<form:label path="text">
 		<h2> <spring:message code="question.title"/> <jstl:out value="${currentQuestionNumber}"/>/<jstl:out value="${numberOfQuestions}"/>: <jstl:out value="${question.statement}"/> </h2> <br>
	</form:label>
	<form:input path="text"/>	
	<form:errors path="text" cssClass="error" />

	<!-- Buttons -->

	<acme:cancel code="question.cancel" url=""/>&nbsp;
	
	<jstl:choose>
		<jstl:when test="${currentQuestionNumber == numberOfQuestions}">
			<input type="submit" name="finish" value="<spring:message code="question.finish" />" />
		</jstl:when>
		<jstl:otherwise>
			<input type="submit" name="next" value="<spring:message code="question.next" />" />
		</jstl:otherwise>
	</jstl:choose>
	
</form:form>