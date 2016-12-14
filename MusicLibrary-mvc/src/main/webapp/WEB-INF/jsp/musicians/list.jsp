<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Musicians">
<jsp:attribute name="body">

    <c:forEach items="${musicians}" var="m">
        <h2><c:out value="${m.name}" /></h2>
        <h4><c:out value="Average rating of this musician is ${m.avgAlbumRating} stars." /></h4>
        <p>[ <my:a href="/musicians/${m.id}">Show albums</my:a> ]</p>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>
