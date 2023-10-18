package fr.diginamic.orm.entites;

import fr.diginamic.orm.annotations.Colonne;
import fr.diginamic.orm.annotations.Table;

@Table(nom="TAB_UTILISATEUR")
public class Utilisateur {
	
	private int id;
	
	@Colonne(nom="NOM", maxLength=50)
	private String nom;
	
	@Colonne(nom="PRENOM")
	private String prenom;

	@Colonne(nom="EMAIL")
	private String email;

	/** Constructeur
	 * @param nom
	 * @param prenom
	 * @param email
	 */
	public Utilisateur(String nom, String prenom, String email) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
	}

	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/** Setter
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/** Getter
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/** Setter
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
