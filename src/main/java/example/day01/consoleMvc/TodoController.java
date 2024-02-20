package example.day01.consoleMvc;

import java.util.ArrayList;

public class TodoController {
    private TodoDao todoDao=new TodoDao();

    public  boolean doPost(TodoDto todoDto){
        return todoDao.doPost(todoDto);
    }
    public ArrayList<TodoDto> doGet(){
        return todoDao.doGet();
    }

}
