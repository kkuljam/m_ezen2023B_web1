 console.log("list.js")
 var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng(37.3218778, 126.8308848), // 지도의 중심좌표
        level : 6 // 지도의 확대 레벨
    });

    // 마커 클러스터러를 생성합니다
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 8 // 클러스터 할 최소 지도 레벨
    });

    // 데이터를 가져오기 위해 jQuery를 사용합니다
    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
    $.get("/product/list.do", (response)=> {
        console.log(response);
        let markers=response.map((data)=>{
             //1. 마커 생성
            let marker=new kakao.maps.Marker({
                position : new kakao.maps.LatLng(data.plat , data.plng)
            })
            // 마커 커스텀
            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'click', function() {
                  // 마커 위에 인포윈도우를 표시합니다
                  alert(`제품명 : ${data.pname} 가격 : ${data.pprice}`)
                  infowindow.open(map, marker);
            });
            return marker; // 클러스터 저장하기 위해 반복문 밖으로 생성된 마커 반환
        })
        //3. 클러스터에 마커들 (markers)
        clusterer.addMarkers(markers);
       /* // 데이터에서 좌표 값을 가지고 마커를 표시합니다
        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
        var markers = $(data.positions).map(function(i, position) {
            return new kakao.maps.Marker({
                position : new kakao.maps.LatLng(position.lat, position.lng)
            });
        });

        // 클러스터러에 마커들을 추가합니다
        clusterer.addMarkers(markers);*/
    });