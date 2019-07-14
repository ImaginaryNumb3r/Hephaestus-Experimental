package lib;

import collections.iterator.Iterables;
import collections.iterator.Iterators;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Creator: Patrick
 * Created: 24.03.2019
 * Purpose:
 */
public class SuffixTree {
    private final List<Node> _rootNodes;

    public SuffixTree(Collection<String>... stringLists) {
        _rootNodes = new ArrayList<>();
        for (Collection<String> strings : stringLists) {
            addAll(strings);
        }
    }

    public void addAll(Collection<? extends CharSequence> strings) {
        for (CharSequence string : strings) {
            add(string);
        }
    }

    public void add(CharSequence string) {
        List<Node> currentLevel = _rootNodes;

        for (Character ch : Iterables.of(string)) {
            var node = getOrCreateNode(currentLevel, ch);
            currentLevel = node._children;
            currentLevel.add(Node.END_NODE);
        }
    }

    public Traverser traverser() {
        return new Traverser(_rootNodes);
    }

    private Node getOrCreateNode(List<Node> root, char ch) {
        for (Node node : root) {
            if (node._char == ch) {
                return node;
            }
        }

        return new Node(ch);
    }

    private static class Node implements Iterable<Node> {
        private static final Node END_NODE = new Node(true);
        private final char _char;
        private final List<Node> _children;
        private final boolean _end;

        public Node(boolean end) {
            _char = 0;
            _children = Collections.emptyList();
            _end = end;
        }

        public Node(char ch) {
            _char = ch;
            _children = new ArrayList<>();
            _end = false;
        }

        @NotNull
        @Override
        public Iterator<Node> iterator() {
            return _children.iterator();
        }
    }

    public class Traverser {
        private List<Node> _cur;

        public Traverser(List<Node> cur) {_cur = cur;}

        /**
         * @param ch
         * @throws java.util.NoSuchElementException
         */
        public void traverse(char ch) {
            var iter = _cur.iterator();
            Node cur = null;
            boolean finished = false;

            while (iter.hasNext() && !finished) {
                cur = iter.next();
                finished = cur._char != ch;
            }

            if (finished) {
                _cur = cur._children;
            } else {
                throw new NoSuchElementException();
            }
        }

        public boolean isWord() {
            for (Node node : _cur) {
                if (node._end) return true;
            }

            return false;
        }

        public Iterator<Character> characters() {
            return Iterators.map(_cur.iterator(), node -> node._char);
        }
    }
}
