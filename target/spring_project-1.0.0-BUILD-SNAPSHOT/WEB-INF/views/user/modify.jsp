<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container-sm">
<%--    로그인 되어있어야지만 접근 가능한 페이지--%>
    <sec:authentication property="principal.userVO" var="userVO"/>
    <form action="/user/modify" method="post">
        <%-- CSRF 토큰 추가 --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="container mt-5">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <h3 class="mb-4">회원 정보 수정</h3>

                    <!-- 이메일 -->
                    <div class="mb-3">
                        <label for="e" class="form-label">이메일</label>
                        <input type="text" class="form-control" id="e" name="email" readonly value="${userVO.email}">
                    </div>

                    <!-- 비밀번호 -->
                    <div class="mb-3">
                        <label for="p" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="p" name="pw" placeholder="새 비밀번호 입력">
                        <small class="text-muted">변경하지 않으려면 비워두세요</small>
                    </div>

                    <!-- 닉네임 -->
                    <div class="mb-3">
                        <label for="n" class="form-label">닉네임</label>
                        <input type="text" class="form-control" id="n" name="name" value="${userVO.name}">
                    </div>

                    <!-- 가입일 -->
                    <div class="mb-3">
                        <label class="form-label">가입일</label>
                        <input type="text" class="form-control" readonly value="${userVO.regDate}">
                    </div>

                    <!-- 최근 접속 -->
                    <div class="mb-4">
                        <label class="form-label">최근 접속</label>
                        <input type="text" class="form-control" readonly value="${userVO.lastLogin}">
                    </div>

                    <!-- 권한 -->
                    <div class="mb-3">
                        <label class="form-label">권한</label>
                        <div>
                            <c:forEach items="${userVO.authList}" var="auth">
                                <span class="badge bg-primary me-2">${auth.auth}</span>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- 버튼 -->
                    <button type="submit" class="btn btn-primary w-100 mb-2">수정하기</button>
                    <a href="/user/delete" class="btn btn-outline-danger w-100">탈퇴하기</a>
                </div>
            </div>
        </div>
    </form>



</div>
<script type="text/javascript">
    const modify_msg = `<c:out value = "${modify_msg}"/>`;
    console.log(modify_msg);
    if(modify_msg === 'fail'){
        alert('회원정보 수정 실패. 다시 시도해주세요.');
    }

    const delete_msg = `<c:out value = "${delete_msg}"/>`;
    console.log(delete_msg);
    if(delete_msg === 'fail'){
        alert('회원탈퇴 실패. 다시 시도해주세요.');
    }
</script>
<jsp:include page="../layout/footer.jsp" />
