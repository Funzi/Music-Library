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
                    <th><fmt:message key="song.attribute.album"/>:</th>
                    <td><my:a href="/albums/${songDTO.album.id}"><c:out value="${songDTO.album.title}"/> </my:a> </td>
                </tr>
                <tr>
                    <th class="col-md-2"><fmt:message key="song.attribute.musician"/>:</th>
                    <td><my:musician musician="${songDTO.musician}"/></td>
                </tr>
                <tr>
                    <th><fmt:message key="song.attribute.genre"/>:</th>
                    <td><my:a href="/songs/?genre=${songDTO.genre.id}"><c:out value="${songDTO.genre.name}"/> </my:a> </td>
                </tr>
                <tr>
                    <th><fmt:message key="song.attribute.position"/>:</th>
                    <td><c:out value="${songDTO.position}"/></td>
                </tr>
                <tr>
                    <th><fmt:message key="song.attribute.bitrate"/>:</th>
                    <td><c:out value="${songDTO.bitrate}"/></td>
                </tr>
                <c:if test="${songDTO.commentary != null}">
                    <tr>
                        <th><fmt:message key="song.attribute.commentary"/>:</th>
                        <td><c:out value="${songDTO.commentary}"/> </td>
                    </tr>
                </c:if>
            </table>
            <p class="pull-right">
                <my:a href="/songs/${songDTO.id}/edit"><button type="button" class="btn btn-primary btn-sm"><fmt:message key="song.button.edit"/> </button> </my:a>
                <my:a href="/songs/${songDTO.id}/delete" data-confirm="Are you sure to delete this song?"><button type="button" class="btn btn-danger btn-sm"><fmt:message key="button.delete"/></button> </my:a>
            </p>
        </div>


    </jsp:attribute>
</my:pagetemplate>