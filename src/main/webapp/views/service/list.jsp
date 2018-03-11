<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="services" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
		<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${isOwn}">
			<display:column>
				<a href="service/manager/edit.do?serviceId=${row.id}"> <spring:message
						code="service.label.edit" />
				</a>
			
			</display:column>
		</jstl:if>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${isOwn}">
			<display:column>
			
				<a href="service/manager/delete.do?serviceId=${row.id}"> <spring:message
						code="service.label.delete" />
				</a>
			</display:column>
		</jstl:if>
		</security:authorize>

		<security:authorize access="hasRole('USER')">
		<display:column>
				<a href="request/user/create.do?serviceId=${row.id}"> <spring:message
						code="service.label.request" />
				</a>
		</display:column>
		</security:authorize>

		<spring:message code="service.label.name" var="nameHeader" />
		<display:column property="services.name"
			title="${nameHeader}" sortable="true" />


		<spring:message code="service.label.description" var="descriptionHeader" />
		<display:column property="services.description"
			title="${descriptionHeader}" sortable="true" />
			
		<spring:message code="service.label.status" var="statusHeader" />
		<display:column property="services.status"
			title="${statusHeader}" sortable="true" />
		
		<spring:message code="service.label.urlPicture" var="urlPictureHeader" />
		<display:column title="${urlPictureHeader}">
			<img src="services.urlPicture" alt="Picture"/>
		</display:column>

</display:table>