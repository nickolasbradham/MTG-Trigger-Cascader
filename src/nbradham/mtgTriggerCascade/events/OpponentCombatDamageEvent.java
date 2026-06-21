package nbradham.mtgTriggerCascade.events;

import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.GameEvent;

public record OpponentCombatDamageEvent(CreatureCard src) implements GameEvent {

	@Override
	public byte getPriority() {
		return 1;
	}
}