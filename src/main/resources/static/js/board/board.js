

// 1. 전체 출력용 : 함수 - 매개변수 = page , 반환 x , 언제 실행할껀지 : 페이지 열릴때(JS)
doViewList( 1 ); // 첫페이지 실행
function doViewList( page ){   console.log( "doViewList()");
    $.ajax({
        url : "/board/do" ,
        method : "get" ,
        data : { 'page' : page },
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
                                 <th> ${ board.bno }</th>       <td> ${ board.btitle } </td>
                                 <td> <img src="/img/${ board.mimg }"
                                       style="width:20px; border-radius:50%;" /> ${ board.mid }
                                 </td>
                                 <td> ${ board.bdate } </td>    <td> ${ board.bview } </td>
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
        }
    }); // ajax end
    return;
}
