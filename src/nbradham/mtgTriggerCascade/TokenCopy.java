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

	public static final TokenCopy createTokenCopy(GameCard copyOf, CardType additionalType) {
		return copyExtras(copyOf, new TokenCopy(getBaseCard(copyOf), additionalType));
	}

	public static final TokenCopy createTokenCopy(GameCard copyOf) {
		return copyExtras(copyOf, new TokenCopy(getBaseCard(copyOf)));
	}

	@Override
	public final void onEnter() {
		super.onEnter();
		copy.onEnter();
	}

	private static final GameCard getBaseCard(final GameCard card) {
		return card instanceof TokenCopy ? ((TokenCopy) card).copy : card;
	}

	private static final TokenCopy copyExtras(final GameCard src, final TokenCopy dest) {
		dest.types.addAll(src.types);
		dest.abilities.addAll(src.abilities); // Yes I know that this will copy summoning sickness status. Too bad!
		return dest;
	}
}