package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.KeywordAbility;

public final class TrueConviction extends GameCard {
	
	private static final KeywordAbility[] MODS = new KeywordAbility[]{KeywordAbility.DoubleStrike, KeywordAbility.Lifelink};
	
	public TrueConviction() {
		super("True Conviction");
		Engine.registerBoardEffects(this, CardType.Creature, MODS);
	}
}