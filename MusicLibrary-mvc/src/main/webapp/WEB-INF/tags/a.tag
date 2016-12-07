<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="href" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value='${href}' var="url" scope="page"/>
<a href="<c:out value='${url}'/>" class="${attr['class']}" <c:if test="${not empty attr['data-confirm']}">data-confirm="${attr['data-confirm']}" </c:if>><jsp:doBody/></a>