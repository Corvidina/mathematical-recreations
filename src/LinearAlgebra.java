import java.util.stream.IntStream;
import java.lang.Math;

public class LinearAlgebra {


    public static void tester(){
        int[][] A = new int[][] {{1,2,4},
                {3,-2,1},
                {0,-3,2}};
        int[][] B =  new int[][]{{2, 3, 1},
                {4, 7, 5},
                {0, -2, 2}};
        int[][] C = new int[][]{{1, 2, 3},
                {4,5,6},
                {7,8,9}};
        int[][] D = new int[][]{
                {1,2,3},
                {4,5,6}
        };
        int[][] E = new int[][]{
                {6,7},
                {8,9},
                {10,11}
        };
        int[][] I = new int[][]{{1,0,0},
                {0,1,0},
                {0,0,1}};
        int[] v = new int[] {1,2,4};
        int[] b = new int[] {-3,1,1};

        System.out.println(Main.matrixToStrings(LinearAlgebra.gaussJordanInversionNoExchanges(B)));

        //System.out.println(LinearAlgebra.getTheta(v,b)/Math.PI);
//        System.out.println(Main.matrixToStrings(LinearAlgebra.left_matrix_multiplication(D,E)));
//        System.out.println(Main.matrixToStrings(LinearAlgebra.left_matrix_multiplication(E,D)));
//
//        System.out.println(LinearAlgebra.dot_product_vectors(v,b));

    }

    public static int dot_product_vectors(int[] a, int[] b){
        return a.length==0?0:a[0]*b[0]+dot_product_vectors(IntStream.range(0,a.length-1).map(i->a[i+1]).toArray(),IntStream.range(0,b.length-1).map(i->b[i+1]).toArray());
    }
    public static double getVectorLength(int[] v) {
        return Math.sqrt(dot_product_vectors(v,v));
    }
    public static double[] getUnitVectorEquivalent(int[] v) {
        var out = new double[v.length];
        var scalar = getVectorLength(v);
        for (int i = 0; i < out.length; i++) {
            out[i]=v[i]/scalar;
        }
        return out;
    }
    public static double getTheta(int[] v, int[] b) {
        return Math.acos((double)dot_product_vectors(v,b)/getVectorLength(v)/getVectorLength(b));
    }
    public static double[][] identityMatrixSizeN(int n) {
        var D = new double[n][n];
        for (int i = 0; i < n; i++) {
            D[i][i] = 1;
        }
        return D;
    }
    public static int[] matrix_vector_product(int[][] M, int[] v) {
        int[] out = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            out[i]=dot_product_vectors(getColumn(M,i),v);
        }
        return out;
    }
    public static int[] getColumn(int[][] M, int column) {
        int[] out = new int[M.length];
        for (int i = 0; i < M.length; i++) {
            out[i] = M[i][column];
        }
        return out;
    }
    public static int[] scaleVector(int[] v, int scalar) {
        int[] out = v.clone();
        for (int i = 0; i < out.length; i++) {
            out[i]*=scalar;
        }
        return out;
    }
    public static double[] scaleVector(int[] v, double scalar) {
        double[] out = new double[v.length];
        for (int i = 0; i < out.length; i++) {
            out[i]=v[i]*scalar;
        }
        return out;
    }
    public static double[] scaleVector(double[] v, double scalar) {
        var out = v.clone();
        for (int i = 0; i < out.length; i++) {
            out[i]*=scalar;
        }
        return out;
    }
    public static int[] linearCombine(int[] v, int[] b) {
        int[] out = v.clone();
        for (int i = 0; i < v.length; i++) {
            out[i]+=b[i];
        }
        return out;
    }
    public static double[] linearCombine(double[] v, double[] b) {
        var out = v.clone();
        for (int i = 0; i < v.length; i++) {
            out[i]+=b[i];
        }
        return out;
    }
    private static int[][] left_matrix_multiplication(int[][] A, int[][] M) {
        int[][] out = new int[A.length][M[0].length];
        for (int row = 0; row < out.length; row++) {
            for (int col = 0; col < out[0].length; col++) {
                out[row][col] = dot_product_vectors(getColumn(M,col), A[row]);
            }
        }
        return out;
    }
    public static int[][] left_matrix_multiplication(int[][]... matrices) {
        int[][] out = matrices[0];
        for (int i = 1; i < matrices.length; i++) {
            out = left_matrix_multiplication(out,matrices[i]);
        }
        return out;
    }
    public static double[] intArrToDouble(int[] v){
        double[] out = new double[v.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = v[i];
        }
        return out;
    }
    public static double[][] intMatrixToDouble(int[][] M) {
        var D = new double[M.length][M[0].length];
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                D[i][j] = M[i][j];
            }
        }
        return D;
    }
    public static double[][] gaussJordanInversionNoExchanges(int[][] M) {
        double[][] D = intMatrixToDouble(M);
        double[][] P = identityMatrixSizeN(M.length);

        // Create Upper Triangular Form
        for (int i = 0; i < D.length-1; i++) {
            for (int j = i+1; j < D.length; j++) {
                var b = D[j][i]/D[i][i];
                D[j] = linearCombine(D[j],scaleVector(D[i],-b));
                P[j] = linearCombine(P[j],scaleVector(P[i],-b));
            }
        }

        // Isolate Diagonal
        for (int i = D.length-1; i >= 0 ; i--) {
            for (int j = i-1; j >= 0; j--) {
                var b =  D[j][i]/D[i][i];
                D[j] = linearCombine(D[j],scaleVector(D[i],-b));
                P[j] = linearCombine(P[j],scaleVector(P[i],-b));
            }
        }

        // Scale to Identity
        for (int i = 0; i < D.length; i++) {
            P[i] = scaleVector(P[i],1/D[i][i]);
            D[i][i]=1;
        }
        System.out.println(Main.matrixToStrings(D));

        return P;
    }

}
