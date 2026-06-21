package nbradham.mtgTriggerCascade.handlers;

import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class PlayerCombatDamageHandler extends GameEventHandler {

	public PlayerCombatDamageHandler(final String text) {
		super(text);
	}

	public abstract void onDamageDealt(final CreatureCard src);

	@Override
	public final String toString() {
		return String.format("Player Combat Damage Trigger[%s]", string);
	}
}