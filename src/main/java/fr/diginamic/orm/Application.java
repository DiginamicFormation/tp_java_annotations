package fr.diginamic.orm;

import fr.diginamic.orm.entites.Utilisateur;
import fr.diginamic.orm.entites.Ville;

public class Application {


	public static void main(String[] args) {
		Utilisateur u1 = new Utilisateur("DUPONT", "Albert", "adupont@gmail.com");
		String requeteInsert1 = SqlGenerator.genererInsertSQL(u1);
		String requeteUpdate1 = SqlGenerator.generateSqlUpdate(u1);
		System.out.println(requeteInsert1);
		System.out.println(requeteUpdate1);
		
		Ville v = new Ville("Montpellier", 285000);
		String requeteInsert2 = SqlGenerator.genererInsertSQL(v);
		System.out.println(requeteInsert2);
	}
	
	

}
