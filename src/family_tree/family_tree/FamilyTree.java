package family_tree.family_tree;

import family_tree.human.Human;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FamilyTree<E extends TreeNode<E>> implements Serializable, Iterable<E> {
    private long countPeople;
    private List<E> humanList;

    public FamilyTree(){this(new ArrayList<>());}

    public FamilyTree(List<E> humanList){this.humanList = humanList;}

    public boolean add(E human){
        if (human == null){
            return false;
        }
        if (!humanList.contains(human)){
            humanList.add(human);
            human.setId(countPeople++);

            addToParents(human);
            addToChildren(human);

            return true;
        }
        return false;
    }

    private void addToParents(E human){
        for (E parent: human.getParents()){
            parent.addChild(human);
        }
    }

    private void addToChildren(E human){
        for (E child: human.getChildren()){
            child.addParent(human);
        }
    }


    public List<E> getByName(String name){
        List<E> res = new ArrayList<>();
        for (E human: humanList){
            if (human.getName().equals(name)){
                res.add(human);
            }
        }
        return res;
    }

    public boolean setWedding(long humanId1, long humanId2){
        if (checkId(humanId1) && checkId(humanId2)){
            E human1 = (E) getById(humanId1);
            E human2 = (E) getById(humanId2);
            return setWedding(human1, human2);
        }
        return false;
    }

    public boolean setWedding(E human1, E human2){
        if (human1.getSpouse() == null && human2.getSpouse() == null){
            human1.setSpouse(human2);
            human2.setSpouse(human1);
            return true;
        }else{
            return false;
        }
    }

    public boolean setDivorse(long humanId1, long humanId2){
        if (checkId(humanId1) && checkId(humanId2)){
            Human human1 = getById(humanId1);
            Human human2 = getById(humanId2);
            return setDivorse((E) human1, (E) human2);
        }
        return false;
    }


    public boolean setDivorse(E human1, E human2){
        if (human1.getSpouse() != null && human2.getSpouse() != null){
            human1.setSpouse(null);
            human2.setSpouse(null);
            return true;
        }else{
            return false;
        }
    }

    public boolean remove(long humansId){
        if (checkId(humansId)){
            Human e = getById(humansId);
            return humanList.remove(e);
        }
        return false;
    }

    private boolean checkId(long id){
        return id < countPeople && id >= 0;
    }

    public Human getById(long id) {
        if (checkId(id)) {
            for (E human : humanList) {
                if (human.getId() == id) {
                    return (Human) human;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("В дереве ");
        sb.append(humanList.size());
        sb.append(" объектов: \n");
        for ( E human: humanList){
            sb.append(human);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new HumanIterator(humanList);
    }
    public void sortByName(){
        humanList.sort((Comparator<? super E>) new FamilyTreeComparatorByName());
    }
    public void sortByBirthDate(){
        humanList.sort((Comparator<? super E>) new FamilyTreeComparatorByBirthDate());
    }

}

