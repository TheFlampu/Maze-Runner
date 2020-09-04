package maze;

import java.util.Scanner;

public class MazeRunner {
    private Maze currentMaze = null;
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            printMenu();

            switch (getAction()) {
                case 1: generateNewMaze();
                break;
                case 2: loadMaze();
                break;
                case 3: saveMaze();
                break;
                case 4: drawMaze();
                break;
                case 5: solveMaze();
                break;
                case 0: return;
            }

        }
    }

    private void printMenu() {
        System.out.println("=== Menu ===" + "\n" +
                "1. Generate a new maze" + "\n" +
                "2. Load a maze"
        );
        if (currentMaze != null)
            System.out.println("3. Save the maze" + "\n" +
                    "4. Display the maze" + "\n" +
                    "5. Find the escape");
        System.out.println("0. Exit");
    }

    private int getAction() {
        int action;
        try {
            action = Integer.parseInt(scanner.nextLine());
            if (action > 5 || action < 0) throw new Exception();
        } catch (Exception ignored) {
            System.out.println("Incorrect option. Please try again");
            return -1;
        }
        return action;
    }

    private void generateNewMaze() {
        int size = getMazeSize();
        if (size < 5) {
            System.out.println("Minimum size 5");
        } else {
            currentMaze = new Maze(size);
            drawMaze();
        }
    }

    private int getMazeSize() {
        System.out.println("Enter the size of a new maze");
        return Integer.parseInt(scanner.nextLine());
    }

    private void drawMaze() {
        currentMaze.draw();
    }

    private void saveMaze() {
        currentMaze.serialize(scanner.nextLine());
    }

    private void loadMaze() {
        currentMaze = Maze.deserialize(scanner.nextLine());
    }

    private void solveMaze() {
        currentMaze.solve();
        drawMaze();
    }
}
