package spell;

public class Trie implements ITrie {

    private final Node root = new Node();

    @Override
    public void add(String word) {

    }

    @Override
    public Node find(String word) {
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
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
