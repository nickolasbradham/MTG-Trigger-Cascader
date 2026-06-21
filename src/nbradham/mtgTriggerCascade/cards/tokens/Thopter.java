package nbradham.mtgTriggerCascade.cards.tokens;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class Thopter extends CreatureCard {

	private static final CardType[] TYPES = { CardType.Creature, CardType.Artifact, CardType.Token, CardType.Thopter };

	public Thopter() {
		super("Thopter", TYPES, 1);
		abilities.add(KeywordAbility.Flying);
	}
}