<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="Albums">
    <jsp:attribute name="body">

        <sec:authorize access="hasAuthority('admin')">
            <my:a href="/albums/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right">Add new</button></my:a>
        </sec:authorize>

        <table class="table table-striped">
            <tr>
                <th>Name</th>
                <th>Musician</th>
                <th>Release date</th>
                <th width="200">Rating</th>
                <sec:authorize access="hasAuthority('admin')">
                <th width="75" align="center">Actions</th>
                </sec:authorize>
            </tr>
            <c:forEach items="${albums}" var="e">
                <tr>
                    <td><my:a href="/albums/${e.key.id}"><c:out value="${e.key.title}" /></my:a></td>

                        <td><c:forEach items="${e.value}" var="m" varStatus="loop">
                            <my:musician musician="${m}" />
                            <c:if test="${!loop.last}">,&nbsp;</c:if>
                        </c:forEach>
                    </td>
                    <td><c:out value="${e.key.releaseDate}" /></td>
                    <td><my:rating rating="${e.key.avgRating}" includeValue="true" /></td>
                    <sec:authorize access="hasAuthority('admin')">
                        <td align="center"><my:a href="/albums/${e.key.id}/edit"><img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" /></my:a>&nbsp;&nbsp;&nbsp;<my:a href="/albums/${e.key.id}/delete" data-confirm="Are you sure to delete this album?"><img src="<c:url value="/images/delete.png" />" title="Edit" alt="Edit" /></my:a></td>
                        </sec:authorize>
                </tr>
            </c:forEach>
        </table>

    </jsp:attribute>
</my:pagetemplate>