package fr.diginamic.orm;

/** Générateur de requêtes SQL
 * @author RichardBONNAMY
 *
 */
public final class SqlGenerator {

	/**
	 * Génère une requête SQL INSERT sur la base des annotations positionnées sur
	 * l'instance de classe passée en paramètre.
	 * 
	 * template : INSERT INTO MA_TABLE 
	 *            (COL1, COL2, COL3) 
	 *            VALUES ('Chaine1', 'Chaine2', number3)
	 * 
	 * @param instance instance de classe
	 * @return String
	 */
	public static String genererInsertSQL(Object instance) {

		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");

		// On récupère le nom de la table sur la classe
		String nomTable = SqlGeneratorUtils.getNomTable(instance.getClass());
		
		sqlBuilder.append(nomTable).append(" (");

		// On ajoute la liste des colonnes, par exemple : NOM, PRENOM, EMAIL, etc...
		SqlGeneratorUtils.generateColumns(sqlBuilder, instance);
		
		// On ajoute la partie séparant les colonnes des valeurs
		sqlBuilder.append(") VALUES (");
		
		// On ajoute les valeurs des colonnes, par ex: 'Dupont', 'Albert', 'adupont@gmail.com'
		SqlGeneratorUtils.generateValues(sqlBuilder, instance);
		
		sqlBuilder.append(")");
		return sqlBuilder.toString();
	}

	/**
	 * Génère une requête SQL UPDATE sur la base des annotations positionnées sur
	 * l'instance de classe passée en paramètre.
	 * 
	 * Exemple : UPDATE UTILISATEUR 
	 *           SET NOM='DUPOND', PRENOM='Albert', EMAIL='adupond@gmail.com' 
	 *           WHERE id=127
	 * 
	 * @param instance instance de classe
	 * @return String
	 */
	public static String generateSqlUpdate(Object instance) {

		StringBuilder sqlBuilder = new StringBuilder("UPDATE ");

		// On récupère le nom de la table sur la classe
		String nomTable = SqlGeneratorUtils.getNomTable(instance.getClass());
		
		sqlBuilder.append(nomTable).append(" SET ");

		// On ajoute la liste des couples COL1=VAL1
		SqlGeneratorUtils.generateColumnsAndValues(sqlBuilder, instance);
		
		// On ajoute la clause WHERE
		sqlBuilder.append(" WHERE ");
		
		// On ajoute la condition sur la valeur de l'identifiant
		
		String idColumnName = SqlGeneratorUtils.getIdColumnName(instance);
		Object idColumnValue = SqlGeneratorUtils.getIdColumnValue(instance);
		
		sqlBuilder.append(idColumnName).append(" = ").append(idColumnValue);
		
		return sqlBuilder.toString();
	}
	

}
