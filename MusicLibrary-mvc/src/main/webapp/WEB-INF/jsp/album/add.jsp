<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<s:message code="albums" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">

        <form:form method="POST" modelAttribute="albumForm" class="form-signin">
        <h2 class="form-signin-heading"><fmt:message key="album.add_new"/></h2>
        <br/>
        <h4><fmt:message key="album.name"/>:</h4>
        <s:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="album.name" var="msg"/>
                <form:input type="text" path="title" class="form-control" placeholder="${msg}"
                            autofocus="true"></form:input>
                <form:errors path="title"></form:errors>
            </div>
        </s:bind>

        <h4><fmt:message key="musician.release_date"/>:</h4>
        <s:bind path="releaseDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="musician.release_date" var="msg"/>
                <form:input type="date" path="releaseDate" class="form-control" placeholder="${msg}"></form:input>
                <form:errors path="releaseDate"></form:errors>
            </div>
        </s:bind>

        <h4><fmt:message key="album.commentary"/>:</h4>
        <s:bind path="commentary">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="album.commentary" var="msg"/>
                <form:textarea path="commentary" class="form-control" placeholder="${msg}" />
                <form:errors path="commentary"></form:errors>
            </div>
        </s:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="form.submit"/></button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>