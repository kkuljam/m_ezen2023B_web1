package example.day09;

public class Example2 {
    public static void main(String[] args) {
        //604. 다른 스레드의 종료를 기다림
        //1. 스레드 객체 생성
        Sum sum = new Sum();
        // sum =0? 왜? 객체 생성시 필드 초기값
        //2. 스레드 실행
        sum.start();

            //main 스레드에게 작업스레드가 끝날 때까지 기다림
        try {
            sum.join(); //main 스레드와 sum 스레드가 join

        }catch (Exception e){
            System.out.println(e);
        }

        //3. 작업스레드 run() 메소드를 처리하기 전에 아래 실행문 처리.
        System.out.println("sum.getSum() = " + sum.getSum());

        //606 다른 스레드에게 실행 양보
        //1. 작업스레드 2개 객체 생성
        WorkThread workThreadA = new WorkThread("workThreadA");
        WorkThread workThreadB = new WorkThread("workThreadB");

        //2. 각 스레드 실행
        workThreadA.start();
        workThreadB.start();

        //3. 5초 뒤에 A작업스레드 작업을 양보하기
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            System.out.println(e);
        }
        // workThreadA.

        //4. 10초 뒤에 A작업스레드 작업을 양보하기
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
