package spell;

public class DLMatrix {
    private final int[][] matrix;
    private String word1;
    private String word2;
    private boolean populated = false;
    public DLMatrix(String inputWord1, String inputWord2) {
        if (inputWord1.length() > inputWord2.length()) {
            word1 = inputWord2;
            word2 = inputWord1;
        } else {
            word1 = inputWord1;
            word2 = inputWord2;
        }
        matrix = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            matrix[0][j] = j;
        }
    }
    private void populateMatrix() {
        // populate matrix
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = i; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1];
                } else {
                    int minTop = Math.min(matrix[i - 1][j], matrix[i - 1][j - 1]);
                    if (i == j) {
                        // matrix[i][j - 1] does not exist in this triangle
                        matrix[i][j] = 1 + minTop;
                    } else {
                        matrix[i][j] = 1 + Math.min(minTop, matrix[i][j - 1]);
                    }
                }
            }
        }
        populated = true;
    }

    public int getDistance() {
        if (!populated) {
            populateMatrix();
        }
        return matrix[word1.length()][word2.length()];
    }
    public void printMatrix() {
        if (!populated) {
            populateMatrix();
        }
        System.out.print("      ");
        for (int j = 0; j < word2.length(); j++) {
            System.out.print(" " + word2.charAt(j) + " ");
        }
        System.out.print("\n");
        for (int i = 0; i <= word1.length(); i++) {
            if (i > 0) {
                System.out.print(" " + word1.charAt(i - 1) + " ");
            } else {
                System.out.print("   ");
            }
            for (int j = 0; j <= word2.length(); j++) {
                if (j < i && j != 0) {
                    System.out.print("   ");
                } else {
                    System.out.print(" " + matrix[i][j] + " ");
                }
            }
            System.out.print("\n");
        }
    }
}
