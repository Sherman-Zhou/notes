import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tree {
    private int value;
    private List<Tree> children = new LinkedList<>();

    public Tree(int value, List<Tree> children) {
        super();
        this.value = value;
        this.children.addAll(children);
    }

    public Tree(int value, Tree... children) {
        this(value, asList(children));
    }

    public int getValue() {
        return value;
    }

    public List<Tree> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public Stream<Tree> flattened() {
        return Stream.concat(
            Stream.of(this),
            children.stream().flatMap(Tree::flattened));
    }

    public static void main(String[] args) {
        Tree t = new Tree(15, new Tree(13), new Tree(124, new Tree(3), new Tree(4)));

        // Get all values in the tree:
        System.out.println("ALL VALUES:  " + t.flattened().map(Tree::getValue).collect(toList()));

        // Get even values:
        System.out.println("EVEN VALUES: " + t.flattened().map(Tree::getValue).filter(v -> v % 2 == 0).collect(toList
            ()));

        // Sum of even values:
        System.out.println("SUM OF EVEN: " + t.flattened().map(Tree::getValue).filter(v -> v % 2 == 0).reduce((a, b)
            -> a + b));

        // Does it contain 13?
        System.out.println("CONTAINS 13: " + t.flattened().anyMatch(n -> n.getValue() == 13));
    }
}
