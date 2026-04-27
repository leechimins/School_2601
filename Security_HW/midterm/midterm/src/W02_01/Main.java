package W02_01;

public class Main {
    public static void main(String[] args) {
        calcSum(10, 20);
        calcSum(10, 20, 30, 40, 50);
    }

    static void calcSum(int... args) {
        int result = 0;
        for (int num : args) {
            result += num;
        }
        System.out.println(result);
    }
}