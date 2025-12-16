<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>


<jsp:include page="layout/header.jsp" />
<div class="container-sm">
    <h1 class="text-center justify-content-center mt-5">My First Spring Project</h1>
</div>

<script type="text/javascript">
    const modify_msg = `<c:out value = "${modify_msg}"/>`;
    console.log(modify_msg);
    if(modify_msg === 'ok'){
        alert('회원정보 수정 완료. 다시 로그인 해주세요.');
    }

    const delete_msg = `<c:out value = "${delete_msg}"/>`;
    console.log(delete_msg);
    if(delete_msg === 'ok'){
        alert('회원탈퇴 완료. 그동안 이용해주셔서 감사합니다.');
    }
</script>


<jsp:include page="layout/footer.jsp" />
