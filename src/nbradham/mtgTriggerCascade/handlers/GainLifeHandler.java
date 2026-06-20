package nbradham.mtgTriggerCascade.handlers;

import nbradham.mtgTriggerCascade.GameEventHandler;

public abstract class GainLifeHandler extends GameEventHandler {

	public GainLifeHandler(final String text) {
		super(text);
	}

	public abstract void onLifeGain(byte gainedLife);

	@Override
	public final String toString() {
		return String.format("Life Gain Trigger[%s]", string);
	}
}