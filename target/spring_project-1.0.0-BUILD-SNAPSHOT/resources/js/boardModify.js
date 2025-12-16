const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

document.getElementById('trigger').addEventListener('click', () => {
    document.getElementById('uploadFile').click();
});

const regExp = new RegExp('^.*\\.(exe|sh|dll|bat|msi|jar)$');
const maxSize = 1024 * 1024 * 20; // 20MB

function fileValidate(fileName, fileSize) {
    if (regExp.test(fileName)) {
        return 0;
    }
    if (fileSize > maxSize) {
        return 0;
    }
    return 1;
}

document.addEventListener('change', (e)=>{
    if(e.target.id=='uploadFile'){
        const fileObj = document.getElementById('uploadFile').files;
        const fileAddBtn = document.getElementById('fileAddBtn');

        // 내가 등록한 파일의 목록을 fileArea 영역에 출력 -> validate 통과한 파일만
        const div = document.getElementById('newfileArea');

        let isOk = 1; // 각 파일마다 validate 통과 여부 저장
        // 여러 파일 등록 가능
        // 모든 검증 결과가 1이여야 register 버튼 활성화
        let ul = `<ul class="list-group list-group-flush">`;
        for(let file of fileObj){
            console.log(file.name, file.size);
            let validResult = fileValidate(file.name, file.size);
            isOk *= validResult;
            ul+= `<li class="list-group-item ${validResult ? '' : 'text-muted bg-light'}">
                    <span>${file.name} (${(file.size/1024).toFixed(1)} KB)</span>
                    <span class="${validResult? 'text-success':'text-danger'}"></span>
            </li>`;
        }
        ul += `</ul>`
        div.innerHTML = ul;

        if(isOk){
            fileAddBtn.disabled = false;
        }else{
            fileAddBtn.disabled = true;
            alert('업로드 불가능한 파일이 포함되어 있습니다.');
        }
    }
})

// 첨부파일 삭제
document.addEventListener('click', function(e) {
    if(e.target.classList.contains('file-x')) {
        const uuid = e.target.dataset.uuid;
        removeFile(uuid).then(result => {
            console.log('result', result);
            if(result.trim() == '1') {
                const fileLi = e.target.closest('li');
                fileLi.remove();
            } else {
            }
        })
    }
});

async function removeFile(uuid){
    try{
        const url = "/board/deleteFile/"+uuid;
        const config = {
            method : 'DELETE',
            headers: {
                [csrfHeader]: csrfToken
            }
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    }catch{
        console.log(error);
    }
}