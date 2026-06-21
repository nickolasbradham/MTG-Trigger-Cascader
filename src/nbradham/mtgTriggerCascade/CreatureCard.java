package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public abstract class CreatureCard extends GameCard {

	private final TurnStartHandler summonSickRemover;
	protected final HashSet<KeywordAbility> abilities = new HashSet<>();
	protected final int pow;
	private byte oneOnes = 0;

	protected CreatureCard(String cardName, CardType[] cardTypes, final int power) {
		super(cardName, cardTypes);
		pow = power;
		summonSickRemover = new TurnStartHandler("(" + name + " remove summoning sickness)") {

			@Override
			public final void onStart() {
				Engine.unregisterEventHandler(this);
				abilities.remove(KeywordAbility.Summoning_Sickness);
			}
		};
	}

	final void addKeywordAbilities(final KeywordAbility[] toAdd) {
		abilities.addAll(Arrays.asList(toAdd));
	}

	@Override
	protected void onEnter() {
		super.onEnter();
		if (types.contains(CardType.Creature)) {
			abilities.add(KeywordAbility.Summoning_Sickness);
			Engine.registerEventHandler(summonSickRemover);
		}
	}

	public final void addOneOnes(final byte toAdd) {
		oneOnes += toAdd;
	}

	final boolean hasAbility(final KeywordAbility ability) {
		return abilities.contains(ability);
	}

	final void attack() {
		final int damage=pow+oneOnes;
		Engine.dealOpponentCombatDamage(damage);
		if(abilities.contains(KeywordAbility.Lifelink))
			Engine.gainLife(damage);
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s, Abilities:%s}", name, types, abilities);
	}
}