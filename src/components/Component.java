package components;


import core.Entity;

/**
 * Extend this class to make a new component
 */
public abstract class Component extends BaseComponent {
    protected Entity entity;

    public void attachToEntity(Entity entity){
        this.entity = entity;
    }
}

abstract class BaseComponent {

}