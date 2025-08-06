
public class Cat implements DeepCopy<Cat> {
	
	private StringBuilder name;
	private final Class<Cat> type;

	public Cat (StringBuilder name) {
		this.type = Cat.class;
		this.name = new StringBuilder(name);
	}
	
	public Class<Cat> getType(){
		return this.type;
	}
	private final StringBuilder getName() {
		return this.name;
	}
	
	@Override
	public Cat deepCopy() {
		return new Cat(getName());
	}

}
