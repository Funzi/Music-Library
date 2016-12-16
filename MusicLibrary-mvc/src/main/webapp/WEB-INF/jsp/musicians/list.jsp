<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:message code="musicians" var="musicians_msg"/>
<my:pagetemplate title="${musicians_msg}">
<jsp:attribute name="body">
    <sec:authorize access="hasAuthority('admin')">
            <my:a href="/musicians/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right"><fmt:message key="musician.add_new"/></button></my:a>
      </sec:authorize>
        
    <c:forEach items="${musicians}" var="m">
        <h2><c:out value="${m.name}" /></h2>
        <p><fmt:message key="musician.avg_rating_message"/>&nbsp<fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${m.avgAlbumRating}" />&nbsp<fmt:message key="musician.stars"/></p>
        <p class="pull-right">
                    <sec:authorize access="hasAuthority('admin')">
                        <s:message code="musician.delete.message" var="msg"/>
                        <my:a href="/musicians/${m.id}/edit"><button type="button" class="btn btn-primary btn-sm"><fmt:message key="musician.edit"/></button></my:a>&nbsp;&nbsp;
                        <my:a href="/musicians/${m.id}/delete" data-confirm="${msg}"><button type="button" class="btn btn-danger btn-sm"><fmt:message key="musician.delete"/></button></my:a>
                    </sec:authorize>
                </p>
        <p>[ <my:a href="/musicians/${m.id}"><fmt:message key="musician.show_albums"/></my:a> ]</p>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>
