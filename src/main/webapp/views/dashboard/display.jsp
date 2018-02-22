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
	
</fieldset>

<h2><spring:message code="dashboard.rendezvousesLinkedPlus10"/></h2>
<fieldset>
	
	<!--TODO: <jstl:out value="${rendezvousesLinkedPlus10}"/>-->
	
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