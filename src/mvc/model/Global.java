package mvc.model;

public class Global {

    // Perceptions global values
    public static final byte CLEAN = -1;
    public static final byte MONSTER = 0;
    public static final byte HOLE = 1;
    public static final byte TREASURE = 2;
    public static final byte WALL = 3;
    public static final byte NUMBER_OF_PERCEPTIONS = 4;

    // Maze global values
    public static final byte MIN_MAZE_SIDE = 4;
    public static final byte MAX_MAZE_SIDE = 16;
    public static final byte MAX_MONSTERS = 1;
    public static final byte MAX_TREASURES = 1;

    // Events
    public static final String MAZE_SIDE_CHANGED = "MAZE_SIDE_CHANGED";
    public static final String SQUARE_CLICKED = "SQUARE_CLICKED";
    public static final String ELEMENT_CHANGED = "ELEMENT_CHANGED";
    public static final String SPEED_CHANGED = "SPEED_CHANGED";
    public static final String NEXT_STEP_CLICKED = "NEXT_STEP_CLICKED";

    // Image paths
    public static final String MONSTER_IMAGE = "resources/monster.png";
    public static final String HOLE_IMAGE = "resources/hole.png";
    public static final String TREASURE_IMAGE = "resources/treasure.png";
    public static final String CLEAN_IMAGE = "resources/clean.png";
    public static final String PLAYER_IMAGE = "resources/player.png";
    public static final String NEXT_STEP_IMAGE = "resources/next_step.png";

    // Execution speeds labels
    public static final String SLOW_SPEED = "SLOW_SPEED";
    public static final String NORMAL_SPEED = "NORMAL_SPEED";
    public static final String FAST_SPEED = "FAST_SPEED";
    public static final String MANUAL_SPEED = "MANUAL_SPEED";

    // Execution speeds values
    public static final int SLOW_SPEED_VALUE = 1_000;
    public static final int NORMAL_SPEED_VALUE = 500;
    public static final int FAST_SPEED_VALUE = 250;
    public static final int MANUAL_SPEED_VALUE = -1;
}
