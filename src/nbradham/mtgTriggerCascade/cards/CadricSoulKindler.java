package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;

public final class CadricSoulKindler extends GameCard {

	private static final CardType[] TYPES = { CardType.Legendary, CardType.Creature, CardType.Wizzard, CardType.Dwarf };

	public CadricSoulKindler() {
		super("Cadric, Soul Kindler", TYPES);
	}
}