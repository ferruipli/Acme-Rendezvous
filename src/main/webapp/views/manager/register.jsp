<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="manager/register.do" modelAttribute="registrationForm">

	<fieldset>
		<legend><spring:message code="manager.fieldset.personalData"/></legend>
	
		<acme:textbox path="name" code="manager.label.name"/>
		<acme:textbox path="surname" code="manager.label.surname"/>
		<acme:textbox path="email" code="manager.label.email"/>
		<acme:textbox path="phoneNumber" code="manager.label.phone"/>
		<acme:textbox path="postalAddress" code="manager.label.address"/>
		<acme:textbox path="birthdate" code="manager.label.birth" placeholder="dd/mm/yyyy"/>
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="manager.fieldset.userAccount"/></legend>
	
		<acme:textbox path="userAccount.username" code="manager.label.username"/>
		<acme:password path="userAccount.password" code="manager.label.pass"/>
		<acme:password path="confirmPassword" code="manager.label.confirmpass"/>
	</fieldset>
	
	<!-- Checkbox -->
	
	<form:errors path="agreement" cssClass="error"/>
	<form:checkbox path="agreement" value="accept" />&nbsp;
	<spring:message code="manager.text.agree"/> <a href="legalTexts/termsAndConditions.do"><spring:message code="manager.link.termsAndConditions"/></a>
	<br><br>
	
	<!-- Buttons -->
	
	<input type="submit" name="register" value="<spring:message code="manager.register"/>"/>&nbsp;
	<acme:cancel url="welcome/index.do" code="manager.cancel"/>

</form:form>