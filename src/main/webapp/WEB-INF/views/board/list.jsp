<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="../layout/header.jsp" />
<div class="container-sm">
    <h3 class="m-5 text-center">게시판 리스트 페이지</h3>
    <nav class="">
        <div class="container-fluid">
            <form class="d-flex" role="search" action="/board/list" method="get">
                <select class="form-select me-2" name="type" style="width: auto;">
                    <option value="t">제목</option>
                    <option value="w">작성자</option>
                    <option value="c">내용</option>
                    <option value="tc">제목 + 내용</option>
                    <option value="wc">작성자 + 내용</option>
                    <option value="tw">제목 + 작성자</option>
                    <option value="twc">전체</option>
                </select>
                <input class="form-control me-2" name="keyword" type="search" placeholder="Search"/>
                <button class="btn btn-outline-primary" type="submit">Search</button>
            </form>
        </div>
    </nav>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">no.</th>
            <th scope="col">title</th>
            <th scope="col">writer</th>
            <th scope="col">작성일</th>
            <th scope="col">조회수</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list }" var="b">
            <tr>
                <th scope="row">${b.bno }</th>
                <td class="py-3">
                    <a href="/board/detail?bno=${b.bno }" class="text-decoration-none text-dark">${b.title }</a>
                    <span class="text-secondary">[${b.cmtQty}]</span>
                    <c:if test="${b.fileQty ne 0}">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-paperclip text-secondary" viewBox="0 0 16 16">
                            <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a3.5 3.5 0 0 1-7 0V5a.5.5 0 0 1 1 0v7a2.5 2.5 0 0 0 5 0V3a1.5 1.5 0 0 0-3 0v9a.5.5 0 0 1-1 0V5z"/>
                        </svg>
                    </c:if>
                </td>
                <td class="py-3">${b.writer }</td>
                <td class="py-3">${b.regDate }</td>
                <td class="py-3">${b.readCount }</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
                <li class="page-item"><a class="page-link" href="#">${i }</a></li>
            </c:forEach>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
    <a class="btn btn-primary" href="/" role="button">Home</a>
    <a class="btn btn-primary" href="/board/register" role="button">글쓰기</a>
</div>
<jsp:include page="../layout/footer.jsp" />
