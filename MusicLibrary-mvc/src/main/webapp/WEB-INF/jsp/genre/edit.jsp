<%-- 
    Document   : edit
    Created on : Dec 15, 2016, 10:15:36 PM
    Author     : Asus N55
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">

        <form:form method="POST" modelAttribute="form" class="form-signin">
        <h2 class="form-signin-heading"><fmt:message key="genre.edit"/></h2>
        <br/>
        <h4><fmt:message key="attributes.name"/>:</h4>
        <s:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="attributes.name" var="msg"/>
                <form:input type="text" path="name" class="form-control" placeholder="${msg}"
                            autofocus="true"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </s:bind>
        
        <h4><fmt:message key="attributes.description"/>:</h4>
        <s:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="attributes.description" var="msg"/>
                <form:input type="text" path="description" class="form-control" placeholder="${msg}"></form:input>
                <form:errors path="description"></form:errors>
            </div>
        </s:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="button.edit"/></button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>
