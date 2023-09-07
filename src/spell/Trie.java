package spell;

import java.util.ArrayList;

public class Trie implements ITrie {

    private final Node root = new Node();

    @Override
    public void add(String word) {
        Node lastNode = findCreate(word);
        lastNode.incrementValue();
    }

    @Override
    public Node find(String word) {
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (currentNode.getChild(c) != null) {
                currentNode = currentNode.getChild(c);
            } else {
                return null;
            }
        }
        if (currentNode.getValue() > 0) {
            return currentNode;
        } else {
            return null;
        }
    }

    public Node findCreate(String word) {
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (currentNode.getChild(c) != null) {
                currentNode = currentNode.getChild(c);
            } else {
                currentNode = currentNode.addChild(c);
            }
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        String[] uniqueWords = root.getSubStrings("");
        return uniqueWords.length;
    }

    @Override
    public int getNodeCount() {
        ArrayList<Node> remainingNodes = new ArrayList<>();
        remainingNodes.add(root);
        int nodeCount = 0;
        while (!remainingNodes.isEmpty()) {
            Node currentNode = remainingNodes.remove(0);
            nodeCount++;
            for (Node child : currentNode.getChildren()) {
                if (child != null) {
                    remainingNodes.add(child);
                }
            }
        }
        return nodeCount;
    }

    @Override
    public String toString() {
        String[] uniqueWords = root.getSubStrings("");
        StringBuilder finalString = new StringBuilder();
        for (String word : uniqueWords) {
            finalString.append(word).append("\n");
        }
        return finalString.toString();
    }

    @Override
    public int hashCode() {
        int firstChildIndex = 0;
        Node[] rootChildren = root.getChildren();
        for (int i = 0; i < rootChildren.length; i++) {
            if (rootChildren[i] != null) {
                firstChildIndex = i;
            }
        }
        return firstChildIndex * getNodeCount() * getWordCount();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Trie) {
            return root.compareChildren(((Trie) o).root);
        } else {
            System.out.println("argument 1 must be of type Trie");
            return false;
        }
    }

    public String findBestWord(String inputWord) {
        ArrayList<String> bestWords = new ArrayList<String>();
        int bestDistance = 100;
        String[] uniqueWords = root.getSubStrings("");
        for (String word : uniqueWords) {
            DLMatrix newMatrix = new DLMatrix(inputWord, word);
            int distance = newMatrix.getDistance();
            if (distance < bestDistance) {
                bestDistance = distance;
                bestWords.clear();
                bestWords.add(word);
            } else if (distance == bestDistance) {
                bestWords.add(word);
            }
        }
        if (bestDistance > 2) {
            return null;
        }
        if (bestWords.size() > 1) {
            // alphabetize and return the first
            bestWords.sort();
            return bestWords.get(0);
        } else {
            return bestWords.get(0);
        }
    }
}
