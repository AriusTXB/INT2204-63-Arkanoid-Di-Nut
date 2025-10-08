package standards.model;

/**
 * The {@code StandardID} enumeration defines a comprehensive list of unique
 * identifiers used throughout the Standards Game Library to categorize and
 * manage game entities and objects.
 * <p>
 * Each constant represents a specific type of in-game object.
 * These IDs are primarily used by the {@code StandardHandler} and
 * {@code StandardGameObject} classes to determine behavior, rendering
 * priority, and interactions between objects.
 * <p>
 * This approach promotes clean object differentiation and supports the
 * Model-View-Controller (MVC) design philosophy by separating logical object
 * identity from its actual implementation details.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public enum StandardID {
    /** Represents the player-controlled entity. */
    Player,

    /** Represents visual particle effects. */
    Particle,

    /** Represents trailing effects or motion trails. */
    Trail,

    /** Represents enemy entities. */
    Enemy,

    /** Represents static wall boundaries or barriers. */
    Wall,

    /** Represents immovable obstacles. */
    Obstacle,

    /** Represents the base class for all game objects. */
    StandardGameObject,

    /** Represents a standard GUI button. */
    StandardButton,

    /** Represents any interactor (e.g., triggers, sensors). */
    Interactor,

    /** Represents generic entities or dynamic objects. */
    Entity,

    /** Represents any weapon-type object. */
    Weapon,

    /** Represents non-player characters (NPCs). */
    NPC,

    /** Represents a large or heavy object variant. */
    Large,

    /** Represents multiple-object groupings. */
    Multi,

    /** Represents collectible power-ups. */
    Powerup,

    /** Represents solid blocks or tiles. */
    Block,

    /** Represents destructible bricks (e.g., Breakout-style games). */
    Brick,

    /** Represents a null or undefined object type. */
    NULL,

    /** Represents pipes or conduits (e.g., Mario-style). */
    Pipe,

    /** Represents the camera object. */
    Camera,

    /** Represents a skeleton-type enemy. */
    Skeleton,

    /** Represents a zombie-type enemy. */
    Zombie,

    /** Represents a boss enemy. */
    Boss,

    /** Represents the final boss entity. */
    FinalBoss,

    /** Represents generic creatures or organic entities. */
    Creature,

    /** Represents a first-tier enemy type. */
    Enemy1,

    /** Represents a second-tier enemy type. */
    Enemy2,

    /** Represents a third-tier enemy type. */
    Enemy3,

    /** Represents a fourth-tier enemy type. */
    Enemy4,

    /** Represents a fifth-tier enemy type. */
    Enemy5,

    /** Represents a sixth-tier enemy type. */
    Enemy6,

    /** Represents a seventh-tier enemy type. */
    Enemy7,

    /** Represents an eighth-tier enemy type. */
    Enemy8,

    /** Represents a ninth-tier enemy type. */
    Enemy9,

    /** Represents a tenth-tier enemy type. */
    Enemy10,

    /** Represents any generic object. */
    Object,

    /** Represents projectiles (e.g., bullets, missiles). */
    Projectile,

    /** Represents objects that should be ignored in collisions or updates. */
    Ignore;
}
