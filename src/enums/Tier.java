package enums;

public enum Tier {
	COMMON,
	UNCOMMON,
	RARE;
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.name().toLowerCase());
		buffer.replace(0, 1, buffer.substring(0, 1).toUpperCase());
		return buffer.toString();
	}
}
