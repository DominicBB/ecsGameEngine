package core.coreSystems;

import components.Tag;

import java.util.Arrays;

/**
 * Checks if a tag contains another tag within it
 */
public class TagSystem extends GameSystem {

	public TagSystem(boolean addToUpdateList) {
		super(Arrays.asList(Tag.class));
	}

	public static boolean tagContainsTag(long wantedTag, long entityTags) {
		return (wantedTag & entityTags) == wantedTag;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
