<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Rendezvous Co., Inc." />
</div>

<div>
	<ul id="jMenu">		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/create.do?role=user"><spring:message code="master.page.register.user"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/list.do"><spring:message code="master.page.user.list"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/list.do"><spring:message code="master.page.rendezvous.list"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/list.do"><spring:message code="master.page.rendezvous.list"/></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/list.do"><spring:message code="master.page.user.list"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/create.do"><spring:message code="master.page.rendezvous.create"/></a></li>
					<li><a href="rendezvous/user/list.do"><spring:message code="master.page.rendezvous.list.rsvp"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.announcement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/user/create.do"><spring:message code="master.page.announcement.create"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message code="master.page.dashboard" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="dashboard/administrator/display.do"><spring:message code="master.page.dashboard.display"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

