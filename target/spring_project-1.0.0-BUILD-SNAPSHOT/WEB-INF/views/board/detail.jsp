<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container-sm">
    <h3 class="mt-5 text-center">게시판 상세 페이지</h3>
    <c:set var="board" value="${boardFileDTO.board }"/>
    <div class="card mb-4 mt-5">
        <div class="card-header d-flex justify-content-between align-items-center">
            <span class="fw-bold fs-5">${board.title}</span>
            <span class="text-muted small">No. ${board.bno}</span>
        </div>
        <div class="card-body">
            <div class="d-flex justify-content-between border-bottom pb-2 mb-3">
                <span><i class="bi bi-person"></i> ${board.writer}</span>
                <span class="text-muted">
                <i class="bi bi-calendar"></i> ${board.regDate}
                &nbsp;|&nbsp;
                <i class="bi bi-eye"></i> ${board.readCount}
            </span>
            </div>
            <div class="py-4" style="min-height: 200px; white-space: pre-wrap;">${board.content}</div>
        </div>
        <div class="card-footer">
            <!-- 첨부파일 영역 -->
            <div id="fileArea">
                <div class="mb-3">
                    <ul class="list-group list-group-flush">
                        <c:forEach var="file" items="${boardFileDTO.fileList }">
                            <li class="list-group-item">
                                <c:choose>
                                    <c:when test="${file.fileType eq 'I'}">
                                        <a href="/upload/${file.saveDir}/${file.uuid}_${file.fileName}" target="_blank">
                                            <img src="/upload/${file.saveDir}/${file.uuid}_${file.fileName}"
                                                 style="width: 150px; height: 150px; object-fit: cover; border-radius: 8px;">
                                        </a>
                                    </c:when>
                                    <c:otherwise>
<%--                                        일반 파일 다운로드 가능--%>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                            <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
                                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
                                        </svg>
                                        <a href="/upload/${file.saveDir}/${file.uuid}_${file.fileName}" download="download">${file.fileName}</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <a href="/board/list"><button type="button" class="btn btn-primary">돌아가기</button></a>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal.userVO.name" var="authName"/>
        <c:if test="${board.writer eq authName}">
            <a href="/board/modify?bno=${board.bno}"><button type="button" class="btn btn-primary">수정하기</button></a>
            <form action="/board/delete" method="post" class="d-inline">
                    <%-- CSRF 토큰 추가 --%>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="bno" value="${board.bno}">
                <button type="submit" class="btn btn-danger">삭제하기</button>
            </form>
        </c:if>
    </sec:authorize>

    <%--  comment  --%>
    <%--  post  --%>
    <sec:authorize access="isAuthenticated()">
        <div class="input-group flex-nowrap mt-5 mb-3">
            <span id="cmtWriter" class="input-group-text">${authName}</span>
            <input id="cmtText" type="text" class="form-control" placeholder="댓글을 입력하세요" aria-describedby="addon-wrapping">
            <button id="cmtAddBtn" type="button" class="btn btn-primary">등록하기</button>
        </div>
    </sec:authorize>

    <%--  list  --%>
    <ul class="list-group list-group-flush" id="cmtListArea">
        <%--  spread by JS  --%>
    </ul>

    <div class="m-3 text-center">
        <button data-page="1" id="cmtMoreBtn" type="button" class="btn btn-outline-primary" style="visibility: hidden">
            more +
        </button>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="cmtModal" tabindex="-1" aria-labelledby="cmtModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="cmtModalWriter"></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="cmtModalCno">
                    <textarea class="form-control" id="cmtTextMod" rows="3"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="cmtModBtn">수정</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>


    <script type="text/javascript">
        <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="authUser"/>
        const loginUser = '${authUser.userVO.name}';
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
        const loginUser = '';
        </sec:authorize>

        const bnoValue = `<c:out value="${board.bno}"/>`;
    </script>
    <script type="text/javascript" src="/resources/js/boardDetailComment.js"></script>
    <script type="text/javascript">spreadCmtList(bnoValue);</script>

</div>
<jsp:include page="../layout/footer.jsp" />
