package standards.handler;

import standards.main.StandardCamera;
import standards.model.StandardGameObject;
import standards.model.StandardID;
import standards.view.Renderable;
import standards.view.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import java.util.ArrayList;

/**
 * The {@code StandardHandler} class is responsible for managing all active
 * {@link StandardGameObject} instances in the game world. It handles object
 * updates (via {@code tick()}) and rendering (via {@code render()}), providing
 * centralized control over all entities.
 * <p>
 * This class serves as the "Model" container in the MVC architecture, while
 * delegating visual rendering to the "View" layer.
 * <p>
 * The handler can be linked to a {@link StandardCamera} to control
 * view-based rendering, only drawing entities within the visible viewport.
 *
 * Modified from Joshua Crotts & Andrew Matzureff's Standards Java Game Library Source Code
 */
public class StandardHandler implements Renderable, Updatable {

    /**
     * List of all {@link StandardGameObject} entities currently active in the game.
     */
    private ArrayList<StandardGameObject> entities;

    /**
     * Optional {@link StandardCamera} controlling viewport translation and
     * visibility culling.
     */
    private StandardCamera stdCamera;

    /**
     * Constructs a {@code StandardHandler} linked to a specific camera.
     *
     * @param stdCamera the camera used for viewport rendering and culling.
     */
    public StandardHandler(StandardCamera stdCamera) {
        this.entities = new ArrayList<>();
        this.stdCamera = stdCamera;
    }

    /**
     * Constructs a {@code StandardHandler} without a camera.
     * Entities will render with absolute coordinates.
     */
    public StandardHandler() {
        this.entities = new ArrayList<>();
    }

    /**
     * Updates a single {@link StandardGameObject} by calling its {@code tick()} method.
     *
     * @param obj the game object to update.
     */
    public static void Object(StandardGameObject obj) {
        obj.tick();
    }

    /**
     * Updates all objects managed by a given {@code StandardHandler}.
     *
     * @param handler the handler whose objects should be updated.
     */
    public static void Handler(StandardHandler handler) {
        handler.tick();
    }

    /**
     * Adds multiple instances of a given object to a {@code StandardHandler}.
     *
     * @param obj        the object to add.
     * @param stdHandler the handler to which objects are added.
     * @param n          number of instances to add.
     */
    public static void Add(StandardGameObject obj, StandardHandler stdHandler, int n) {
        for (int i = 0; i < n; i++) {
            stdHandler.addEntity(obj);
        }
    }

    /**
     * Updates all entities managed by this handler.
     * Each entity’s {@code tick()} method is invoked once per update cycle.
     */
    @Override
    public void tick() {
        for (StandardGameObject entity : this.entities) {
            entity.tick();
        }
    }

    /**
     * Renders all visible entities using the provided {@link GraphicsContext}.
     *
     * @param gc the JavaFX graphics context for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        double vpo = 300;
        Rectangle2D camBounds = null;

        if (this.stdCamera != null) {
            camBounds = new Rectangle2D(
                    this.stdCamera.getX() - vpo - this.stdCamera.getVpw(),
                    this.stdCamera.getY() - this.stdCamera.getVph(),
                    this.stdCamera.getVpw() * 2 + vpo * 2,
                    this.stdCamera.getVph() * 2
            );
        }

        for (StandardGameObject entity : this.entities) {
            if (camBounds == null || entity.getBounds().intersects(
                    camBounds.getMinX(), camBounds.getMinY(), camBounds.getWidth(), camBounds.getHeight())) {
                entity.render(gc);
            }
        }
    }

    /**
     * Performs a render operation without camera translation or culling.
     *
     * @param gc the JavaFX graphics context for rendering.
     */
    public void stdRender(GraphicsContext gc) {
        this.entities.forEach(entity -> entity.render(gc));
    }

