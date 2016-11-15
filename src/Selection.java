import java.util.*;

public class Selection {

    private int[] indexes;
    private Comparable[] a;
    private Comparator comparator;

    public int[] indirectSort(Comparable[] a, Comparator comparator) {
        this.indexes = new int[a.length];
        for (int i = 0; i < a.length; i++)
            this.indexes[i] = i;
        this.a = a;
        this.comparator = comparator;
        this.sort();
        return this.indexes;
    }

    private void sort() {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(j, min)) min = j;
            }
            exch(i, min);
        }
        assert isSorted();
    }

    private Comparable get(int i) {
        return this.a[this.indexes[i]];
    }

    private boolean less(int i, int j) {
        return comparator.compare(this.get(i), this.get(j)) < 0;
    }

    private void exch(int i, int j) {
        int swap = this.indexes[i];
        this.indexes[i] = this.indexes[j];
        this.indexes[j] = swap;
    }

    private boolean isSorted() {
        for (int i = 1; i <= a.length; i++)
            if (less(i, i - 1)) return false;
        return true;
    }

    public static void main(String[] args) {

        Student s1 = new Student(4);
        Student s2 = new Student(2);
        Student s3 = new Student(1);
        Student s4 = new Student(3);
        Student[] students = {s1, s2, s3, s4};

        Selection s = new Selection();
        int[] ins = s.indirectSort(students, new StudentComparator());

        for (int i : ins) System.out.println(i);
    }
}

class StudentComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.getGpa() - s2.getGpa();
    }
}

class Student implements Comparable<Student> {
    private int gpa;

    Student(int gpa) {
        this.gpa = gpa;
    }

    int getGpa() {
        return this.gpa;
    }

    @Override
    public int compareTo(Student o) {
        return this.gpa - o.gpa;
    }

}
