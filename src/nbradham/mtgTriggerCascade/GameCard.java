package nbradham.mtgTriggerCascade;

public abstract class GameCard {

	private CardType[] types;
	
	boolean isType(final CardType type) {
		for (CardType t:types)
			if(t==type)
				return true;
		return false;
	}

	final void addModifiers(Modifier[] modifiers) {
		
	}
}