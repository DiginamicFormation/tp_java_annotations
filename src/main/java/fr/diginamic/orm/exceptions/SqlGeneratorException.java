package fr.diginamic.orm.exceptions;

/** Exception technique 
 * @author RichardBONNAMY
 *
 */
public class SqlGeneratorException extends RuntimeException {

	/** Constructeur
	 * @param message message
	 */
	public SqlGeneratorException(String message) {
		super(message);
	}
	
	/** Constructeur
	 * @param message message
	 * @param cause cause racine
	 */
	public SqlGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
