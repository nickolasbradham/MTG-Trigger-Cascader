package nbradham.mtgTriggerCascade;

public abstract class GameEventHandler {
	
	protected final String string;

	protected GameEventHandler() {
		this("");
	}
	
	protected GameEventHandler(String abilityString) {
		string = abilityString;
	}

	@Override
	public abstract String toString();
}