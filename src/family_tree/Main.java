package family_tree;

import family_tree.family_tree.FamilyTree;
import family_tree.human.Gender;
import family_tree.human.Human;
import family_tree.writer.FileHandler;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
    FamilyTree tree = load();
        System.out.println(tree);
        //save(tree);
    }

    private static FamilyTree load(){
        String filePath = "src/family_tree/writer/tree.txt";
        FileHandler fileHandler = new FileHandler();
        return (FamilyTree) fileHandler.read(filePath);
    }

    private static void save(FamilyTree tree){
        String filePath = "src/family_tree/writer/tree.txt";
        FileHandler fileHandler = new FileHandler();
        fileHandler.save(tree, filePath);
    }

    static FamilyTree testTree(){
        FamilyTree tree = new FamilyTree();

        Human vasya = new Human("Василий", Gender.Male, LocalDate.of(1963,12,10));
        Human masha = new Human("Мария", Gender.Female, LocalDate.of(1965,9,15));
        tree.add(vasya);
        tree.add(masha);
        tree.setWedding(vasya,masha);

        Human christina = new Human("Кристина", Gender.Female, LocalDate.of(1988, 7, 5), vasya,masha);
        Human semyon = new Human("Семен", Gender.Male, LocalDate.of(1991, 1, 25), vasya, masha);
        tree.add(christina);
        tree.add(semyon);

        Human grandmother = new Human("Лариса", Gender.Female, LocalDate.of(1945, 9, 1));
        grandmother.addChild(vasya);
        tree.add(grandmother);

        return tree;
    }
}
