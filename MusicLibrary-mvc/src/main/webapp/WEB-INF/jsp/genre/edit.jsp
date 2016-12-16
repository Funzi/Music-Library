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

<my:pagetemplate title="Genres">
    <jsp:attribute name="body">

        <form:form method="POST" modelAttribute="form" class="form-signin">
        <h2 class="form-signin-heading">Edit genre</h2>
        <s:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control" placeholder="Genre name"
                            autofocus="true"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </s:bind>

        <s:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="description" class="form-control" placeholder="Genre description"></form:input>
                <form:errors path="description"></form:errors>
            </div>
        </s:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="button.edit"/></button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>
