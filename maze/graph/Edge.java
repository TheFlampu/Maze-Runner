package maze.graph;

import java.util.Objects;

public class Edge {
    private final byte weight;
    private final Node nodeA;
    private final Node nodeB;

    public Edge(byte weight, Node nodeA, Node nodeB) {
        this.weight = weight;
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public byte getWeight() {
        return weight;
    }

    public Node getNodeA() {
        return nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public boolean containNode(Node node) {
        return nodeA.equals(node) || nodeB.equals(node);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", nodeA=" + nodeA +
                ", nodeB=" + nodeB +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return nodeA.equals(edge.nodeA) &&
                nodeB.equals(edge.nodeB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB);
    }
}
