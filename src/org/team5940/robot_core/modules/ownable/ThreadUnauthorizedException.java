package org.team5940.robot_core.modules.ownable;

/**
 * This exception should be thrown by implementations of OwnableModule if its methods are accessed by a Thread when another Thread owns it.
 * @author David Boles
 *
 */
public class ThreadUnauthorizedException extends Exception {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the ThreadNotAuthorizedException with a message containing the .toString()s of the arguments.
	 * @param module The module that was accessed by an unauthorized.
	 * @param accessing The unauthorized Thread that attempted to access module.
	 * @param owner The Thread that owns module.
	 */
	public ThreadUnauthorizedException(OwnableModule module, Thread accessing, Thread owner) {
		super(accessing.toString() + " attempted to access OwnableModule " + module.toString() + " which is owned by " + owner.toString() + ".");
	}
}
