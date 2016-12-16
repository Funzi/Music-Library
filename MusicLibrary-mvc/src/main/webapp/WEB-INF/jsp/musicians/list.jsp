<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:pagetemplate title="Musicians">
<jsp:attribute name="body">
    <sec:authorize access="hasAuthority('admin')">
            <my:a href="/musicians/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right">Add new musician</button></my:a>
      </sec:authorize>
        
    <c:forEach items="${musicians}" var="m">
        <h2><c:out value="${m.name}" /></h2>
        <p>Average rating of this musician is <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${m.avgAlbumRating}" /> stars.</p>
        <p class="pull-right">
                    <sec:authorize access="hasAuthority('admin')">
                        <my:a href="/musicians/${m.id}/edit"><button type="button" class="btn btn-primary btn-sm">Edit musician</button></my:a>&nbsp;&nbsp;
                        <my:a href="/musicians/${m.id}/delete" data-confirm="Are you sure to delete this musicians?"><button type="button" class="btn btn-danger btn-sm">Delete musician</button></my:a>
                    </sec:authorize>
                </p>
        <p>[ <my:a href="/musicians/${m.id}">Show albums</my:a> ]</p>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>
