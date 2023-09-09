package ro.ubbcluj.map.sem6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Main {

    public static <E> void printArie(List<E> l, Arie<E> f) {
        l.forEach(x -> System.out.println(f.calcul(x)));
    }

    public static <E> List<E>  filterGeneric(List<E> lista, Predicate<E> p) {
        return lista.stream()
                .filter(p)
                .toList();

    }

    public static <E> List<E>  filterAndSorterGeneric(List<E> lista, Predicate<E> p, Comparator<E> comparator) {
        return lista.stream()
                .filter(p)
                .sorted(comparator)
                .toList();

    }

    public static <E> void filterGeneric2 (List<E> list, Predicate<E> predicate) {

    }

    public static <E> void filterGeneric3 (Predicate<E> predicate) {

    }

    public static <E> void filterGeneric4 (Predicate<E> predicate, List<E> list) {

    }

    public static void main(String[] args) {
        Cerc cerc = new Cerc(10);
        Patrat patrat = new Patrat(5);

        Arie<Patrat> patratArie = (x) -> x.getLatura() * x.getLatura();
        // Arie<Patrat> patratArie = x -> x.getLatura() * x.getLatura();

        System.out.println(patratArie.calcul(patrat));

        Arie<Cerc> cercArie = x -> x.getRaza() * x.getRaza() * Arie.PI;
        System.out.println(cercArie.calcul(cerc));

        List<Cerc> lCerc = Arrays.asList(cerc, new Cerc(1), new Cerc(2));
        List<Patrat> lPatrat = Arrays.asList(patrat, new Patrat(1), new Patrat(2));
        //printArie(l, cercArie);

        Predicate<Cerc> cercPredicate = x -> x.getRaza() >= 3;
        Predicate<Patrat> patratPredicate = x -> x.getLatura() >= 3;
        //List<Cerc> listaFiltrat = filterGeneric(l, cercPredicate);
        //List<Cerc> listaFiltrat = filterGeneric(l, patratPredicate);
        List<Cerc> listaFiltrat = filterGeneric(lCerc, x -> x.getRaza() >= 3);

        filterGeneric2(lCerc, x -> x.getRaza() >= 3);

        filterGeneric4(x -> x.getRaza() >= 3, lCerc);
        //listaFiltrat.forEach(System.out::println);

        List<String> stringList = Arrays.asList("Ana", "Anioara", "Ionut", "Andrada", "Timotei", "Albulescu", "Andrei", "Mircea");

        List<String> listaFiltrataSiSortata = filterAndSorterGeneric(stringList, x -> x.startsWith("A"), (x, y) -> x.compareTo(y));

        listaFiltrataSiSortata.forEach(System.out::println);
        //List<String> listaFiltrataSiSortata = filterAndSorterGeneric(stringList, x -> x.startsWith("A"), String::compareTo);
    }
}
