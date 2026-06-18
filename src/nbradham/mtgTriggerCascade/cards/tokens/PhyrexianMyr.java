package nbradham.mtgTriggerCascade.cards.tokens;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;

public final class PhyrexianMyr extends GameCard {

	private static final CardType[] TYPES = { CardType.Creature, CardType.Artifact, CardType.Token, CardType.Myr,
			CardType.Phyrexian };

	public PhyrexianMyr() {
		super("Phyrexian Myr", TYPES);
	}
}