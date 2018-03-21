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
	<a href="welcome/index.do"><img src="images/logo.png" alt="Acme Rendezvous Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register.do"><spring:message code="master.page.register.user"/></a></li>
					<li><a href="actor/register.do"><spring:message code="master.page.register.manager"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/list.do"><spring:message code="master.page.user.list"/></a></li>
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
		<!-- 
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/list.do"><spring:message code="master.page.rendezvous.list"/></a></li>
				</ul>
			</li>
		 -->
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="actor/profile.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MANAGER')">
						<li><a href="actor/profile.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.service" />
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="service/list.do"><spring:message code="master.page.availableService" /></a></li>
					<security:authorize access="hasRole('MANAGER')">
						<li><a href="service/manager/list.do"><spring:message code="master.page.ownService" /></a></li>
					</security:authorize>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/list.do"><spring:message code="master.page.user.list"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/create.do"><spring:message code="master.page.rendezvous.create"/></a></li>
					<li><a href="rendezvous/list.do"><spring:message code="master.page.rendezvous.list"/></a></li>
					<li><a href="rendezvous/user/createdRendezvouses.do"><spring:message code="master.page.rendezvous.list.createdRendezvouses"/></a></li>
					<li><a href="rendezvous/user/list.do"><spring:message code="master.page.rendezvous.list.rsvp"/></a></li>
					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.announcement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/user/list.do"><spring:message code="master.page.announcement.list.rsvp"/></a></li>
					
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
			<li><a class="fNiv"><spring:message code="master.page.rendezvous" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/list.do"><spring:message code="master.page.rendezvous.list"/></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.comment" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="comment/administrator/list.do"><spring:message code="master.page.comment.list"/></a></li>
				</ul>
			</li>
		</security:authorize>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

