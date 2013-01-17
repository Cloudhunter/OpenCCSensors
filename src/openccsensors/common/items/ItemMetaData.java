package openccsensors.common.items;

public class ItemMetaData {
	private int id;
	private String name;

	public ItemMetaData(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}
}
