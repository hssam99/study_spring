<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container m-auto" style="max-width: 1000px;">
    <h3 class="m-5 text-center">게시판 글쓰기 페이지</h3>
    <sec:authentication property="principal.userVO.name" var="authName"/>
    <form action="/board/insert" method="post" enctype="multipart/form-data">
        <%-- CSRF 토큰 추가 --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
            <label for="t" class="form-label">Title</label>
            <input type="text" class="form-control" id="t" name="title" placeholder="title">
        </div>
        <div class="mb-3">
            <label for="w" class="form-label">writer</label>
            <input type="text" class="form-control" id="w" name="writer" value="${authName}" readonly>
        </div>
        <div class="mb-3">
            <label for="c" class="form-label">Content</label>
            <textarea class="form-control" id="c" name="content" rows="3" placeholder="contents"></textarea>
        </div>
        <%-- 첨부파일 업로드 라인 --%>
        <div class="mb-3">
            <input type="file" class="form-control" id="uploadFile" name="file" placeholder="files" multiple="multiple" style="display: none;">
            <button id="trigger" type="button" class="btn btn-outline-primary">file</button>
        </div>
        <!-- 업로드한 파일 목록 보여주는 영역 -->
        <div id="fileArea" class="mb-3"></div>
        <button id="registerBtn" type="submit" class="btn btn-primary">글쓰기</button>
        <a class="btn btn-primary" href="/" role="button">Home</a>
    </form>
    <script type="text/javascript" src="/resources/js/boardRegisterFile.js"></script>
</div>
<jsp:include page="../layout/footer.jsp" />
