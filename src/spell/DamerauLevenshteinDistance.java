package spell;

public class DamerauLevenshteinDistance {
    public static int damerauLevenshteinDistance(String a, String b) {
        // "Infinity" -- greater than maximum possible edit distance
        // Used to prevent transpositions for first characters
        int INF = a.length() + b.length();

        // Matrix: (M + 2) x (N + 2)
        int[][] matrix = new int[a.length() + 2][b.length() + 2];
        for (int i = 0; i < b.length() + 2; i++) {
            matrix[0][i] = INF;
        }
        for (int i = 1; i < a.length() + 2; i++) {
            matrix[i][0] = INF;
            matrix[i][1] = i - 1;
        }
        for (int i = 2; i < b.length() + 2; i++) {
            matrix[1][i] = i - 1;
        }

        // Holds last row each element was encountered: `DA` in the Wikipedia pseudocode
        int[] lastRow = new int[26];
        for (int i = 0; i < 26; i++) {
            lastRow[i] = 0;
        }

        // Fill in costs
        for (int row = 1; row < a.length() + 1; row++) {
            // Current character in `a`
            char chA = a.charAt(row - 1);

            // Column of last match on this row: `DB` in pseudocode
            int lastMatchCol = 0;

            for (int col = 1; col < b.length() + 1; col++) {
                // Current character in `b`
                char chB = b.charAt(col - 1);

                // Last row with matching character; `i1` in pseudocode
                int lastMatchingRow = lastRow[chB - 'a'];

                // Cost of substitution
                int cost = chA == chB ? 0 : 1;

                // Compute substring distance
                matrix[row + 1][col + 1] = Math.min(
                        matrix[row][col] + cost, // Substitution
                        Math.min(matrix[row + 1][col] + 1, // Addition
                                matrix[row][col + 1] + 1) // Deletion
                );

                // Transposition
                if (row > 1 && col > 1 && chA == b.charAt(col - 2) && a.charAt(row - 2) == chB) {
                    matrix[row + 1][col + 1] = Math.min(matrix[row + 1][col + 1],
                            matrix[row - 1][col - 1] + cost);
                }

                // If there was a match, update lastMatchCol
                // Doing this here lets me be rid of the `j1` variable from the original pseudocode
                if (cost == 0) {
                    lastMatchCol = col;
                }
            }

            // Update last row for current character
            lastRow[chA - 'a'] = row;
        }

        // Return last element
        return matrix[a.length() + 1][b.length() + 1];
    }
}