package example.day07._1트리컬렉션;

import org.jetbrains.annotations.NotNull;

public class Person implements Comparable<Person>{
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(@NotNull Person o) {
        // * 같으면 0 , 적으면 -1, 크면 1
        //1. age 나이 정렬
       /* if(this.age < o.age)return -1;
        else if(this.age==o.age)return 0;
        else  return 1;*/

        //.2 name 정렬
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
