<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Albums">
    <jsp:attribute name="body">

        <form:form method="POST" modelAttribute="albumForm">
        <h2 class="form-signin-heading">Add new album</h2>
        <s:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="title" class="form-control" placeholder="Album title"
                            autofocus="true"></form:input>
                <form:errors path="title"></form:errors>
            </div>
        </s:bind>

        <s:bind path="releaseDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="date" path="releaseDate" class="form-control" placeholder="Release date"></form:input>
                <form:errors path="releaseDate"></form:errors>
            </div>
        </s:bind>

        <s:bind path="releaseDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:textarea path="commentary" class="form-control" placeholder="Commentary" />
                <form:errors path="commentary"></form:errors>
            </div>
        </s:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>