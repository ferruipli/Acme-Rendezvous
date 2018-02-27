<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${rendezvous.urlPicture != null || rendezvous.urlPicture != '' }">
	<div id="picture">
		<img src="images/${rendezvous.urlPicture}" alt="Picture" />
	</div>
</jstl:if>

<p>
	<strong> <spring:message code="rendezvous.name"/>: </strong>
	<jstl:out value="${rendezvous.name}" />
</p>

<p>
	<strong> <spring:message code="rendezvous.description"/>: </strong>
	<jstl:out value="${rendezvous.description}" />
</p>

<spring:message code="moment.format" var="formatMomentVar" />
<strong> <spring:message code="rendezvous.moment" />: </strong>
<fmt:formatDate value="${rendezvous.moment}" pattern="${formatMomentVar}" />

<jstl:if test="${rendezvous.gpsCoordinates.latitude!=0.0 && rendezvous.gpsCoordinates.longitude!=0.0}">
	<p> <strong> <spring:message code="rendezvous.gpsCoordinates" />: </strong> </p>
	<p style="text-indent:1.5em">
		<strong> <spring:message code="gps.latitude"/>: </strong>
		<jstl:out value="${rendezvous.gpsCoordinates.latitude}" />
	</p>
	<p style="text-indent:1.5em">
		<strong> <spring:message code="gps.longitude"/>: </strong>
		<jstl:out value="${rendezvous.gpsCoordinates.longitude}" />
	</p>	
</jstl:if>

<p>
	<strong> <spring:message code="rendezvous.comments"/>: </strong>
	<a href="comment/user/list.do?rendezvousId=${rendezvous.id }">
	 	<spring:message code="comment.list" />
	</a>
</p>

<p>
	<strong> <spring:message code="rendezvous.attendants"/>: </strong>
	<a href="questions/list.do?rendezvousIs=${rendezvous.id}">
	 	<spring:message code="rendezvous.link" />
	</a>
</p>

<p>
	<strong> <spring:message code="rendezvous.creator"/>: </strong>
	<a href="actor/display.do?userId=${rendezvous.creator.id}">
	 	<jstl:out value="${rendezvous.creator.name}" />
	</a>
</p>

<jstl:if test="${similarOnes.size() > 0}">
	<p> <strong> <spring:message code="rendezvous.similarOnes" />: </strong> </p>
	<ul>
		<jstl:forEach items="${similarOnes}" var="similarOne">
			<li>
				<a href="rendezvous/user/display.do?rendezvousId=${similarOne.id}">
		 			<jstl:out value="${similarOne.name}" />
		 		</a>
			</li>
	</jstl:forEach>
	</ul>
</jstl:if>

<jstl:if test="${announcements.size() > 0}">
	<p> <strong> <spring:message code="rendezvous.announcements" /> </strong> </p>
	<display:table name="announcements" id="row" class="displaytag">
		<spring:message code="announcement.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
	
		<spring:message code="announcement.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" sortable="true" />
		
		<spring:message code="rendezvous.formatMoment" var="formatMomentVar" />
		<spring:message code="announcement.moment" var="momentHeader" />
		<display:column property="title" title="${momentHeader}" format="${formatMomentVar}" sortable="true" />
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="announcement/administrator/display.do?announcementId=${row.id}">
				<spring:message code="rendezvous.display" />
			</a>
		</display:column>
		
	</security:authorize>
	
		
	</display:table>
</jstl:if>

<security:authorize access="hasRole('USER')">
	
	<jstl:if test="${isCreator==true}">
		<a href="announcement/user/create.do?rendezvousId=${rendezvous.id}">
			<spring:message code="announcement.create" />
		</a>
	</jstl:if>
	
	<br />
	
	<jstl:choose>
		<jstl:when test="${isReserved==false}">
			<acme:submit name="rsvp" code="rendezvous.reserve" />
		</jstl:when>
		<jstl:when test="${isReserved==true}">
			<acme:submit name="cancelRSVP" code="rendezvous.cancelReserve" />
		</jstl:when>
	</jstl:choose>
	
</security:authorize>
