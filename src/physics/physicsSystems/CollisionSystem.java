package physics.physicsSystems;

import components.Component;

import java.util.List;

public class CollisionSystem extends PhysicsSystem {

    public CollisionSystem(List<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    public void update() {

    }
}
