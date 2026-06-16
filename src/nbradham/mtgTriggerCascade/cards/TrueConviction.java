package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.Modifier;

public final class TrueConviction extends GameCard {
	
	private static final Modifier[] MODS = new Modifier[]{Modifier.DoubleStrike, Modifier.Lifelink};
	
	public TrueConviction() {
		super();
		Engine.registerBoardEffects(this, CardType.Creature, MODS);
	}
}