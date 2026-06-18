package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class ShardingSphinx extends GameCard {

	private static final CardType[] TYPES = { CardType.Creature, CardType.Artifact, CardType.Sphinx };

	public ShardingSphinx() {
		super("Sharding Sphinx", TYPES);
		abilities.add(KeywordAbility.Flying);
	}
}