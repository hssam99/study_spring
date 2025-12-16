console.log(bnoValue);
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


const cmtAddBtn = document.getElementById('cmtAddBtn');

if(cmtAddBtn){
cmtAddBtn.addEventListener('click', () => {
    const writer = document.getElementById('cmtWriter');
    const text = document.getElementById('cmtText');

    if(writer.value === '' || text.value === '') {
        alert('작성자와 댓글 내용을 모두 입력해주세요.');
        return;
    }

    const cmtData = {
        bno: bnoValue,
        writer: writer.innerText,
        content: text.value
    };

    console.log(cmtData);

    // 비동기로 cmtData를 서버로 전송
    postComment(cmtData).then(result => {
        if(result==="1"){
            alert('댓글이 등록되었습니다.');
            text.value = '';
            spreadCmtList(bnoValue);
        }else {
            alert('댓글 등록에 실패했습니다. 다시 시도해주세요.');
        }
    });
});
}



async function postComment(cmtData) {
    try {
        const url = '/comment/post';
        const config = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        console.log(result);
        return result;
    } catch (error) {
        console.error('Error posting comment:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}

// 댓글 목록 가져오기
async function getCmtList(bno, page) {
    try{
        const resp = await fetch(`/comment/list/${bno}/${page}`);
        const cmtList = await resp.json();
        console.log(cmtList);
        return cmtList;

    }catch (error) {
        console.log('Error getting comment list:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}


// 페이지 로드 시 댓글 목록 표시
function spreadCmtList(bno, page=1) {
    getCmtList(bno, page).then(result => {
        console.log(result)
        const cmtListArea = document.getElementById('cmtListArea');
        if (result.cmtList.length === 0) {
            cmtListArea.innerHTML =
                '<div className="mb-3">댓글이 없습니다</div>';
            return;
        }

        let html = '';
        result.cmtList.forEach(cmt => {
            html += `
                <div class="cmtItem" data-cno="${cmt.cno}">
                    <p>
                        <strong>${cmt.writer}</strong>
                        <em>${new Date(cmt.regDate).toLocaleString()}</em>
                    </p>
                    <p>${cmt.content}</p>
                    ${loginUser === cmt.writer ? `
                        <button type="button" class="btn btn-outline-primary btn-sm cmtBtnMod"
                        data-bs-toggle="modal" data-bs-target="#cmtModal">수정</button>
                        <button type="button" class="btn btn-outline-primary btn-sm cmtBtnDel">삭제</button>
                    ` : ''}
                </div>
                <hr/>
            `;
        });
        // 페이지1이면 교체, 아니면 추가
        if (page === 1) {
            cmtListArea.innerHTML = html;
        } else {
            cmtListArea.innerHTML += html;  // += 로 추가!
        }

        let moreBtn = document.getElementById('cmtMoreBtn');
        if(result.paging.pageNo < result.realEndPage){
            moreBtn.style.visibility = 'visible';
            moreBtn.dataset.page = page + 1;
        }else{
            moreBtn.style.visibility = 'hidden';
        }
    })
}

document.addEventListener('click', (e)=>{
    if(e.target && e.target.id === 'cmtMoreBtn'){
        const nextPage = parseInt(e.target.dataset.page);
        spreadCmtList(bnoValue, nextPage);
    }

    if(e.target && e.target.classList.contains('cmtBtnMod')){
        let li = e.target.closest('.cmtItem');
        let cno = li.dataset.cno;
        console.log('Modify comment:', cno);

        let cmtWriter = li.querySelector('p:nth-of-type(1) strong').innerText;
        let cmtText = li.querySelector('p:nth-of-type(2)').innerText;

        console.log('Modify comment:', cmtWriter, typeof cmtWriter);
        console.log('Modify comment:', cmtText);

        // 모달에 데이터 채우기
        document.getElementById('cmtModalCno').value = cno;
        document.getElementById('cmtModalWriter').innerText = cmtWriter;
        document.getElementById('cmtTextMod').value = cmtText;  // textarea니까 .value OK
    }

    if (e.target && e.target.id === 'cmtModBtn') {
        let cmtData = {
            cno: document.getElementById('cmtModalCno').value,
            content: document.getElementById('cmtTextMod').value
        };

        console.log('수정 요청:', cmtData);

        // 수정 API 호출
        updateComment(cmtData).then(result => {
            if (result === "1") {
                alert('댓글이 수정되었습니다.');
                // 목록 새로고침
                spreadCmtList(bnoValue);
                // 모달 닫기
                document.querySelector(".btn-close").click();
            } else {
                alert('수정 실패');
            }
        });
    }

    if(e.target && e.target.classList.contains('cmtBtnDel')){
        let li = e.target.closest('.cmtItem');
        let cno = li.dataset.cno;
        console.log('Delete comment:', cno);

        if(confirm('정말 삭제하시겠습니까?')){
            // 삭제 API 호출
            deleteComment(cno, bnoValue).then(result => {
                if(result === "1"){
                    alert('댓글이 삭제되었습니다.');
                    spreadCmtList(bnoValue);
                }else{
                    alert('삭제 실패');
                }
            });
        }
    }
})

// 댓글 수정
async function updateComment(cmtData) {
    try {
        const url = '/comment/modify';
        const config = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        console.log(result);
        return result;
    } catch (error) {
        console.error('Error updating comment:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}

// 댓글 삭제
async function deleteComment(cno, bno) {
    try {
        const url = `/comment/remove/${cno}/${bno}`;
        const config = {
            method: 'DELETE',
            headers: {
                [csrfHeader]: csrfToken
            }
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        // console.log(result);
        return result;
    } catch (error) {
        console.error('Error deleting comment:', error);
        alert('서버와의 통신 중 오류가 발생했습니다.');
    }
}



