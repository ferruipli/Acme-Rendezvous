<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="users" id="user" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column>
		<a href="user/profile.do?userId=${user.id}"><spring:message code="question.label.profile"/></a>
	</display:column>
	
	<spring:message code="question.label.name" var="titleName"/>
	<display:column property="name" title="${titleName}" sortable="true"/>
	
	<spring:message code="question.label.surname" var="titleSurname"/>
	<display:column property="surname" title="${titleSurname}" sortable="true"/>
	
	<spring:message code="question.label.email" var="titleEmail"/>
	<display:column property="email" title="${titleEmail}" sortable="true"/>
	
	<spring:message code="question.label.phoneNumber" var="titlePhone"/>
	<display:column property="phoneNumber" title="${titlePhone}" sortable="true"/>
</display:table>

<jstl:if test="${editable}">
	<br>
	<a href="question/user/create.do"><spring:message code="question.label.add"/></a>
	<br>
</jstl:if>

<jstl:if test="${not empty questions}">
	<jstl:forEach var="question" items="${questions}" varStatus="i">
		<h2><jstl:out value="${question.statement}"/></h2>
		<jstl:if test="${editable}"><a href="question/user/edit.do?questionId=${question.id}"><spring:message code="question.label.edit"/></a></jstl:if>
		<jstl:if test="${not empty rsvps}">
			<ul>
				<jstl:forEach var="rsvp" items="${rsvps}" varStatus="j">
					<li><jstl:out value="${rsvp.user.name}"/>: <jstl:out value="${answers[i.index + j.index*numberOfQuestions].text}"/></li>
				</jstl:forEach>
			</ul>
		</jstl:if>
	</jstl:forEach>
</jstl:if>