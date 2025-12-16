<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container mt-5">
    <h2 class="mb-4">회원 목록</h2>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>이메일</th>
            <th>닉네임</th>
            <th>권한</th>
            <th>가입일</th>
            <th>최근 접속</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList }" var="userVO">
            <tr>
                <td>${userVO.email}</td>
                <td>${userVO.name}</td>
                <td>
                    <c:forEach items="${userVO.authList}" var="auth">
                        <span class="badge bg-primary me-1">${auth.auth}</span>
                    </c:forEach>
                </td>
                <td>${userVO.regDate}</td>
                <td>${userVO.lastLogin}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="../layout/footer.jsp" />
