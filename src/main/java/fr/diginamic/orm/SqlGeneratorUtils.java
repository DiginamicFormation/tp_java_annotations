package fr.diginamic.orm;

import java.lang.reflect.Field;

import fr.diginamic.orm.annotations.Colonne;
import fr.diginamic.orm.annotations.Id;
import fr.diginamic.orm.annotations.Table;
import fr.diginamic.orm.exceptions.SqlGeneratorException;

/**
 * Fournit des méthodes réutilisables
 * 
 * @author RichardBONNAMY
 *
 */
public class SqlGeneratorUtils {

	/**
	 * Extrait le nom de la table sur laquelle la classe est mappée.
	 * <p>
	 * Par exemple si la classe possède @Table(nom="TOTO") alors le nom de la table
	 * est TOTO.
	 * <p>
	 * Si la classe ne possède pas d'annotation @Table on génère une exception.
	 * 
	 * @param classe classe de l'entité
	 * @return String
	 */
	public static String getNomTable(Class<?> classe) {
		if (classe.isAnnotationPresent(Table.class)) {
			return classe.getAnnotation(Table.class).nom();
		}
		throw new SqlGeneratorException("L'entité doit possèder l'annotation @Table");
	}

	/**
	 * Génère la liste des colonnes à partir des annotations @Colonne positionnées
	 * sur les attributs d'instance de la classe.
	 * 
	 * @param sqlBuilder contient la requête SQL à compléter
	 * @param instance   instance
	 */
	public static void generateColumns(StringBuilder sqlBuilder, Object instance) {

		Field[] instanceAttrs = instance.getClass().getDeclaredFields();

		for (int i = 0; i < instanceAttrs.length; i++) {

			String columnName = getColumnName(instanceAttrs[i]);
			if (columnName != null) {
				sqlBuilder.append(columnName);
				if (i < instanceAttrs.length - 1) {
					sqlBuilder.append(", ");
				}
			}
		}
	}

	/**
	 * Retourne le nom de la colonne d'un attribut d'instance donné (positionné
	 * sur @Colonne)
	 * 
	 * @param instanceAttr attribut d'instance
	 * @return String
	 */
	public static String getColumnName(Field instanceAttr) {
		String nomColonne = null;
		if (instanceAttr.isAnnotationPresent(Colonne.class)) {
			Colonne col = instanceAttr.getAnnotation(Colonne.class);
			nomColonne = col.nom();
		}
		return nomColonne;
	}

	/**
	 * Génère la liste des valeurs à partir des valeurs des attributs d'instance de
	 * l'instance passée en paramètre.
	 * 
	 * @param sqlBuilder contient la requête SQL à compléter
	 * @param instance   instance
	 */
	public static void generateValues(StringBuilder sqlBuilder, Object instance) {

		Field[] instanceAttrs = instance.getClass().getDeclaredFields();

		for (int i = 0; i < instanceAttrs.length; i++) {

			if (instanceAttrs[i].isAnnotationPresent(Colonne.class)) {

				String sqlValue = getSqlValue(instanceAttrs[i], instance);

				sqlBuilder.append(sqlValue);
				if (i < instanceAttrs.length - 1) {
					sqlBuilder.append(", ");
				}
			}
		}
	}

	/**
	 * Génère la liste des valeurs à partir des valeurs des attributs d'instance de
	 * l'instance passée en paramètre.
	 * 
	 * @param sqlBuilder contient la requête SQL à compléter
	 * @param instance   instance
	 */
	public static void generateColumnsAndValues(StringBuilder sqlBuilder, Object instance) {

		Field[] instanceAttrs = instance.getClass().getDeclaredFields();

		for (int i = 0; i < instanceAttrs.length; i++) {

			if (instanceAttrs[i].isAnnotationPresent(Colonne.class)) {

				String columnName = getColumnName(instanceAttrs[i]);
				String sqlValue = getSqlValue(instanceAttrs[i], instance);

				sqlBuilder.append(columnName).append("=").append(sqlValue);
				if (i < instanceAttrs.length - 1) {
					sqlBuilder.append(", ");
				}
			}
		}
	}

	/**
	 * On récupère la valeur SQL d'un attribut pour une entité donnée.
	 * <p>
	 * Exemple 1:<br>
	 * si la ville (entity) possède un attribut nom (field) qui vaut Nantes, Alors
	 * la SqlValue vaut 'Nantes' (on doit en effet ajouter des simples quotes pour
	 * des chaines de caractères).
	 * <p>
	 * Exemple 1:<br>
	 * si une instance d'utilisateur (entity) possède un attribut age (field) qui
	 * vaut 28, Alors la SqlValue vaut 28 (on ne met pas de quote pour des nombres).
	 * 
	 * @param instanceAttr attribut d'instance
	 * @param instance     instance
	 * @return String
	 */
	public static String getSqlValue(Field instanceAttr, Object instance) {

		Colonne col = instanceAttr.getAnnotation(Colonne.class);
		String nom = col.nom();
		int maxLength = col.maxLength();

		String sqlValue = "";
		try {
			// Indispensable pour accéder à la valeur d'un attribut privé
			instanceAttr.setAccessible(true);

			// On récupère la valeur de l'attribut (exemple: DUPONT pour l'attribut nom)
			Object value = instanceAttr.get(instance);

			// Si la valeur n'est pas un nombre on doit ajouter des simples quotes
			if (!(value instanceof Number)) {
				sqlValue = "'" + value + "'";
			} else {
				sqlValue = "" + value;
			}

			// Si l'attribut possède un attribut maxlength on doit contrôler son nb
			// de caractères qui ne doit pas dépasser maxLength
			if (maxLength != -1 && sqlValue.length() > maxLength) {
				throw new SqlGeneratorException(
						"La longueur de l'attribut " + nom + " ne doit pas dépasser " + maxLength + " caractères");
			}
		}
		// Certaines opérations sur les classes peuvent générer des exceptions.
		catch (IllegalArgumentException | IllegalAccessException e) {
			throw new SqlGeneratorException(e.getMessage(), e);
		}
		return sqlValue;
	}

	/**
	 * Recherche dans l'instance l'annotation @Id et plus particulièrement le nom de
	 * la colonne en base de données.
	 * 
	 * @param instance instance
	 * @return String
	 */
	public static String getIdColumnName(Object instance) {
		Field[] instanceAttrs = instance.getClass().getDeclaredFields();

		for (int i = 0; i < instanceAttrs.length; i++) {
			if (instanceAttrs[i].isAnnotationPresent(Id.class)) {
				Id id = instanceAttrs[i].getAnnotation(Id.class);
				return id.nom();
			}
		}
		throw new SqlGeneratorException("La classe ne possède pas de clé primaire");
	}

	/**
	 * Recherche dans l'instance l'annotation @Id et plus particulièrement la valeur de
	 * l'attribut.
	 * 
	 * @param instance instance
	 * @return String
	 */
	public static String getIdColumnValue(Object instance) {
		Field[] instanceAttrs = instance.getClass().getDeclaredFields();

		for (int i = 0; i < instanceAttrs.length; i++) {
			if (instanceAttrs[i].isAnnotationPresent(Id.class)) {

				try {
					instanceAttrs[i].setAccessible(true);

					String sqlValue = null;
					Object value = instanceAttrs[i].get(instance);
					if (!(value instanceof Number)) {
						sqlValue = "'" + value + "'";
					} else {
						sqlValue = "" + value;
					}
					return sqlValue;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new SqlGeneratorException(e.getMessage(), e);
				}
			}
		}
		throw new SqlGeneratorException("La classe ne possède pas de clé primaire");
	}

}
