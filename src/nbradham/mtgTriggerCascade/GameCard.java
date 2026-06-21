package nbradham.mtgTriggerCascade;

import java.util.Arrays;
import java.util.HashSet;

import nbradham.mtgTriggerCascade.handlers.TurnStartHandler;

public abstract class GameCard {

	protected final String name;

	private final TurnStartHandler untapper;

	protected final HashSet<CardType> types = new HashSet<>();

	private boolean untapped = true;

	protected GameCard(final String cardName, final CardType[] cardTypes) {
		name = cardName;
		types.addAll(Arrays.asList(cardTypes));
		untapper = new TurnStartHandler("(" + name + " untap)") {
			@Override
			public final void onStart() {
				Engine.unregisterEventHandler(this);
				untapped = true;
			}
		};
	}

	final String getName() {
		return name;
	}

	public boolean isType(final CardType type) {
		return types.contains(type);
	}

	@Override
	public String toString() {
		return String.format("%s:{Types:%s}", name, types);
	}

	final boolean isUntapped() {
		return untapped;
	}

	final void tap() {
		untapped = false;
		Engine.registerEventHandler(untapper);
	}

	protected void onEnter() {
	}

	protected void registerBattlefieldHandlers() {
	}

	protected void unregisterBattlefieldHandlers() {
	}
}