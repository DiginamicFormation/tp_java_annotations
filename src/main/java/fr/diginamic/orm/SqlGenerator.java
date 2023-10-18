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

		Class<?> classe = instance.getClass();
		
		// On récupère le nom de la table sur la classe
		String nomTable = SqlGeneratorUtils.getNomTable(classe);
		
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


	

}
