package nbradham.mtgTriggerCascade;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameCard {

	private final String name;
	protected final ArrayList<CardType> types = new ArrayList<>();
	private final ArrayList<KeywordAbility> abilities = new ArrayList<>();
	
	protected GameCard(final String cardName) {
		name=cardName;
	}
	
	final String getName() {
		return name;
	}
	
	boolean isType(final CardType type) {
		return types.contains(type);
	}
	
	protected final void addType(final CardType additionalType) {
		types.add(additionalType);
	}

	final void addKeywordAbilities(final KeywordAbility[] toAdd) {
		abilities.addAll(Arrays.asList(toAdd));
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s, %s]", getName(), types, abilities);
	}
}