package mvc.model;

/**
 * The {@code Global} class serves as a container for shared constants, utility methods,
 * and enums used across the application. It centralizes the definitions for square statuses,
 * perception types, cave configuration, event constants, image paths, and speed settings.
 *
 * <p>This class provides a single point of reference for constants and utility methods,
 * enhancing code readability and maintainability.</p>
 *
 * @author Sergio Vega García
 */
public class Global {

    public enum Directions {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public static final byte[][] DIRECTIONS_DELTAS = new byte[][]{
            {-1, 0},    // NORTH
            {0, 1},     // EAST
            {1, 0},     // SOUTH
            {0, -1},    // WEST
    };

    /**
     * The default status for a {@code Square} in the cave.
     */
    public static final SquareStatus SQUARE_STATUS_DEFAULT = SquareStatus.MONSTER;

    /**
     * Maps a {@code SquareStatus} to its corresponding {@code PerceptionType}.
     *
     * @param status The status of the square.
     * @return The corresponding perception type, or {@code null} if no perception is associated.
     */
    public static PerceptionType mapStatusToPerception(SquareStatus status) {
        return switch (status) {
            case MONSTER -> PerceptionType.STENCH;
            case HOLE -> PerceptionType.BREEZE;
            case TREASURE -> PerceptionType.RADIANCE;
            case PLAYER, CLEAN, UNKNOWN -> null;
        };
    }

    /**
     * Maps a {@code PerceptionType} to its corresponding {@code SquareStatus}.
     *
     * @param perception The perception of the square.
     * @return The corresponding status type, or {@code null} if no status is associated.
     */
    public static SquareStatus mapPerceptionToStatus(PerceptionType perception) {
        return switch (perception) {
            case STENCH -> SquareStatus.MONSTER;
            case BREEZE -> SquareStatus.HOLE;
            case RADIANCE -> SquareStatus.TREASURE;
            case BANG, GROAN -> null;
        };
    }

    /**
     * Enum representing the types of perceptions in the cave.
     */
    public enum PerceptionType {
        STENCH, BREEZE, RADIANCE, BANG, GROAN
    }

    /**
     * Enum representing the possible statuses of a square in the cave.
     */
    public enum SquareStatus {
        MONSTER, HOLE, TREASURE, PLAYER, CLEAN, UNKNOWN
    }

    /**
     * Class containing constants related to cave configuration.
     */
    public static class Cave_Constants {
        /**
         * The minimum allowed length of the cave's side.
         */
        public static final byte MIN_SIDE = 4;

        /**
         * The maximum allowed length of the cave's side.
         */
        public static final byte MAX_SIDE = 16;

        /**
         * The maximum number of monsters allowed in the cave.
         */
        public static final byte MAX_MONSTERS = 1;

        /**
         * The maximum number of treasures allowed in the cave.
         */
        public static final byte MAX_TREASURES = 1;

        /**
         * The maximum number of players allowed in the cave.
         */
        public static final byte MAX_PLAYERS = 2;
    }

    /**
     * Class containing constants related to game events.
     */
    public static class Events_Constants {
        public static final String CAVE_SIDE_CHANGED = "CAVE_SIDE_CHANGED";
        public static final String SQUARE_CLICKED = "SQUARE_CLICKED";
        public static final String STATUS_CHANGED = "STATUS_CHANGED";
        public static final String SPEED_CHANGED = "SPEED_CHANGED";
        public static final String NEXT_STEP_CLICKED = "NEXT_STEP_CLICKED";
        public static final String START_CLICKED = "START_CLICKED";
        public static final String CAVE_UPDATED = "CAVE_UPDATED";
    }

    /**
     * Class containing constants for image file paths.
     */
    public static class Images_Constants {
        public static final String MONSTER = "resources/monster.png";
        public static final String HOLE = "resources/hole.png";
        public static final String TREASURE = "resources/treasure.png";
        public static final String CLEAN = "resources/clean.png";
        public static final String PLAYER = "resources/player.png";
        public static final String NEXT_STEP = "resources/next_step.png";
        public static final String START = "resources/start.png";

        /**
         * Default image path.
         */
        public static final String DEFAULT = MONSTER;
    }

    /**
     * Class containing constants for game speed settings.
     */
    public static class Speed_Constants {
        public static final String SLOW = "SLOW_SPEED";
        public static final String NORMAL = "NORMAL_SPEED";
        public static final String FAST = "FAST_SPEED";
        public static final String MANUAL = "MANUAL_SPEED";

        /**
         * Default speed setting.
         */
        public static final String DEFAULT = FAST;

        /**
         * The delay (in milliseconds) for slow speed.
         */
        public static final int SLOW_VALUE = 3_000;

        /**
         * The delay (in milliseconds) for normal speed.
         */
        public static final int NORMAL_VALUE = 2_000;

        /**
         * The delay (in milliseconds) for fast speed.
         */
        public static final int FAST_VALUE = 1_000;

        /**
         * Special value indicating manual control.
         */
        public static final int MANUAL_VALUE = -1;

        /**
         * Default delay value for the game speed.
         */
        public static final int DEFAULT_VALUE = FAST_VALUE;
    }
}
