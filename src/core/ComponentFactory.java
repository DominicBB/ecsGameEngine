package core;

import components.Component;

/**
 * Create components at runtime
 */
public class ComponentFactory {
	public <T extends Component> Component create(Class<T> type) {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
