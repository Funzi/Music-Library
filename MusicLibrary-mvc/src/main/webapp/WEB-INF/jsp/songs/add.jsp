<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New Song">
    <jsp:attribute name="body">
        <form:form method="POST" modelAttribute="songForm">
            <s:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="title" class="form-control" placeholder="Song title" autofocus="true"/>
                    <form:errors path="title"/>
                </div>
            </s:bind>
            <s:bind path="position">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="number" path="position" class="form-control" placeholder="Song position"/>
                    <form:errors path="position"/>
                </div>
            </s:bind>
            <s:bind path="bitrate">
               <div class="form-group ${status.error ? 'has-error' : ''}">
                   <form:input type="number" path="bitrate" class="form-control" placeholder="Song bitrate"/>
                   <form:errors path="bitrate"/>
               </div>
           </s:bind>
            <s:bind path="commentary">
               <div class="form-group ${status.error ? 'has-error' : ''}">
                   <form:textarea path="commentary" class="form-control" placeholder="Commentary" />
                   <form:errors path="commentary"></form:errors>
               </div>
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


            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>
    </jsp:attribute>
</my:pagetemplate>