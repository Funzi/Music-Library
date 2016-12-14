<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="${musician.name}">
<jsp:attribute name="body">

    <c:forEach items="${albums}" var="a">
        <table class="table table-striped">
            <tr>
                <th>Name</th>
                <th>Release date</th>
                <th width="200">Rating</th>
                    <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center">Actions</th>
                    </sec:authorize>
            </tr>
            <c:forEach items="${albums}" var="a">
                <tr>
                    <td><my:a href="/albums/${a.id}"><c:out value="${a.title}" /></my:a></td>
                    <td><c:out value="${a.releaseDate}"/></td>
                    <td><c:out value="${a.avgRating}"/></td>
                </td>
                 
                </tr>
            </c:forEach>
        </table>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>
