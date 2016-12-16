<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="${musician.name}">
<jsp:attribute name="body">

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
                    <sec:authorize access="hasAuthority('admin')">
                        <td align="center"><my:a href="/albums/${a.id}/edit"><img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" /></my:a>&nbsp;&nbsp;&nbsp;<my:a href="/albums/${a.id}/delete" data-confirm="Are you sure to delete this album?"><img src="<c:url value="/images/delete.png" />" title="Edit" alt="Edit" /></my:a></td>
                    </sec:authorize>
                </td>
                 
                </tr>
            </c:forEach>
        </table>
   

</jsp:attribute>
</my:pagetemplate>
