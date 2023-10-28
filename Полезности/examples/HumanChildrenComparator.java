import java.util.Comparator;

class HumanChildrenComparator implements Comparator<Human> {

    @Override
    public int compare(Human a, Human b){
        return a.childrenAmount - b.childrenAmount;
    }
}