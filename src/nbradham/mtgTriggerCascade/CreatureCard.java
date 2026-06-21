package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public abstract class CreatureCard extends GameCard {

	private final TurnStartHandler summonSickRemover;
	protected final HashSet<KeywordAbility> abilities = new HashSet<>();
	protected final int pow;
	public short oneOnes = 0;
	private boolean noSummoningSickness;

	protected CreatureCard(String cardName, CardType[] cardTypes, final int power) {
		super(cardName, cardTypes);
		pow = power;
		summonSickRemover = new TurnStartHandler("(" + name + " remove summoning sickness)") {

			@Override
			public final void onStart() {
				Engine.unregisterEventHandler(this);
				noSummoningSickness = true;
			}
		};
	}

	final void addKeywordAbilities(final KeywordAbility[] toAdd) {
		abilities.addAll(Arrays.asList(toAdd));
	}

	@Override
	protected void registerBattlefieldHandlers() {
		super.registerBattlefieldHandlers();
		if (types.contains(CardType.Creature)) {
			noSummoningSickness = false;
			Engine.registerEventHandler(summonSickRemover);
		}
	}

	@Override
	protected void unregisterBattlefieldHandlers() {
		super.unregisterBattlefieldHandlers();
		if (types.contains(CardType.Creature))
			Engine.unregisterEventHandler(summonSickRemover);
	}

	public final void addOneOnes(final int gainedLife) {
		oneOnes += gainedLife;
	}

	final boolean hasAbility(final KeywordAbility ability) {
		return abilities.contains(ability);
	}

	final void attack() {
		final int damage = pow + oneOnes;
		Engine.dealOpponentCombatDamage(this, damage);
		if (abilities.contains(KeywordAbility.Lifelink))
			Engine.gainLife(this, damage);
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s, Abilities:%s, +1/+1s:%d}", name, types, abilities, oneOnes);
	}

	boolean noSummoningSickness() {
		return noSummoningSickness;
	}
}