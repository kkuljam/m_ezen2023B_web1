console.log('member.js')


//2. 로그인
function login(){
    console.log('login')
    //1. HTML 입력값 호출 [document.querySelector()]
    let id =document.querySelector('#id').value; console.log(id);
    let pw=document.querySelector('#pw').value; console.log(pw);
    //2. 객체화
    let info={id : id , pw : pw};
    console.log(info);
    //3. 객체를 배열에 저장
    $.ajax({
        url : '/member/login',
        method : 'POST',
        data :  info  ,
        success : function ( result ){
            console.log(result)
            if(result){
            alert('로그인성공');
            }else{
            alert('로그인 실패')
            }
        }
    })
    //4. 결과

}
//1. 회원가입
function signup(){
    //1. HTML 입력값 호출 [document.querySelector()]
    let id=document.querySelector('#id').value;         console.log(id)
    let pw=document.querySelector('#pw').value;         console.log(pw)
    let name=document.querySelector('#name').value;     console.log(name)
    let phone=document.querySelector('#phone').value;   console.log(phone)
    let email=document.querySelector('#email').value;   console.log(email)
    let img=document.querySelector('#img').value;       console.log(img)
    //2. 객체화
    let info={
        id : id , pw : pw, name : name, phone: phone, email : email , img: img
    }; console.log(info);
    //3. 객체를 배열에 저장 --> spring controller 서버 와 통신 []
    $.ajax({
            url : '/member/signup',
            method : 'POST',
            data : info,
            success : function result( result ){
                console.log(result);
                //4. 결과
                if(result){
                    alert('회원가입 성공');
                    location.href='/member/login';
                }else {
                    alert('회원가입 실패');
                }
            }
        })
    //4. 결과
}