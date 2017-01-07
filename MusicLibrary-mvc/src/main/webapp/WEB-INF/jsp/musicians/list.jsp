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
        
    <table class="table table-striped">
            <tr>
                <th><fmt:message key="musician.name"/></th>
                <th><fmt:message key="album.rating"/></th>
                <th><fmt:message key="albums"/></th>
                <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center"><fmt:message key="form.actions"/></th>
                </sec:authorize>
            </tr>
            <c:forEach items="${musicians}" var="m">
                <tr>
                    <td><c:out value="${m.name}"/></td>
                    <td><p><fmt:message key="musician.avg_rating_message"/>&nbsp<fmt:formatNumber type="number" 
                        maxFractionDigits="2" value="${m.avgAlbumRating}" />&nbsp<fmt:message key="musician.stars"/></p></td>
                    <td>[<my:a href="/musicians/${m.id}"><fmt:message key="musician.show_albums"/></my:a>]</td>
                    <sec:authorize access="hasAuthority('admin')">
                    <td align="center">
                        <s:message code="musician.delete.message" var="msg"/>
                        <my:a href="/musicians/${m.id}/delete"  data-confirm="${msg}">
                            <s:message code="button.delete" var="msg"/>
                            <s:message code="button.delete.alt" var="msg2"/>
                            <img src="<c:url value="/images/delete.png" />" title="${msg}" alt="${msg2}"/>
                        </my:a>
                        <my:a href="/musicians/${m.id}/edit">
                            <img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" />
                        </my:a>
                    </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
    </table>

</jsp:attribute>
</my:pagetemplate>
