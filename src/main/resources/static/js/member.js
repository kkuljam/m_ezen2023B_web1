/*
    onclick     : 클릭 할때 마다
    onchange    : 값이 변경될때 마다
    onkeyup     : 키보드 키를 떼었을때.

    ------ 정규표현식 ------
    정규표현식란 : 특정한 규칙을 가진 문자열의 집합을 표현할때 사용하는 형식 언어
        - 주로 문자열 데이터 검사할때 사용 - 유효성검사.
        - 메소드
            정규표현식.test( 검사할대상 );
        - 형식 규칙
            /^      : 정규표현식 시작 알림.
            $/      : 정규표현식 끝 알림
            { 최소길이 , 최대길이 }    : 허용 문자 길이 규칙
            [ 허용할 문자/숫자 ]             : 허용 문자 규칙 규칙
            1. [ a-z ]
            2. [ A-Z ]
            3. [ 0-9 ] , \d
            4. [ !@#$% ]
                [a-z]                   : 소문자 a ~ z 허용
                [a-zA-Z]                : 영 대소문자 a ~ z 허용
                [a-zA-Z0-9]             : 영 대소문자 , 숫자 허용
                [a-zA-Z0-9가-힣]        : 영 대소문자 , 숫자 , 한글 허용
                [ac]                    : a 또는 c 허용
            +   : 앞 에 있는 패턴 1개 이상 반복
            ?   : 앞 에 있는 패턴 0개 혹은 1개 이상 반복
            *   : 앞 에 있느 패턴 0개 반복
            .   : 1개 문자
                (?=.*[1개이상문자패턴])
            ( ) : 패턴의 그룹
            ?!.* : 문자열내 존재하지 않음.
            ?=.* : 문자열내 존재함.

            예1)  /^[a-z0-9]{5,30}$/
                영 소문자와숫자 조합의 5~30글자 허용
            예2)  /^[A-Za-z0-9]{5,30}$/
                영 대소문자와숫자 조합의 5~30글자 허용
            예3) (?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,30}
                영 대소문자 1개 이상 필수 , 숫자 1개 이상 필수
            예4) /^[가-힣]{5,20}$/
                한글 5~20글자
            예5) 000-0000-0000 또는 00-000-0000
                /^ ([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{4}) $/
            예6) 문자@문자.문자
                qwe@naver.com , asdas@kakao.net
                /^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z]+$/

*/
// **** 현재 유효성검사 체크 현황
let checkArray=[false,false,false,false,false]
//4. 아이디 유효성 검사 ( 아이디 입력할때 마다)
function idCheck(){
    console.log('idCheck()');
    let id=document.querySelector('#id').value;
    console.log(id);

    // 간단한 검사 결과
    //2. 정규표현식
    let 아이디규칙= /^[a-z0-9]{5,30}$/

    //3. 정규표현식
    console.log(아이디규칙.test(id));
    if(아이디규칙.test(id)){

        // 아이디 중복체크 (ajax)
        $.ajax({
            method: "get",
            url: `/member/find/idcheck`,
            data: {'id': id},
            success: (r)=>{
                if(r){
                    document.querySelector('.idcheckbox').innerHTML='사용중인 아이디';
                    checkArray[0]=false
                }else{
                    document.querySelector('.idcheckbox').innerHTML='통과';
                    checkArray[0]=true;
                }
            }
        });
    }else{
        document.querySelector('.idcheckbox').innerHTML='영소문자 +숫자';
        checkArray[0]=false;
    }
    // 유효성 검사 결과 출력
    //document.querySelector('.idcheckbox').innerHTML=ld.length;
}

//5.
function pwCheck(){
    console.log("pwcheck()");

    //1. 입력값을 가져온다
    let pw=document.querySelector('#pw').value;
    let pwconfirm=document.querySelector('#pwconfirm').value;

    //유효성검사
    let msg="";
    checkArray[1]=false;
        //1. 비밀번호에 대한 정규표현식 : 영문대소문자 1개 필수 와 숫자 1개 필수
        let 비밀번호규칙=/^(?=.*[A-Za-z])(?=.*[0-9])[a-z0-9]{5,30}$/
        //2
        if(비밀번호규칙.test(pw)){
            if(비밀번호규칙.test(pwconfirm)){
                if(pw==pwconfirm){
                    msg="통과";
                    checkArray[1]=true;
                }else{
                    msg="패스워드 불일치"
                }
            }else{
                msg="영문대소문자 1개 필수 와 숫자 1개 필수"
            }
        }else{
            msg="영문대소문자 1개 필수 와 숫자 1개 필수"
        }
        //2. 비밀번호와 비밀번호 확인 동일한지 비교

    document.querySelector('.pwcheckbox').innerHTML=msg;
}

