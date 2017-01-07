<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<s:message code="albums" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">

        <sec:authorize access="hasAuthority('admin')">
            <my:a href="/albums/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right"><fmt:message key="album.add_new"/></button></my:a>
        </sec:authorize>
        <div class="row">
            <form:form method="GET" modelAttribute="form">
                <div class="col-md-3">
                    <fmt:message key="musicians"/>
                    <s:bind path="musicians">
                        <s:message code="musicians" var="msg"/>
                        <form:select path="musicians" name="musicians" items="${allMusicians}" class="form-control" placeholder="{$msg}" multiple="true"></form:select>
                    </s:bind>
                </div>
                <div class="col-md-3">
                     <fmt:message key="genre.genres"/>
                    <s:bind path="genres">
                        <form:select path="genres" name="genres" items="${allGenres}" class="form-control" placeholder="Genres" multiple="true"></form:select>
                    </s:bind>
                </div>

            </div>

                <p class="pull-right" style="margin-bottom: 20px"><button class="btn btn-sm btn-primary" type="submit"><fmt:message key="album.apply_filter"/></button>&nbsp;<my:a href="/albums/"><button type="button" class="btn btn-danger btn-sm"><fmt:message key="album.reset_filter"/></button></my:a></p>
        </form:form>


        <table class="table table-striped">
            <tr>
                <th><fmt:message key="album.name"/></th>
                <th> <fmt:message key="attributes.musician"/></th>
                <th> <fmt:message key="musician.release_date"/></th>
                <th width="200"> <fmt:message key="album.rating"/></th>
                    <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center"><fmt:message key="musician.actions"/></th>
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
                        <s:message code="album.delete.message" var="deleteMsg"/>
                        <td align="center"><my:a href="/albums/${e.key.id}/edit"><img src="<c:url value="/images/pencil.png" />" title="<fmt:message key="button.edit"/>" alt="<fmt:message key="button.edit"/>" /></my:a>&nbsp;&nbsp;&nbsp;<my:a href="/albums/${e.key.id}/delete" data-confirm="${deleteMsg}"><img src="<c:url value="/images/delete.png" />" title="<fmt:message key="button.delete"/>" alt="<fmt:message key="button.delete"/>" /></my:a></td>
                        </sec:authorize>
                </tr>
            </c:forEach>
        </table>

    </jsp:attribute>
</my:pagetemplate>