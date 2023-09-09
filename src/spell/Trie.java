package spell;

public class Trie implements ITrie {

    private int nodeCount = 1;
    private int wordCount;
    private final Node root = new Node();

    @Override
    public void add(String word) {
        boolean wordAlreadyExisted = (find(word) != null);
        Node lastNode = findCreate(word);
        lastNode.incrementValue();
        if (!wordAlreadyExisted) {
            wordCount++;
        }
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
                nodeCount++;
            }
        }
        return currentNode;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        return root.getSubStrings("");
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
        return firstChildIndex * nodeCount * wordCount;
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
}
