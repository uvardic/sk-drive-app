package scene.manager;

import javafx.scene.Parent;

import java.util.Objects;

import static util.Preconditions.checkNotNull;

public class SceneComponents<T> {

    private final Parent parent;

    private final T controller;

    SceneComponents(final Parent parent, final T controller) {
        this.parent     = checkNotNull(parent);
        this.controller = checkNotNull(controller);
    }

    public Parent getParent() {
        return parent;
    }

    public T getController() {
        return controller;
    }

    @Override
    public String toString() {
        return String.format("SceneComponents{parent=%s, controller=%s}", parent, controller);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof SceneComponents) {
            final SceneComponents other = (SceneComponents) o;

            return Objects.equals(this.controller, other.controller);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(controller);
    }
}
