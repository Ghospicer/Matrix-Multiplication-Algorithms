import java.util.Random;
import java.util.Scanner;

public class TestBench {
	
	public static int[][] initializeMatrix(int n) {
        int[][] matrix = new int[n][n];
        Random rand = new Random();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix[i][j] = rand.nextInt(10) + 1;
            }
        }

        return matrix;
    }

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
    	System.out.println("Please enter matrix size: ");
        int n = sc.nextInt(); 
        int[][] matrixA = initializeMatrix(n);
        int[][] matrixB = initializeMatrix(n);
        long bruteSum = 0;
        long divncSum = 0;
        long strsnSum = 0;
        for(int i=1;i<6;i++) {
        	System.out.println("Matrix A*B with Brute Force");
            long brute1 = System.nanoTime();
            int[] [] resultbf = Bruteforce.multiply(matrixA, matrixB);
            Bruteforce.printMatrix(resultbf);
            long brute2 = System.nanoTime();
            long bruteTime = brute2 - brute1;
            System.out.printf("Run " + i + ": " + bruteTime + " ns\n");
            if (i>1) {
            	bruteSum += bruteTime;
            }
        }
        for(int i=1;i<6;i++) {
            System.out.println("Matrix A*B with Divide & Conquer");
            long divnc1 = System.nanoTime();
            int[] [] resultDnC = NaiveDnC.multiply(matrixA, matrixB);
            NaiveDnC.printMatrix(resultDnC);
            long divnc2 = System.nanoTime();
            long divncTime = divnc2 - divnc1;
            System.out.printf("Run " + i + ": " + divncTime + " ns\n");
            if (i>1) {
            	divncSum += divncTime;
            }
        }
        for(int i=1;i<6;i++) {
            System.out.println("Matrix A*B with Strassen's");
            long strsn1 = System.nanoTime();
            int[] [] resultstrsn = Strassens.multiply(matrixA, matrixB);
            Bruteforce.printMatrix(resultstrsn);
            long strsn2 = System.nanoTime();
            long strsnTime = strsn2 - strsn1;
            System.out.printf("Run " + i + ": " + strsnTime + " ns\n");
            if (i>1) {
            	strsnSum += strsnTime;
            }
        }
        long bruteAvg = bruteSum/4;
        long divncAvg = divncSum/4;
        long strsnAvg = strsnSum/4;
        System.out.println("Avg. of Brute Force:" + bruteAvg);
        System.out.println("Avg. of Divide & Conquer:" + divncAvg);
        System.out.println("Avg. of Strassen's:" + strsnAvg);
        sc.close();
        
    }

}
