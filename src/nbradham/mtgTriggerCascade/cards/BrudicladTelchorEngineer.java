package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;

public final class BrudicladTelchorEngineer extends GameCard {

	private static final CardType[] TYPES = { CardType.Legendary, CardType.Artifact, CardType.Creature,
			CardType.Artificer, CardType.Phyrexian };

	public BrudicladTelchorEngineer() {
		super("Brudiclad, Telchor Engineer", TYPES);
	}
}