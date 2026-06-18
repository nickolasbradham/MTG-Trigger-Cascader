package nbradham.mtgTriggerCascade;

final class TokenCard extends GameCard {

	private final GameCard copy;

	TokenCard(GameCard copyOf, CardType additionalType) {
		super("Token of " + copyOf.getName(), copyOf.types.toArray(new CardType[0]));
		copy = copyOf;
		types.add(additionalType);
	}
}