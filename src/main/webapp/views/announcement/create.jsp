<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentDate" value="${now}" pattern="yyyy-MM-dd" />

<form:form action="announcement/user/create.do" modelAttribute="announcement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path = "rendezvous"/>
	
	<acme:textbox code = "announcement.title" path="title"/>
	<acme:textbox code = "announcement.description" path = "description"/>

	<acme:submit name="save" code="announcement.save" />
	<jstl:if test = "${rendezvous.id !=0 }">
		<acme:submit name="delete" code="announcement.delete" />
	</jstl:if>
	<acme:cancel code="announcement.return" url="rendezvous/list.do" />

	<br />
	


</form:form>