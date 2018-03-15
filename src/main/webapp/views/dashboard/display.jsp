<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<title><spring:message code="dashboard.title" /></title>

<h2><spring:message code="dashboard.avgSqrtRendezvousesPerUser"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtRendezvousesPerUser1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtRendezvousesPerUser2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.ratioOfUsersWithRendezvousVsUsersWithoutRendezvous"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.ratio"/>: <jstl:out value="${ratioOfUsersWithRendezvousVsUsersWithoutRendezvous}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.avgSqrtUsersPerRendezvous"/></h2>
<fieldset>
	
	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtUsersPerRendezvous1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtUsersPerRendezvous2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.avgSqrtRendezvousesRSVPdPerUser"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtRendezvousesRSVPdPerUser1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtRendezvousesRSVPdPerUser2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.top10RendezvousesRSVPd"/></h2>
<fieldset>
	
	<!--TODO: <jstl:out value="${top10RendezvousesRSVPd}"/>-->
	<display:table name="top10RendezvousesRSVPd" id="top10RendezvousesRSVPd" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.rendezvous.name" var="titleName"/>
	<display:column property="name" title="${titleName}" sortable="true"/>
	
	<spring:message code="dashboard.rendezvous.description" var="titleDescription"/>
	<display:column property="description" title="${titleDescription}" sortable="true"/>
	
	
</display:table>
	
</fieldset>

<h2><spring:message code="dashboard.rendezvousesLinkedPlus10"/></h2>
<fieldset>
	
	<!--TODO: <jstl:out value="${rendezvousesLinkedPlus10}"/>-->
	<display:table name="rendezvousesLinkedPlus10" id="rendezvousesLinkedPlus10" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.rendezvous.name" var="titleName"/>
	<display:column property="name" title="${titleName}" sortable="true"/>
	
	<spring:message code="dashboard.rendezvous.description" var="titleDescription"/>
	<display:column property="description" title="${titleDescription}" sortable="true"/>
	
	
</display:table>

</fieldset>

<h2><spring:message code="dashboard.avgSqrtAnnouncementsPerRendezvous"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtAnnouncementsPerRendezvous1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtAnnouncementsPerRendezvous2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.rendezvousesWhoseMoreThat75Announcements"/></h2>
<fieldset>
	
	<!--TODO: <jstl:out value="${rendezvousesWhoseMoreThat75Announcements}"/>-->
	<display:table name="rendezvousesWhoseMoreThat75Announcements" id="rendezvousesWhoseMoreThat75Announcements" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.rendezvous.name" var="titleName"/>
	<display:column property="name" title="${titleName}" sortable="true"/>
	
	<spring:message code="dashboard.rendezvous.description" var="titleDescription"/>
	<display:column property="description" title="${titleDescription}" sortable="true"/>
	
	
</display:table>
	
</fieldset>

<h2><spring:message code="dashboard.avgSqrtQuestionsPerRendezvous"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtQuestionsPerRendezvous1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtQuestionsPerRendezvous2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.avgSqrtAnswersToQuestionsPerRendezvous"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtAnswersToQuestionsPerRendezvous1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtAnswersToQuestionsPerRendezvous2}"/></li>
	</ul>
</fieldset>

<h2><spring:message code="dashboard.avgSqrtRepliesPerComment"/></h2>
<fieldset>

	<ul>
		<li><spring:message code="dashboard.avg"/>: <jstl:out value="${avgSqrtRepliesPerComment1}"/></li>
		<li><spring:message code="dashboard.sqrt"/>: <jstl:out value="${avgSqrtRepliesPerComment2}"/></li>
	</ul>
</fieldset>
	
<h2><spring:message code="dashboard.findManagerMoreServiceThanAvg"/></h2>
<fieldset>

<display:table name="findManagerMoreServiceThanAvg" id="findManagerMoreServiceThanAvg" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.manager.name" var="managerName"/>
	<display:column property="name" title="${managerName}" sortable="true"/>
	
	<spring:message code="dashboard.manager.surname" var="managerSurname"/>
	<display:column property="surname" title="${managerSurname}" sortable="true"/>
	
	<spring:message code="dashboard.manager.phone" var="managerPhone"/>
	<display:column property="phoneNumber" title="${managerPhone}" sortable="true"/>
	
	
<spring:message code="dashboard.manager.email" var="managerEmail"/>
	<display:column property="email" title="${managerEmail}" sortable="true"/>
	
</display:table>
</fieldset>
	
<h2><spring:message code="dashboard.findBestSellingService"/></h2>
<fieldset>
<display:table name="findBestSellingService" id="findBestSellingService" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.service.name" var="serviceName"/>
	<display:column property="name" title="${serviceName}" sortable="true"/>
	
	<spring:message code="dashboard.service.description" var="serviceDescription"/>
	<display:column property="description" title="${serviceDescription}" sortable="true"/>
	

</display:table>
</fieldset>
<!-- 
<h2><spring:message code="dashboard.findManagerMoreServiceCancelled"/></h2>


<fieldset>
<display:table name="findManagerMoreServiceCancelled" id="findManagerMoreServiceCancelled" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="dashboard.manager.name" var="managerName"/>
	<display:column property="name" title="${managerName}" sortable="true"/>
	
	<spring:message code="dashboard.manager.surname" var="managerSurname"/>
	<display:column property="surname" title="${managerSurname}" sortable="true"/>
	
	<spring:message code="dashboard.manager.phone" var="managerPhone"/>
	<display:column property="phoneNumber" title="${managerPhone}" sortable="true"/>
	
	
<spring:message code="dashboard.manager.email" var="managerEmail"/>
	<display:column property="email" title="${managerEmail}" sortable="true"/>
	
</display:table>
	
</fieldset>
 -->
