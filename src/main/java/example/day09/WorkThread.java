package example.day09;

public class WorkThread extends Thread {

    // 필드
    private boolean work=true;
    // 생성자
    public WorkThread(String name){
        setName(name); // 매개변수로 스레드 이름 변경
        //Thread 클래스
        //setName();    // 스레드 이름 변경 함수
        //getName();    // 스레드 이름 호출 함수
        //run()         // 작업스레드가 실행할 코드 함수
    }
    //오버라이딩


    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
                System.out.println(e);
            }

            if(work){
                System.out.println(getName());
            }else {
                System.out.println("1");
                Thread.yield(); // 점유 상태 되었을 때 대기 상태로 돌아감
                System.out.println("2");
            }
        }
    }
}
