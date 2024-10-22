package mvc.model;

public class Global {

    // Perceptions global values
    public static final int MONSTER = 0;
    public static final int HOLE = 1;
    public static final int TREASURE = 2;
    public static final int WALL = 3;
    public static final int NUMBER_OF_PERCEPTIONS = 4;

    // Maze global values
    public static final int MIN_MAZE_SIDE = 4;
    public static final int MAX_MAZE_SIDE = 16;

    // Events
    public static final String MAZE_SIDE_CHANGED = "MAZE_SIDE_CHANGED";
    public static final String SQUARE_CLICKED = "SQUARE_CLICKED";
    public static final String ELEMENT_CHANGED = "ELEMENT_CHANGED";

    // Image paths
    public static final String MONSTER_IMAGE = "resources/monster.png";
    public static final String HOLE_IMAGE = "resources/hole.png";
    public static final String TREASURE_IMAGE = "resources/treasure.png";
    public static final String CLEAN_IMAGE = "resources/clean.png";
    public static final String PLAYER_IMAGE = "resources/player.png";
}
