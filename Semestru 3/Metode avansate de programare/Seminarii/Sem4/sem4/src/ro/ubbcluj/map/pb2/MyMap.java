package ro.ubbcluj.map.pb2;
import ro.ubbcluj.map.pb1.Student;

import java.security.KeyStore;
import java.util.*;

public class MyMap {
    private Map<Integer, List<Student>> gradesMap;

    /*public MyMap(Map<Integer, List<Student>> gradesMap) {
        this.gradesMap = gradesMap;
    }*/

    public MyMap() {
        this.gradesMap = new TreeMap<>(new ComparatorDupaMedie());
    }

    public void add(Student stud) {
        if (gradesMap.get(Math.round(stud.getMedia())) == null) {
            List<Student> studenti = new ArrayList<Student>();

            studenti.add(stud);
            gradesMap.put(Math.round(stud.getMedia()), studenti);
        }
        else
        {
            gradesMap.get(Math.round(stud.getMedia())).add(stud);
        }
    }

    public Set<Map.Entry<Integer, List<Student>>> getEntries() {
        return gradesMap.entrySet();
    }

    private static class ComparatorDupaMedie implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
}
