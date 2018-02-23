<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvouses" requestURI="rendezvous/user/list.do" id="row">

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="rendezvous.description" var="descriptionHeader"/>
	<display:column property="description" title="${descriptionHeader}" sortable="true"/>
	
	<spring:message code="rendezvous.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"/>

	<spring:message code="rendezvous.creator" var="creatorHeader" />
	<display:column property="creator.name" title="${creatorHeader}"/>

	<spring:message code="rendezvous.finalMode" var="finalModeHeader" />
	<display:column property="finalMode" title="${finalModeHeader}" />


	<spring:message code="rendezvous.remove" var="removeHeader" />
	<display:column title="${removeHeader}">

		<!--  <a href="rendezvous/edit.do?rendezvousId=${row.id}"><spring:message
				code="rendezvous.edit" /> </a> AÑADIR URL DE BORRAR -->

	</display:column>
	<spring:message code="rendezvous.display" var="displayHeader" />
	<display:column title="${displayHeader}">

		<a href="rendezvous/display.do?rendezvousId=${row.id}"><spring:message
				code="rendezvous.display" /> </a>

	</display:column>
	
	<spring:message code="rendezvous.edit" var="editHeader" />
	<display:column title="${editHeader}">

		<a href="rendezvous/edit.do?rendezvousId=${row.id}"><spring:message
				code="rendezvous.edit" /> </a>

	</display:column>
	
	<spring:message code="rendezvous.cancel" var="cancelHeader" />
	<display:column title="${cancelHeader}">

		<a href="rendezvous/user/delete.do?rendezvousId=${row.id}"><spring:message
				code="rendezvous.delete" /> </a>

	</display:column>
	
	<input type="button" name="comment" value="<spring:message code="rendezvous.comment" />" 
		onclick="javascript: relativeRedir('comment/user/create.do" />





</display:table>