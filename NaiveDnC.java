public class NaiveDnC {

    public static int[][] multiply(int[][] matrix1, int[][] matrix2) {
        int rowsA = matrix1.length;
        int colsA = matrix1[0].length;
        int colsB = matrix2[0].length;

        int[][] result = new int[rowsA][colsB];

        if (rowsA == 1 && colsA == 1 && colsB == 1) {
            // Base case: single element multiplication
            result[0][0] = matrix1[0][0] * matrix2[0][0];
        } else {
            // Divide matrix1 and matrix2 into quadrants
            int[][] A11 = getQuadrant(matrix1, 0, 0, rowsA / 2, colsA / 2);
            int[][] A12 = getQuadrant(matrix1, 0, colsA / 2, rowsA / 2, colsA);
            int[][] A21 = getQuadrant(matrix1, rowsA / 2, 0, rowsA, colsA / 2);
            int[][] A22 = getQuadrant(matrix1, rowsA / 2, colsA / 2, rowsA, colsA);

            int[][] B11 = getQuadrant(matrix2, 0, 0, colsA / 2, colsB / 2);
            int[][] B12 = getQuadrant(matrix2, 0, colsB / 2, colsA / 2, colsB);
            int[][] B21 = getQuadrant(matrix2, colsA / 2, 0, colsA, colsB / 2);
            int[][] B22 = getQuadrant(matrix2, colsA / 2, colsB / 2, colsA, colsB);

            // Recursively calculate products
            int[][] C11 = add(multiply(A11, B11), multiply(A12, B21));
            int[][] C12 = add(multiply(A11, B12), multiply(A12, B22));
            int[][] C21 = add(multiply(A21, B11), multiply(A22, B21));
            int[][] C22 = add(multiply(A21, B12), multiply(A22, B22));

            // Combine results
            combineQuadrant(result, C11, 0, 0);
            combineQuadrant(result, C12, 0, colsB / 2);
            combineQuadrant(result, C21, rowsA / 2, 0);
            combineQuadrant(result, C22, rowsA / 2, colsB / 2);
        }

        return result;
    }

    public static int[][] getQuadrant(int[][] matrix, int startRow, int startCol, int endRow, int endCol) {
        int rows = endRow - startRow;
        int cols = endCol - startCol;
        int[][] quadrant = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                quadrant[i][j] = matrix[startRow + i][startCol + j];
            }
        }

        return quadrant;
    }

    public static void combineQuadrant(int[][] result, int[][] quadrant, int startRow, int startCol) {
        int rows = quadrant.length;
        int cols = quadrant[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[startRow + i][startCol + j] = quadrant[i][j];
            }
        }
    }

    public static int[][] add(int[][] matrix1, int[][] matrix2) {
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

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
