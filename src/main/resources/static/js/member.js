
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
            // js 페이지 전환
            location.href="/"; // 로그인 성공하면 메인 페이지
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
    //1. 데이터 하나씩 요청
    /*
    let id=document.querySelector('#id').value;         console.log(id)
    let pw=document.querySelector('#pw').value;         console.log(pw)
    let name=document.querySelector('#name').value;     console.log(name)
    let phone=document.querySelector('#phone').value;   console.log(phone)
    let email=document.querySelector('#email').value;   console.log(email)
    let img=document.querySelector('#img').value;       console.log(img)
    */
    //2. 폼 가져오기
    let signUpForm = document.querySelector('.signUpForm');
        console.log(signUpForm)
    let signUpFormData= new FormData(signUpForm);
        console.log(signUpFormData) // new FormData
    //2. 객체화
    /*
    let info={
        id : id , pw : pw, name : name, phone: phone, email : email , img: img
    }; console.log(info);

    */
    //3. 객체를 배열에 저장 --> spring controller 서버 와 통신 []
    $.ajax({
            url : '/member/signup',
            method : 'POST',
            data : signUpFormData,
            contentType: false,
            processData : false,
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
function onChangeImg(event){
    console.log('preimg');
    console.log(event);
    console.log(event.files); // 현재 input의 첨부파일들
    console.log(event.files[0]); // 첨부파일들 중에 첫번쨰
    //- input 에 업로드된 파일을 바이트로 가져오기
        // new FileReader() : 파일 읽기 관련 메소드 제공
    // 1. 파일 읽기 객체 생성
    let fileReader = new FileReader();
    fileReader.readAsDataURL(event.files[0]);
    console.log(fileReader);

    fileReader.onload =e =>{
        console.log(e);
        console.log(e.target);
        console.log(e.target.result);
        document.querySelector('#preimg').src=e.target.result
    }

}