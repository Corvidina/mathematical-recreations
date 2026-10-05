import java.util.stream.IntStream;

public class LinearAlgebra {


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
    public static int[] linearCombine(int[] v, int[] b) {
        int[] out = v.clone();
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
    public static double[][] gausjordanelimination(int[][] M) {
        throw new Error("IMPLEMENT");
    }

}
