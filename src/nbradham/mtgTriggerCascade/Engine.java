package nbradham.mtgTriggerCascade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import javax.swing.JOptionPane;

import nbradham.mtgTriggerCascade.cards.BrudicladTelchorEngineer;
import nbradham.mtgTriggerCascade.cards.CadricSoulKindler;
import nbradham.mtgTriggerCascade.cards.NykthosParagon;
import nbradham.mtgTriggerCascade.cards.ShardingSphinx;
import nbradham.mtgTriggerCascade.cards.TrueConviction;
import nbradham.mtgTriggerCascade.events.LifeGainedEvent;
import nbradham.mtgTriggerCascade.events.OpponentCombatDamageEvent;
import nbradham.mtgTriggerCascade.handlers.CombatBeginHandler;
import nbradham.mtgTriggerCascade.handlers.GainLifeHandler;
import nbradham.mtgTriggerCascade.handlers.PlayerCombatDamageHandler;
import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public final class Engine {

	private static Engine engine;

	private final ArrayList<GameCard> board = new ArrayList<>();
	private final HashMap<GameCard, Object[]> keywords = new HashMap<>();
	private final HashSet<CombatBeginHandler> combatHandlers = new HashSet<>();
	private final HashSet<GainLifeHandler> gainLifeHandlers = new HashSet<>();
	private final HashSet<TurnStartHandler> turnStartHandlers = new HashSet<>();
	private final HashSet<PlayerCombatDamageHandler> playerDamageHandlers = new HashSet<>();
	private final HashSet<GameEventHandler> unregisterQueue = new HashSet<>();
	private final PriorityQueue<GameEvent> eventQueue = new PriorityQueue<>(Comparator.comparing(e -> e.getPriority()));
	private short lifeGained = 0, oppDamage = 0;

	private Engine() {
		engine = this;
	}

	public static final boolean isCardAllTypes(final GameCard card, final CardType[] types) {
		for (CardType t : types)
			if (!card.isType(t))
				return false;
		return true;
	}

	private static final void addKeywordsIfValid(final GameCard card, final CardType[] types,
			final KeywordAbility[] keywords) {
		if (isCardAllTypes(card, types)) {
			System.out.printf("  %s is gaining %s from board state.%n", card, Arrays.toString(keywords));
			((CreatureCard) card).addKeywordAbilities(keywords);
		}
	}

	private final void addCard(final GameCard card) {
		System.out.printf("  Adding %s to board...%n", card);
		board.add(card);
		card.onEnter();
		addCardModifiers(card);
	}

	private final void addCardModifiers(final GameCard card) {
		keywords.values().forEach(v -> addKeywordsIfValid(card, (CardType[]) v[0], (KeywordAbility[]) v[1]));
	}

	public static final void registerBoardEffects(final GameCard card, final CardType[] types,
			final KeywordAbility[] keywords) {
		System.out.printf("  %s is registering %s to %s cards on board...%n", card, Arrays.toString(keywords),
				Arrays.toString(types));
		engine.keywords.put(card, new Object[] { types, keywords });
		engine.board.forEach(c -> addKeywordsIfValid(c, types, keywords));
	}

	public static final void registerEventHandler(final GameEventHandler handler) {
		System.out.printf("  Registering: %s%n", handler);
		if (handler instanceof CombatBeginHandler)
			engine.combatHandlers.add((CombatBeginHandler) handler);
		else if (handler instanceof GainLifeHandler)
			engine.gainLifeHandlers.add((GainLifeHandler) handler);
		else if (handler instanceof TurnStartHandler)
			engine.turnStartHandlers.add((TurnStartHandler) handler);
		else if (handler instanceof PlayerCombatDamageHandler)
			engine.playerDamageHandlers.add((PlayerCombatDamageHandler) handler);
	}

	public static final void unregisterEventHandler(final GameEventHandler handler) {
		System.out.printf("  Adding %s to unregister pool...%n", handler);
		engine.unregisterQueue.add(handler);
	}

	public static final void staticAddCard(final GameCard card) {
		engine.addCard(card);
	}

	public static final CreatureCard mayChooseToken() {
		CreatureCard gc = (CreatureCard) engine.board.get(0);
		System.out.printf("Choosing token: %s%n", gc);
		return gc;
	}

	public static final ArrayList<GameCard> getCards() {
		return engine.board;
	}

	/*
	 * Yes I know this is a terrible way to handle board-wide status effects. Too
	 * bad! I realized this too late.
	 */
	private final void privReplaceAll(final UnaryOperator<GameCard> operator) {
		ArrayList<GameCard> field = Engine.getCards();
		int end = field.size();
		for (byte i = 0; i < end; ++i) {
			GameCard orig = field.get(i), after = operator.apply(orig);
			field.set(i, after);
			if (orig != after)
				addCardModifiers((CreatureCard) after);
		}
	}

	public static final void replaceAll(final UnaryOperator<GameCard> operator) {
		Engine.engine.privReplaceAll(operator);
	}

	private final void unregisterHandlers() {
		System.out.printf("Unregister pool cleanup(%d)...%n", unregisterQueue.size());
		for (GameEventHandler handler : unregisterQueue.toArray(new GameEventHandler[0])) {
			System.out.printf("  Unregistering: %s%n", handler);
			unregisterQueue.remove(handler);
			if (handler instanceof CombatBeginHandler)
				combatHandlers.remove(handler);
			else if (handler instanceof GainLifeHandler)
				gainLifeHandlers.remove(handler);
			else if (handler instanceof TurnStartHandler)
				turnStartHandlers.remove(handler);
			else if (handler instanceof PlayerCombatDamageHandler)
				playerDamageHandlers.remove(handler);
		}
	}

	public static final boolean mayDoNykthosBuff(final int gainedLife) {
		// TODO: Do better logic.
		return true;
	}

	public static final void forEach(final Consumer<GameCard> consumer) {
		Engine.engine.board.forEach(consumer);
	}

	// More of a formality.
	public static final boolean mayDoShardingAbility() {
		return true;
	}

	public static final void dealOpponentCombatDamage(final CreatureCard src, final int damage) {
		engine.privDealOpponentCombatDamage(src, damage);
	}

	private final void privDealOpponentCombatDamage(final CreatureCard src, final int damage) {
		oppDamage += damage;
		eventQueue.offer(new OpponentCombatDamageEvent(src));
	}

	public static final void gainLife(final CreatureCard src, final int life) {
		engine.privGainLife(src, life);
	}

	private void privGainLife(final CreatureCard src, final int life) {
		lifeGained += life;
		eventQueue.offer(new LifeGainedEvent(src, life));
	}

	private final void proccessEvents() {
		System.out.printf("  Proccessing event queue(%d)...%n", eventQueue.size());
		while (eventQueue.size() != 0) {
			final GameEvent ev = eventQueue.poll();
			System.out.printf("    Proccessing %s%n", ev);
			if (ev instanceof OpponentCombatDamageEvent)
				playerDamageHandlers.forEach(h -> h.onDamageDealt(((OpponentCombatDamageEvent) ev).src()));
			else if (ev instanceof LifeGainedEvent)
				gainLifeHandlers.forEach(h -> h.onLifeGain(((LifeGainedEvent) ev).amount()));
		}
	}

	private final void start() {
		System.out.println("Setting up board state...");
		for (GameCard c : new GameCard[] { TokenCopy.createTokenCopy(new NykthosParagon(), CardType.Artifact),
				new BrudicladTelchorEngineer(), new TrueConviction(), new ShardingSphinx(), new CadricSoulKindler() })
			addCard(c);
		System.out.printf("Turn start. Board state(%d): %s%n", board.size(), board);
		turnStartHandlers.forEach(c -> {
			if (!unregisterQueue.contains(c)) {
				System.out.printf("  Turn start trigger: %s%n", c);
				c.onStart();
			}
		});
		unregisterHandlers();
		System.out.printf("Combat start. Board state(%d): %s%n", board.size(), board);
		combatHandlers.forEach(c -> {
			if (!unregisterQueue.contains(c)) {
				System.out.printf("  Combat begin trigger: %s%n", c);
				c.beginCombat();
			}
		});
		unregisterHandlers();
		System.out.printf("  Declare attackers. Board state(%d): %s%n", board.size(), board);
		final ArrayList<CreatureCard> attackers = new ArrayList<>();
		board.forEach(c -> {
			if (c.isType(CardType.Creature) && c.isUntapped() && (((CreatureCard) c).noSummoningSickness()
					|| ((CreatureCard) c).hasAbility(KeywordAbility.Haste))) {
				if (!((CreatureCard) c).hasAbility(KeywordAbility.Vigilance))
					c.tap();
				attackers.add((CreatureCard) c);
			}
		});
		System.out.printf("  Attackers(%d):%s%n  Attacking (First Strike)...%n", attackers.size(), attackers);
		attackers.forEach(c -> {
			if (c.hasAbility(KeywordAbility.Double_Strike) || c.hasAbility(KeywordAbility.First_Strike)) {
				System.out.printf("    Attack: %s%n", c);
				c.attack();
			}
		});
		proccessEvents();
		System.out.printf("  Attacking (Normal)...%n", attackers.size(), attackers);
		attackers.forEach(c -> {
			System.out.printf("    Attack: %s%n", c);
			c.attack();
		});
		proccessEvents();
		System.out.printf("Life Gained: %d  Damage Dealt: %d  Board(%d): %s%n", lifeGained, oppDamage, board.size(),
				board);
	}

	public static void main(final String[] args) {
		if (System.console() == null && args.length > 1 && args[0].equals("debug")) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Engine().start();
	}
}