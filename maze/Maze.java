package maze;

import maze.graph.Edge;
import maze.graph.Graph;
import maze.graph.Node;

import java.io.*;
import java.util.List;

public class Maze implements Serializable {
    private static final long serialVersionUID = 7L;
    private byte[][] maze;
    private boolean solved = false;

    public Maze(int mazeSize) {
        generateMaze(mazeSize) ;
    }

    public void draw() {
        for (byte[] line : maze) {
            for (byte column : line) {
                if (column == 0 || column == 3 && !solved) {
                    System.out.print("  ");
                } else if (column == 1) {
                    System.out.print("\u2588\u2588");
                } else if (column == 3) {
                    System.out.print("//");
                }
            }
            System.out.print("\n");
        }
    }

    private void generateMaze(int mazeSize) {
        maze = new byte[mazeSize][mazeSize];
        Graph graph = new Graph(mazeSize);
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                if (y % 2 == 0 || x % 2 == 0) maze[y][x] = 1;
                if (mazeSize % 2 == 0 && y == mazeSize - 1) maze[y][x] = 1;
                if (mazeSize % 2 == 0 && x == mazeSize - 1) maze[y][x] = 1;
            }
        }

        List<Edge> edgeList = graph.MST();

        for (Edge edge : edgeList) {
            Node nodeA = edge.getNodeA();
            Node nodeB = edge.getNodeB();
            maze[nodeA.getY() + nodeB.getY() + 1][nodeA.getX() + nodeB.getX() + 1] = 0;
        }

        Node entryNode = graph.getEntryNode();
        maze[entryNode.getY() * 2 + 1][0] = 3;

        Node exitNode = graph.getExitNode();
        maze[exitNode.getY() * 2 + 1][mazeSize - 1] = 3;
        if (mazeSize % 2 == 0) maze[exitNode.getY() * 2 + 1][mazeSize - 2] = 3;

        List<Edge> dfs = graph.getDfs();

        for (Edge edge : dfs) {
            Node nodeA = edge.getNodeA();
            Node nodeB = edge.getNodeB();
            maze[nodeA.getY() + nodeB.getY() + 1][nodeA.getX() + nodeB.getX() + 1] = 3;
            maze[nodeA.getY() * 2 + 1][nodeA.getX() * 2 + 1] = 3;
            maze[nodeB.getY() * 2 + 1][nodeB.getX() * 2 + 1] = 3;
        }
    }

    public void solve() {
        solved = true;
    }

    public void serialize (String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
        } catch (Exception ignored) {
        }
    }

    public static Maze deserialize (String fileName) {
        Maze maze = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            maze = (Maze) ois.readObject();
        } catch (FileNotFoundException ignored) {
            System.out.println("The file " + fileName + " does not exist");
        } catch (Exception ignored) {
        }
        return maze;
    }
}
