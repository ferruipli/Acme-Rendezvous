<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="question/user/edit.do" modelAttribute="question">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="answers"/>
	<form:hidden path="rendezvous"/>
	
	<acme:textbox path="statement" code="question.label.statement"/>

	<!-- Buttons -->

	<acme:submit name="save" code="question.label.save"/>
	<jstl:if test="${question.id != 0}">
		<acme:submit name="delete" code="question.label.delete"/>
	</jstl:if>
	<acme:cancel url="question/list.do?rendezvousId=${rendezvousId}" code="question.label.cancel"/>
	
</form:form>