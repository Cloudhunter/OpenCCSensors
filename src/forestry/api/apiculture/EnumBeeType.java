package forestry.api.apiculture;

public enum EnumBeeType {
	PRINCESS("bees.princess"), QUEEN("bees.queen"), DRONE("bees.drone");

	String name;

	private EnumBeeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
