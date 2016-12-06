<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="rating" required="true" type="Double" %>
<%@ attribute name="includeValue" required="false" type="Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="i" begin="1" end="5">
    <c:choose>
        <c:when test="${rating >= i}">
            <img src="<c:url value="/images/rating_full.png"/>" width="16" />
        </c:when>
        <c:when test="${rating >= (i - 0.5)}">
            <img src="<c:url value="/images/rating_half.png"/>" width="16" />
        </c:when>
        <c:otherwise>
            <img src="<c:url value="/images/rating_empty.png"/>" width="16" />
        </c:otherwise>
    </c:choose>
</c:forEach>
<c:if test="${includeValue}">
    (<fmt:formatNumber type="number" maxFractionDigits="1" value="${rating}" />)
</c:if>