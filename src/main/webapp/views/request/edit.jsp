<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="request/user/edit.do" modelAttribute="request">	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="service" />
	
	<acme:textbox code="request.comment" path="comment" />
	
	<h2><spring:message code="request.creditCard.title"/></h2>
	<acme:textbox code="request.creditCard.holderName" path="creditCard.holderName" />
	<acme:textbox code="request.creditCard.brandName" path="creditCard.brandName" />
	<acme:textbox code="request.creditCard.number" path="creditCard.number"/>
	<acme:textbox code="request.creditCard.expirationMonth" path="creditCard.expirationMonth" placeholder="MM"/>
	<acme:textbox code="request.creditCard.expirationYear" path="creditCard.expirationYear" placeholder="YY"/>
	<acme:textbox code="request.creditCard.cvvCode" path="creditCard.cvvCode" placeholder="XXX"/>

	<acme:selectMultiple path="rendezvous" code="request.rendezvous"
		items="${rendezvouses}" itemLabel="name" />
	
	<acme:submit name="save" code="request.save" />
	<acme:cancel code="rendezvous.return" url="service/list.do" />
</form:form>