package fr.diginamic.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation à positionner sur l'attribut d'instance qui représente la clé
 * primaire
 * 
 * @author RichardBONNAMY
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

	/** Nom de la colonne
	 * @return String
	 */
	String nom();
}
