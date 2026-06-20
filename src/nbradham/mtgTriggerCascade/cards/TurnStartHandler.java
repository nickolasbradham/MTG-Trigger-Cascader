package nbradham.mtgTriggerCascade.cards;

import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class TurnStartHandler extends GameEventHandler {
	
	public abstract void onStart();
	
	@Override
	public final String toString() {
		return String.format("Turn Start Trigger[%s]", string);
	}
}