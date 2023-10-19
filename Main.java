import java.util.Scanner;

public class Main {
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		Matrix matrix = new Matrix();
		matrix.inputMatrix();

		Matrix anotherMatrix = new Matrix();
		anotherMatrix.inputMatrix();
		matrix.printMatrix();
		System.out.println("-----");
		anotherMatrix.printMatrix();
		Matrix newarr = matrix.multiply(anotherMatrix);
		System.out.println("It's multiplied:");
		newarr.printMatrix();

		
	}
}