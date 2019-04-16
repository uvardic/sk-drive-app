package scene.manager;

import javafx.scene.Parent;

import static util.Preconditions.checkNotNull;

public class ScenePair<T extends Parent, E> {

    private final T parent;

    private final E controller;

    public ScenePair(final T parent, final E controller) {
        this.parent     = checkNotNull(parent);
        this.controller = checkNotNull(controller);
    }

    public T getParent() {
        return parent;
    }

    public E getController() {
        return controller;
    }

    @Override
    public String toString() {
        return String.format("ScenePair{parent=%s, controller=%s}", parent, controller);
    }
}
