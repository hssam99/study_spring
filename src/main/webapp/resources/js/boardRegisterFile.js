console.log('boardRegisterFile.js loaded');

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
        const registerBtn = document.getElementById('registerBtn');

        // 내가 등록한 파일의 목록을 fileArea 영역에 출력 -> validate 통과한 파일만
        const div = document.getElementById('fileArea');

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
            registerBtn.disabled = false;
        }else{
            registerBtn.disabled = true;
            alert('업로드 불가능한 파일이 포함되어 있습니다.');
        }
    }
})