package ro.ubbcluj.map.pb2;

import ro.ubbcluj.map.pb1.Student;

public class TestPb2 {
    public static void main() {
        Student s1 = new Student("Dan", 9.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 8.7f);
        Student s4 = new Student("Andrei", 9.75f);
        Student s5 = new Student("Barbu", 7.0f);

        MyMap map = new MyMap();
        map.add(s1);
        map.add(s2);
        map.add(s3);
        map.add(s4);
        map.add(s5);

        System.out.println("Da");

        //map.getEntries(x -> System.out.println(x));
    }
}
