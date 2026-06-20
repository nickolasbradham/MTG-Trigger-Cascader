package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public abstract class GameCard {

	private final TurnStartHandler SUMMON_SICK_REMOVER = new TurnStartHandler() {

		@Override
		public void onStart() {
			abilities.remove(KeywordAbility.Summoning_Sickness);
		}
	};

	private final String name;
	protected final HashSet<CardType> types = new HashSet<>();
	protected final HashSet<KeywordAbility> abilities = new HashSet<>();
	private byte oneOnes = 0;

	protected GameCard(final String cardName, final CardType[] cardTypes) {
		name = cardName;
		types.addAll(Arrays.asList(cardTypes));
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
			Engine.registerEventHandler(SUMMON_SICK_REMOVER);
		}
	}

	public final void addOneOnes(byte toAdd) {
		oneOnes += toAdd;
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s, Abilities:%s}", name, types, abilities);
	}
}