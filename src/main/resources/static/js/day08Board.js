
console.log( 'day08Board');

// 1. 저장 메소드 : 실행조건 : 등록 버튼 클릭시    매개변수x , 리턴x
function doCreate(){
    console.log( "doCreate()" );
    let bcontent=document.querySelector('#bcontent').value;         console.log(bcontent)
    let bwriter=document.querySelector('#bwriter').value;         console.log(bwriter)
    let bpw=document.querySelector('#bpw').value;         console.log(bpw)
    let info={bcontent : bcontent, bwriter: bwriter, bpw:bpw}
    //------------AJAX-------------
     $.ajax({
                url : '/board/create',
                method : 'post',
                data :info ,
                success : function result( result ){
                    console.log(result);
                    if(result){
                    alert('글쓰기 성공'); doRead();
                    }else{alert('글쓰기 실패')}
                }
            })
     //--------------------------
}
// 2. 전체 호출 메소드 : 실행조건 : 페이지 열릴때 , 변화(저장,수정,삭제)가 있을때(새로고침)   매개변수 x , 리턴 x
doRead(); // JS 열릴때 최초 실행;
function doRead( ){
    console.log( "doRead()" );
    //------------AJAX-------------
    $.ajax({
        url : '/board/read',
        method : 'get',
        success : function result( result ){
            console.log(result);
             let tbody=document.querySelector('table tbody');
                let html="";
                for(let i =0; i<result.length; i++){
                    html+=`<tr>
                                   <th> ${result[i].bno} </th> <th> ${result[i].bcontent} </th> <th> ${result[i].bwriter} </th>
                                   <th>
                                   <button onclick="doDelete( ${result[i].bno})" >삭제</button>
                                   <button onclick="doUpdate( ${result[i].bno})" >수정</button>
                                    </th>
                               </tr>`
                }
                tbody.innerHTML=html;
        }
    })
     //--------------------------
}
// 3. 수정 메소드 : 실행조건 : 수정 버튼 클릭시    매개변수 : 수정할식별키bno , 리턴 x
function doUpdate( bno ){
    console.log( "doUpdate()" + bno );
    let bcontent=prompt('수정할 내용');
    let bpw=prompt('비밀번호 확인');
        //------------AJAX-------------
         $.ajax({
                    url : '/board/update',
                    method : 'post',
                    data : {bno:bno , bcontent : bcontent , bpw:bpw},
                    success : function result( result ){
                        console.log(result);
                        if(result){
                            alert('수정 성공'); doRead();
                        }else{alert('수정 실패')}
                    }
                })
         //--------------------------
}
// 4. 삭제 메소드 : 실행조건 : 삭제 버튼 클릭시    매개변수 : 삭제할식별키bno , 리턴 x
function doDelete( bno ){
    console.log( "doDelete()" + bno  );
    let bpw=prompt('비밀번호 확인');
    //------------AJAX-------------
     $.ajax({
                url :  '/board/delete/'+bno+'/'+bpw,
                method : 'get',
                data : {bpw:bpw},
                success : function result( result ){
                    console.log(result);
                    if(result){
                        alert('삭제 성공'); doRead();
                    }else{alert('삭제 실패')}
                }
            })
     //--------------------------
 }