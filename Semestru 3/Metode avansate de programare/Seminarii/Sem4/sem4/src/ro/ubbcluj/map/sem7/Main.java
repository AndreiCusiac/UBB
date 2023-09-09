package ro.ubbcluj.map.sem7;

import ro.ubbcluj.map.sem7.domain.Nota;
import ro.ubbcluj.map.sem7.domain.NotaDto;
import ro.ubbcluj.map.sem7.domain.Student;
import ro.ubbcluj.map.sem7.domain.Tema;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static List<Student> getStudents() {
        Student s1 = new Student("andrei", 221);
        s1.setId(2l);
        Student s2 = new Student("dan", 222);
        s2.setId(2l);
        Student s3 = new Student("gigi", 221);
        s3.setId(2l);
        Student s4 = new Student("costel", 222);
        s4.setId(2l);
        return Arrays.asList(s1, s2, s3, s4);
    }

    private static List<Tema> getTeme() {
        return Arrays.asList(
                new Tema("t1", "desc1"),
                new Tema("t2", "desc2"),
                new Tema("t3", "desc3"),
                new Tema("t4", "desc4")
        );
    }

    private static List<Nota> getNote(List<Student> stud, List<Tema> teme) {
        return Arrays.asList(
                new Nota(stud.get(0), teme.get(0), 10d, LocalDate.of(2019, 11, 2), "profesor1"),
                new Nota(stud.get(1), teme.get(0), 9d, LocalDate.of(2019, 11, 2).minusWeeks(1), "profesor1"),
                new Nota(stud.get(1), teme.get(1), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(1), teme.get(2), 10d, LocalDate.of(2019, 10, 20), "profesor2"),
                new Nota(stud.get(2), teme.get(1), 7d, LocalDate.of(2019, 10, 28), "profesor1"),
                new Nota(stud.get(2), teme.get(3), 9d, LocalDate.of(2019, 10, 27), "profesor2"),
                new Nota(stud.get(1), teme.get(3), 10d, LocalDate.of(2019, 10, 29), "profesor2")
        );
    }

    /**
     * creati/afisati o lista de obiecte NotaDto apoi filtrati dupa profesor si grupa
     * (toate notele acordate de un anumit profesor, la o anumita grupa)
     * GENERALIZARE: FILTRU COMPUS
     */
    private static List<NotaDto> report1(List<Nota> note) {

        Predicate<Nota> filterByTeacher = x -> x.getProfesor().equals("profesor2");
        Predicate<Nota> filterByGroup = x -> x.getStudent().getGroup() == 222;
        Predicate<Nota> filterByAll = filterByTeacher.and(filterByGroup);

        return note.stream()
                .filter(filterByAll)
                .map(x -> new NotaDto(x.getStudent().getName(), x.getTema().getId(), x.getValue(), x.getProfesor()))
                .sorted((x,y) -> x.getStudentName().compareTo(y.getStudentName()))
                .toList();
    }

    /**
     * media notelor de lab pt fiecare student
     *
     * @param note
     */
    private static void report2(List<Nota> note) {

        Map<Student, List<Nota>> studentsGrouped = note.stream().
                collect(Collectors.groupingBy(x -> x.getStudent()));

        System.out.println("\nReport2\n");

        studentsGrouped.entrySet().forEach(x -> System.out.println(x));



        studentsGrouped.entrySet().
                forEach(x -> {
                    var sumOfStudent = x.getValue().stream()
                                        .map(y -> y.getValue())
                                        .reduce((double) 0, (u, v) -> u + v);

                    double medie = sumOfStudent / 4;

                    System.out.println(x.getKey().getName() + " are media " + medie);

                });
    }

    /**
     * media notelor la o anumita tema
     *
     * @param note
     */
    private static void report3(List<Nota> note) {

        Map<Tema, List<Nota>> temeGrouped = note.stream().
                collect(Collectors.groupingBy(x -> x.getTema()));

        temeGrouped.entrySet()
                .forEach(x -> {
                    var sumOfStudent = x.getValue().stream()
                            .map(y -> y.getValue())
                            .reduce((double) 0, (u, v) -> u + v);

                    double medie = sumOfStudent / x.getValue().size();

                    System.out.println(x.getKey().getId() + " are media " + medie);
                });

    }

    private static void report4(List<Nota> note) {

        Map<Tema, List<Nota>> temeGrouped = note.stream().
                collect(Collectors.groupingBy(x -> x.getTema()));

        Map.Entry<Tema, Double> ceaMaiGreaTema = temeGrouped.entrySet()
                .stream()
                .map(x -> {
                    var sumOfStudent = x.getValue().stream()
                            .map(y -> y.getValue())
                            .reduce((double) 0, (u, v) -> u + v);

                    double medie = sumOfStudent / x.getValue().size();

                    Map.Entry<Tema, Double> rez = new AbstractMap.SimpleEntry<>(x.getKey(), medie);

                    return rez;
                })
                .min(Comparator.comparing(Map.Entry::getValue))
                .get();

        System.out.println("Cea mai grea tema: " + ceaMaiGreaTema.getKey().getId() + ", cu media " + ceaMaiGreaTema.getValue());
    }


    public static void main(String[] args) {

        /*System.out.println("List");
        List<String> list = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");
        list.stream()
                .filter(x -> {
                    System.out.println(x);
                    return x.startsWith("b");
                })
                .map(x -> {
                    System.out.println(x);
                    return x.toUpperCase();
                })
                .forEach(System.out::println);


        System.out.println("\nList1");
        List<String> list1 = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");
        String rez = list1.stream()
                .filter(x -> {
                    System.out.println(x);
                    return x.startsWith("b");
                })
                .map(x -> {
                    System.out.println(x);
                    return x.toUpperCase();
                })
                .reduce("", (x,y) -> x+y);
        System.out.println(rez);

        System.out.println("\nList2");
        List<String> list2 = Arrays.asList("asf", "bcd", "asd", "bed", "bbb");
        Optional<String> rez2=list2.stream()
                .filter(x -> {
                    //System.out.println("filter: " + x);
                    return x.startsWith("b");
                })
                .map(x -> {
                    //System.out.println("map: " + x);
                    return x.toUpperCase();
                })
                .reduce((x,y)->x+y);
        if (!rez2.isEmpty())
            System.out.println(rez2.get());
        rez2.ifPresent(x-> System.out.println(x));*/

        report1(getNote(getStudents(), getTeme())).forEach(System.out::println);
        report2(getNote(getStudents(), getTeme()));
        report3(getNote(getStudents(), getTeme()));
        report4(getNote(getStudents(), getTeme()));

    }
}

