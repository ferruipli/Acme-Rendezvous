<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

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

<p>
	<spring:message code="rendezvous.formatMoment" var="formatMomentVar" />
	<strong> <spring:message code="rendezvous.moment"/>: </strong>
	<fmt:formatDate value="${rendezvous.moment}" pattern="${formatMomentVar}" />
</p>

<jstl:if test="${rendezvous.gpsCoordinates != null}">
	<p> <strong> <spring:message code="rendezvous.gpsCoordinates" />: </strong> </p>
	<p>
		<strong> <spring:message code="gps.latitude"/>: </strong>
		<jstl:out value="${rendezvous.gpsCoordinates.latitude}" />
	</p>
	<p>
		<strong> <spring:message code="gps.longitude"/>: </strong>
		<jstl:out value="${rendezvous.gpsCoordinates.longitude}" />
	</p>	
</jstl:if>

<p>
	<strong> <spring:message code="rendezvous.attendants"/>: </strong>
	<a href="questions/user/list.do">
	 	<jstl:out value="rendezvous.link" />
	</a>
</p>

<p>
	<strong> <spring:message code="rendezvous.creator"/>: </strong>
	<a href="actor/display.do?userId=${rendezvous.creator.id}">
	 	<jstl:out value="${rendezvous.creator.name}" />
	</a>
</p>

<jstl:if test="${similarOnes.size > 0}">
	<p> <strong> <spring:message code="rendezvous.similarOnes" />: </strong> </p>
	<jstl:forEach items="${similarOnes}" var="similarOne">
		<p>
			<a href="rendezvous/user/display.do?rendezvousId=${similarOne.id}">
		 		<jstl:out value="${similarOne.name}" />
		 	</a>
		</p>
	</jstl:forEach>
</jstl:if>

<p>
	<strong> <spring:message code="rendezvous.comments"/>: </strong>
	<a href="comment/user/list.do">
	 	<jstl:out value="<spring:message code="comment.list" />" />
	</a>
</p>

<jstl:if test="${rendezvous.announcements.size > 0}">
	<p> <strong> <spring:message code="rendezvous.announcements" /> </strong> </p>
	<display:table name="announcements" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="announcement.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
	
		<spring:message code="announcement.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" sortable="true" />
		
		<spring:message code="" var="" />
		<spring:message code="announcement.moment" var="momentHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
	</display:table>
	 
</jstl:if>
