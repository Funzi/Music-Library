<%-- 
    Document   : list
    Created on : Dec 15, 2016, 3:54:49 PM
    Author     : Martin Kulisek
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
    <jsp:attribute name="body">

        <sec:authorize access="hasAuthority('admin')">
            <p><my:a href="/genres/add"><button type="button" class="btn btn-primary btn-sm pull-right"><fmt:message key="content.add_new_genre"/></button></my:a></p>
        </sec:authorize>

        <div>
            <h1><fmt:message key="content.genres"/>:</h1>
        <c:forEach items="${genres}" var="genre">
            
             <p class="pull-right">
                    <sec:authorize access="hasAuthority('admin')">
                        <my:a href="/genres/${genre.id}/edit"><button type="button" class="btn btn-primary btn-sm"><fmt:message key="content.edit"/></button></my:a>&nbsp;&nbsp;
                        <my:a href="/genres/${genre.id}/delete" data-confirm="Are you sure to delete this genre?"><button type="button" class="btn btn-danger btn-sm"><fmt:message key="content.delete"/></button></my:a>
                    </sec:authorize>
            </p><h3><my:a href="/genres/${genre.id}"><c:out value="${genre.name}" /></my:a></h3>
            <hr>
        </c:forEach>
        </div>

    </jsp:attribute>
</my:pagetemplate>