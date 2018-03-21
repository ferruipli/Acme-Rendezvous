<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="actor/register.do" modelAttribute="registrationForm">

	<fieldset>
		<legend><spring:message code="actor.fieldset.personalData"/></legend>
	
		<acme:textbox path="name" code="actor.label.name"/>
		<acme:textbox path="surname" code="actor.label.surname"/>
		<acme:textbox path="email" code="actor.label.email"/>
		<acme:textbox path="phoneNumber" code="actor.label.phone"/>
		<acme:textbox path="postalAddress" code="actor.label.address"/>
		<acme:textbox path="birthdate" code="actor.label.birth" placeholder="dd/mm/yyyy"/>
	</fieldset>
	
	<fieldset>
		<legend><spring:message code="actor.fieldset.userAccount"/></legend>
	
		<acme:textbox path="userAccount.username" code="actor.label.username"/>
		<acme:password path="userAccount.password" code="actor.label.pass"/>
		<acme:password path="confirmPassword" code="actor.label.confirmpass"/>
	</fieldset>
	
	<!-- Checkbox -->
	
	<form:errors path="agreement" cssClass="error"/>
	<form:checkbox path="agreement" value="accept" />&nbsp;
	<spring:message code="actor.text.agree"/> <a href="legalTexts/termsAndConditions.do"><spring:message code="actor.link.termsAndConditions"/></a>
	<br><br>
	
	<!-- Buttons -->
	<jstl:choose>
		<jstl:when test="${user==true}">
			<input type="submit" name="registerUser" value="<spring:message code="actor.register"/>"/>&nbsp;
		</jstl:when>
		<jstl:when test="${manager==true}">
			<input type="submit" name="registerManager" value="<spring:message code="actor.register"/>"/>&nbsp;
		</jstl:when>
	</jstl:choose>

	
	<acme:cancel url="welcome/index.do" code="actor.cancel"/>

</form:form>