
public class Dog implements DeepCopy<Dog> {
	
	private StringBuilder name;
	private final Class<Dog> type;

	public Dog (StringBuilder name) {
		this.type = Dog.class;
		this.name = new StringBuilder(name);
	}
	
	public Class<Dog> getType(){
		return this.type;
	}
	private final StringBuilder getName() {
		return this.name;
	}
	
	@Override
	public Dog deepCopy() {
		return new Dog(getName());
	}

}
