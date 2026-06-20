package nbradham.mtgTriggerCascade.cards.tokens;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class Thopter extends GameCard {

	private static final CardType[] TYPES = { CardType.Creature, CardType.Artifact, CardType.Token, CardType.Thopter };

	public Thopter() {
		super("Phyrexian Myr", TYPES);
		abilities.add(KeywordAbility.Flying);
	}
}