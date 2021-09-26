package project.tuthree.testlogic;

import project.tuthree.domain.user.Category;

public class EnumTest {
    public static void main(String[] args) {

        for(Category category: Category.values()){
            System.out.println("category = " + category);
        }
    }
}
