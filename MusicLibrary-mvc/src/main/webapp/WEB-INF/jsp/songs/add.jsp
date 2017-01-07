<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="song.new" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">
        <form:form method="POST" modelAttribute="songForm" class="form-signin">
           
            <h4><fmt:message key="song.attribute.title"/>:</h4>
            <s:bind path="title">
                <s:message code="song.attribute.title" var="msg"/>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="title" class="form-control" placeholder="${msg}" autofocus="true"/>
                    <form:errors path="title"/>
                </div>
            </s:bind>
            <h4><fmt:message key="song.attribute.position"/>:</h4>
            <s:bind path="position">
                <s:message code="song.attribute.position" var="msg"/>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="number" path="position" class="form-control" placeholder="${msg}"/>
                    <form:errors path="position"/>
                </div>
            </s:bind>
            <h4><fmt:message key="song.attribute.bitrate"/>:</h4>
            <s:bind path="bitrate">
                <s:message code="song.attribute.bitrate" var="msg"/>
               <div class="form-group ${status.error ? 'has-error' : ''}">
                   <form:input type="number" path="bitrate" class="form-control" placeholder="${msg}"/>
                   <form:errors path="bitrate"/>
               </div>
            </s:bind>
            <h4><fmt:message key="song.attribute.commentary"/>:</h4>
            <s:bind path="commentary">

               <s:message code="song.attribute.commentary" var="msg"/>
               <div class="form-group ${status.error ? 'has-error' : ''}">
                   <form:textarea path="commentary" class="form-control" placeholder="${msg}" />
                   <form:errors path="commentary"></form:errors>
               </div>
            </s:bind>

            <h4><fmt:message key="attributes.album"/>:</h4>
            <s:bind path="albumId">
                <form:select path="albumId" items="${allAlbums}" itemLabel="title" itemValue="id" class="form-control"></form:select>
                <form:errors path="albumId"></form:errors>
            </s:bind>
            <h4><fmt:message key="attributes.musician"/>:</h4>
            <s:bind path="musicianId">
                <form:select path="musicianId" items="${allMusicians}" itemLabel="name" itemValue="id" class="form-control"></form:select>
            </s:bind>
            <h4><fmt:message key="attributes.genre"/>:</h4>               
            <s:bind path="genreId">
                <form:select path="genreId" items="${allGenres}" itemLabel="name" itemValue="id" class="form-control"></form:select>
            </s:bind>
            <br/>
            <br/>
           <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="form.submit"/> </button>
        
        </form:form>
    </jsp:attribute>
</my:pagetemplate>