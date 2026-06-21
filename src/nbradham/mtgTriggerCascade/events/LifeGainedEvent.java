package nbradham.mtgTriggerCascade.events;

import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.GameEvent;

public record LifeGainedEvent(GameCard src, int amount) implements GameEvent {

	@Override
	public byte getPriority() {
		return 0;
	}
}