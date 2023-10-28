import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] ages = new int[]{32, 21, 19, 56, 79, 21, 6};
        int[] salaries = new int[]{1000000, 0, 140000, 500000, 3000, 50000, 0};
        int[] childrenAmounts = new int[]{1, 0, 0, 3, 10, 2, 0};
        boolean[] genders = new boolean[]{true, false, true, false, true, false, true};
        String[] surname = new String[]{"Иванов", "Попова", "Попов", "Течер", "Палий", "Дрозьд", "Паркт"};


        Human[] humans = new Human[ages.length];
        for (int i = 0; i < humans.length; i++) {
            humans[i] = new Human(ages[i], salaries[i], childrenAmounts[i], genders[i], surname[i]);
        }
        Arrays.sort(humans);

        for (Human h: humans)
            System.out.println(h);

        Arrays.sort(humans, new HumanChildrenComparator());


        ArrayList<Human> humanArrayList = new ArrayList<>();
        LinkedList<Human> humanLinkedList = new LinkedList<>();

        for (int i = 0; i < humans.length; i++) {
            humanArrayList.add(new Human(ages[i], salaries[i], childrenAmounts[i], genders[i], surname[i]));
            humanLinkedList.add(new Human(ages[i], salaries[i], childrenAmounts[i], genders[i], surname[i]));
        }

        humanArrayList.sort(null);

        for (Human h: humanArrayList)
            System.out.println(h);

        humanLinkedList.sort(new HumanChildrenComparator());
        Collections.sort(humanArrayList);

    }
}
