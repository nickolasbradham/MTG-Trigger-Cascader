package nbradham.mtgTriggerCascade;

public final class TokenCopy extends GameCard {

	private final GameCard copy;

	TokenCopy(GameCard copyOf, CardType additionalType) {
		this(copyOf);
		types.add(additionalType);
	}

	public TokenCopy(GameCard copyOf) {
		super("Token of " + copyOf.getName(), copyOf.types.toArray(new CardType[0]));
		copy = copyOf;
		types.add(CardType.Token);
	}
}