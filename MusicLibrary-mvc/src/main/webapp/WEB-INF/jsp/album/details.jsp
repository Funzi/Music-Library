<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="${album.title}">
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-md-3"><img src="
                                       <c:choose>
                                           <c:when test="${album.art != null}">
                                               data:<c:out value="${album.art.imageType}" />;base64,<c:out value="${album.art.image}" />
                                           </c:when>
                                           <c:otherwise>
                                               <c:url value="/images/not_available.gif"/>
                                           </c:otherwise>
                                       </c:choose>" width="230" />
                <br>
                <form method="POST" enctype="multipart/form-data" action="<c:url value="/albums/${album.id}/upload-cover?${_csrf.parameterName}=${_csrf.token}" />">
                    <input type="file" name="file" /><input type="submit" value="Upload" />
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form></div>
            <div class="col-md-9">
                <p class="pull-right">
                    <sec:authorize access="hasAuthority('admin')">
                        <my:a href="/albums/${album.id}/edit"><button type="button" class="btn btn-primary btn-sm">Edit album</button></my:a>&nbsp;&nbsp;
                        <my:a href="/albums/${album.id}/delete" data-confirm="Are you sure to delete this album?"><button type="button" class="btn btn-danger btn-sm">Delete album</button></my:a>
                    </sec:authorize>
                </p>
                <table class="table">
                    <tr>
                        <th class="col-md-2">Musicians:</th>
                        <td>
                            <c:forEach items="${musicians}" var="m" varStatus="loop">
                                <my:musician musician="${m}" />
                                <c:if test="${!loop.last}">,&nbsp;</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Genres:</th>
                        <td>
                            <c:forEach items="${genres}" var="g" varStatus="loop">
                                <c:out value="${g.name}" />
                                <c:if test="${!loop.last}">,&nbsp;</c:if>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Release date:</th>
                        <td><c:out value="${album.releaseDate}" /></td>
                    </tr>
                    <tr>
                        <th>Rating:</th>
                        <td><my:rating rating="${album.avgRating}" includeValue="true" /></td>
                    </tr>
                    <tr>
                        <th>Commentary:</th>
                        <td><c:out value="${album.commentary}" /></td>
                    </tr>
                </table>
            </div>
        </div>
        <div>
            <p class="pull-right">
                <sec:authorize access="hasAuthority('admin')">
                    <my:a href="/songs/add?album=${album.id}"><button type="button" class="btn btn-primary btn-sm">Add new</button></my:a>&nbsp;&nbsp;
                </sec:authorize>
            </p>
            <h2>Songs</h2>
            <table class="table table-striped">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Musician</th>
                        <sec:authorize access="hasAuthority('admin')">
                        <th width="75" align="center">Actions</th>
                        </sec:authorize>
                </tr>
                <tbody>
                    <c:forEach items="${album.songs}" var="s">
                        <tr>
                            <td><c:out value="${s.position}" /></td>
                            <td><c:out value="${s.title}" /></td>
                            <td><my:musician musician="${s.musician}" /></td>
                            <sec:authorize access="hasAuthority('admin')">
                                <td align="center"><my:a href="/songs/${s.id}/edit"><img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" /></my:a>&nbsp;<my:a href="javascript:positionDialog(${s.id});"><img src="<c:url value="/images/arrow_updown.png" />" title="Update position" alt="Update position" /></my:a>&nbsp;<my:a href="/songs/${s.id}/delete" data-confirm="Are you sure to delete this song?"><img src="<c:url value="/images/delete.png" />" title="Edit" alt="Edit" /></my:a></td>
                                </sec:authorize>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <sec:authorize access="isAuthenticated()">
                <c:if test="${!hasRated}">
                    <button id="opener-rating" type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right">Rate this album!</button>
                </c:if>
            </sec:authorize>
            <h2>Ratings</h2>
            <c:forEach items="${ratings}" var="r">
                <div class="alert alert-info" role="alert">
                    <div class="row">
                        <div class="col-md-11">
                            <c:out value="${r.user.username}" />&nbsp;&nbsp;&nbsp;<my:rating rating="${r.rvalue}" includeValue="true" /></div>
                        <div class="offset8">[ <c:out value="${r.added}" /> ]
                            <sec:authorize access="isAuthenticated() and (authentication.name == '${r.user.username}' or hasAuthority('admin'))">
                                <br><my:a href="/album-rating/${r.id}/delete" data-confirm="Are you sure to delete this rating?"><img src="<c:url value="/images/delete.png" />" title="Delete" alt="Delete" /></my:a>
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

        <div id="dialog-rating" title="Rate album" hidden="hidden">
            <c:url var="rate_action" value="/albums/${album.id}/rate" />
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

        <div id="dialog-position" title="Update position" hidden="hidden">
            <c:url var="update_position_action" value="/songs/update-position" />
            <form action="${update_position_action}" method="POST" class="form">
                <input type="hidden" name="song" />
                <input type="text" class="form-control" placeholder="New position" name="position" autofocus="true" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
            </form>
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