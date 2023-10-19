package fr.diginamic.orm.entites;

import java.util.Objects;

import fr.diginamic.orm.annotations.Colonne;
import fr.diginamic.orm.annotations.Id;
import fr.diginamic.orm.annotations.Table;

/** La classe Ville représente le concept de ville dans l'application de Recensement<br>
 *  Elle possède 2 attributs: nom et population
 * @author RichardBONNAMY
 *
 */
@Table(nom="VILLES")
public class Ville implements Comparable<Ville> {

	@Id(nom="ID")
	private long id;
	
	/** nom */
	@Colonne(nom="NOM")
	private String nom;
	
	/** population */
	@Colonne(nom="POP")
	private int population;
	
	/** Constructeur
	 * @param nom nom
	 * @param population population
	 */
	public Ville(String nom, int population) {
		super();
		this.nom = nom;
		this.population = population;
	}
	
	@Override
	public boolean equals(Object object) {
		
		// Etape 1 : on vérifie que object est bien une instance de Ville
		if (!(object instanceof Ville)) {
			return false;
		}
		
		// Etape 2 : object est une instance de Ville (en mémoire) mais object est une référence
		// de type Object ce qui ne convient pas.
		// Il faut créer une nouvelle référence en castant object en Ville
		Ville other = (Ville)object;
		
		// Etape 3: on compare 2 à 2 les attributs. Pour comparer les attributs non primitifs
		// on va utiliser Objects.equals de manière à tester toutes les possibilités et notamment
		// les cas null
		
		return Objects.equals(this.nom, other.getNom()) && this.population == other.getPopulation();
	}

	@Override
	public int compareTo(Ville autre) {

		if (this.getPopulation() > autre.getPopulation()) {
			return -1;
		}
		if (this.getPopulation() < autre.getPopulation()) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Ville [nom=" + nom + ", population=" + population + "]";
	}

	/** Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/** Setter
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
}
