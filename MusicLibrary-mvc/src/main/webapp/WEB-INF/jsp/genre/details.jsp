<%-- 
    Document   : details
    Created on : Dec 15, 2016, 3:54:49 PM
    Author     : Martin Kulisek
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="${genre.name}">
    <jsp:attribute name="body">

        <h3>Description:</h3>
        <c:out value="${genre.description}"/>

        <h3>All songs:</h3>
        <%--<p><my:a href="/songs/?genre=${genre.id}">List all songs with this genre</my:a></p>--%>
        <table class="table table-striped">
            <tr>
                <th>Title</th>
                <th>Musician</th>
                <th>Album Name</th>
            </tr>
            <c:forEach items="${songs}" var="s">
                <tr>
                    <td><c:out value="${s.musician.name}"/></td>
                    <td><c:out value="${s.album.title}"/></td>
                    <td><c:out value="${s.genre.name}"/></td>
                </tr>
            </c:forEach>
        </table>

    </jsp:attribute>
</my:pagetemplate>
