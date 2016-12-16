<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="musicians" var="musicians_msg"/>
<my:pagetemplate title="${musicians_msg}">
    <jsp:attribute name="body">
    <form:form method="POST" modelAttribute="musicianForm">
        <h2 class="form-signin-heading"><fmt:message key="musician.add_new"/></h2>
        <s:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <s:message code="musician.name" var="msg"/>
                <form:input type="text" path="name" class="form-control" placeholder="${msg}"
                            autofocus="true"></form:input>
                <form:errors path="name"></form:errors>
            </div>
        </s:bind>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="form.submit"/></button>
    </form:form>

    </jsp:attribute>
</my:pagetemplate>
