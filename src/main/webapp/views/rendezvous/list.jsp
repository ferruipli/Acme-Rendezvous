<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous" requestURI="${requestURI}" id="row">
	

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="rendezvous.description" var="descriptionHeader"/>
	<display:column property="description" title="${descriptionHeader}" sortable="true"/>
	
	<spring:message code="rendezvous.formatMoment" var="formatMomentHeader" />
	<spring:message code="rendezvous.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${formatMomentHeader}" sortable="true" />

	<spring:message code="rendezvous.creator" var="creatorHeader" />
	<display:column title="${creatorHeader}">
		<a href="user/profile.do?userId=${row.creator.id}">
			 <jstl:out value="${row.creator.name}"/>
		</a>
	</display:column>
	
	<spring:message code="rendezvous.attendants" var="attendantsHeader" />
	<display:column title="${attendantsHeader}">
		<jstl:forEach items="${row.attendants}" var="attendant">
			<a href="user/profile.do?userId=${attendant.id}">
			 	<jstl:out value="${attendant.name}"/>
			</a>
			<br/>
		</jstl:forEach>
	</display:column>
	
	<spring:message code="rendezvous.finalMode" var="finalModeHeader" />
	<display:column property="finalMode" title="${finalModeHeader}" />

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="rendezvous/administrator/remove.do?rendezvousId=${row.id}">
				<spring:message code="rendezvous.remove" /> 		
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="rendezvous/user/display.do?rendezvousId=${row.id}">
				<spring:message code="rendezvous.display" />
			</a>
		</display:column>
		<display:column>
			<a href="rendezvous/user/edit.do?rendezvousId=${row.id}">
				<spring:message code="rendezvous.edit" />
			</a>
		</display:column>
	</security:authorize>
	
</display:table>


