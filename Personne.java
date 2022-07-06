package generateurNoms;

import java.util.Scanner;

public class Personne {
	
	private long idPersonne;
	private static long compteurPersonne = 1;
	protected Scanner sc;
	
	private String nom = "";
	private String prenom = "";
	
	Personne(String prenom, String nom) {
		this.prenom = prenom;
		this.nom = nom;
		setIdPersonne((long)compteurPersonne++);
	}

	Personne() {
		this.setIdPersonne(compteurPersonne++);
	}
	
	
	public void ajoutPersonne() {
		sc = new Scanner(System.in);
		System.out.println("Vous voulez ajoute rune personne...");
		System.out.println("Entrer son prenom: ");
		prenom = sc.next();
		setPrenom(prenom);
		
		System.out.println("Entrer son nom de famille: ");
		nom = sc.next();
		setNom(nom);
		
		
	}
	
	
	public void setNom(String nom) {this.nom = nom;}
	public String getNom() {
		return nom;
	}
	
	public void setPrenom(String prenom) {this.prenom = prenom;}
	public String getPrenom() { return prenom; }
	
	public static void setCompteurPersonne(long compteurPersonne ) {
		Personne.compteurPersonne = compteurPersonne;
	}
	public static long getCompteurPersonne() { return compteurPersonne; }
	
	
	public long getIdPersonne() { return idPersonne; }
	private void setIdPersonne(long idPersonne) {
		this.idPersonne = idPersonne;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {	return getIdPersonne() + " " + getNom() + " " + getPrenom();}
	
	
	
}
