package Physics.physicsSystems;

import components.Component;
import components.PhysicsComponents.RigidBody;
import components.TransformComponent;
import core.coreSystems.Time;
import util.Mathf.Mathf3D.Vector3D;

import java.util.Arrays;

public class SimulationSystem extends PhysicsSystem {

    public SimulationSystem() {
        super(Arrays.asList(RigidBody.class, TransformComponent.class));
    }

    @Override
    public void update() {
        for (int entityID : entityListner.getEntityIDsOfInterest()) {
            Component[] relevantComponents = entityListner.getRelevantComponents(entityID);
            RigidBody rigidBody = (RigidBody) relevantComponents[0];
            TransformComponent transformComponent = (TransformComponent) relevantComponents[1];

            updateRigidbody(rigidBody);
            moveEntity(rigidBody, transformComponent);
        }
    }

    private void updateRigidbody(RigidBody rigidBody) {
        updateAcceleration(rigidBody);
        rigidBody.velocity = rigidBody.velocity.plus(rigidBody.acceleration.mul(Time.getDeltaTime()));
    }

    private void updateAcceleration(RigidBody rigidBody) {
        rigidBody.currentForce = rigidBody.currentForce.plus((rigidBody.useGravity) ? rigidBody.gravityForce : Vector3D.newZeros());
        rigidBody.acceleration = rigidBody.currentForce.divide(rigidBody.weight);
    }

    private void moveEntity(RigidBody rigidBody, TransformComponent transform) {
//        Matrix4x4.newTranslation(rigidBody.velocity.mul(Time.getDeltaTime())).multiply4x4(transform.transform.position);
        transform.transform.position.add(rigidBody.velocity.mul(Time.getDeltaTime()));
    }


}
