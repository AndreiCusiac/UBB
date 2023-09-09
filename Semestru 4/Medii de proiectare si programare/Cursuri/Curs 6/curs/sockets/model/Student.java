package curs.sockets.model;

public class Student extends Person {
    String name;
    Integer age;
    Integer group;

    public Student(String name, Integer age, Integer group) {
        super(name, age);
        this.name = name;
        this.age = age;
        this.group = group;
    }

    public Student(String name, Integer age) {
        super(name, age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
