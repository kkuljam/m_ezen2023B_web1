// 모든 페이지에서 적용할 공통 js

// 1. 로그인 여부 확인 요청
$.ajax({
    url:'/member/login/check'  ,
    method:'get'  ,
    success:  (r)=>{
        console.log(r)
        let login_menu= document.querySelector('#login_menu');
        let html = ` `;
        if(r != '' ){
            $.ajax({
                url:'/member/login/info',
                method:'get',
                data:{id:r},
                async : false,
                success:(r2)=>{
                    console.log(r2);
                    console.log(r2.uuidFile);
                    html +=`
                        <li class="nav-item">
                            <a class="nav-link" onclick="logout()">logout</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">mypage</a>
                        </li>
                        <li class="nav-item">
                            <img src="/img/${r2.uuidFile}">${r} 님
                        </li>`
                }
            });

        }else{
        html +=`

                <li class="nav-item">
                    <a class="nav-link" href="/member/login">login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/member/signup">sign</a>
                </li>`
        }
        login_menu.innerHTML=html;
    }
})

// 2. 로그아웃
function logout(){
$.ajax({
    url: `/member/logout`,
    method: `get`,
    success: (r)=>{
        if(r){
        alert('로그아웃 했습니다.');
        location.href='/'
        }else{
        alert('로그아웃 실패 관리자에게 문의')
        }
    }
})
}