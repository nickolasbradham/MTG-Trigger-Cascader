package nbradham.mtgTriggerCascade;

final class TokenCard extends GameCard {

	private final GameCard copy;
	
	TokenCard(GameCard copyOf, CardType additionalType) {
		super(copyOf.getName());
		copy=copyOf;
		copy.addType(additionalType);
	}
	
	@Override
	public final String toString() {
		return String.format("(Token) %s", copy);
	}
}