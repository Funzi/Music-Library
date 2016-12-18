<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<s:message code="songs" var="msg"/>
<my:pagetemplate title="${msg}">
    <jsp:attribute name="body">

        <table class="table table-striped">
            <tr>
                <th><fmt:message key="song.attribute.title"/></th>
                <th><fmt:message key="song.attribute.musician"/></th>
                <th><fmt:message key="song.attribute.album"/></th>
                <th><fmt:message key="song.attribute.genre"/></th>
                <sec:authorize access="hasAuthority('admin')">
                    <th width="75" align="center"><fmt:message key="form.actions"/></th>
                </sec:authorize>
            </tr>
            <c:forEach items="${songDTOList}" var="s">
                <tr>
                    <td><my:a href="/songs/${s.id}"><c:out value="${s.title}"/></my:a></td>
                    <td><c:out value="${s.musician.name}"/></td>
                    <td><c:out value="${s.album.title}"/></td>
                    <td><c:out value="${s.genre.name}"/></td>
                    <sec:authorize access="hasAuthority('admin')">
                    <td align="center">
                        <s:message code="song.delete.confirm" var="msg"/>
                        <my:a href="/songs/${s.id}/delete"  data-confirm="${msg}">
                            <s:message code="button.delete" var="msg"/>
                            <s:message code="button.delete.alt" var="msg2"/>
                            <img src="<c:url value="/images/delete.png" />" title="${msg}" alt="${msg2}"/>
                        </my:a>
                        <my:a href="/songs/${s.id}/edit">
                            <img src="<c:url value="/images/pencil.png" />" title="Edit" alt="Edit" />
                        </my:a>
                    </td>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </table>
       <sec:authorize access="hasAuthority('admin')">
           <my:a href="/songs/add"><button type="button" style="margin-bottom: 20px;" class="btn btn-primary btn-sm pull-right"><fmt:message key="button.addnew"/></button></my:a>          
        </sec:authorize>
    </jsp:attribute>
</my:pagetemplate>
