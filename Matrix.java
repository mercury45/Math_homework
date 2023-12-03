import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class Matrix {
	double[][] array;
	int rowsCount;
	int colsCount;

	Matrix() {
		this(0,0, new double[0][0]);
	}
	Matrix(int rowsCount, int colsCount, double[][] array) {
		this.rowsCount = rowsCount;
		this.colsCount = colsCount;
		this.array = array;
	}

	Scanner sc = new Scanner(System.in);
	public void inputMatrix() {
		do {
			System.out.print("Input matrix's sizes (m * n): ");
			rowsCount = sc.nextInt();
			colsCount = sc.nextInt();
		} while (rowsCount <= 0 || colsCount <= 0);

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
		System.out.println("Now put strings one by one. FOR FLOAT NUMBERS USE ','. Example: (2,5 ; 6,23)");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < rowsCount; i++) {
			System.out.println("Put your " + (i + 1) + " string");
			for (int j = 0; j < colsCount; j++) {
				array[i][j] = sc.nextDouble();
			}
		}

	}

	public void secondMethod() {
		System.out.println("Now put your elements one by one. FOR FLOAT NUMBERS USE ','. Example: (2,5 ; 6,23)");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < colsCount; j++) {
				System.out.println("Put your (" + (i+1) + ", " + (j+1) + ") element");
				array[i][j] = sc.nextDouble();
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

	}


	/*
	public void printMatrix() {
		if (this.rowsCount > 0 && this.colsCount > 0) {
			System.out.println("You have matrix (" + rowsCount + " * " + colsCount + "):");
			System.out.print(toString(array));
		} else {
			System.out.println("Matrix is null");
		}
	}
	*/

	public static String toString (double[][] array) {
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
		String line = "Matrix (" + rowsCount + " * " + colsCount + "):\n";
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
	public static int findRang(Matrix matrix) {
		int rang;

		double[][] arr = new double[matrix.rowsCount][matrix.colsCount];
		for (int i = 0; i < matrix.rowsCount; i++) {
			for (int j = 0; j < matrix.colsCount; j++) {
				arr[i][j] = matrix.array[i][j];
			}
		}
		rang = matrix.rowsCount < matrix.colsCount ? matrix.rowsCount : matrix.colsCount; // Находим максимальный ранг
		arr = matrix.forwardGauss(arr);
		int depended = 0;
		for (int i = 0; i < rang; i++) {
			int count = 0;
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == 0 || Math.abs(arr[i][j]) < 1e-5) {
					count++;
				}
			}
			if (count == arr[0].length) {
				depended++;
			}
		}
		return (rang-depended);

	}





	public Matrix solve(Matrix vector) {

		double[][] arr = new double[this.rowsCount][this.colsCount+1];	
		for (int i = 0; i < this.rowsCount; i++) {
			for (int j = 0; j < this.colsCount; j++) {
				arr[i][j] = this.array[i][j];
			}
			arr[i][this.colsCount] = vector.array[0][i];
		}
		Matrix m = new Matrix(this.rowsCount, this.colsCount+1, arr);


		arr = Matrix.forwardGauss(arr);
		if (findRang(this) == findRang(m) && findRang(this) == colsCount) {
			arr = Matrix.backGauss(arr);
			vector.array = Matrix.solutionDiagonal(arr);
			System.out.println("Solution:\n" + toString(vector.array));
		} else if (findRang(this) == findRang(m) && findRang(this) < colsCount) {
			System.out.println("There is infinity number of solutions");
			return null;
		} else if (findRang(this) != findRang(m)) {
			System.out.println("There is no solutions");
			return null;
		}

		return vector;


		
	}






	private static double[][] forwardGauss(double[][] array) {
		double div;
		int j2 = 0;
		for (int i = 0; i < array.length-1; i++) {
			// Замена строк если на главной диагонали нуль
			if (array[i][i+j2] == 0) {
				boolean f = false;
				double replace;
				for (int z = i+1; z < array.length && f == false; z++) {
					if (array[z][i+j2] != 0) {
						f = true;
						for (int l = 0; l < array[0].length; l++) {
							replace = array[i][l];
							array[i][l] = array[z][l];
							array[z][l] = replace;
						}
					}

				}
				// Если после замен, на главной диагонали остался нуль
				if (array[i][i+j2] == 0) {
					i--;
					j2++;
					continue;
				}
			}

			// Делаем прямой ход Гаусса.
			for (int j = i+1; j < array.length; j++) {
				div = (-1) * array[j][i+j2] / array[i][i+j2];
				for (int k = i+j2; k < array[0].length; k++) {
					array[j][k] += div*array[i][k];
				}
			}
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
			vector[0][i] = Math.round(array[i][array[0].length-1] / array[i][i] * 1000) / 1000.0;
		}
		return vector;	
	}

	private static double det(double[][] array) throws LinealDependenceException {
		double[][] arr = copyArray(array);
		double div;
		double det = 1;
		int sign = 1;
		for (int i = 0; i < arr.length-1; i++) {
			// Замена строк если на главной диагонали нуль
			if (arr[i][i] == 0) {
				boolean f = false;
				double replace;
				for (int z = i+1; z < arr.length && f == false; z++) {
					if (arr[z][i] != 0) {
						f = true;
						for (int l = 0; l < arr[0].length; l++) {
							replace = arr[i][l];
							arr[i][l] = arr[z][l];
							arr[z][l] = replace;
							// При изменении строк меняем знак
							sign *= -1;
						}
					}

				}
				// Если после замен, на главной диагонали остался нуль
				if (arr[i][i] == 0) {
					det = 0;
					throw new LinealDependenceException();
				}
			}

			// Делаем прямой ход Гаусса.
			for (int j = i+1; j < arr.length; j++) {
				div = (-1) * arr[j][i] / arr[i][i];
				for (int k = i; k < arr[0].length; k++) {
					arr[j][k] += Math.round(div*arr[i][k] * 10000) / 10000.0;
				}
			}
		}

		// Считаем det
		for (int i = 0; i < arr.length; i++) {
			det *= arr[i][i];
		}

		return sign * det;
		// think about changing sign
	}

	private static double[][] changeColumns(double[][] array, double[][] vector, int place) {
		double[][] arr = copyArray(array);
		for (int i = 0; i < arr.length; i++) {
			arr[i][place] = vector[0][i];
		}
		return arr;
	}

	private static double[][] copyArray(double[][] array) {
		int cols = array[0].length;
		int rows = array.length;
		double[][] arr = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				arr[i][j] = array[i][j];
			}
		}
		return arr;
	}


	public Matrix solveCramer(Matrix vector) throws LinealDependenceException {
		Matrix solutions = new Matrix(1,colsCount, new double[1][colsCount]);
		if (det(array) != 0) {
			double det = det(array);
			for (int i = 0; i < array.length; i++) {
				double arr[][] = copyArray(changeColumns(array, vector.array, i));
				solutions.array[0][i] = Math.round((det(arr) / det) * 10000) / 10000.0;
			}
			return solutions;
		} else {
			throw new LinealDependenceException();
		}
	}

}


