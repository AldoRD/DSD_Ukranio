public class Ejercicios4 {
    public static void main(String[] args) {
        int fibb[] = new int[20];
        fibb[0] = 0;fibb[1]=1;fibb[2]=3;
        for(int i=3;i<20;i++){
            fibb[i] = fibb[i-1] + fibb[i-2]+fibb[i-3];
        }
        for(int i = 0;i<fibb.length;i++){
            System.out.println(fibb[i]);
        }
    }
}