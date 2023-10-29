import java.util.Scanner;

public class Main {
	public static void clean() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.println("");
		}
	}

	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		Matrix matrix = new Matrix();
		while (true) {
			System.out.print("1. Input matrix\n2. Output matrix\n3. Multiply on other matrix\n4. Summarize matrix with other\n5. Find a rang on matrix\n");
			String chose;
			boolean f = true;
			
			do {
				chose = sc.next();
				switch (chose) {
					case "1":
						matrix.inputMatrix();
						f = false;
						break;
					case "2":
						matrix.printMatrix();
						f = false;
						break;
					case "3":
						Matrix matrixMultiply = new Matrix();
						matrixMultiply.inputMatrix();	
						try {
							matrixMultiply = matrix.multiply(matrixMultiply);
						} catch (Exception e) {
							System.out.println(e);
							System.out.println(e.getMessage());
						}
						f = false;
						break;
					case "4":
						Matrix matrixsum = new Matrix();
						matrixsum.inputMatrix();
						matrix.plus(matrixsum);
						f = false;
						break;
					case "5":
						f = false;
						break;
					default:
						System.out.println("Bad command. Try again");
						break;
				}
			} while (f);
		}

		
	}
}