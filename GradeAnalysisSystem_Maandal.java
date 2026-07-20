import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Grade Analysis System for a Teacher
 *
 * INPUT (entered manually by the teacher at runtime):
 *  - The number of students in the class
 *  - For each student: their name (String) and final score (integer, 0 - 100)
 *
 * OUTPUT:
 *  - Letter grade for each student
 *  - Name and score of top-performing student (all students tied for the top score)
 *  - Class average (mean of all scores)
 *  - Count of students who passed (score >= 60)
 *  - Count of students who did not pass (score < 60)
 *  - A boolean flag: did the class average fall below 70?
 *
 * Grading Scale:
 *  A+  98 - 100
 *  A   92 - 97
 *  B+  87 - 91
 *  B   81 - 86
 *  C+  77 - 80
 *  C   71 - 76
 *  D   60 - 70
 *  F   0  - 59
 */
public class GradeAnalysisSystem {

    // ---------- Core logic ----------

    /**
     * Returns the letter grade for a given numeric score based on the grading scale.
     */
    public static String getLetterGrade(int score) {
        if (score >= 98 && score <= 100) return "A+";
        if (score >= 92 && score <= 97)  return "A";
        if (score >= 87 && score <= 91)  return "B+";
        if (score >= 81 && score <= 86)  return "B";
        if (score >= 77 && score <= 80)  return "C+";
        if (score >= 71 && score <= 76)  return "C";
        if (score >= 60 && score <= 70)  return "D";
        if (score >= 0  && score <= 59)  return "F";
        return "Invalid"; // safety net for out-of-range input
    }

    /**
     * Calculates the mean (average) of all scores.
     */
    public static double calculateAverage(int[] scores) {
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return (double) sum / scores.length;
    }

    /**
     * Finds the highest score in the array.
     */
    public static int findHighestScore(int[] scores) {
        int highest = scores[0];
        for (int score : scores) {
            if (score > highest) {
                highest = score;
            }
        }
        return highest;
    }

    /**
     * Returns the names of all students who achieved the highest score
     * (handles ties by returning every matching student).
     */
    public static List<String> findTopPerformers(String[] names, int[] scores, int highestScore) {
        List<String> topPerformers = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] == highestScore) {
                topPerformers.add(names[i]);
            }
        }
        return topPerformers;
    }

    /**
     * Counts how many students passed (score >= 60).
     */
    public static int countPassed(int[] scores) {
        int count = 0;
        for (int score : scores) {
            if (score >= 60) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts how many students did not pass (score < 60).
     */
    public static int countFailed(int[] scores) {
        int count = 0;
        for (int score : scores) {
            if (score < 60) {
                count++;
            }
        }
        return count;
    }

    // ---------- Program entry point ----------

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== GRADE ANALYSIS SYSTEM ==========\n");

        // Ask the teacher how many students there are
        int numStudents = 0;
        while (true) {
            System.out.print("Enter the number of students: ");
            String input = scanner.nextLine().trim();
            try {
                numStudents = Integer.parseInt(input);
                if (numStudents <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }

        String[] names = new String[numStudents];
        int[] scores = new int[numStudents];

        // Teacher manually encodes each student's name and score
        System.out.println("\nEnter each student's name and final score (0 - 100).");
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\nStudent " + (i + 1) + ":");

            System.out.print("  Name: ");
            names[i] = scanner.nextLine().trim();

            int score = -1;
            while (true) {
                System.out.print("  Score: ");
                String scoreInput = scanner.nextLine().trim();
                try {
                    score = Integer.parseInt(scoreInput);
                    if (score < 0 || score > 100) {
                        System.out.println("  Score must be between 0 and 100. Try again.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid input. Please enter a whole number between 0 and 100.");
                }
            }
            scores[i] = score;
        }

        System.out.println("\n========== ANALYSIS RESULTS ==========\n");

        // 1. Letter grade for each student
        System.out.println("--- Individual Student Grades ---");
        for (int i = 0; i < names.length; i++) {
            String letterGrade = getLetterGrade(scores[i]);
            System.out.printf("%-15s | Score: %-5d | Grade: %s%n", names[i], scores[i], letterGrade);
        }

        // 2. Top-performing student(s)
        int highestScore = findHighestScore(scores);
        List<String> topPerformers = findTopPerformers(names, scores, highestScore);

        System.out.println("\n--- Top Performing Student(s) ---");
        System.out.println("Highest Score: " + highestScore);
        for (String name : topPerformers) {
            System.out.println(" - " + name);
        }

        // 3. Class average
        double average = calculateAverage(scores);
        System.out.printf("%n--- Class Average ---%n%.2f%n", average);

        // 4. Pass / fail counts
        int passedCount = countPassed(scores);
        int failedCount = countFailed(scores);

        System.out.println("\n--- Pass / Fail Summary ---");
        System.out.println("Students who passed (score >= 60): " + passedCount);
        System.out.println("Students who did not pass (score < 60): " + failedCount);

        // 5. Boolean flag: did the class average fall below 70?
        boolean belowSeventy = average < 70;
        System.out.println("\n--- Class Performance Flag ---");
        System.out.println("Did the class average fall below 70? " + belowSeventy);

        System.out.println("\n============================================");

        scanner.close();
    }
}
