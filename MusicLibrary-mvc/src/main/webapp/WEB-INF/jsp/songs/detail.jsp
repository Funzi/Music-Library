<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="${songDTO.title}">
    <jsp:attribute name="body">

        <div class="row">
            <table class="table">
                <tr>
                    <th>Album:</th>
                    <td><my:a href="/albums/${songDTO.album.id}"><c:out value="${songDTO.album.title}"/> </my:a> </td>
                </tr>
                <tr>
                    <th class="col-md-2">Musician:</th>
                    <td><my:musician musician="${songDTO.musician}"/></td>
                </tr>
                <tr>
                    <th>Genre:</th>
                    <td><my:a href="/songs/?genre=${songDTO.genre.id}"><c:out value="${songDTO.genre.name}"/> </my:a> </td>
                </tr>
                <tr>
                    <th>Position:</th>
                    <td><c:out value="${songDTO.position}"/></td>
                </tr>
                <tr>
                    <th>Bitrate:</th>
                    <td><c:out value="${songDTO.bitrate}"/></td>
                </tr>
                <c:if test="${songDTO.commentary != null}">
                    <tr>
                        <th>Commentary:</th>
                        <td><c:out value="${songDTO.commentary}"/> </td>
                    </tr>
                </c:if>
            </table>
            <p class="pull-right">
                <my:a href="/songs/${songDTO.id}/delete" data-confirm="Are you sure to delete this song?"><button type="button" class="btn btn-danger btn-sm">Delete song</button> </my:a>
            </p>
        </div>


    </jsp:attribute>
</my:pagetemplate>