package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.KeywordAbility;
import nbradham.mtgTriggerCascade.TokenCopy;
import nbradham.mtgTriggerCascade.handlers.CombatBeginHandler;
import nbradham.mtgTriggerCascade.cards.tokens.PhyrexianMyr;

public final class BrudicladTelchorEngineer extends CreatureCard {

	private static final CardType[] TYPES = { CardType.Legendary, CardType.Artifact, CardType.Creature,
			CardType.Artificer, CardType.Phyrexian }, COND_TYPES = { CardType.Token, CardType.Creature };
	private static final KeywordAbility[] MODS = new KeywordAbility[] { KeywordAbility.Haste };

	public BrudicladTelchorEngineer() {
		super("Brudiclad, Telchor Engineer", TYPES, 4);
	}

	@Override
	protected final void onEnter() {
		super.onEnter();
		Engine.registerBoardEffects(this, COND_TYPES, MODS);
		Engine.registerEventHandler(new CombatBeginHandler(
				"At the beginning of combat on your turn, create a 2/1 blue Phyrexian Myr artifact creature token. Then you may choose a token you control. If you do, each other token you control becomes a copy of that token.") {
			@Override
			public final void beginCombat() {
				Engine.staticAddCard(new PhyrexianMyr());
				CreatureCard token = Engine.mayChooseToken();
				Engine.replaceAll(c -> {
					if (c.isType(CardType.Token) && c != token)
						return TokenCopy.createTokenCopy(token);
					else
						return c;
				});
			}
		});
	}
}