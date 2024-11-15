package mvc.model;

public class Global {

    // Perception-related constants
    public static class Perception_Constants {
        public static final byte UNKNOWN = -2;
        public static final byte CLEAN = -1;
        public static final byte MONSTER = 0;
        public static final byte HOLE = 1;
        public static final byte TREASURE = 2;
        public static final byte WALL = 3;
        public static final byte NUMBER_OF_PERCEPTIONS = 4;
        public static final byte PLAYER = 4;
    }

    // Maze-related constants
    public static class Maze_Constants {
        public static final byte MIN_SIDE = 4;
        public static final byte MAX_SIDE = 16;
        public static final byte MAX_MONSTERS = 1;
        public static final byte MAX_TREASURES = 1;
        public static final byte MAX_PLAYERS = 1;
    }

    // Event-related constants
    public static class Events_Constants {
        public static final String MAZE_SIDE_CHANGED = "MAZE_SIDE_CHANGED";
        public static final String SQUARE_CLICKED = "SQUARE_CLICKED";
        public static final String ELEMENT_CHANGED = "ELEMENT_CHANGED";
        public static final String SPEED_CHANGED = "SPEED_CHANGED";
        public static final String NEXT_STEP_CLICKED = "NEXT_STEP_CLICKED";
        public static final String START_CLICKED = "START_CLICKED";
    }

    // Image paths
    public static class Images_Constants {
        public static final String MONSTER = "resources/monster.png";
        public static final String HOLE = "resources/hole.png";
        public static final String TREASURE = "resources/treasure.png";
        public static final String CLEAN = "resources/clean.png";
        public static final String PLAYER = "resources/player.png";
        public static final String NEXT_STEP = "resources/next_step.png";
        public static final String START = "resources/start.png";
    }

    // Speed-related constants
    public static class Speed_Constants {
        public static final String SLOW = "SLOW_SPEED";
        public static final String NORMAL = "NORMAL_SPEED";
        public static final String FAST = "FAST_SPEED";
        public static final String MANUAL = "MANUAL_SPEED";

        public static final int SLOW_VALUE = 1_000;
        public static final int NORMAL_VALUE = 500;
        public static final int FAST_VALUE = 250;
        public static final int MANUAL_VALUE = -1;
    }

    // Utility method
    public static int getSquarePositionInMaze(byte row, byte column, byte sideSize) {
        return row * sideSize + column;
    }
}
