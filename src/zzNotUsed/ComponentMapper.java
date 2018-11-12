package zzNotUsed;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import components.Component;
import util.EntityComponentBag;

public class ComponentMapper {

    private EntityComponentBag<Component> entityComponentBag = new EntityComponentBag<>(Component.class);
    private Queue<Integer> freeIndexs = new ArrayDeque<>();


    public List<Component> getComponents(int[] ids) {
        return entityComponentBag.safeGetAll(ids);
    }

    public Component getComponent(int id) {
        return entityComponentBag.safeGet(id);
    }

    public void addComponents(Collection<Component> components) {
        entityComponentBag.addAll(components);
    }

    public void addComponent(Component component) {
        entityComponentBag.addComponent(component);
    }

    public void removeComponent(int index) {
        entityComponentBag.removeComponent(index);
    }

    public void removeAllComponents(int[] indexs) {
        entityComponentBag.removeComponents(indexs);
    }
}
