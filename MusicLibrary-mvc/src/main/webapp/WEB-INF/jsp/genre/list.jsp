<%-- 
    Document   : list
    Created on : Dec 15, 2016, 3:54:49 PM
    Author     : Martin Kulisek
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:message code="genre.genres" var="genres_msg"/>
<my:pagetemplate title="${genres_msg}">
    <jsp:attribute name="body">
       
        <sec:authorize access="hasAuthority('admin')">
            <p><my:a href="/genres/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right"><fmt:message key="genre.add_new"/></button></my:a></p>
        </sec:authorize>
        <br/>
        <table class="table table-striped">
            <tr>
                <th><fmt:message key="genre.name"/></th>
                <th><fmt:message key="attributes.description"/></th>
                <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center"><fmt:message key="form.actions"/></th>
                </sec:authorize>
            </tr>
            <c:forEach items="${genres}" var="g">
                <tr>
                    <td><my:a href="/genres/${g.id}"><c:out value="${g.name}" /></my:a></td>
                    <td><c:out value="${g.description}"/></td>
                    <sec:authorize access="hasAuthority('admin')">
                    <td align="center">
                        <s:message code="musician.delete.message" var="msg"/>
                        <my:a href="/genres/${g.id}/delete"  data-confirm="${msg}">
                            <s:message code="button.delete" var="msg"/>
                            <s:message code="button.delete.alt" var="msg2"/>
                            <img src="<c:url value="/images/delete.png" />" title="${msg}" alt="${msg2}"/>
                        </my:a>
                        <my:a href="/genres/${g.id}/edit">
                            <img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" />
                        </my:a>
                    </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
    </table>

    </jsp:attribute>
</my:pagetemplate>