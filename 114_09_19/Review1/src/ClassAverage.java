import java.util.Scanner;

public class ClassAverage {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int total = 0;
        int gradeCounter = 0;

        System.out.println("輸入完成請輸入-1，結束輸入\n");

        while (input.hasNext()) {
            if (input.hasNextInt()) {
                int grade = input.nextInt();
                if (grade == -1) break;
                total += grade;
                gradeCounter++;
            } else {
                System.out.println("請輸入整數成績，該筆資料已跳過。");
                input.next(); // 跳過錯誤輸入
            }
        }

        if (gradeCounter != 0) {
            double average = (double) total / gradeCounter;
            System.out.printf("總分: %d%n", total);
            System.out.printf("成績數: %d%n", gradeCounter);
            System.out.printf("平均: %.2f%n", average);
        } else {
            System.out.println("沒有輸入成績");
        }
    }
}
