package example.day10._1example;

import javax.sound.midi.Track;

public class Calculator {

    private int memory;

    //* getter
    public int getMemory() {
        return memory;
    }

    //* setter : 매개변수를 저장 [ 2초간 뒤에 저장된 값을 출력]
    //synchronized : 동기화 : 여러 스레드가 해당 메소드/블록 호출했을 때 순서메기기
    public synchronized void setMemory(int memory) {
        this.memory = memory;
        try{Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(Thread.currentThread().getName()+" : "+this.memory);
    }
}
