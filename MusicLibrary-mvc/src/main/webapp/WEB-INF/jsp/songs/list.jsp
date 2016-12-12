<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<my:pagetemplate title="Songs">
    <jsp:attribute name="body">

        <table class="table table-striped">
            <tr>
                <th>Title</th>
                <th>Musician</th>
                <th>Album Name</th>
                <th>Genre</th>
                <th width="75" align="center">Actions</th>
            </tr>
            <c:forEach items="${songDTOList}" var="s">
                <tr>
                    <td><my:a href="/songs/${s.id}"><c:out value="${s.title}"/></my:a></td>
                    <td><c:out value="${s.musician.name}"/></td>
                    <td><c:out value="${s.album.title}"/></td>
                    <td><c:out value="${s.genre.name}"/></td>
                    <td align="center"><my:a href="/songs/${s.id}/delete"  data-confirm="Are you sure to delete this song?">
                        <img src="<c:url value="/images/delete.png" />" title="Delete" alt="Delete"/>
                    </my:a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </jsp:attribute>
</my:pagetemplate>
