console.log('todo.js 실행')

//1.할일등로 함수
function doPost(){
    console.log('doPost()');
    //1. HTML 입력받은 값 가져오기
    let content = document.querySelector('#content').value;
    let deadline=document.querySelector('#deadline').value;
    //2.
    let info={
        content : content,
        deadline : deadline
    }; console.log(info)
    //3. 컨트롤러에게 요청/응답
        //HTTP통신 : 어디에(action/url) 어떻게(method/method) 보낼데이터(name/data) 응답데이터(x/success)
       $.ajax({
            url : '/todo/post.do',
            method :'post' ,
            data : info,
            success : function( result ){
            console.log(result);
            if(result==true){
                // 화면 갱신
                doGet();
            }
            }
       })
    //4.
}

//2. 할일 출력 함수
doGet()
function doGet(){
    //- 스프링(자바) 와 통신 (주고받는)
    //JQUERY AJAX
    //$.ajax(json형식의 통신정보)
    //$.ajax({ 강사님 깃 복붙})
    $.ajax({
        url:'/todo/get.do',
        method:'get',

        success: function result (resultValue){
        console.log(resultValue);
        let tbody=document.querySelector('table tbody')
        let html =``;
            for(let i=0; i<resultValue.length; i++){
            html+=` <tr>
                               <th>${resultValue[i].id}</th>
                               <th>${resultValue[i].content}</th>
                               <th>${resultValue[i].deadline}</th>
                               <th>${resultValue[i].state}</th>
                           </tr>`
            }
        tbody.innerHTML=html;
        }
    })
}
//3. 할일 상태 수정 함수
function doPut(){}
//4. 할일 삭제 함수
function doDelete(){}

