package nbradham.mtgTriggerCascade;

import java.util.ArrayList;
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
	private final HashMap<GameCard, Object[]> modifiers = new HashMap<>();

	private Engine() {
		engine = this;
	}

	private final void start() {
		System.out.println("Setting up board state...");

		board.add(new TokenCard(new NykthosParagon(), CardType.Artifact));
		board.add(new BrudicladTelchorEngineer());
		board.add(new TrueConviction());
		board.add(new ShardingSphinx());
		board.add(new CadricSoulKindler());
	}

	public static void main(String[] args) {
		if (System.console() == null) {
			JOptionPane.showMessageDialog(null, "Run me from the command line.");
			return;
		}
		new Engine().start();
	}

	public static final void registerBoardEffects(GameCard card, CardType type, Modifier[] modifiers) {
		engine.modifiers.put(card, new Object[] { type, modifiers });
		engine.board.forEach(c -> {if(c.isType(type))c.addModifiers(modifiers);});
	}
}