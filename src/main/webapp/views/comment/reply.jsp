<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="comment/user/createReply.do" modelAttribute="reply">	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="comment"/>
	<form:hidden path="user"/>
	
	<form:label path="text">
		<spring:message code="reply.text" />:
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br /><br/>	
	
	<input type="submit" name="reply" value="<spring:message code="comment.save" />" />
	<input type="button" name="cancel"	value="<spring:message code="comment.cancel" />
			"onclick="javascript:window.history.back();" />
	<br />
	
	
</form:form>