package example.day09;

import java.awt.*;

public class Example1 {
    //* main 함수 안에는 main 스레드 포함
    public static void main(String[] args) {
        // 1. 현재 코드의 스레드 객체 호출
//        Thread.currentThread()
        Thread thread =Thread.currentThread();
        //2. 현재 코드를 실행하는 스레드 객체의 이름
        System.out.println("1.thread.getName() = " + thread.getName());

        //3. 작업스레드 생성 4가지 방법
            // 자식 익명 객체 : 부모타입 변수명 = new 부모타입(){재정의;}
        for(int i=0;i<3;i++){
            Thread threadA=new Thread(){
                @Override
                public void run() { // - 작업 스레드가 실행할 때 최초로 실행되는 함수
                    Thread thread =Thread.currentThread();
                    //2. 현재 코드를 실행하는 스레드 객체의 이름
                    System.out.println("2.thread.getName() = " + thread.getName());
                }
            };
            //*2. 작업스레드 실행
            threadA.start();
        }

        System.out.println("3.thread.getName() = " + thread.getName());

        //------ 603. 주어진 시간동안 스레드 일시정지 .sleep(): 정적메소드(static) : 정적메소드 호출하는 방법 : 클래스명.정적메소드();
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        for(int i=0; i<10; i++){
            toolkit.beep();
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
        // main함수 코드 모두 끝나도 다른 작업스레드의 코드가 끝날때까지 대기


    }

}
