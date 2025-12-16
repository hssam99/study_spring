<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container-sm">
<h3>게시판 수정 페이지</h3>
    <c:set var="board" value="${boardFileDTO.board }"/>
    <form action="/board/update" method="post" enctype="multipart/form-data">
        <%-- CSRF 토큰 추가 --%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="mb-3">
            <label for="bno" class="form-label">bno</label>
            <input type="text" class="form-control" id="bno" name="bno" value="${board.bno}" readonly>
        </div>
        <div class="mb-3">
            <label for="t" class="form-label">title</label>
            <input type="text" class="form-control" id="t" name="title" value="${board.title}">
        </div>
        <div class="mb-3">
            <label for="w" class="form-label">writer</label>
            <input type="text" class="form-control" id="w" name="writer" value="${board.writer}" readonly>
        </div>
        <div class="mb-3">
            <label for="c" class="form-label">content</label>
            <textarea class="form-control" id="c" name="content" rows="3">${board.content}</textarea>
        </div>
        <div class="mb-3">
            <label for="rd" class="form-label">regDate</label>
            <input type="text" class="form-control" id="rd" name="regDate" value="${board.regDate}" readonly>
        </div>
        <div class="mb-3">
            <label for="rc" class="form-label">readCount</label>
            <input type="text" class="form-control" id="rc" name="readCount" value="${board.readCount}" readonly>
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
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
                                            <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
                                            <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
                                        </svg>
                                        <a href="/upload/${file.saveDir}/${file.uuid}_${file.fileName}">${file.fileName}</a>
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" class="btn btn-close file-x" id="delFileBtn" data-uuid="${file.uuid}"></button>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="mb-3">
            <input type="file" class="form-control" id="uploadFile" name="file" placeholder="files" multiple="multiple" style="display: none;">
            <button id="trigger" type="button" class="btn btn-outline-primary">file</button>
        </div>
        <!-- 업로드한 파일 목록 보여주는 영역 -->
        <div id="newfileArea" class="mb-3"></div>

        <button type="submit" id="fileAddBtn" class="btn btn-primary">수정하기</button>
        <a href="/board/detail?bno=${board.bno}"><button type="button" class="btn btn-primary">돌아가기</button></a>
    </form>
<script src="/resources/js/boardModify.js"></script>
</div>
<jsp:include page="../layout/footer.jsp" />
