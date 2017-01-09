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
            <sec:authorize access="hasAuthority('admin')">
                <p class="pull-right">
                    <my:a href="/songs/${songDTO.id}/edit"><button type="button" class="btn btn-primary btn-sm"><fmt:message key="song.button.edit"/> </button> </my:a>
                    <my:a href="/songs/${songDTO.id}/delete" data-confirm="Are you sure to delete this song?"><button type="button" class="btn btn-danger btn-sm"><fmt:message key="button.delete"/></button> </my:a>
                    </p>
            </sec:authorize>
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
                <tr>
                    <th><fmt:message key="attributes.rating"/></th>
                    <td><my:rating rating="${songDTO.avgRating}" includeValue="true" /></td>
                </tr>
                <c:if test="${songDTO.commentary != null}">
                    <tr>
                        <th><fmt:message key="song.attribute.commentary"/>:</th>
                        <td><c:out value="${songDTO.commentary}"/> </td>
                    </tr>
                </c:if>
            </table>
        </div>

        <div>
            <sec:authorize access="isAuthenticated()">
                <c:if test="${!hasRated}">
                    <button id="opener-rating" type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right"><fmt:message key="song.rate"/></button>
                </c:if>
            </sec:authorize>

            <h2><fmt:message key="attributes.rating"/></h2>
            <c:forEach items="${ratings}" var="r">
                <div class="alert alert-info" role="alert">
                    <div class="row">
                        <div class="col-md-11">
                            <c:out value="${r.user.name}" />&nbsp;&nbsp;&nbsp;<my:rating rating="${r.rvalue}" includeValue="true" /></div>
                        <div class="offset8">[ <c:out value="${r.added}" /> ]
                            <sec:authorize access="isAuthenticated() and (authentication.name == '${r.user.username}' or hasAuthority('admin'))">
                                <br><my:a href="/song-rating/${r.id}/delete" data-confirm="Are you sure to delete this rating?"><img src="<c:url value="/images/delete.png" />" title="Delete" alt="Delete" /></my:a>
                            </sec:authorize>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <c:out value="${r.comment}" />
                        </div>
                    </div>

                </div>
            </c:forEach>
        </div>
        <s:message code="song.rate" var="msg"/>
        <div id="dialog-rating" title="${msg}" hidden="hidden">
            <c:url var="rate_action" value="/songs/${songDTO.id}/rate" />
            <form:form action="${rate_action}" method="POST" modelAttribute="ratingForm" class="form">
                <s:bind path="rvalue">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:select type="text" path="rvalue" items="${ratingValues}" class="form-control" placeholder="Rating"
                                     autofocus="true"></form:select>
                        <form:errors path="rvalue"></form:errors>
                        </div>
                </s:bind>

                <s:bind path="comment">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:textarea path="comment" class="form-control" placeholder="Comment" />
                        <form:errors path="comment"></form:errors>
                        </div>
                </s:bind>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            </form:form>
        </div>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        prepareDialog("rating");

        function positionDialog(song) {
        $("#dialog-position").dialog({autoOpen: true});
        $("#dialog-position input[name='song']").val(song);
        }
    </jsp:attribute>
</my:pagetemplate>