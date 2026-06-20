package nbradham.mtgTriggerCascade.handlers;

import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class CombatBeginHandler extends GameEventHandler {

	public CombatBeginHandler(final String text) {
		super(text);
	}

	public abstract void beginCombat();

	@Override
	public final String toString() {
		return String.format("Combat Begin Trigger[%s]", string);
	}
}