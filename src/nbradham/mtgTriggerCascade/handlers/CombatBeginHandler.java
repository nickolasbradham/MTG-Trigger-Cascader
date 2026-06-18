package nbradham.mtgTriggerCascade.handlers;

import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class CombatBeginHandler implements GameEventHandler {

	private final String string;

	protected CombatBeginHandler(String abilityString) {
		string = abilityString;
	}

	public abstract void beginCombat();

	@Override
	public final String toString() {
		return String.format("Combat Begin Trigger[%s]", string);
	}
}