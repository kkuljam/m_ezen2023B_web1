console.log( 'api.js');
// 3. 카카오 지도 api
    // 1. 카카오 지도 객체
    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng(37.3218778, 126.8308848), // 지도의 중심좌표 // 안산시 : 126.8308848, 37.3218778
        level : 6 // 지도의 확대 레벨 [ 0~14 ]
    });
    // 2. 마커 클러스터러( 마커가 여러개 일때 합쳐지는 효과 )를 생성합니다
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 8 // 클러스터
    });
    // 3. 데이터 호출해서 map 이용한 마커 1개 생성후 markers 담는다. map 반복문 종료후 markers를 클러스터에 담아준다.
    $.ajax({
        url : "https://api.odcloud.kr/api/15109590/v1/uddi:3e550608-d205-411b-a92d-e7fd2278b7bc?page=1&perPage=100&serviceKey=z427Q0DLkQqM0SDOc1Lz8jPzk%2BKj0ng%2Bvz7h3I8CpVs3T90219bWi2o%2BmStIxJW%2B9lwayA%2FsAT6apxsxuvydQg%3D%3D" ,
        method : "get",
        success : ( response ) => {
            var markers = response.data.map( (object) => {
                // 마커 1개 만들어서 리턴 해서 markers 에 대입.
                return new kakao.maps.Marker({
                    position : new kakao.maps.LatLng( object.식당위도, object.식당경도 ), // 마커의 위치

                });
            });
            // 클러스터러에 마커*들을 추가합니다
            clusterer.addMarkers(markers);
        }
    }); // ajax end

$.ajax({
    url:"https://api.odcloud.kr/api/15109590/v1/uddi:3e550608-d205-411b-a92d-e7fd2278b7bc?page=1&perPage=100&serviceKey=0RyZcqaVYzh%2Ft%2BQ88RdKSKQ0D7y0zsu%2BB6UV3Mx5aExQfNT0VsHnxk5KRD0nLJlEWp95bvOnC65vTVFxSVNu5g%3D%3D",
    method:"get",
    success: (result)=>{
        console.log(result);

        let apiTable2 =document.querySelector('.apiTable2');
        let html='';

        result.data.forEach((object)=>{
            html+=`<tr>
                       <th>${object.사업장명}</th>
                       <th>${object.도로명전체주소}</th>
                       <th>${object.대표메뉴1}</th>
                       <th>${object.메뉴가격1.toLocaleString()}</th>
                       <th>${object.대표전화}</th>
                       <th>${object['주차 가능']}</th>
                   </tr>`
        });
        apiTable2.innerHTML=html;
    }
})



$.ajax({
    url:"https://api.odcloud.kr/api/15036246/v1/uddi:89cfa610-8953-424f-a76d-bcc807c40e68?page=1&perPage=10&serviceKey=0RyZcqaVYzh%2Ft%2BQ88RdKSKQ0D7y0zsu%2BB6UV3Mx5aExQfNT0VsHnxk5KRD0nLJlEWp95bvOnC65vTVFxSVNu5g%3D%3D",
    method:'get',
    success:(r)=>{
        console.log(r);

        let apiTable1 =document.querySelector('.apiTable1');
        let html=``;

            r.data.forEach((object)=>{
                html+=`<tr>
                           <th>${object.경도}</th>
                           <th>${object.데이터기준일자}</th>
                           <th>${object.시군구명}</th>
                           <th>${object.시도명}</th>
                           <th>${object.위도}</th>
                       </tr>`
            })
        apiTable1.innerHTML = html;
    }
})