package mvc.view;

import mvc.controller.Controller;
import mvc.model.cave.Cave;
import mvc.model.cave.Square;

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
 * A JPanel-based component for visualizing and interacting with a cave.
 *
 * <p>This class renders a cave based on a {@link Cave} and allows user interaction
 * by clicking on squares in the cave. Each square in the cave can represent different
 * statuses, such as Monster, Hole, Treasure, Player, or Clean, with corresponding images.
 *
 * <p>Usage:
 * <ul>
 *   <li>Create an instance by passing a {@link Controller}, the desired window side,
 *       and a {@link Cave}.</li>
 *   <li>Call {@link #updateCave()} to refresh the cave view after changes to the model.</li>
 * </ul>
 *
 * @author Sergio Vega García
 * @see Cave
 * @see Square
 * @see mvc.controller.Controller
 * @see javax.swing.JPanel
 * <p>
 */
public class CaveView extends JPanel {

    /**
     * The background color of each square in the cave.
     */
    private static final Color SQUARE_COLOR = Color.WHITE;

    /**
     * The border color of each square in the cave.
     */
    private static final Color BORDER_COLOR = Color.BLACK;

    /**
     * The controller to handle user interactions.
     */
    private final Controller controller;

    /**
     * The side of the window in pixels.
     */
    private final int windowSide;

    /**
     * A cache for storing loaded images corresponding to cave statuses.
     */
    private final Map<SquareStatus, BufferedImage> imageCache = new HashMap<>();

    /**
     * The model representing the cave.
     */
    private final Cave cave;

    /**
     * The side of a single square in the cave, dynamically calculated.
     */
    private int squareSide;

    /**
     * Constructs a {@code caveView} with the specified controller, window side, and cave model.
     *
     * <p>This constructor initializes the cave view, loads images for the cave statuses,
     * and sets up the panel for rendering and interaction.
     *
     * @param controller the {@link Controller} to handle interactions with the cave
     * @param windowSide the side of the window in pixels
     * @param cave       the {@link Cave} containing the cave's data
     */
    public CaveView(Controller controller, int windowSide, Cave cave) {
        this.controller = controller;
        this.windowSide = windowSide;
        this.cave = cave;

        initializeImages();
        configure();
        updateCave();
    }

    /**
     * Loads images for cave statuses into the cache.
     */
    private void initializeImages() {
        imageCache.put(SquareStatus.MONSTER, loadImage(Images_Constants.MONSTER));
        imageCache.put(SquareStatus.HOLE, loadImage(Images_Constants.HOLE));
        imageCache.put(SquareStatus.TREASURE, loadImage(Images_Constants.TREASURE));
        imageCache.put(SquareStatus.PLAYER, loadImage(Images_Constants.PLAYER));
    }

    /**
     * Configures the panel's layout and interaction settings.
     */
    private void configure() {
        setPreferredSize(new Dimension(windowSide, windowSide));
        addMouseListener(new CaveMouseListener());
    }

    /**
     * Updates the cave view by recalculating square side and repainting the panel.
     */
    public void updateCave() {
        squareSide = windowSide / cave.getCaveSide();
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
     * Paints the cave onto the panel.
     *
     * <p>This method iterates through the squares in the cave and renders each square
     * along with its corresponding status, if any.
     *
     * @param g the {@link Graphics} object used for rendering
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        byte caveSide = cave.getCaveSide();
        Square[] squares = cave.getSquares();

        for (byte row = 0; row < caveSide; row++) {
            for (byte column = 0; column < caveSide; column++) {
                int x = column * squareSide;
                int y = row * squareSide;
                Square square = squares[getSquarePositionInCave(row, column, caveSide)];

                drawSquare(g, x, y);
                drawStatus(g, square.getStatus(), x, y);
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
        g.fillRect(x, y, squareSide, squareSide);
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, squareSide, squareSide);
    }

    /**
     * Draws the status associated with a square.
     *
     * @param g      the {@link Graphics} object
     * @param status the status of the square
     * @param x      the x-coordinate of the square
     * @param y      the y-coordinate of the square
     */
    private void drawStatus(Graphics g, SquareStatus status, int x, int y) {
        if (status != SquareStatus.CLEAN) {
            BufferedImage image = imageCache.get(status);
            if (image != null) {
                g.drawImage(image, x, y, squareSide, squareSide, null);
            }
        }
    }

    /**
     * A mouse listener for handling clicks on the cave.
     */
    private class CaveMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            e.consume();
            byte row = (byte) (e.getY() / squareSide);
            byte column = (byte) (e.getX() / squareSide);
            controller.notify(Events_Constants.SQUARE_CLICKED, row, column);
        }
    }
}
