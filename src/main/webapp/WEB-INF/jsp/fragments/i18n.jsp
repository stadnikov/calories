<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    const i18n = [];
    i18n["addTitle"] = window.location.href.endsWith('users') ?
        '<spring:message code="user.add"/>' : '<spring:message code="meal.add"/>';
    i18n["editTitle"] = window.location.href.endsWith('users') ?
        '<spring:message code="user.edit"/>' : '<spring:message code="meal.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>