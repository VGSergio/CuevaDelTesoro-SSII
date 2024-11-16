package mvc.view;

import mvc.controller.Controller;
import mvc.model.maze.MazeModel;
import mvc.model.maze.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static mvc.model.Global.*;

/**
 * A JPanel-based component for visualizing and interacting with a maze.
 *
 * <p>This class renders a maze based on a {@link MazeModel} and allows user interaction
 * by clicking on squares in the maze. Each square in the maze can represent different
 * statuses, such as Monster, Hole, Treasure, Player, or Clean, with corresponding images.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller}, the desired window size,
 *       and a {@link MazeModel}.</li>
 *   <li>Call {@link #updateMaze()} to refresh the maze view after changes to the model.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see MazeModel
 * @see Square
 * @see mvc.controller.Controller
 * @see javax.swing.JPanel
 * <p>
 */
public class MazeView extends JPanel {

    /**
     * The background color of each square in the maze.
     */
    private static final Color SQUARE_COLOR = Color.WHITE;

    /**
     * The border color of each square in the maze.
     */
    private static final Color BORDER_COLOR = Color.BLACK;

    /**
     * The controller to handle user interactions.
     */
    private final Controller controller;

    /**
     * The size of the window in pixels.
     */
    private final int windowSize;

    /**
     * A cache for storing loaded images corresponding to maze elements.
     */
    private final Map<Integer, BufferedImage> imageCache = new HashMap<>();

    /**
     * The model representing the maze.
     */
    private final MazeModel mazeModel;

    /**
     * The size of a single square in the maze, dynamically calculated.
     */
    private int squareSize;

    /**
     * Constructs a {@code MazeView} with the specified controller, window size, and maze model.
     *
     * <p>This constructor initializes the maze view, loads images for the maze elements,
     * and sets up the panel for rendering and interaction.
     *
     * @param controller the {@link Controller} to handle interactions with the maze
     * @param windowSize the size of the window in pixels
     * @param mazeModel  the {@link MazeModel} containing the maze's data
     */
    public MazeView(Controller controller, int windowSize, MazeModel mazeModel) {
        this.controller = controller;
        this.windowSize = windowSize;
        this.mazeModel = mazeModel;

        initializeImages();
        configure();
        updateMaze();
    }

    /**
     * Loads images for maze elements into the cache.
     */
    private void initializeImages() {
        imageCache.put((int) Status_Constants.MONSTER, loadImage(Images_Constants.MONSTER));
        imageCache.put((int) Status_Constants.HOLE, loadImage(Images_Constants.HOLE));
        imageCache.put((int) Status_Constants.TREASURE, loadImage(Images_Constants.TREASURE));
        imageCache.put((int) Status_Constants.PLAYER, loadImage(Images_Constants.PLAYER));
    }

    /**
     * Configures the panel's layout and interaction settings.
     */
    private void configure() {
        setPreferredSize(new Dimension(windowSize, windowSize));
        addMouseListener(new MazeMouseListener());
    }

    /**
     * Updates the maze view by recalculating square sizes and repainting the panel.
     */
    public void updateMaze() {
        squareSize = windowSize / mazeModel.getMazeSide();
        repaint();
    }

    /**
     * Loads an image from the given file path.
     *
     * @param path the path to the image file
     * @return the loaded {@link BufferedImage}, or {@code null} if loading fails
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    /**
     * Paints the maze onto the panel.
     *
     * <p>This method iterates through the squares in the maze and renders each square
     * along with its corresponding element, if any.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        byte mazeSide = mazeModel.getMazeSide();
        Square[] squares = mazeModel.getSquares();

        for (byte row = 0; row < mazeSide; row++) {
            for (byte column = 0; column < mazeSide; column++) {
                int x = column * squareSize;
                int y = row * squareSize;
                Square square = squares[getSquarePositionInMaze(row, column, mazeSide)];

                drawSquare(g, x, y);
                drawElement(g, square.getStatus(), x, y);
            }
        }
    }

    /**
     * Draws a square at the specified position.
     *
     * @param g the {@link Graphics} object
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */
    private void drawSquare(Graphics g, int x, int y) {
        g.setColor(SQUARE_COLOR);
        g.fillRect(x, y, squareSize, squareSize);
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, squareSize, squareSize);
    }

    /**
     * Draws the element associated with a square.
     *
     * @param g       the {@link Graphics} object
     * @param element the status of the square
     * @param x       the x-coordinate of the square
     * @param y       the y-coordinate of the square
     */
    private void drawElement(Graphics g, byte element, int x, int y) {
        if (element != Status_Constants.CLEAN) {
            BufferedImage image = imageCache.get((int) element);
            if (image != null) {
                g.drawImage(image, x, y, squareSize, squareSize, null);
            }
        }
    }

    /**
     * A mouse listener for handling clicks on the maze.
     */
    private class MazeMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            e.consume();
            byte row = (byte) (e.getY() / squareSize);
            byte column = (byte) (e.getX() / squareSize);
            controller.notify(Events_Constants.SQUARE_CLICKED, row, column);
        }
    }
}
