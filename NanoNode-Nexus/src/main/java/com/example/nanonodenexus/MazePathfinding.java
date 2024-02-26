package com.example.nanonodenexus;

import com.almasb.fxgl.pathfinding.maze.Maze;
import java.util.*;

public class MazePathfinding {

    private Maze maze; // Your Maze class
    private int width, height;

    public MazePathfinding(Maze maze) {
        this.maze = maze;
        this.width = maze.getWidth();
        this.height = maze.getHeight();
    }

    public List<AStarMazeCell> findPath(int x1, int y1, int x2, int y2) {
        Queue<AStarMazeCell> openSet = new LinkedList<>();
        Set<AStarMazeCell> closedSet = new HashSet<>();
        Map<AStarMazeCell, AStarMazeCell> cameFrom = new HashMap<>();

        AStarMazeCell start = new AStarMazeCell(maze.get(x1, y1));
        AStarMazeCell target = new AStarMazeCell(maze.get(x2, y2));

        openSet.add(start);

        while (!openSet.isEmpty() && openSet.size() < width * height) {
            AStarMazeCell current = openSet.poll();

            if (current.getX() == target.getX() && current.getY() == target.getY()) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            for (AStarMazeCell neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor)) continue;

                if (!openSet.contains(neighbor)) {
                    cameFrom.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }

        // No path has been found, return an empty list
        return Collections.emptyList();
    }

    private List<AStarMazeCell> reconstructPath(Map<AStarMazeCell, AStarMazeCell> cameFrom, AStarMazeCell current) {
        List<AStarMazeCell> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
            System.out.println("reconstructing path");
        }
        Collections.reverse(path);
        return path;
    }

    public List<AStarMazeCell> getNeighbors(AStarMazeCell cell) {

        List<AStarMazeCell> neighbors = new ArrayList<>();
        int y = cell.getY();
        int x = cell.getX();

        // top cell
        if (y > 0 && !(maze.get(x, y).hasTopWall())) {
            neighbors.add(new AStarMazeCell(maze.get(x, y - 1)));
        }
        // right cell
        if (x < width - 1 && !(maze.get(x + 1, y).hasLeftWall())) {
            neighbors.add(new AStarMazeCell(maze.get(x + 1, y)));
        }
        // bottom cell
        if (y < height - 1 && !maze.get(x, y + 1).hasTopWall()) {
            neighbors.add(new AStarMazeCell(maze.get(x, y + 1)));
        }
        // left cell
        if (x > 0 && !cell.hasLeftWall()) {
            neighbors.add(new AStarMazeCell(maze.get(x - 1, y)));
        }

        //for (AStarMazeCell _cell : neighbors) {
        //    System.out.println("target: " + cell.getMazeCell().toString());
        //    System.out.println("neighbors: " + _cell.getMazeCell().toString());
        //}

        return neighbors;
    }
}
