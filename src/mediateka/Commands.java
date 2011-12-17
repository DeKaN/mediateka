package mediateka;

public final class Commands {
	public enum ClientCommands {
		ADD, DELETE, FIND, FIND_BY_ID, UPDATE, QUIT
	}

	public enum ServerCommands {
		OK, NOTIFY, ERR_UNRECOGNIZED, ERR_ADD, ERR_DEL, ERR_FIND, ERR_UPD, ERR
	}
}
