package generateurNoms;

////////////////////////////////////////////////////////////////////////////////////////
//Main.java		@authors wuddy
//
//Application principale et point d'entree au programme. Affichage du menu. 
//args0 = banqueDeNoms.txt
////////////////////////////////////////////////////////////////////////////////////////
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;




public class Main {
	
	static Scanner scan = new Scanner(System.in);
	private static ArrayList<Personne> tableauPersonnes = new ArrayList<Personne>(); // Pquoi arraylist: 	Utilisation de sort, simplification des methodes, pas besoin de gerer la grosseur du tableau, etc.
	

	    

	public static void main(String[] args) throws IOException {
		try {
			lectureFichier(args[0]);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int min;
		int max;
		int order;
		int noNoms;
		int choix = -1;

		do {

			choix = MenuPrincipal();

			switch(choix){
			case 0: 
				// Quitter
				System.out.println("Fin du programme.");
				break;
			case 1:
				// Generer des noms
				System.out.println("[GENERATEUR DE NOMS]");
				System.out.println("Combien de caracteres minimum pour le nom?");
				min = scan.nextInt();
				

				System.out.println();
				System.out.println("Combien de caracteres maximum pour le nom?");
				max = scan.nextInt();
				

				while(min > max) {
					System.err.println("Vous devez avoir un nombre minimum supperieur au nombre maximum.");
					System.err.println();
					System.out.println("Combien de caracteres maximum pour le nom?");
					max = scan.nextInt();
				}
				
				while(min == max) {
					System.err.println("Vous devez avoir un nombre maximum supperieur au nombre minimum.");
					System.err.println();
					System.out.println("Combien de caracteres maximum pour le nom?");
					max = scan.nextInt();
				}
				

				System.out.println();
				System.out.println("Combien de noms voulez-vous generer?");
				noNoms = scan.nextInt();

				order = 4;
				
			
				

				System.out.println();
				System.out.println();

				System.out.println("Les noms seront maintenant genere...");



				Markov markov = new Markov(min,max,order,noNoms);
				markov.setfileToSelectFrom();
				markov.setProbabilities();
				ArrayList<String> list = markov.creeNoms();
				for(String s: list)
				{
					
					Personne personneTemp = new Personne();
					
					String strFullName = s;
					
					int position = strFullName.indexOf(" ");
					String strFirstName = strFullName.substring(0,position+1);
					strFirstName.trim();
					
					String strLastName = strFullName.substring(position+1, strFullName.length());
					strFirstName.trim();
				
					
					personneTemp.setNom(strLastName);
					personneTemp.setPrenom(strFirstName);
					
					
					getTableauPersonnes().add(personneTemp);
					
					System.out.println(personneTemp.getPrenom() + personneTemp.getNom());
				}
				
				break;
			case 2:
				// Afficher la banque de nom
				afficherBanqueDeNoms();
				break;
			case 3:
				// Ajouter une personne a la liste
				Personne temp = new Personne();
				temp.ajoutPersonne();
				getTableauPersonnes().add(temp);
				afficherBanqueDeNoms();

				temp = null;	
				break;
			case 4: 
				initialiser();
				try {
					lectureFichier(args[0]);

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				break;
				
			case 5:
				break;
			default:
				System.err.println("Erreur. Entree invalide");
				break;
			} // end switch(input)

		} while(choix != 0);
		scan.close();
		System.exit(0);


	}
	
	


	private static void lectureFichier(String filePathA) throws FileNotFoundException {
		File leFichier = new File(filePathA);
		try (FileInputStream fis = new FileInputStream(leFichier);
				BufferedInputStream bis = new BufferedInputStream(fis);
				Scanner scan = new Scanner(bis)) {
			scan.useDelimiter("[^A-Za-z]+");
			// On peut alors parcourir les lignes
			while(scan.hasNext()) { //Tant qu'il y a une autre ligne, on recupere chaque parametre et on construit l'objet
				String nom = scan.next() ;
				String prenom = scan.next() ;

				//appel constructeur et creer l'objet
				Personne laPersonne = new Personne(nom, prenom);

				getTableauPersonnes().add(laPersonne);
			};
			scan.close();

		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'a pas ete trouve!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}



	public static int MenuPrincipal() throws IOException {
		System.out.println("\n[MENU]");
		System.out.println("1. Generer des noms.");
		System.out.println("2. Afficher la banque de nom.");
		System.out.println("3. Ajouter un nom dans la liste.");
		System.out.println("4. Remettre le generateur a son etat initial.");
		System.out.println("0. Quitter.");
		System.out.println("Faites votre choix et appuyer sur ENTER");
		return getInt();
	}


	public static void afficherBanqueDeNoms(){
		System.out.println("Voici le tableau de personnes: ");

		for(Personne str: getTableauPersonnes()) {
			System.out.println(str.getNom() + " "+ str.getPrenom());

		}

	}


	public static void initialiser() {
		getTableauPersonnes().remove(getTableauPersonnes());
	}


	public static int getInt() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return Integer.parseInt(br.readLine());	
	}




	public static ArrayList<Personne> getTableauPersonnes() {
		return tableauPersonnes;
	}




	public static void setTableauPersonnes(ArrayList<Personne> tableauPersonnes) {
		Main.tableauPersonnes = tableauPersonnes;
	}


}
