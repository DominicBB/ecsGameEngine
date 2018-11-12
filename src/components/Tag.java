package components;

import components.Component;

/**
 * Stores a long that acts as a series of tags. There can only ever be 64 tags as each bit represents a Tag
 */
public class Tag extends Component {

	private long tag = 0;

	public Tag(long tag) {

	}

	public Tag() {

	}

	/**
	 * Tag MUST BE a POWER OF TWO
	 * @param tag
	 */
	public void addTag(long tag) {
		this.tag += tag;
	}
	
	public long getTag() {
		return tag;
	}

}
