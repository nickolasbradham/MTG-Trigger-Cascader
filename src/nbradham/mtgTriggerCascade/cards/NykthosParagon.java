package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.handlers.GainLifeHandler;
import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public final class NykthosParagon extends CreatureCard {

	private static final CardType[] TYPES = { CardType.Enchantment, CardType.Creature, CardType.Human,
			CardType.Soldier };
	private final TurnStartHandler TURN_START_HANDLER = new TurnStartHandler("(Nykthos Paragon ability enabler)") {
		@Override
		public final void onStart() {
			canBuff = true;
			Engine.unregisterEventHandler(this);
			Engine.registerEventHandler(GAIN_LIFE_HANDLER);
		}
	};

	private boolean canBuff = true;
	private final GainLifeHandler GAIN_LIFE_HANDLER = new GainLifeHandler(
			"Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.") {
		@Override
		public final void onLifeGain(final int gainedLife) {
			if (canBuff && Engine.mayDoNykthosBuff(gainedLife)) {
				canBuff = false;
				Engine.forEach(c -> {
					if (c.isType(CardType.Creature))
						((CreatureCard) c).addOneOnes(gainedLife);
				});
				Engine.unregisterEventHandler(this);
				Engine.registerEventHandler(TURN_START_HANDLER);
			}
		}
	};

	public NykthosParagon() {
		super("Nykthos Paragon", TYPES, 4);
	}

	@Override
	protected final void onEnter() {
		super.onEnter();
		Engine.registerEventHandler(GAIN_LIFE_HANDLER);
	}
}