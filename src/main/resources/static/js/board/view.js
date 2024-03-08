//* 경로(URL)상의 쿼리스트링 (매개변수) 호출하기
    // 1. new URL(location.href): 현재 페이지의 경로호출
//console.log(new URL(location.href));
    //2. 경로상의 (쿼리스트링) 매개변수들
//console.log(new URL(location.href).searchParams);
    //3. [.get('queryStringKey')] (쿼리스트링)매개변수들 에서 특정 매개변수 호출
//console.log(new URL(location.href).searchParams.get('bno'));

let bno=new URL(location.href).searchParams.get('bno');
//1. 게시물 개별 조회
onView();
function onView(){
    console.log( "onView()");
    $.ajax({
        url : "/board/view.do" ,
        method : "get" ,
        data : { "bno" : bno },   // 쿼리스트링.
        success : (r)=>{
            console.log(r);
            document.querySelector('.btitle').innerHTML = r.btitle
            document.querySelector('.bcontent').innerHTML = r.bcontent;

            document.querySelector('.bcno').innerHTML = r.bcno;
            document.querySelector('.mid').innerHTML = r.mid;
            document.querySelector('.bdate').innerHTML = r.bdate;
            document.querySelector('.bview').innerHTML = r.bview;
            //* 다운로드 링크
                //유효성 검사
            if(r.bfile !='null'){
                document.querySelector('.bfile').innerHTML = `<a href="/board/file/download?bfile=${ r.bfile }"> ${ r.bfile } </a>`;
            }
            //* 삭제/ 수정버튼 활성화 ( 해당 보고있는 클라이언트가 게시물 작성자의 아이디와 동일하면)
                // 유효성 검사
                // 현재 로그인된 아이디
                //let loginId= document.querySelector('.top_menu > /login_menu')
                // 현재 로그인된 아이디 또는 번호 (1. 헤더HTML 가져온다 2. )
                $.ajax({
                       url : "/member/login/check",
                       method : 'get',
                       success : (loginId)=>{
                           if( loginId == r.mid ){
                               let btnHTML = `<button type="button" onclick="onDelete( )"> 삭제 </button>`
                               btnHTML +=  `<button type="button" onclick="location.href='/board/update?bno=${ r.bno }'"> 수정 </button>`
                               document.querySelector('.btnBox').innerHTML += btnHTML
                           }
                       }
                })

        }
    }) // ajax end
} // f end

//2. 게시물 삭제 함수
function onDelete(){
    console.log("onDelete()")
    $.ajax({
        url : "/board/delete.do" ,
        method : "delete" ,
        data : { "bno" : bno },   // 쿼리스트링.
        success : (r)=>{
            if(r){
                alert('삭제 성공');
                location.href="/board";
            }else{alert('삭제 실패')}
        }
    }) // ajax end
}

