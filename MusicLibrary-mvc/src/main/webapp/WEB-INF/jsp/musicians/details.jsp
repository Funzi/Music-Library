<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="${musician.name}">
    <jsp:attribute name="body">

        <h2>Albums</h2>
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="musician.album_name"/></th>
                <th><fmt:message key="musician.release_date"/></th>
                <th width="200"><fmt:message key="musician.rating"/></th>
                    <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center"><fmt:message key="musician.actions"/></th>
                    </sec:authorize>
            </tr>
            <c:forEach items="${albums}" var="a">
                <tr>
                    <td><my:a href="/albums/${a.id}"><c:out value="${a.title}" /></my:a></td>
                    <td><c:out value="${a.releaseDate}"/></td>
                    <td><my:rating rating="${a.avgRating}" includeValue="true" /></td>
                    <sec:authorize access="hasAuthority('admin')">
                        <td align="center"><my:a href="/albums/${a.id}/edit"><img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" /></my:a>&nbsp;&nbsp;&nbsp;<my:a href="/albums/${a.id}/delete" data-confirm="Are you sure to delete this album?"><img src="<c:url value="/images/delete.png" />" title="Delete" alt="Delete" /></my:a></td>
                        </sec:authorize>

                </tr>
            </c:forEach>
        </table>
    </jsp:attribute>
</my:pagetemplate>
