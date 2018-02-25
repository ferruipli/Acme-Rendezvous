<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="user/register.do" modelAttribute="registrationForm">

	<fieldset>
		<legend><spring:message code="user.fieldset.personalData"/></legend>
	
		<acme:textbox path="name" code="user.label.name"/>
		<acme:textbox path="surname" code="user.label.surname"/>
		<acme:textbox path="email" code="user.label.email"/>
		<acme:textbox path="phoneNumber" code="user.label.phone"/>
		<acme:textbox path="postalAddress" code="user.label.address"/>
		<acme:textbox path="birthdate" code="user.label.birth" placeholder="dd/mm/yyyy"/>
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="user.fieldset.userAccount"/></legend>
	
		<acme:textbox path="userAccount.username" code="user.label.username"/>
		<acme:password path="userAccount.password" code="user.label.pass"/>
		<acme:password path="confirmPassword" code="user.label.confirmpass"/>
	</fieldset>
	
	<!-- Checkbox -->
	
	<form:errors path="agreement" cssClass="error"/>
	<form:checkbox path="agreement" value="accept" />&nbsp;
	<spring:message code="user.text.agree"/> <a href="legalTexts/termsAndConditions.do"><spring:message code="user.link.termsAndConditions"/></a>
	<br><br>
	
	<!-- Buttons -->
	
	<input type="submit" name="register" value="<spring:message code="user.register"/>"/>&nbsp;
	<acme:cancel url="welcome/index.do" code="user.cancel"/>

</form:form>