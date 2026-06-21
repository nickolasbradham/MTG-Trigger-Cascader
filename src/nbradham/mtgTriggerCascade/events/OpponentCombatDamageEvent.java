package nbradham.mtgTriggerCascade.events;

import nbradham.mtgTriggerCascade.CreatureCard;

public record OpponentCombatDamageEvent(CreatureCard src) implements GameEvent {

	@Override
	public byte getPriority() {
		return 1;
	}
}