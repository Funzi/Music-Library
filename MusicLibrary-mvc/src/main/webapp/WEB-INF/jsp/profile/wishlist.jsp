<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<s:message code="profile.wishlist" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">

        <table class="table table-striped">
            <tr>
                <th><fmt:message key="album.name"/></th>
                <th width="200">&nbsp;</th>
            </tr>
            <c:forEach items="${wishlist}" var="e">
                <tr>
                    <td><my:a href="/albums/${e.id}"><c:out value="${e.title}" /></my:a></td>
                    <td align="center"><my:a href="/albums/${album.id}/removeFromWishlist"><button type="button" class="btn btn-primary btn-sm"><fmt:message key="album.remove_from_wishlist"/></button></my:a></td>
                </tr>
            </c:forEach>
        </table>

    </jsp:attribute>
</my:pagetemplate>