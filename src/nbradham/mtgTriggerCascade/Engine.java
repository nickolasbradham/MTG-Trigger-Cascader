package nbradham.mtgTriggerCascade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import nbradham.mtgTriggerCascade.cards.BrudicladTelchorEngineer;
import nbradham.mtgTriggerCascade.cards.CadricSoulKindler;
import nbradham.mtgTriggerCascade.cards.NykthosParagon;
import nbradham.mtgTriggerCascade.cards.ShardingSphinx;
import nbradham.mtgTriggerCascade.cards.TrueConviction;

public final class Engine {

	private static Engine engine;

	private final ArrayList<GameCard> board = new ArrayList<>();
	private final HashMap<GameCard, Object[]> keywords = new HashMap<>();

	private Engine() {
		engine = this;
	}

	private static final void addKeywordsIfValid(GameCard card, CardType type, KeywordAbility[] keywords) {
		if (card.isType(type))
			System.out.printf("%s is gaining %s from board state.%n", card, Arrays.toString(keywords));
		card.addKeywordAbilities(keywords);
	}

	private final void addCard(GameCard card) {
		System.out.printf("Adding %s to board...%n", card);
		board.add(card);
		card.onEnter();
		keywords.values().forEach(v -> addKeywordsIfValid(card, (CardType) v[0], (KeywordAbility[]) v[1]));
	}

	private final void start() {
		System.out.println("Setting up board state...");
		for (GameCard c : new GameCard[] { new TokenCard(new NykthosParagon(), CardType.Artifact),
				new BrudicladTelchorEngineer(), new TrueConviction(), new ShardingSphinx(), new CadricSoulKindler() })
			addCard(c);
		System.out.printf("Board:%s%n", board);
	}

	public static void main(String[] args) {
		if (System.console() == null && args.length > 1 && args[0].equals("debug")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Engine().start();
	}

	public static final void registerBoardEffects(GameCard card, CardType type, KeywordAbility[] keywords) {
		System.out.printf("%s is registering %s to %s cards on board...%n", card, Arrays.toString(keywords), type);
		engine.keywords.put(card, new Object[] { type, keywords });
		engine.board.forEach(c -> addKeywordsIfValid(c, type, keywords));
	}
}