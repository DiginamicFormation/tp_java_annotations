package fr.diginamic.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A positionner sur chaque attribut d'instance mappée sur une colonne
 * 
 * @author RichardBONNAMY
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Colonne {

	/**
	 * Nom de la colonne en base
	 * 
	 * @return String
	 */
	String nom();

	/**
	 * Taille max de la valeur : utile pour les colonnes de type text
	 * 
	 * @return int
	 */
	int maxLength() default -1;
}
