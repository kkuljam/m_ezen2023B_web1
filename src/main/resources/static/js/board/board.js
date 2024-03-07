
//페이지 관련 객체 = 여러개 변수 묶음

let pageObject={
    page : 1,   // 현재 페이지
    pageBoardSize : 5, // 페이지당 표시할 게시물 수
    bcno : 0,    // 현재 카테고리
    key : 'b.btitle',   // 현재 key
    keyword : ''        // 현재 keyword
}
// 1. 전체 출력용 : 함수 - 매개변수 = page , 반환 x , 언제 실행할껀지 : 페이지 열릴때(JS)
doViewList( 1 ); // 첫페이지 실행
function doViewList( page ){   console.log( "doViewList()");
    pageObject.page=page; // 매개변수로 들어온 페이지를 현재페이지에 대입
    $.ajax({
        url : "/board/do" ,
        method : "get" ,
        data : pageObject,
        success : (r)=>{    console.log( r );
            // 테이블에 레코드 구성
            // 1. 어디에
            let boardTableBody = document.querySelector("#boardTableBody");
            // 2. 무엇을
            let html = ``;
                // 서버가 보내준 데이터를 출력
                // 1.
                r.list.forEach( board => {
                    console.log( board );
                    html += `<tr>
                                 <th> ${ board.bno }</th>
                                 <td> <a href="/board/view?bno=${board.bno}"> ${ board.btitle } </td>
                                 <td> <img src="/img/${ board.mimg }"
                                       style="width:20px; border-radius:50%;" /> ${ board.mid }
                                 </td>
                                 <td> ${ board.bdate } </td>
                                 <td> ${ board.bview } </td>
                             </tr>`
                });
            // 3. 출력
            boardTableBody.innerHTML = html;

            //==========페이지네이션
            //1. 어디에
            let pagination=document.querySelector('.pagination');
            let pagehtml=``;
                //이전
                pagehtml+=`<li class="page-item"><a class="page-link" onclick="doViewList(${page-1 < 1? 1 :page-1})">이전</a></li>`

                //페이지 번호 버튼
                for(let i=r.startBtn; i<=r.endBtn; i++){
                pagehtml+=`<li class="page-item ${i==page? 'active' : ''}"><a class="page-link" onclick="doViewList(${i})">${i}</a></li>`
                }

                //다음
                 pagehtml+=`<li class="page-item"><a class="page-link" onclick="doViewList(${page+1 < r.totalPage? page+1 : r.totalPage})">다음</a></li>`
                //3. 출력
                pagination.innerHTML=pagehtml;

                document.querySelector('.totalPage').innerHTML = r.totalPage;
                document.querySelector('.totalBoardSize').innerHTML = r.totalBoardSize;

        }
    }); // ajax end
    return;
}
//2. 페이지
function onPageBoardSize(object){
    console.log(object)
    pageObject.pageBoardSize=object.value;
    doViewList(1);
}
//3. 카테고리
function onBcno(bcno){
    //bcno : 카테고리 식별번호 [0: 전체 보기]
    pageObject.bcno=bcno;
    // 검색 제거 (검색이 없다는 기준 데이터)
    pageObject.key = 'b.title';
       pageObject.keyword = '';
       document.querySelector('.key').value = 'b.btitle'
       document.querySelector('.keyword').value = '';

    //1. 모든 카테고리 버튼 호출
    let categoryBtns = document.querySelectorAll(".boardCategoryBox>button");
    console.log(categoryBtns);
    //2. 선택된 카테고리번호(매개변수bcno)에 class 대입
    //categoryBtns[bcno].classList.add()
    //1. 활성화 초기화
    for(let i=0; i<categoryBtns.length ; i++){
        categoryBtns[i].classList.remove("categoryActive");
    }
    //2. 활성화 대입
    categoryBtns[bcno].classList.add("categoryActive");

    doViewList(1);
}
//4. 검색 함수
function onSearch(){
    let key=document.querySelector('.key').value;
    let keyword=document.querySelector('.keyword').value;
    console.log(key,keyword);

    //2. 서버에 전송할 객체에 담아주고
    pageObject.key=key;
    pageObject.keyword=keyword;

    //3. 출력 함수 호출
    doViewList(1);
}

/*
//5. 수정 함수
function onUpdate(){
     $.ajax({
            url : "/board/do" ,
            method : "get" ,
            data : {bno:bno},
            success : (r)=>{    console.log( r );
                // 테이블에 레코드 구성
                // 1. 어디에
                let updateBoardForm = document.querySelector(".updateBoardForm");
                // 2. 무엇을
                let html = ``;
                    // 서버가 보내준 데이터를 출력
                    // 1.
                        console.log( r );
                        html += ` 카테고리 <select name="bcno">
                                         <option value="1">자유</option>
                                         <option value="2">노하우</option>
                                     </select> </br>
                                         제목 <input name="btitle" type="text" value="${r.btitle}"></br>
                                         내용 <textarea id="summernote"name="bcontent">${r.bcontent}</textarea></br>
                                         첨부파일 <input name="uploadfile" type="file"></br>
                                         <button onclick="onUpdate()" type="button">수정</button>`
                // 3. 출력
                boardTableBody.innerHTML = html;
}
*/





















