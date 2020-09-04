package maze.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private List<Edge> dfs = new ArrayList<>();
    private List<Edge> mst = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();
    private Map<Node, Node> parents = new HashMap<>();
    private Node entryNode;
    private Node exitNode;


    public Graph(int mazeSize) {
        generateGraph(mazeSize);
    }

    private void generateGraph(int mazeSize) {
        int graphSize = (mazeSize - 1) / 2;

        for (int y = 0; y < graphSize; y++) {
            for (int x = 0; x < graphSize; x++) {
                Node currentNode = new Node(y, x);
                parents.put(currentNode, currentNode);
                if (y != 0) {
                    byte weight = (byte) (Math.random() * 16 + 1);
                    Edge edge = new Edge(weight, currentNode, new Node(y -  1, x));
                    edgeList.add(edge);
                }

                if (x != graphSize - 1) {
                    byte weight = (byte) (Math.random() * 16 + 1);
                    Edge edge = new Edge(weight, currentNode, new Node(y, x + 1));
                    edgeList.add(edge);
                }
            }
        }
        edgeList.sort(Comparator.comparingInt(Edge::getWeight));

        entryNode = parents.get(new Node((int) (Math.random() * graphSize), 0));
        exitNode = parents.get(new Node((int) (Math.random() * graphSize), graphSize - 1));
    }

    public List<Edge> MST() {
        for (Edge edge : edgeList) {
            Node nodeA = find(edge.getNodeA());
            Node nodeB = find(edge.getNodeB());

            if (!nodeA.equals(nodeB)) {
                union(nodeA, nodeB);
                mst.add(edge);
            }
        }

        return mst;
    }

    public Node find(Node node){
        if(!parents.get(node).equals(node))
            return find(parents.get(node));
        return node;
    }

    public void union(Node nodeA, Node nodeB){
        Node nodeAParent = find(nodeA);
        Node nodeBParent = find(nodeB);
        parents.replace(nodeBParent, nodeAParent);
    }

    public List<Edge> getDfs() {
        travel(entryNode);
        return dfs;
    }


    public boolean travel(Node node) {
        List<Edge> nextEdges = mst.stream().filter(el -> el.containNode(node) && !dfs.contains(el)).collect(Collectors.toList());

        if (node.equals(exitNode)) return true;
        if (nextEdges.isEmpty()) return false;

        for (Edge nextEdge : nextEdges) {
            dfs.add(nextEdge);
            Node nextNode = nextEdge.getNodeA().equals(node) ? nextEdge.getNodeB() : nextEdge.getNodeA();
            if (!travel(nextNode)) {
                dfs.remove(nextEdge);
            } else {
                return true;
            }
        }

        return false;
    }

    public Node getEntryNode() {
        return entryNode;
    }

    public Node getExitNode() {
        return exitNode;
    }
}
