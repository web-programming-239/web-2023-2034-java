import java.util.Comparator;

class Human implements Comparable<Human> {
    int age, salary, childrenAmount;
    boolean isMale;
    String surname;


    public Human(int age, int salary, int childrenAmount, boolean isMale, String surname) {
        this.age = age;
        this.salary = salary;
        this.childrenAmount = childrenAmount;
        this.isMale = isMale;
        this.surname = surname;
    }

    @Override
    public String toString() {
        String gender = (isMale ? "М" : "Ж");
        return String.format("%6s %s %3d %5d %d", surname, gender, age, salary / 1000, childrenAmount);
    }

    @Override
    public int compareTo(Human o) {
        return this.surname.compareTo(o.surname);
    }
}
