<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                                       </c:choose>" width="230" /></div>
            <div class="col-md-9">
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
            <h2>Songs</h2>
            <table class="table table-striped">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Musician</th>
                </tr>
                <c:forEach items="${album.songs}" var="s">
                    <tr>
                        <td><c:out value="${s.position}" /></td>
                        <td><c:out value="${s.title}" /></td>
                        <td><my:musician musician="${s.musician}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </jsp:attribute>
</my:pagetemplate>