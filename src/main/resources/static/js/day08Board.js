console.log('day08Board');

//1. 저장 메소드 : 실행조건 : 등록버튼 클릭시
function doCreate(){
    console.log("doCreate()")
}
//2. 호출 : 실행조건 : 페이지 열릴 때, 변화(저장, 수정, 삭제가 있을 때 (새로고침) 매개변수x
function doRead(){
    console.log("doRead()")}
//3. 수정 : 실행조건 : 수정버튼 클릭시 매개변수 : 수정할 식별키bno
function doUpdate(){
    console.log("doUpdate()")
}
//4. 삭제 : 실행조건 : 삭제버튼 클릭시 매개변수 : 수정할 식별키bno
function doDelete(){
    console.log("doDelete()")
}