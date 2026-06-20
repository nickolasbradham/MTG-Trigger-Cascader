package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

public abstract class GameCard {

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
	}

	public final void addOneOnes(byte toAdd) {
		oneOnes += toAdd;
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s, Abilities:%s}", name, types, abilities);
	}
}