package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.GameCard;
import nbradham.mtgTriggerCascade.handlers.GainLifeHandler;
import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public final class NykthosParagon extends GameCard {

	private static final CardType[] TYPES = { CardType.Enchantment, CardType.Creature, CardType.Human,
			CardType.Soldier };
	private static final TurnStartHandler TURN_START_HANDLER = new TurnStartHandler(
			"(Nykthos Paragon ability enabler)") {
		@Override
		public final void onStart() {
			Engine.unregisterEventHandler(this);
			Engine.registerEventHandler(GAIN_LIFE_HANDLER);
		}
	};
	private static final GainLifeHandler GAIN_LIFE_HANDLER = new GainLifeHandler(
			"Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.") {
		@Override
		public final void onLifeGain(final byte gainedLife) {
			if (Engine.mayDoNykthosBuff(gainedLife)) {
				Engine.forEach(c -> {
					if (c.isType(CardType.Creature))
						c.addOneOnes(gainedLife);
				});
				Engine.unregisterEventHandler(this);
				Engine.registerEventHandler(TURN_START_HANDLER);
			}
		}
	};

	public NykthosParagon() {
		super("Nykthos Paragon", TYPES);
	}

	@Override
	protected final void onEnter() {
		super.onEnter();
		Engine.registerEventHandler(GAIN_LIFE_HANDLER);
	}
}