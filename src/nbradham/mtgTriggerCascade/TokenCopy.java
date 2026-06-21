package nbradham.mtgTriggerCascade;

public final class TokenCopy extends CreatureCard {

	private final CreatureCard copy;

	private TokenCopy(CreatureCard copyOf, CardType additionalType) {
		this(copyOf);
		types.add(additionalType);
	}

	private TokenCopy(CreatureCard copyOf) {
		super("Token of " + copyOf.getName(), copyOf.types.toArray(new CardType[0]), copyOf.pow);
		copy = copyOf;
		types.add(CardType.Token);
	}

	public static final TokenCopy createTokenCopy(CreatureCard copyOf, CardType additionalType) {
		return copyExtras(copyOf, new TokenCopy(getBaseCard(copyOf), additionalType));
	}

	public static final TokenCopy createTokenCopy(CreatureCard copyOf) {
		return copyExtras(copyOf, new TokenCopy(getBaseCard(copyOf)));
	}

	@Override
	public final void onEnter() {
		super.onEnter();
		copy.onEnter();
	}

	private static final CreatureCard getBaseCard(final CreatureCard card) {
		return card instanceof TokenCopy ? ((TokenCopy) card).copy : card;
	}

	private static final TokenCopy copyExtras(final CreatureCard src, final TokenCopy dest) {
		dest.types.addAll(src.types);
		dest.abilities.addAll(src.abilities); // Yes I know that this will copy summoning sickness status. Too bad!
		return dest;
	}
}