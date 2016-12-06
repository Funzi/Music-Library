<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="musician" required="true" type="cz.muni.fi.pa165.api.dto.MusicianDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>

<my:a href="/musicians/${musician.id}"><c:out value="${musician.name}" /></my:a>