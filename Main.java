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
		int f = 0;
		Matrix newArr = new Matrix();
		try {
			newArr = matrix.multiply(anotherMatrix);
		} catch (Exception e) {
			System.out.println("NULL, couldn't multiply");

			f = 1;
		}
		newArr.printMatrix();
	}
}