//6. 이름 유효성검사
function namecheck(){
    let name = document.querySelector('#name').value;
    let 이름규칙=/^[가-힣]{5,20}$/
    let msg='';
    checkArray[2]=false;
    if(이름규칙.test(name)){
        msg='통과';
        checkArray[2]=true;
    }else{
        msg='한글 5~20글자'
    }
    document.querySelector('.namecheckbox').innerHTML=msg;
}

//7. 전화번호 유효성검사
function phonecheck(){
    let phone = document.querySelector('#phone').value;
    let 번호규칙=/^([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{3,4})$/
    let msg='';
    checkArray[3]=false;
    if(번호규칙.test(phone)){
        msg='통과';
        checkArray[3]=true;
    }else{
        msg='000-0000-0000 또는 00-000-0000 입력해주세요'
    }
    document.querySelector('.phonecheckbox').innerHTML=msg;
}
//8. 이메일 검사
function emailcheck(){
    let email = document.querySelector('#email').value;
    let 이메일규칙=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z]+$/
    let msg='아이디@도메인 입력해주세요';
    checkArray[4]=false;
    authreqbtn.disabled=true;
    if(이메일규칙.test(email)){
        authreqbtn.disabled=false;
    }
    document.querySelector('.emailcheckbox').innerHTML=msg;
}
let time=0;
let authbox =document.querySelector('.authbox');
let authreqbtn=document.querySelector('.authreqbtn');
let timerInter=null;

//9. 인증요청
function authreq(){
console.log('df')
    let html=` <span class="timebox">00:00</span>
                      <input type="text" class="ecodeinput">
                      <button onclick="auth()"type="button">인증</button>`
// cnffur
    authbox.innerHTML=html;
    //=========================================
     $.ajax({
            method: "get",
            url: `/auth/email/req`,
            data:{"email":document.querySelector('#email').value},
            success: (r)=>{
                if(r){
                    time=10;
                    ontimer();
                    authreqbtn.disablea=true;
                }else{
                    alert('관리자에게 문의.')
                }
            }
        });
    let authcode='1234';
    //====================================

}
//10. 타이머 함수
function ontimer(){
console.log('df7')

    timerInter=setInterval(()=>{
        // 1. 초 변수를 분/초 변환
        let m = parseInt(time/60); // 분
        let s = parseInt(time%60); // 초

        //2. 시간을 두 자릿수로 표현
        m = m<10? "0"+m : m;
        s = s<10? "0"+s : s;
        console.log(m);
        console.log(s);
        //3. 시간 출력
        document.querySelector('.timebox').innerHTML=`${m}분${s}초`;
        //4. 초 감소
        time--;
        if(time<0){
            clearInterval(timerInter);
            authbox.innerHTML='다시 요청해주세요';
            authreqbtn.disablea=false;
        }
    },1000);
}

//11 인증함수
function auth(){
    let ecodeinput=document.querySelector('.ecodeinput').value;
    $.ajax({
        method: "get",
        url: `/auth/email/check`,
        data:{'ecodeinput':ecodeinput},
        success: (r)=>{
            if(r){
                checkArray[4]=true;
                document.querySelector('.emailcheckbox').innerHTML='통과';
                clearInterval(timerInter);
                authbox.innerHTML='';
                authreqbtn.disablea=false;
            }else{
                 alert('인증번호가 다릅니다. 또는 인증세션 시간 초과')
            }
        }
    });
}

//let time=120;//초
////테스트
//    //setInterval(함수, 밀리초) 특정 밀리초마다 함수 실행
//    //clearInterval (Interval 변수) : 종료할 Interval의 변수 대입
//    //setInterval(function(){},1000); setInterval(함수(),1000);
//let timerInter=setInterval(()=>{
//    // 1. 초 변수를 분/초 변환
//    let m = parseInt(time/60); // 분
//    let s = parseInt(time&60); // 초
//
//    //2. 시간을 두 자릿수로 표현
//    m = m<10? "0"+m : m;
//    s = s<10? "0"+s : s;
//    //3. 시간 출력
//    document.querySelector('.authbox').innerHTML=`${m}분${s}초`;
//    //4. 초 감소
//    time--;
//
//    if(time<0){
//        clearInterval(timerInter);
//    }
//},1000);

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
    //유효성검사 체크
    for(let i=0; i<checkArray.length;i++){
        if(!checkArray[i]){
            alert('입력사항들을 모두 정확히 입력해주세요');
            return;
        }
    }



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