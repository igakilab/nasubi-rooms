package mongodb;

public class Foreach {
	public static void main(String[] args){
		String[] names = {"koike", "kitaba", "shinya", "mitsui"};

		for(String n : names){
			System.out.println(n);
		}
	}
}
