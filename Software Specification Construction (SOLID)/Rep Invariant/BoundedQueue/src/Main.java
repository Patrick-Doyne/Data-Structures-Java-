import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Chooser<T> has an ArrayList<T> field
		Collection<Integer> x = new ArrayList<>();
        ImprovedChooser<Integer> one = new ImprovedChooser<>(x);
        
        //Try to add <T>
        one.addChoice(1);
        System.out.println("YAY");
	}

}