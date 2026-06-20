package nbradham.mtgTriggerCascade;

public final class TokenCopy extends GameCard {

	private final GameCard copy;

	private TokenCopy(GameCard copyOf, CardType additionalType) {
		this(copyOf);
		types.add(additionalType);
	}

	private TokenCopy(GameCard copyOf) {
		super("Token of " + copyOf.getName(), copyOf.types.toArray(new CardType[0]));
		copy = copyOf;
		types.add(CardType.Token);
	}

	
	//TODO: Copy types.
	public static final TokenCopy createTokenCopy(GameCard copyOf, CardType additionalType) {
		if (copyOf instanceof TokenCopy)
			return new TokenCopy(((TokenCopy) copyOf).copy, additionalType);
		else
			return new TokenCopy(copyOf, additionalType);
	}

	//TODO: Copy types.
	public static final TokenCopy createTokenCopy(GameCard copyOf) {
		if (copyOf instanceof TokenCopy)
			return new TokenCopy(((TokenCopy) copyOf).copy);
		else
			return new TokenCopy(copyOf);
	}

	@Override
	public final void onEnter() {
		super.onEnter();
		copy.onEnter();
	}
}