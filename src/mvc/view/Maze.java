package mvc.view;

import mvc.controller.Controller;

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

public class Maze extends JPanel {

    private static final Color SQUARE_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    private final Controller controller;
    private final int windowSize;
    private final Map<Integer, BufferedImage> imageCache = new HashMap<>();
    private byte mazeSide;
    private int squareSize;
    private byte[] squares;

    public Maze(Controller controller, int windowSize, byte mazeSide, byte[] squares) {
        this.controller = controller;
        this.windowSize = windowSize;

        setMaze(mazeSide, squares);
        initializeImages();
        configure();
    }

    public void setMaze(byte mazeSide, byte[] squares) {
        this.mazeSide = mazeSide;
        this.squares = squares;
        squareSize = windowSize / mazeSide;

        revalidate();
        repaint();
    }

    private void initializeImages() {
        imageCache.put((int) Perception_Constants.MONSTER, loadImage(Images_Constants.MONSTER));
        imageCache.put((int) Perception_Constants.HOLE, loadImage(Images_Constants.HOLE));
        imageCache.put((int) Perception_Constants.TREASURE, loadImage(Images_Constants.TREASURE));
        imageCache.put((int) Perception_Constants.PLAYER, loadImage(Images_Constants.PLAYER));
    }

    private void configure() {
        setPreferredSize(new Dimension(windowSize, windowSize));
        addMouseListener(new MazeMouseListener());
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
            return null;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (byte row = 0; row < mazeSide; row++) {
            for (byte column = 0; column < mazeSide; column++) {
                int x = column * squareSize;
                int y = row * squareSize;
                byte element = squares[getSquarePositionInMaze(row, column, mazeSide)];

                drawSquare(g, x, y);
                drawElement(g, element, x, y);
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y) {
        g.setColor(SQUARE_COLOR);
        g.fillRect(x, y, squareSize, squareSize);
        g.setColor(BORDER_COLOR);
        g.drawRect(x, y, squareSize, squareSize);
    }

    private void drawElement(Graphics g, byte element, int x, int y) {
        if (element != Perception_Constants.CLEAN) {
            BufferedImage image = imageCache.get((int) element);
            if (image != null) {
                g.drawImage(image, x, y, squareSize, squareSize, null);
            }
        }
    }

    public void placeElement(byte element, byte row, byte column) {
        squares[getSquarePositionInMaze(row, column, mazeSide)] = element;
        repaint(column * squareSize, row * squareSize, squareSize, squareSize); // Repaint the specific square
    }

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
