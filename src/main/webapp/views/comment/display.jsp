<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<p>
	<strong> <spring:message code="comment.text"/>: </strong>
	<jstl:out value="${comment.text}" />
</p>

<p>
<spring:message code="moment.format" var="formatMomentVar" />
<strong> <spring:message code="comment.moment" />: </strong>
<fmt:formatDate value="${comment.moment}" pattern="${formatMomentVar}" />
</p>

<p>
	<strong> <spring:message code="comment.urlPicture"/>: </strong>
	<jstl:out value="${comment.urlPicture}" />
</p>

<p>
	<strong> <spring:message code="comment.user"/>: </strong>
	<jstl:out value="${comment.user.name}" />
</p>

<p>
	<strong> <spring:message code="comment.rendezvous"/>: </strong>
	<jstl:out value="${comment.rendezvous.name}" />
</p>

<p>
	<strong> <spring:message code="comment.parentComment"/>: </strong>
	<jstl:out value="${comment.parentComment.text}" />
</p>


<jstl:if test="${descendantComments.size() > 0}">
	<p> <strong> <spring:message code="comment.descendantComments" /> </strong> </p>
	<display:table name="descendantComments" id="row" class="displaytag">
		<spring:message code="descendantComments.text" var="textHeader" />
		<display:column property="text" title="${textHeader}" sortable="true" />
		
		<spring:message code="descendantComments.formatMoment" var="formatMomentVar" />
		<spring:message code="descendantComments.moment" var="momentHeader" />
		<display:column property="moment" title="${momentHeader}" format="${formatMomentVar}" sortable="true" />	
		
	</display:table>
</jstl:if>

<security:authorize access="hasRole('USER')">
	<a href="comment/user/reply.do?commentId=${comment.id}">
				<spring:message code="comment.replyComment" /> 		
	</a>	

</security:authorize>

<br/> <br/>
	<acme:cancel code="comment.return" url="comment/user/list.do?rendezvousId=${comment.rendezvous.id }" />
