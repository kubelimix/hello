package com.limix.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 2022-03-14
 * 树遍历
 * INFO 深度遍历- java.util.Queue java.util.LinkedList
 * INFO java.util.Queue, add remove
 * LIMIX TODO 是否可以使用非循环的方式来进行深度遍历了?
 */
public class TreeTravel {

    public static class Node {
        public char text;
        public Node left;
        public Node right;
        public Node(char a) {
            this.text = a;
        }
        public Node(char a, Node left, Node right) {
            this.text = a;
            this.left = left;
            this.right = right;
        }
    }

    public static void firstTravelTree(Node node, Consumer<Node> consumer) {
        if (node == null) {
            return;
        }
        consumer.accept(node);
        firstTravelTree(node.left, consumer);
        firstTravelTree(node.right, consumer);
    }

    public static void midTravelTree(Node node, Consumer<Node> consumer) {
        if (node == null) {
            return;
        }
        midTravelTree(node.left, consumer);
        consumer.accept(node);
        midTravelTree(node.right, consumer);
    }

    public static void afterTravelTree(Node node, Consumer<Node> consumer) {
        if (node == null) {
            return;
        }
        afterTravelTree(node.left, consumer);
        afterTravelTree(node.right, consumer);
        consumer.accept(node);
    }

    public static void depthTravelTree(Queue<Node> queue, Node node, Consumer<Node> consumer) {
        if (queue.isEmpty()) {
            consumer.accept(node);
            queue.add(node.left);
            queue.add(node.right);
        } else {

        }
    }

    public static void main(String[] args) {
        Node F = new Node('F');
        Node B = new Node('B');
        Node G = new Node('G');
        Node A = new Node('A');
        Node D = new Node('D');
        Node C = new Node('C');
        Node E = new Node('E');
        Node I = new Node('I');
        Node H = new Node('H');
        F.left = B;
        F.right = G;
        B.left = A;
        B.right = D;
        D.left = C;
        D.right = E;
        G.right = I;
        I.left = H;

        // 先序遍历
        firstTravelTree(F, n -> System.out.print(n.text));
        System.out.println();
        // 中序遍历
        midTravelTree(F, n -> System.out.print(n.text));
        System.out.println();
        // 后序遍历
        afterTravelTree(F, n -> System.out.print(n.text));
        System.out.println();
        // 广度遍历
        // 添加队列-
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(F);
        while (!queue.isEmpty()) {
            Node a = queue.remove();
            System.out.print(a.text);
            if (a.left != null) {
                queue.add(a.left);
            }
            if (a.right != null) {
                queue.add(a.right);
            }
        }
    }
}
