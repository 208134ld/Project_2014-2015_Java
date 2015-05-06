package util;

import java.util.Iterator;
import java.util.Stack;
import javafx.scene.control.TreeItem;

public class TreeIterator<T> implements Iterator<TreeItem<T>> {

    private Stack<TreeItem<T>> stack = new Stack<>();
    private TreeItem<T> itemS;

    public TreeIterator(TreeItem<T> root) {
        stack.push(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeItem<T> next() {
        TreeItem<T> nextItem = stack.pop();
        itemS = nextItem;
        nextItem.getChildren().forEach(stack::push);
        return nextItem;
    }

    public void expand() {
        itemS.setExpanded(true);
        itemS.setExpanded(false);
    }

}
