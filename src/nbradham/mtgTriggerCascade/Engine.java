package nbradham.mtgTriggerCascade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JOptionPane;

import nbradham.mtgTriggerCascade.cards.BrudicladTelchorEngineer;
import nbradham.mtgTriggerCascade.cards.CadricSoulKindler;
import nbradham.mtgTriggerCascade.cards.NykthosParagon;
import nbradham.mtgTriggerCascade.cards.ShardingSphinx;
import nbradham.mtgTriggerCascade.cards.TrueConviction;
import nbradham.mtgTriggerCascade.handlers.CombatBeginHandler;

public final class Engine {

	private static Engine engine;

	private final ArrayList<GameCard> board = new ArrayList<>();
	private final HashMap<GameCard, Object[]> keywords = new HashMap<>();
	private final HashSet<CombatBeginHandler> combatHandlers = new HashSet<>();

	private Engine() {
		engine = this;
	}

	private static final void addKeywordsIfValid(final GameCard card, final CardType[] types,
			final KeywordAbility[] keywords) {
		for (CardType t : types)
			if (!card.isType(t))
				return;
		System.out.printf("%s is gaining %s from board state.%n", card, Arrays.toString(keywords));
		card.addKeywordAbilities(keywords);
	}

	private final void addCard(final GameCard card) {
		System.out.printf("Adding %s to board...%n", card);
		board.add(card);
		card.onEnter();
		keywords.values().forEach(v -> addKeywordsIfValid(card, (CardType[]) v[0], (KeywordAbility[]) v[1]));
	}

	public static final void registerBoardEffects(final GameCard card, final CardType[] types,
			final KeywordAbility[] keywords) {
		System.out.printf("%s is registering %s to %s cards on board...%n", card, Arrays.toString(keywords),
				Arrays.toString(types));
		engine.keywords.put(card, new Object[] { types, keywords });
		engine.board.forEach(c -> addKeywordsIfValid(c, types, keywords));
	}

	public static final void registerEventHandler(final GameEventHandler handler) {
		System.out.printf("Registering: %s%n", handler);
		if (handler instanceof CombatBeginHandler)
			engine.combatHandlers.add((CombatBeginHandler) handler);
	}

	public static final void staticAddCard(final GameCard card) {
		engine.addCard(card);
	}

	public static final GameCard mayChooseToken() {
		GameCard gc = engine.board.get(0);
		System.out.printf("Choosing token: %s%n", gc);
		return gc;
	}

	public static final ArrayList<GameCard> getCards() {
		return engine.board;
	}

	private final void start() {
		System.out.println("Setting up board state...");
		for (GameCard c : new GameCard[] { new TokenCopy(new NykthosParagon(), CardType.Artifact),
				new BrudicladTelchorEngineer(), new TrueConviction(), new ShardingSphinx(), new CadricSoulKindler() })
			addCard(c);
		System.out.printf("Board ready:%s%nCombat begin.%n", board);
		combatHandlers.forEach(c -> {
			System.out.printf("Combat begin trigger: %s%n", c);
			c.beginCombat();
		});
		System.out.printf("Board:%s%n", board);
	}

	public static void main(final String[] args) {
		if (System.console() == null && args.length > 1 && args[0].equals("debug")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Engine().start();
	}
}