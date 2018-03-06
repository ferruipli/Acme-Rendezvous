<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvouses" requestURI="${requestURI}" id="row">

	<jsp:useBean id="currentMoment" class="java.util.Date" />

	<jstl:choose>
		<jstl:when test="${row.isFlagged==true}">
			<jstl:set var="colorClass" value="colorRed" />
		</jstl:when>
		<jstl:when test="${row.isFlagged==false && row.moment<currentMoment}">
			<jstl:set var="colorClass" value="colorGreen" />
		</jstl:when>
		<jstl:when test="${row.isFlagged==false}">
			<jstl:set var="colorClass" value="colorWhite" />
		</jstl:when>
	</jstl:choose>

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column class="${colorClass}" property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="rendezvous.description" var="descriptionHeader"/>
	<display:column class="${colorClass}" property="description" title="${descriptionHeader}" sortable="true"/>
	
	<spring:message code="rendezvous.moment" var="momentHeader" />
	<spring:message code="rendezvous.formatMoment" var="formatHeader" />
	<display:column class="${colorClass}" property="moment" title="${momentHeader}" format="${formatHeader}" />

	<spring:message code="rendezvous.creator" var="creatorHeader" />
	<display:column class="${colorClass}" title="${creatorHeader}">
		<a href="user/profile.do?userId=${row.creator.id}">
			 <jstl:out value="${row.creator.name}"/>
		</a>
	</display:column>
	
	<spring:message code="rendezvous.attendants" var="attendantsHeader" />
	<display:column class="${colorClass}" title="${attendantsHeader}">
		<jstl:forEach items="${row.attendants}" var="attendant">
			<a href="user/profile.do?userId=${attendant.id}">
			 	<jstl:out value="${attendant.name}"/>
			</a>
			<br/>
		</jstl:forEach>
	</display:column>

	<spring:message code="rendezvous.finalMode" var="finalModeHeader" />
	<display:column class="${colorClass}" property="finalMode" title="${finalModeHeader}" />
	
	<spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
	<display:column class="${colorClass}" property="adultOnly" title="${adultOnlyHeader}" />

	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column class="${colorClass}">
			<a href="rendezvous/administrator/remove.do?rendezvousId=${row.id}">
				<spring:message code="rendezvous.remove" /> 		
			</a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="isAnonymous()">
		<display:column class="${colorClass}">
			<jstl:if test="${row.adultOnly==false}">
				<a href="rendezvous/display.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.display" />
				</a>
			</jstl:if>
		</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
				<display:column class="${colorClass}">
					<a href="rendezvous/display.do?rendezvousId=${row.id}">
						<spring:message code="rendezvous.display" />
					</a>
				</display:column>
		</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<display:column class="${colorClass}">
			<a href="rendezvous/user/display.do?rendezvousId=${row.id}">
				<spring:message code="rendezvous.display" />
			</a>
		</display:column>
		<display:column class="${colorClass}">
			<jstl:if test="${row.finalMode==false}">
				<a href="rendezvous/user/edit.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.edit" />
				</a>
			</jstl:if>
		</display:column>
		<display:column class="${colorClass}">
			<jstl:choose>
				<jstl:when test="${isReserved==true }">
					<a href="comment/user/create.do?rendezvousId=${row.id}"> <spring:message
							code="rendezvous.comment" />
					</a>
				</jstl:when>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>

<div id="message">
	<p> 
		<spring:message code="rendezvous.notice" var="varNotice" />
		<jstl:out value="${varNotice}" />
	</p>
</div>

<security:authorize access="hasRole('USER')">
	<p>
		<a href="rendezvous/user/create.do">
			<spring:message code="rendezvous.create" />
		</a>
	</p>
</security:authorize>