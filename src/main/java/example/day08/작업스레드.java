package example.day08;

import java.awt.*;

public class 작업스레드 implements Runnable{
    //작업스레드가 최초실행하는 함수
    @Override
    public void run() {
        for(int i=1;i<=5;i++){
            Toolkit toolkit=Toolkit.getDefaultToolkit();
            toolkit.beep();
            try {
                Thread.sleep(500);// main 스레드를 0.5초간 일시정지
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }
}
