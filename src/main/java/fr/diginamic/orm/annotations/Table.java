package fr.diginamic.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A positionner sur une signature de classe afin de préciser le nom de la table
 * sur laquelle la classe est mappée
 * 
 * @author RichardBONNAMY
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

	/** Nom de la table en base
	 * @return String
	 */
	String nom();

}
