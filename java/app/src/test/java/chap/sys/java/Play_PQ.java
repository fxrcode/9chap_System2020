package chap.sys.java;

import java.util.*;

import org.junit.Test;

public class Play_PQ {
    private PriorityQueue<Node> minPQ;

    @Test
    public void play1() {
        minPQ = new PriorityQueue<>((a,b) -> (a.f - b.f));
        Node a = new Node(1, 10);
        Node b = new Node(3,30);
        Node c = new Node(2,20);
        minPQ.add(a);
        minPQ.add(b);
        minPQ.add(c);
        while (minPQ.size() != 0) {
            System.out.println(minPQ.remove());
        }
    }

    class Node {
        int k, f;

        public Node(int key, int freq) {
            this.k = key;
            this.f = freq;
        }

		@Override
		public String toString() {
			return "Node [f=" + f + ", k=" + k + "]";
		}

    }
}
