import java.util.Scanner;
import java.util.Locale;
import java.util.Arrays;
public class Matrix {
	double[][] array;
	int rowsCount;
	int colsCount;
	Scanner sc = new Scanner(System.in);
	public void inputMatrix() {
		do {
			System.out.print("Input matrix's sizes (m * n): ");
			rowsCount = sc.nextInt();
			colsCount = sc.nextInt();
		} while (rowsCount == 0 || colsCount == 0);

		this.array = new double[rowsCount][colsCount];

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
		System.out.print("Input your method num (1 - by strings/2 - elem by elem):");
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
			multipliedMatrix.array = new double[this.rowsCount][newArray.colsCount];
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
					return multipliedMatrix;
				} else {
					return null;
				}
				
			} else {
				return null;
			}

		}
		
	}

	public Matrix plus(Matrix matrix) {
		int m1 = this.rowsCount; 
		int n1 = this.colsCount;
		int m2 = matrix.rowsCount; 
		int n2 = matrix.colsCount;

		if (m1 == m2 && n1 == n2) {
			Matrix mx = new Matrix();
			mx.rowsCount = m1;
			mx.colsCount = n1;
			mx.array = new double[m1][n1];
			for (int i = 0; i < m1; i++) {
				for (int j = 0; j < n1; j++) {
					mx.array[i][j] += this.array[i][j] + matrix.array[i][j];
				}
			}
			return mx;
		} else {
			return null;
		}
	}


	public void printMatrix() {
		if (this.rowsCount != 0 && this.colsCount != 0) {
			System.out.println("You have matrix (" + rowsCount + " * " + colsCount + "):");
			System.out.print(toString());
		} else {
			System.out.println(rowsCount + " " + colsCount);
			System.out.println("Matrix is null");
		}
	}

	public String toString (double[][] array) {
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



	public Matrix inputVector(int m) {
		Matrix vector = new Matrix();
		vector.array = new double[1][m];
		vector.rowsCount = 1;
		vector.colsCount = m;
		for (int i = 0; i < m; i++) {
			vector.array[0][i] = sc.nextDouble();
		}
		return vector;
	}

	// Ранг матрицы
	public int findRang(Matrix matrix) {
		int rang = 0;

		double[][] arr = new double[this.rowsCount][this.colsCount];
		for (int i = 0; i < this.rowsCount; i++) {
			for (int j = 0; j < this.colsCount; j++) {
				arr[i][j] = this.array[i][j];
			}
		}

		arr = matrix.forwardGaussVector(arr);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i][i] != 0) {
				rang += 1;
			} else {
				return rang;
			}
		}
		return rang;

	}





	public Matrix solve(Matrix vector) {

		double[][] arr = new double[this.rowsCount][this.colsCount+1];	
		for (int i = 0; i < this.rowsCount; i++) {
			for (int j = 0; j < this.colsCount; j++) {
				arr[i][j] = this.array[i][j];
			}
			arr[i][this.colsCount] = vector.array[0][i];
		}

		boolean can = true;
		try {
			arr = Matrix.forwardGauss(arr);
		} catch (LinealDependenceException e) {
			can = false;
		}
		if (can) {
			arr = Matrix.backGauss(arr);
			vector.array = Matrix.solutionDiagonal(arr);
			System.out.println("----------\n" + toString(vector.array));
			return vector;
		} else {
			System.out.println("Система имеет беск.решений или не имеет их вовсе");
			return null;
		}
		
	}

	private static double[][] forwardGaussVector(double[][] array) {
		double div;
		for (int i = 0; i < array.length-1; i++) {
			// Замена строк если на главной диагонали нуль
			if (array[i][i] == 0) {
				boolean f = false;
				double replace;
				for (int z = i+1; z < array.length && f == false; z++) {
					if (array[i][z] != 0) {
						f = true;
						for (int l = 0; l < array[0].length; l++) {
							replace = array[i][l];
							array[i][l] = array[z][l];
							array[z][l] = replace;
						}
					}

				}
				// Если после замен, на главной диагонали остался нуль, то это линейно зависимые и их обнуляем.
				if (array[i][i] == 0) {
					for (int h = 0; h < array.length-i; h++) {
						for (int k = 0; k < array[0].length;k++) {
							array[array.length-h-1][k] = 0;
						}
					}
					continue;
				}
			}

			// Делаем прямой ход Гаусса.
			for (int j = i+1; j < array.length; j++) {
				div = (-1) * array[j][i] / array[i][i];
				array[j][i] = 0;
				for (int k = 0; k < array.length; k++) {
					if (i==k) continue;
					array[j][k] += div*array[i][k];
				}

			}
		}
		return array;
	}




	private static double[][] forwardGauss(double[][] array) throws LinealDependenceException {
		double div;
		for (int i = 0; i < array.length-1; i++) {
			// Замена строк если на главной диагонали нуль
			if (array[i][i] == 0) {
				boolean f = false;
				double replace;
				for (int z = i+1; z < array.length && f == false; z++) {
					if (array[i][z] != 0) {
						f = true;
						for (int l = 0; l < array[0].length; l++) {
							replace = array[i][l];
							array[i][l] = array[z][l];
							array[z][l] = replace;
						}
					}

				}
				// Если после замен, на главной диагонали остался нуль, то это линейно зависимые и их обнуляем.
				if (array[i][i] == 0) {
					for (int h = 0; h < array.length-i; h++) {
						for (int k = 0; k < array[0].length;k++) {
							array[array.length-h-1][k] = 0;
						}
					}
					continue;
				}
			}

			// Делаем прямой ход Гаусса.
			for (int j = i+1; j < array.length; j++) {
				div = (-1) * array[j][i] / array[i][i];
				array[j][i] = 0;
				for (int k = 0; k < array.length+1; k++) {
					if (i==k) continue;
					array[j][k] += div*array[i][k];
				}

			}
		}
		// Если на главной диагонали остался нуль в конце, значит линейно зависимые есть и отправляем ошибку.
		if (array[array.length-1][array[0].length-2] == 0) {
			throw new LinealDependenceException();
		}
		return array;
	}

	private static double[][] backGauss(double [][] array) {
		double div;
		for (int i = array.length-1; i > 0; i--) {
			for (int j = i-1; j >= 0; j--) {
				div = (-1) * array[j][i] / array[i][i];
				array[j][i] = 0;
				for (int k = array[0].length-1; k >= 0; k--) {
					if (i==k) continue;
					array[j][k] += div*array[i][k];
				}

			}
		}

		return array;
	}

	private static double[][] solutionDiagonal(double[][] array) {
		double[][] vector = new double[1][array.length];
		for (int i = 0; i < array.length; i++) {
			vector[0][i] = Math.round(array[i][array[0].length-1] / array[i][i]);
		}
		return vector;	
	}


}


