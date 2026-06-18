package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class TrueConviction extends GameCard {

	private static final CardType[] TYPES = { CardType.Enchantment };
	private static final KeywordAbility[] MODS = new KeywordAbility[] { KeywordAbility.DoubleStrike,
			KeywordAbility.Lifelink };

	public TrueConviction() {
		super("True Conviction", TYPES);
	}

	@Override
	protected final void onEnter() {
		Engine.registerBoardEffects(this, CardType.Creature, MODS);
	}
}