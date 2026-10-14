import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //System.out.println(longestSentence(new File("C:\\users\\corvi\\onedrive\\desktop\\notes.txt")));
        //projectEuler1();
        //LinearAlgebra.tester();

        whatFactorialIsOverflow();
    }

    public static String matrixToStrings(int[][] a){
        StringBuilder builder = new StringBuilder();
        for (int[] ints : a) {
            builder.append(Arrays.toString(ints)).append("\n");
        }
        return builder.toString();
    }
    public static String matrixToStrings(double[][] a) {
        StringBuilder builder = new StringBuilder();
        for (double[] doubles : a) {
            builder.append(Arrays.toString(doubles)).append("\n");
        }
        return builder.toString();
    }

    public static String longestSentence(File file) {
        String full_text = "";
        try {
            Scanner scan = new Scanner(file.getAbsoluteFile());
            while(scan.hasNextLine()){
                full_text+=scan.nextLine();
            }

            String sentence = nextSentence(full_text);
            while(nextSentence(full_text)!=null){
                String c=nextSentence(full_text);
                if(wordsInSentence(sentence)>wordsInSentence(c)){
                    sentence=c;
                }
                full_text=full_text.substring(c.length());
            }
            return sentence;

        } catch (Exception e){
            System.out.println("Something failed");
            return null;
        }
    }
    public static String nextSentence(String text){
        return switch (whatNext(text)) {
            case PERIOD -> text.substring(0, text.indexOf(".") + 1);
            case QUESTION_MARK -> text.substring(0, text.indexOf("?") + 1);
            case EXCLAMATION_POINT -> text.substring(0, text.indexOf("!") + 1);
            default -> null;
        };
    }
    public static final int PERIOD = 0;
    public static final int QUESTION_MARK = 1;
    public static final int EXCLAMATION_POINT = 2;
    public static final int NONE = 3;
    public static int whatNext(String text){
        int p = text.contains(".")?text.indexOf("."):Integer.MAX_VALUE;
        int q = text.contains("?")?text.indexOf("?"):Integer.MAX_VALUE;
        int e = text.contains("!")?text.indexOf("!"):Integer.MAX_VALUE;
        int min = Math.min(Math.min(p,q),e);
        if(min==Integer.MAX_VALUE){
            return NONE;
        } else
            return min;

    }
    public static int wordsInSentence(String sentence){
        int words = 0;
        sentence=sentence.substring(0,sentence.length()-1)+" ";
        while(sentence.contains(" ")){
            words++;
            sentence=sentence.substring(sentence.indexOf(" "));
        }
        return  words;
    }
    public static void projectEuler1(){
        int o = 0;
        for (int v = 0; v < 1000; v++) {
            if(v%3==0 || v%5==0)
                o+=v;
        }
        System.out.println(o);
    }

    public static void whatFactorialIsOverflow() {
        int a = 1;
        for (int i = 1; i < 31; i++) {
            a*=i;
            if(a<0)
                break;
            System.out.println(i + " : " + a);
            // up to 12
        }
    }

}
