<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Genres">
<jsp:attribute name="body">

    <c:forEach items="${genres}" var="genre">
        <h2><c:out value="${genre.name}" /></h2>
        <p><c:out value="${genre.description}" /></p>
        <p>[ <my:a href="/songs/?genre=${genre.id}">List all songs with this genre</my:a> ]</p>
    </c:forEach>

</jsp:attribute>
</my:pagetemplate>