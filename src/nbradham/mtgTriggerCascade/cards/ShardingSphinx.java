package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.CardType;
import nbradham.mtgTriggerCascade.CreatureCard;
import nbradham.mtgTriggerCascade.Engine;
import nbradham.mtgTriggerCascade.KeywordAbility;
import nbradham.mtgTriggerCascade.cards.tokens.Thopter;
import nbradham.mtgTriggerCascade.handlers.PlayerCombatDamageHandler;

public final class ShardingSphinx extends CreatureCard {

	private static final CardType[] TYPES = { CardType.Creature, CardType.Artifact, CardType.Sphinx };
	private static final CardType[] ART_CREAT = { CardType.Artifact, CardType.Creature };
	private static final PlayerCombatDamageHandler PLAYER_DAMAGE_HANDLER = new PlayerCombatDamageHandler(
			"Whenever an artifact creature you control deals combat damage to a player, you may create a 1/1 blue Thopter artifact creature token with flying.") {

		@Override
		public void onDamageDealt(CreatureCard src) {
			if (Engine.isCardAllTypes(src, ART_CREAT) && Engine.mayDoShardingAbility())
				Engine.staticAddCard(new Thopter());
		}
	};

	public ShardingSphinx() {
		super("Sharding Sphinx", TYPES, 4);
		abilities.add(KeywordAbility.Flying);
	}

	@Override
	public final void onEnter() {
		super.onEnter();
		Engine.registerEventHandler(PLAYER_DAMAGE_HANDLER);
	}
}