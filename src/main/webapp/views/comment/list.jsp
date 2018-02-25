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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="comments" requestURI="${requestURI}" id="row">

	<spring:message code="comment.formatMoment" var="formatMomentHeader" />
	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" format="${formatMomentHeader}" sortable="true" />
	
	<spring:message code="comment.text" var="textHeader"/>
	<display:column property="text" title="${textHeader}" sortable="true"/>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="comment/administrator/remove.do?commentId=${row.id}">
				<spring:message code="comment.remove" /> 		
			</a>
		</display:column>
	</security:authorize>

	
	
	
	
</display:table>


