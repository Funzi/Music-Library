<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="song.update" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">

        <form:form method="POST" modelAttribute="form">
            <div class="col-md-6">
                <s:bind path="title">
                    <s:message code="song.attribute.title" var="msg"/>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" path="title" class="form-control" placeholder="${msg}" autofocus="true"/>
                        <form:errors path="title"/>
                    </div>
                </s:bind>
                <s:bind path="position">
                    <s:message code="song.attribute.position" var="msg"/>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="number" path="position" class="form-control" placeholder="${msg}"/>
                        <form:errors path="position"/>
                    </div>
                </s:bind>
                <s:bind path="bitrate">
                    <s:message code="song.attribute.bitrate" var="msg"/>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="number" path="bitrate" class="form-control" placeholder="${msg}"/>
                        <form:errors path="bitrate"/>
                    </div>
                </s:bind>
                <s:bind path="commentary">
                    <s:message code="song.attribute.commentary" var="msg"/>
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:textarea path="commentary" class="form-control" placeholder="${msg}" />
                        <form:errors path="commentary"></form:errors>
                        </div>
                    </div>
                    <div class="col-md-4">
                </s:bind>
                <s:bind path="albumId">
                    <form:select path="albumId" items="${allAlbums}" itemLabel="title" itemValue="id" class="form-control"></form:select>
                </s:bind>
                <s:bind path="musicianId">
                    <form:select path="musicianId" items="${allMusicians}" itemLabel="name" itemValue="id" class="form-control"></form:select>
                </s:bind>
                <s:bind path="genreId">
                    <form:select path="genreId" items="${allGenres}" itemLabel="name" itemValue="id" class="form-control"></form:select>
                </s:bind>
            </div>
            <div class="col-md-2 pull-right">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="form.submit"/> </button>
            </div>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>