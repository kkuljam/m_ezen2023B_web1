console.log('team3');

//========================================
doRead();
function doCreate(){ // 저장
    console.log( "doCreate()" );
    let name=document.querySelector('#name').value;         console.log(name)
    let phone=document.querySelector('#phone').value;         console.log(phone)
    let info={name : name, phone: phone}
    //------------AJAX-------------
     $.ajax({
                url : '/team3/create',
                method : 'post',
                data :info ,
                success : function result( result ){
                    console.log(result);
                    if(result){
                    alert('저장 성공'); doRead();
                    }else{alert('저장 실패')}
                }
            })
     //--------------------------
}
function doRead(){ //전체 출력
    console.log('doRead()');
    //------------AJAX-------------
     $.ajax({
                url : '/team3/read',
                method : 'post',
                success : function result( result ){
                    console.log(result);
                          let tbody=document.querySelector('#team3');
                             let html="";
                             for(let i =0; i<result.length; i++){
                                 html+=`<tr>
                                                <th> ${result[i].no} </th> <th> ${result[i].name} </th> <th> ${result[i].phone} </th>
                                                <th>
                                                <button onclick="doDelete( ${result[i].no})" >삭제</button>
                                                <button onclick="doUpdate( ${result[i].no})" >수정</button>
                                                <button onclick="doPlus( ${result[i].no})" >인사고과</button>
                                                 </th>
                                            </tr>`
                             }
                             tbody.innerHTML=html;
                }
            })
     //--------------------------
}
function doUpdate(no){ // 수정
    console.log( "doUpdate()" );
    let phone=prompt('수정할 번호를 입력하세요');
    console.log(phone);
    let info={ phone: phone}
    //------------AJAX-------------
     $.ajax({
                url : '/team3/update/'+no,
                method : 'post',
                data :info ,
                success : function result( result ){
                    console.log(result);
                    if(result){
                    alert('수정 성공'); doRead();
                    }else{alert('수정 실패')}
                }
            })
     //--------------------------
}
function doDelete(no){ // 삭제
    console.log( "doDelete()" );
    //------------AJAX-------------
     $.ajax({
                url : '/team3/delete/'+no,
                method : 'post',
                success : function result( result ){
                    console.log(result);
                    if(result){
                    alert('삭제 성공'); doRead();
                    }else{alert('삭제 실패')}
                }
            })
     //--------------------------
}
//--------------------- 추가 요구-----------------
function doPlus(no){ // 저장
    console.log( "doPlus()" );
     let html=`<h3> 인사고과 </h3>
                     <div id="box2">
                         내역 : <input id="content" />   <br/>
                         점수 : <input id="point"/>  <br/>
                         <button id="saveBtn1" onclick="doCreate()"> 등록 </button>
                     </div>

                     <table>
                         <thead>
                         <tr>
                             <th> 날짜 </th> <th> 내역 </th> <th> 점수 </th> <th> 비고 </th>
                         </tr>
                         </thead>
                         <tbody id="team3plus">

                         </tbody>
                     </table>`
     let tbody=document.querySelector('#plus');
     tbody.innerHTML=html;
     doPlusRead();
}
function doPlusCreate(no){ // 저장
    console.log( "doPlusCreate()" );
    let content=document.querySelector('#content').value;         console.log(content)
    let point=document.querySelector('#point').value;         console.log(point)
    let info={no:no , content : content, point: point}
    //------------AJAX-------------
     $.ajax({
                url : '/team3/pluscreate',
                method : 'post',
                data :info ,
                success : function result( result ){
                    console.log(result);
                    if(result){
                    alert('등록 성공'); doRead();
                    }else{alert('등록 실패')}
                }
            })
     //--------------------------
}
function doPlusRead(){ //전체 출력
    console.log('doPlusRead()');
    //------------AJAX-------------
     $.ajax({
                url : '/team3/plusread',
                method : 'post',
                success : function result( result ){
                    console.log(result);
                          let tbody=document.querySelector('#team3plus');
                             let html="";
                             for(let i =0; i<result.length; i++){
                                 html+=`<tr>
                                                <th> ${result[i].datetime} </th> <th> ${result[i].content} </th> <th> ${result[i].point} </th>
                                                <th>

                                                 </th>
                                            </tr>`
                             }
                             tbody.innerHTML=html;
                }
            })
     //--------------------------
}