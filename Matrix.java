import java.util.Scanner;
import java.util.Locale;
public class Matrix {

	int[][] array;
	int rowsCount;
	int colsCount;

	Scanner sc = new Scanner(System.in);
	public void inputMatrix() {
		System.out.print("Input matrix's sizes (m n): ");
		rowsCount = sc.nextInt();
		colsCount = sc.nextInt();
		this.array = new int[rowsCount][colsCount];

		int method = methodAsk();
		if (method == 1) {
			firstMethod();
		} else if (method == 2) {
			secondMethod();
		} else {
			System.out.println("Wrong input");
		}

	}
	public int methodAsk() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Input your method num (1/2):");
		int methodNum = sc.nextInt();
		return methodNum;
	}

	public void firstMethod() {
		System.out.println("Now put your matrix's strings one by one.");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < rowsCount; i++) {
			System.out.println("Put your " + (i + 1) + " string");
			for (int j = 0; j < colsCount; j++) {
				array[i][j] = sc.nextInt();
			}
		}

	}

	public void secondMethod() {
		System.out.println("Now you should put your elements one by one.");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < colsCount; j++) {
				System.out.println("Put your (" + (i+1) + ", " + (j+1) + ") element");
				array[i][j] = sc.nextInt();
			}
		}
	}

	public Matrix multiply(Matrix newArray) {
		Matrix multipliedMatrix = new Matrix();
		if (this.colsCount == newArray.rowsCount) {
			multipliedMatrix.rowsCount = this.rowsCount;
			multipliedMatrix.colsCount = newArray.colsCount;
			multipliedMatrix.array = new int[this.rowsCount][newArray.colsCount];
			System.out.println(this.rowsCount + " " + newArray.colsCount);
			for (int i = 0; i < this.rowsCount; i++) {
				for (int j = 0; j < newArray.colsCount; j++) {
					for (int k = 0; k < this.colsCount; k++) {
						multipliedMatrix.array[i][j] += this.array[i][k]*newArray.array[k][j];
					}

				}
			}
			System.out.println("Multiply of two matrix:");
			for (int i = 0; i < multipliedMatrix.array.length;i++) {
				for (int j = 0; j < multipliedMatrix.array[i].length; j++) {
					System.out.print(multipliedMatrix.array[i][j] + " ");
				}
				System.out.println("");
			}
			return multipliedMatrix;
		} else {
			System.out.println("Sizes dont suit!");
			if (newArray.colsCount == this.rowsCount) {
				System.out.println("You can multiply in other order do you want to do it?");
				String r = sc.next().toLowerCase();
				if ( (r.equals("y") || (r.equals("yes") || (r.equals("да")) || (r.equals("д"))))) {
					
					multipliedMatrix = newArray.multiply(this);
				}
				
			}
		} 
		return multipliedMatrix;
		
	}

	public void printMatrix() {

		System.out.println("Matrix (" + rowsCount + " * " + colsCount + "):");
		System.out.print(toString());
	}

	public String toString () {
		String line = "";
		for (int i = 0; i < array.length; i++) {
			line += array[i][0];
			for (int j = 1; j < array[0].length; j++) {
				line += " " + array[i][j];
			}
			line += "\n";
		}
		return line;
	}
}