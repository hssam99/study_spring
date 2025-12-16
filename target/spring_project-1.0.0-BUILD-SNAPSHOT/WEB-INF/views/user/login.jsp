<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container-sm p-5">
    <h3 class="text-center">로그인</h3>
    <form action="/user/login" method="post">
        <%-- CSRF 토큰 추가 --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
            <label for="e" class="form-label">email</label>
            <input type="text" class="form-control" id="e" name="email" placeholder="id@example.com">
        </div>
        <div class="mb-3">
            <label for="p" class="form-label">password</label>
            <input type="text" class="form-control" id="p" name="pw" placeholder="비밀번호 입력">
        </div>
<%--        로그인 실패 시 에러메시지 출력--%>
        <c:if test = "${errorMessage ne null}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>


        <button type="submit" class="btn btn-primary">로그인</button>
    </form>
</div>
<jsp:include page="../layout/footer.jsp" />
