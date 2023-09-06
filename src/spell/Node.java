package spell;

import javax.management.openmbean.InvalidKeyException;
import java.util.ArrayList;
import java.util.Collections;

public class Node implements INode {
    private int count = 0;
    private final Node[] nodes = new Node[26];

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return nodes;
    }

    public Node getChild(char c) {
        if (c >= 'a' && c <= 'z') {
            return nodes[c - 'a'];
        } else {
            throw new InvalidKeyException("Character " + c + "is invalid.");
        }
    }

    public Node addChild(char c) {
        if (c >= 'a' && c <= 'z') {
            Node newNode = new Node();
            nodes[c - 'a'] = newNode;
            return newNode;
        } else {
            throw new InvalidKeyException("Character " + c + "is invalid.");
        }
    }

    public boolean hasChildren() {
        for (Node node : nodes) {
            if (node != null) {
                return true;
            }
        }
        return false;
    }

    public boolean compareChildren(Node o) {
        Node[] otherNodes = o.getChildren();
        boolean stillEqual = true;
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            Node otherNode = otherNodes[i];
            boolean bothExist = (node != null && otherNode != null);
            boolean sameExistence = bothExist || (node == null && otherNode == null);
            stillEqual = stillEqual && sameExistence;
            if (bothExist) {
                stillEqual = node.compareChildren(otherNode);
            }
        }
        return stillEqual;
    }

    public String[] getSubStrings(String parentString) {
        ArrayList<String> subStrings = new ArrayList<>();
        if (getValue() > 0) {
            subStrings.add(parentString);
        }
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                String newParentString = parentString + Character.toString(i + 'a');
                String[] newSubStrings = nodes[i].getSubStrings(newParentString);
                Collections.addAll(subStrings, newSubStrings);
            }
        }
        return subStrings.toArray(new String[subStrings.size()]);
    }
}
