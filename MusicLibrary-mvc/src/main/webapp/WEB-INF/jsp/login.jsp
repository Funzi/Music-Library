<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page isELIgnored="false"%>

<my:pagetemplate title="${album.title}">
    <jsp:attribute name="body">
    <h1 align="center">  <fmt:message key="navigation.project"/></h1> 
    <h5> Let us introduce you the Music Library used for managing songs. 
        Each song has a title, bitrate, its position on the album's playlist, and commentary. 
        For the sake of simplicity, each song belongs to exactly one musician, it is part of exactly one album, and can be of exactly one genre. 
        Each album has basic attributes such as date of release, title, commentary, musician and album art. 
        In order to support compilation albums each album can contain songs of different genres from different musicians.</h5>

    <sec:authorize access="!isAuthenticated()">
    <form method="POST" action="<c:url value='/login' />" class="form-signin">
        <h2 class="form-heading"><fmt:message key="content.login"/></h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder=<fmt:message key="content.username"/>
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder=<fmt:message key="content.password"/>/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="content.log_in"/></button>
        </div>
    </form>
    </sec:authorize>
    
    <sec:authorize access="isAuthenticated()">
    <form method="POST" action="<c:url value='/login' />" class="form-signin">
        <h2 class="form-heading"><fmt:message key="content.logout"/></h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <span>${error}</span>
            <form id="logoutForm" method="POST" action="<c:url value="/logout" />">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <my:a href="javascript:document.forms['logoutForm'].submit();" class="btn btn-danger btn-block"><fmt:message key="content.log_out"/></my:a>
        </div>
    </form>
    </sec:authorize>
    </jsp:attribute>
</my:pagetemplate>
