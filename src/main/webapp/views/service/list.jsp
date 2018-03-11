<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="services" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
	<jstl:if test="${editable}">
		<display:column>
			<jstl:if test="${!row.isRequested}">
				<a href="service/manager/edit.do?serviceId=${row.id}"> <spring:message code="service.label.edit" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${editable}">
		<display:column>
			<jstl:if test="${!row.isRequested}">
				<a href="service/manager/delete.do?serviceId=${row.id}"> <spring:message code="service.label.delete" /></a>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${!row.isCancelled}">
				<a href="request/user/create.do?serviceId=${row.id}"> <spring:message code="service.label.request" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="service/administrator/cancel.do?serviceId=${row.id}"> <spring:message code="service.label.cancel" /></a>
		</display:column>
	</security:authorize>

	<spring:message code="service.label.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="service.label.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
		
	<jstl:if test="${editable}">
		<spring:message code="service.label.status" var="statusHeader" />
		<display:column title="${statusHeader}" sortable="true">
			<jstl:if test="${row.isCancelled}">
				<spring:message code="service.label.cancelled"/>
			</jstl:if>
			<jstl:if test="${!row.isCancelled}">
				<spring:message code="service.label.available"/>
			</jstl:if>
		</display:column>
	</jstl:if>
	
	<spring:message code="service.label.urlPicture" var="urlPictureHeader" />
	<display:column title="${urlPictureHeader}">
		<jstl:choose>
			<jstl:when test="${not empty row.urlPicture}">
				<img src="${row.urlPicture}" alt="Picture"/>
			</jstl:when>
			<jstl:otherwise>
				-
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

</display:table>

<security:authorize access="hasRole('MANAGER')">
	<a href="service/manager/create.do"><spring:message code="service.link.create"/></a>
</security:authorize>