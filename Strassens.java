public class Strassens {

    public static int[][] multiply(int[][] matrix1, int[][] matrix2) {
        int rowsA = matrix1.length;
        int colsA = matrix1[0].length;
        int colsB = matrix2[0].length;

        // Ensure the matrices are square and of size 2^n
        int n = Math.max(Math.max(rowsA, colsA), colsB);
        int size = 1;
        while (size < n) {
            size *= 2;
        }

        int[][] A = padMatrix(matrix1, size);
        int[][] B = padMatrix(matrix2, size);

        int[][] result = new int[size][size];

        strassenMultiply(A, B, result, 0, 0, 0, 0, 0, 0, size);

        return result;
    }

    private static void strassenMultiply(int[][] A, int[][] B, int[][] result,
                                         int rowA, int colA, int rowB, int colB,
                                         int rowResult, int colResult, int size) {
        if (size == 1) {
            result[rowResult][colResult] += A[rowA][colA] * B[rowB][colB];
            return;
        }

        int newSize = size / 2;

        // Create submatrices
        int[][] A11 = new int[newSize][newSize];
        int[][] A12 = new int[newSize][newSize];
        int[][] A21 = new int[newSize][newSize];
        int[][] A22 = new int[newSize][newSize];

        int[][] B11 = new int[newSize][newSize];
        int[][] B12 = new int[newSize][newSize];
        int[][] B21 = new int[newSize][newSize];
        int[][] B22 = new int[newSize][newSize];

        // Divide matrices into submatrices
        divideMatrix(A, A11, A12, A21, A22, rowA, colA, newSize);
        divideMatrix(B, B11, B12, B21, B22, rowB, colB, newSize);

        // Calculate intermediate matrices
        int[][] P1 = multiply(add(A11, A22), add(B11, B22));
        int[][] P2 = multiply(add(A21, A22), B11);
        int[][] P3 = multiply(A11, subtract(B12, B22));
        int[][] P4 = multiply(A22, subtract(B21, B11));
        int[][] P5 = multiply(add(A11, A12), B22);
        int[][] P6 = multiply(subtract(A21, A11), add(B11, B12));
        int[][] P7 = multiply(subtract(A12, A22), add(B21, B22));

        // Calculate result submatrices
        int[][] C11 = add(subtract(add(P1, P4), P5), P7);
        int[][] C12 = add(P3, P5);
        int[][] C21 = add(P2, P4);
        int[][] C22 = add(subtract(add(P1, P3), P2), P6);

        // Combine result submatrices
        combineMatrix(result, C11, C12, C21, C22, rowResult, colResult, newSize);
    }

    private static int[][] padMatrix(int[][] matrix, int newSize) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] paddedMatrix = new int[newSize][newSize];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                paddedMatrix[i][j] = matrix[i][j];
            }
        }

        return paddedMatrix;
    }

    private static void divideMatrix(int[][] matrix, int[][] C11, int[][] C12, int[][] C21, int[][] C22,
                                     int startRow, int startCol, int newSize) {
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C11[i][j] = matrix[startRow + i][startCol + j];
                C12[i][j] = matrix[startRow + i][startCol + j + newSize];
                C21[i][j] = matrix[startRow + i + newSize][startCol + j];
                C22[i][j] = matrix[startRow + i + newSize][startCol + j + newSize];
            }
        }
    }

    private static int[][] add(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }

    private static int[][] subtract(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return result;
    }

    private static void combineMatrix(int[][] matrix, int[][] C11, int[][] C12, int[][] C21, int[][] C22,
                                      int startRow, int startCol, int newSize) {
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                matrix[startRow + i][startCol + j] = C11[i][j];
                matrix[startRow + i][startCol + j + newSize] = C12[i][j];
                matrix[startRow + i + newSize][startCol + j] = C21[i][j];
                matrix[startRow + i + newSize][startCol + j + newSize] = C22[i][j];
            }
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
