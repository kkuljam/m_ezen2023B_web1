package example.day07._2스택;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Example1 {
    public static void main(String[] args) {

        //1. 스택 컬렉션 생성 [Vector 상속]
        Stack<Integer>coinBox=new Stack<>();

        //2. 동전 넣기 = 스택 삽입 = push()
        coinBox.push(100);
        coinBox.push(50);
        coinBox.push(500);
        coinBox.push(10);
        System.out.println("coinBox = " + coinBox);

        //3. 빼기 = pop()
        coinBox.pop();
        coinBox.pop();
        coinBox.pop();

        // 1. queue 컬렉션 생성
        Queue<String> messageQueue=new LinkedList<>();

        //2. 메시지 넣기
        messageQueue.offer("안녕 홍길동");
        messageQueue.offer("안녕 신용권");
        messageQueue.offer("안녕 김자바");
    }
}
