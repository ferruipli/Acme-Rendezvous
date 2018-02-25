<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="now" class="java.util.Date" />

<display:table name="announcement" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		
		<display:column>
			<input type= "submit" name="delete" value = "<spring:message code="announcement.delete" />"
			onclick="return confirm('<spring:message code="announcement.confirm.delete" />')" />
			
		</display:column>
	</security:authorize>
	
	
	<spring:message code="announcement.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="announcement.moment" var="momentHeader"  />
	<spring:message code="announcement.formatDate" var="formatDate" />
	<display:column property="moment" title="${momentHeader}" sortable="true"  format="${formatDate }"/>
	
	<spring:message code="announcement.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
	
</display:table>


<br/>
