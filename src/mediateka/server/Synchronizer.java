package mediateka.server;

public final class Synchronizer {
	private boolean locked = false;

	public void setLocked(boolean state) {
		locked = state;
	}

	public boolean isLocked() {
		return locked;
	}

	public Synchronizer() {
		this(false);
	}

	public Synchronizer(boolean state) {
		locked = state;
	}
}
