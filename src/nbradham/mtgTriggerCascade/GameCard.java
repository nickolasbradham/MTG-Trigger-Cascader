package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public abstract class GameCard {

	private final String name;

	private final TurnStartHandler summonSickRemover, untapper;

	protected final HashSet<CardType> types = new HashSet<>();
	protected final HashSet<KeywordAbility> abilities = new HashSet<>();
	private byte oneOnes = 0;

	private boolean untapped = true;

	protected GameCard(final String cardName, final CardType[] cardTypes) {
		name = cardName;
		types.addAll(Arrays.asList(cardTypes));
		summonSickRemover = new TurnStartHandler("(" + name + " remove summoning sickness)") {

			@Override
			public final void onStart() {
				Engine.unregisterEventHandler(this);
				abilities.remove(KeywordAbility.Summoning_Sickness);
			}
		};
		untapper = new TurnStartHandler("(" + name + " untap)") {
			@Override
			public final void onStart() {
				Engine.unregisterEventHandler(this);
				untapped = true;
			}
		};
	}

	final String getName() {
		return name;
	}

	public boolean isType(final CardType type) {
		return types.contains(type);
	}

	final void addKeywordAbilities(final KeywordAbility[] toAdd) {
		abilities.addAll(Arrays.asList(toAdd));
	}

	protected void onEnter() {
		if (types.contains(CardType.Creature)) {
			abilities.add(KeywordAbility.Summoning_Sickness);
			Engine.registerEventHandler(summonSickRemover);
		}
	}

	public final void addOneOnes(final byte toAdd) {
		oneOnes += toAdd;
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s, Abilities:%s}", name, types, abilities);
	}

	final boolean isUntapped() {
		return untapped;
	}

	final boolean hasAbility(final KeywordAbility ability) {
		return abilities.contains(ability);
	}

	final void tap() {
		untapped = false;
		Engine.registerEventHandler(untapper);
	}
}