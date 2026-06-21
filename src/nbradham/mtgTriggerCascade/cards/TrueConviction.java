package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class TrueConviction extends GameCard {

	private static final CardType[] TYPES = { CardType.Enchantment }, COND_TYPES = { CardType.Creature };
	private static final KeywordAbility[] MODS = new KeywordAbility[] { KeywordAbility.Double_Strike,
			KeywordAbility.Lifelink };

	public TrueConviction() {
		super("True Conviction", TYPES);
	}

	@Override
	protected final void registerBattlefieldHandlers() {
		super.registerBattlefieldHandlers();
		Engine.registerBoardEffects(this, COND_TYPES, MODS);
	}

	@Override
	protected final void unregisterBattlefieldHandlers() {
		super.unregisterBattlefieldHandlers();
		Engine.unregisterBoardEffects(this);
	}
}