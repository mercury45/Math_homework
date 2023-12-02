import java.util.Scanner;

public class Main {
	public static void clean() {
		System.out.print("");
	}

	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		Matrix matrix = new Matrix();
		while (true) {
			System.out.print("1. Input matrix\n2. Output matrix\n3. Multiply on other matrix\n4. Summarize matrix with other\n5. Find a rang of matrix\n6. Make the Gauss Elimination\n7. End the programm.\n8. Solve by Cramer's rule.\n");
			String chose;
			boolean f = true;
			do {
				chose = sc.next();
				switch (chose) {
					case "1":
						clean();
						matrix.inputMatrix();
						f = false;
						break;
					case "2":
						clean();
						System.out.println(matrix);
						f = false;
						break;
					case "3":
						clean();
						Matrix matrixMultiply = new Matrix();
						matrixMultiply.inputMatrix();	
						try {
							matrixMultiply = matrix.multiply(matrixMultiply);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						f = false;
						break;
					case "4":
						clean();
						Matrix matrixsum = new Matrix();
						matrixsum.inputMatrix();
						if (matrixsum.colsCount == matrix.colsCount && matrixsum.rowsCount == matrix.rowsCount){
							matrixsum = matrix.plus(matrixsum);
							System.out.println("Summarize of two matrix:");
							System.out.println(matrixsum);
						} else {
							System.out.println("Sizes don't suit!");
						}
						f = false;
						break;
					case "5":
						clean();
						int rang = Matrix.findRang(matrix);
						if (rang != 0) {
							System.out.println("rang of matrix = " + rang);
						} else {
							System.out.println("rang = 0 ?!");
						}
						f = false;
						break;
					case "6":
						clean();
						f = false;
						int m = matrix.rowsCount;
						System.out.println("Give me a vector by size (1*" + m + " or "+ m + "*1):");
						Matrix vector = new Matrix();
						vector = vector.inputVector(m);

						try {
							matrix.solve(vector);
						} catch (Exception e) {
							System.out.println(e);
						}
						break;
					case "7":
						System.exit(0);
						f = false;
						break;
					case "8":

						m = matrix.rowsCount;
						if (m > 0 && matrix.rowsCount == matrix.colsCount) {
							System.out.println("Give me a vector by size (1*" + m + " or " + m + "*1):");
							Matrix vector1 = new Matrix();
							vector1 = vector1.inputVector(m);
							try {
								System.out.println(matrix.solveCramer(vector1));
							} catch (LinealDependenceException e) {
								System.out.println("No solutions");
							};
						} else {
							System.out.println("bad sizes of matrix!");
						}
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