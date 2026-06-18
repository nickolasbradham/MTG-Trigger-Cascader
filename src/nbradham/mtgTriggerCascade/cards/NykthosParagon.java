package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;

public final class NykthosParagon extends GameCard {

	private static final CardType[] TYPES = { CardType.Enchantment, CardType.Creature, CardType.Human,
			CardType.Soldier };

	public NykthosParagon() {
		super("Nykthos Paragon", TYPES);
	}
}