package com.example.nanonodenexus;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.pathfinding.maze.Maze;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        //Queue<AStarMazeCell> openSet = new LinkedList<>();
        Comparator<AStarMazeCell> comparator = Comparator.comparingInt(c -> c.getFCost());
        PriorityQueue<AStarMazeCell> openSet = new PriorityQueue<>(comparator);
        Set<AStarMazeCell> closedSet = new HashSet<>();
        Map<AStarMazeCell, AStarMazeCell> cameFrom = new HashMap<>();

        AStarMazeCell start = new AStarMazeCell(maze.get(x1, y1));
        AStarMazeCell target = new AStarMazeCell(maze.get(x2, y2));

        start.setGCost(0);
        start.setHCost(heuristic(start, target));

        openSet.add(start);
        int maceCellWidth = FXGL.geti("maceCellWidth");

        while (!openSet.isEmpty() && openSet.size() < width * height * 100) { //
            AStarMazeCell current = openSet.poll();
            System.out.printf("openSet.size(): " + openSet.size());
            if (current.getX() == target.getX() && current.getY() == target.getY()) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            Rectangle rect = new Rectangle(3, 3, Color.GREEN);
            FXGL.entityBuilder()
                    .at((current.getX()+.5) * maceCellWidth, (current.getY()+.5) * maceCellWidth)
                    .view(rect)
                    .with(new EffectComponent())
                    .buildAndAttach();

            for (AStarMazeCell neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor)) continue;

                int tentativeGScore = current.getGCost() + 1; // Assuming each step costs 1
                boolean contains = false;
                for (AStarMazeCell cell : openSet) {
                    if (cell.getX() == neighbor.getX() && cell.getY() == neighbor.getY()) {
                        contains = true;
                        break;
                    }
                }

                if (!contains) { // || tentativeGScore < neighbor.getGCost()
                    // Update the neighbor's scores and parent
                    cameFrom.put(neighbor, current);
                    neighbor.setGCost(tentativeGScore);
                    if (!contains) {
                        openSet.add(neighbor);
                    } else {
                        // This block may be needed or not depending on how your priority queue handles updates
                        openSet.remove(neighbor);
                        openSet.add(neighbor);
                    }
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
        }
        System.out.printf("path length: " + path.size());
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

        return neighbors;
    }

    private int heuristic(AStarMazeCell cell, AStarMazeCell target) {
        return Math.abs(cell.getX() - target.getX()) + Math.abs(cell.getY() - target.getY());
    }
}
