package mvc.model;

public class Global {

    public static final SquareStatus SQUARE_STATUS_DEFAULT = SquareStatus.MONSTER;

    // Utility method
    public static int getSquarePositionInMaze(byte row, byte column, byte sideSize) {
        return row * sideSize + column;
    }

    public static PerceptionType squareStatusToPerceptionType(SquareStatus status) {
        return switch (status) {
            case MONSTER -> PerceptionType.MONSTER;
            case HOLE -> PerceptionType.HOLE;
            case TREASURE -> PerceptionType.TREASURE;
            case PLAYER, CLEAN, UNKNOWN -> null;
        };
    }

    // Perception-related constants
    public enum PerceptionType {
        MONSTER, HOLE, TREASURE, WALL, MOAN
    }

    // Status-related constants
    public enum SquareStatus {
        MONSTER, HOLE, TREASURE, PLAYER, CLEAN, UNKNOWN

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
        public static final String STATUS_CHANGED = "ELEMENT_CHANGED";
        public static final String SPEED_CHANGED = "SPEED_CHANGED";
        public static final String NEXT_STEP_CLICKED = "NEXT_STEP_CLICKED";
        public static final String START_CLICKED = "START_CLICKED";
        public static final String MAZE_UPDATED = "MAZE_UPDATED";
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
        public static final String DEFAULT = MONSTER;
    }

    // Speed-related constants
    public static class Speed_Constants {
        public static final String SLOW = "SLOW_SPEED";
        public static final String NORMAL = "NORMAL_SPEED";
        public static final String FAST = "FAST_SPEED";
        public static final String MANUAL = "MANUAL_SPEED";
        public static final String DEFAULT = FAST;

        public static final int SLOW_VALUE = 3_000;
        public static final int NORMAL_VALUE = 2_000;
        public static final int FAST_VALUE = 1_000;
        public static final int MANUAL_VALUE = -1;
        public static final int DEFAULT_VALUE = FAST_VALUE;
    }
}
