package ro.ubbcluj.map.pb1;

import java.util.*;

public class TestPb1 {
    public static void main(String[] args) {
        Student s1 = new Student("Dan", 4.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 4.5f);
        Student s4 = new Student("Andrei", 1.1f);
        Student s5 = new Student("Barbu", 10.0f);

        Set<Student> set1 = new HashSet<>();
        set1.addAll(Arrays.asList(s1, s2, s3, s4, s5));
        //System.out.println(set1);

        /*Set<Student> set2 = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getNume().compareTo(o2.getNume());
            }
        });
        set2.addAll(Arrays.asList(s1, s2, s3, s4, s5));
        set2.forEach(x -> System.out.println(x));*/

        Set<Student> set4 = new TreeSet<>((o1, o2) -> o1.getNume().compareTo(o2.getNume()));
        set4.addAll(Arrays.asList(s1, s2, s3, s4, s5));
        set4.forEach(x -> System.out.println(x));

        Set<Student> set3 = new TreeSet<>();
        set3.addAll(Arrays.asList(s1, s2, s3, s4, s5));
        set3.forEach(x -> System.out.println(x));
    }
}
