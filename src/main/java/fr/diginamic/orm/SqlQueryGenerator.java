package fr.diginamic.orm;

import java.lang.reflect.Field;

import fr.diginamic.orm.annotations.Colonne;
import fr.diginamic.orm.annotations.Table;
import fr.diginamic.orm.exceptions.SqlGeneratorException;

/** Générateur de requêtes SQL
 * @author RichardBONNAMY
 *
 */
public final class SqlQueryGenerator {

	/**
	 * Génère une requête SQL INSERT sur la base des annotations positionnées sur
	 * l'entité passée en paramètre.
	 * 
	 * template : INSERT INTO MA_TABLE 
	 *            (COL1, COL2, COL3) 
	 *            VALUES ('Chaine1', 'Chaine2', number3)
	 * 
	 * @param entity entité
	 * @return String
	 */
	public static String genererInsertSQL(Object entity) {

		// 
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

		Class<?> classe = entity.getClass();
		
		String nomTable = null;
		if (!classe.isAnnotationPresent(Table.class)) {
			nomTable = classe.getSimpleName();
		}
		else {
			nomTable = classe.getAnnotation(Table.class).nom();
		}

		sqlBuilder.append(nomTable).append(" (");

		Field[] attributs = classe.getDeclaredFields();
		for (int i = 0; i < attributs.length; i++) {
			Field attr = attributs[i];
			String nom = null;
			if (attr.isAnnotationPresent(Colonne.class)) {
				Colonne col = attr.getAnnotation(Colonne.class);
				nom = col.nom();
			}
			else {
				nom = attr.getName();
			}
			sqlBuilder.append(nom);
			if (i < attributs.length - 1) {
				sqlBuilder.append(", ");
			} 
		}
		sqlBuilder.append(") VALUES (");
		for (int i = 0; i < attributs.length; i++) {
			Field attr = attributs[i];
			if (attr.isAnnotationPresent(Colonne.class)) {

				Colonne col = attr.getAnnotation(Colonne.class);
				String nom = col.nom();
				int maxLength = col.maxLength();

				String sqlValue = "";
				try {
					attr.setAccessible(true);
					Object value = attr.get(entity);
					if (!(value instanceof Number)) {
						sqlBuilder.append("'").append(value).append("'");
					} else {
						sqlBuilder.append(value);
					}

					if (maxLength != -1 && sqlValue.length() > maxLength) {
						throw new SqlGeneratorException("La longueur de l'attribut " + nom + " ne doit pas dépasser " + maxLength
								+ " caractères");
					}

				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new SqlGeneratorException(e);
				}
				sqlBuilder.append(sqlValue);
				if (i < attributs.length - 1) {
					sqlBuilder.append(", ");
				}
			}
		}
		sqlBuilder.append(")");
		return sqlBuilder.toString();
	}

}