    /**
     * Replaces the current list of entities with a new list.
     *
     * @param entities the new list of {@link StandardGameObject} instances.
     */
    public void setEntities(ArrayList<StandardGameObject> entities) {
        this.entities = entities;
    }

    /**
     * Adds a new entity to the handler.
     *
     * @param obj the entity to add.
     */
    public void addEntity(StandardGameObject obj) {
        this.entities.add(obj);
    }

    /**
     * Removes an entity from the handler.
     *
     * @param obj the entity to remove.
     */
    public void removeEntity(StandardGameObject obj) {
        this.entities.remove(obj);
    }

    /**
     * Clears all entities except the {@code Player} from the handler.
     */
    public void clearEntities() {
        this.entities.removeIf(e -> e.getId() != StandardID.Player);
    }

    /**
     * Removes all entities from the handler.
     */
    public void clearAllEntities() {
        this.entities.clear();
    }

    /**
     * Sorts entities based on their type (Player, Enemy, etc.) to determine
     * render/update priority.
     */
    public void sort() {
        for (int i = 0; i < this.entities.size(); i++) {
            StandardGameObject e = this.entities.get(i);

            if (e.getId() == StandardID.Player) {
                this.entities.remove(i);
                this.entities.add(0, e);
            } else if (e.getId() == StandardID.Enemy) {
                this.entities.remove(i);
                this.entities.add(1, e);
            }
        }
    }

    /**
     * Checks whether the specified object can collide with others.
     *
     * @param obj2 the object to check.
     * @return {@code true} if the object participates in collisions; otherwise {@code false}.
     */
    private boolean validCollision(StandardGameObject obj2) {
        return ((obj2.getId() == StandardID.Block || obj2.getId() == StandardID.Brick
                || obj2.getId() == StandardID.Obstacle || obj2.getId() == StandardID.NPC
                || obj2.getId() == StandardID.Powerup)
                && obj2.getId() != StandardID.Player && obj2.getId() != StandardID.Enemy);
    }

    /**
     * Checks for collisions between entities in the handler.
     */
    public void checkCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            StandardGameObject obj1 = entities.get(i);

            // Chỉ xử lý cho Ball
            if (obj1.getId() == StandardID.Enemy) {
                for (int j = 0; j < entities.size(); j++) {
                    StandardGameObject obj2 = entities.get(j);
                    if (i == j) continue;

                    // Ball chạm Paddle
                    if (obj2.getId() == StandardID.Player &&
                            obj1.getBounds().intersects(obj2.getBounds())) {
                        obj1.setVelY(-Math.abs(obj1.getVelY())); // nảy lên
                    }

                    // Ball chạm Brick
                    if (obj2.getId() == StandardID.Brick &&
                            obj1.getBounds().intersects(obj2.getBounds())) {
                        obj1.setVelY(-obj1.getVelY()); // nảy lại
                        removeEntity(obj2); // xóa brick
                        break;
                    }
                }
            }
        }
    }


    /**
     * Returns the number of entities currently managed by this handler.
     *
     * @return the entity count.
     */
    public int size() {
        return this.entities.size();
    }

    /**
     * Retrieves an entity at the specified index.
     *
     * @param i the index.
     * @return the {@link StandardGameObject} at the given position.
     */
    public StandardGameObject get(int i) {
        return this.entities.get(i);
    }

    /**
     * Returns the list of all entities managed by this handler.
     *
     * @return an {@link ArrayList} of {@link StandardGameObject}.
     */
    public ArrayList<StandardGameObject> getEntities() {
        return this.entities;
    }

    /**
     * Assigns a new camera to this handler.
     *
     * @param cam the new {@link StandardCamera}.
     */
    public void setCamera(StandardCamera cam) {
        this.stdCamera = cam;
    }

    /**
     * Returns the camera currently assigned to this handler.
     *
     * @return the {@link StandardCamera} in use, or {@code null} if none.
     */
    public StandardCamera getCamera() {
        return this.stdCamera;
    }
}
