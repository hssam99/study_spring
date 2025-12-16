<%--
  Created by IntelliJ IDEA.
  User: suminhong
  Date: 2025. 12. 5.
  Time: 오후 4:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <sec:csrfMetaTags/>
    <meta name="_csrf" content="CSRF_TOKEN_VALUE"/>
    <meta name="_csrf_header" content="X-CSRF-TOKEN"/>
    <title>My Spring</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <script type="text/javascript" src="/resources/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Spring</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/board/register">게시판 글쓰기</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/board/list">게시판 리스트 조회</a>
                </li>

                <sec:authorize access="isAnonymous()">
                <%-- 인증이 안되어야 허용 --%>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/user/register">회원가입</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/user/login">로그인</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <%-- 인증이 되어야만 허용 --%>
                    <%-- 인증 후 객체 가져오기 -> 현재 로그인 정보 : principal --%>
                <sec:authentication property="principal" var="loginUser"/>
                    <li class="nav-item">
                        <form action="/user/logout" method="post" id="logoutForm">
                                <%-- CSRF 토큰 추가 --%>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <a class="nav-link active" aria-current="page" href="" id="logoutLink">로그아웃</a>
                        </form>
                    </li>
                    <li class="nav-item">
                            ${loginUser.userVO.name}님 안녕하세요!
                        <a class="nav-link active" aria-current="page" href="/user/modify">회원정보 수정</a>
                    </li>
                <sec:authorize access="hasRole('ADMIN')">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/user/list">ADMIN</a>
                </li>
                </sec:authorize>
                </sec:authorize>
        </div>
    </div>
</nav>

<script type="text/javascript">
    document.getElementById("logoutLink").addEventListener('click', (e)=>{
        e.preventDefault(); // 기존 a 태그의 링크를 없애는 역할
        document.getElementById("logoutForm").submit();
    })
</script>