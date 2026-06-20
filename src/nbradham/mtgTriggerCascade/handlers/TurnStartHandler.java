package nbradham.mtgTriggerCascade.handlers;

import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class TurnStartHandler extends GameEventHandler {

	public TurnStartHandler(final String text) {
		super(text);
	}

	public abstract void onStart();

	@Override
	public final String toString() {
		return String.format("Turn Start Trigger[%s]", string);
	}
}