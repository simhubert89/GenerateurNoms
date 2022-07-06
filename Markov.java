package generateurNoms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

////////////////////////////////////////////////////////////////////////////////////////
//Markov.java	@authors wuddy
//
//Objet qui inclue les frequences et les probabilites  dans le calcul de generation de noms. 
////////////////////////////////////////////////////////////////////////////////////////
public class Markov {
	private int min;
	private int max;
	private int ordre;
	private int noNoms;
	private HashMap<String, HashMap<String,Double>> hashMap; 	// Hashmap = list of keys && values
																// a list of all possible states
	private ArrayList<String> nomExistant;
	
	
	public Markov(int min, int max, int ordre, int noNoms)
	{
		this.ordre = ordre;
		this.min = min + this.ordre*2;
		this.max = max + this.ordre*2;
		this.noNoms = noNoms;
		hashMap = new HashMap<String, HashMap<String, Double>> ();
		nomExistant = new ArrayList<String>();
		
	}
	
	public void setFileToSelectFrom() throws IOException {
		File file = new File("banqueDeNoms.txt");
		try (BufferedReader lecteur = new BufferedReader(new FileReader(file))) {
			String nom;
			while ((nom = lecteur.readLine() )!= null) {
				nomExistant.add(nom);
				generateurSeq(nom);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * cette methode va set les probabilites pour chaque prochain character selon une sequence desire.
	 */
	public void generateurSeq(String nom) { // generateur de sequences
		String nouveauNom = "_".repeat(ordre) + nom + "_".repeat(ordre);
		nouveauNom = nouveauNom.trim();
		
		for(int i=0; i< (nouveauNom.length()-ordre); i++) {
			HashMap<String, Double> lettres = new HashMap<String, Double>();
			
			String seq = nouveauNom.substring(i,i+ordre);
			String prediction = nouveauNom.substring(i+ordre, i+ordre+1);
			
			if(!hashMap.containsKey(seq))
			{
				lettres.put(prediction, 1.0);
				lettres.put("total", 1.0);
				hashMap.put(seq, lettres);
			}
			else {
				lettres = hashMap.get(seq);
				
				if(lettres.containsKey(prediction)) {
					lettres.put("total", lettres.get("total")+1.0);
					lettres.put(prediction, lettres.get(prediction)+1.0);
				}
				else {
					lettres.put("total", lettres.get("total")+1.0);
					lettres.put(prediction,1.0);
				}
			}
		}
	}
	
	
	public void setProbabilites() {
		for(String sequence: hashMap.keySet()) {
			for(String prochain: hashMap.get(sequence).keySet()) {
				if(!prochain.equals("total")) {
					double compteur = hashMap.get(sequence).get(prochain);
					double total = hashMap.get(sequence).get("total");
					double probabilite = compteur/total;
					hashMap.get(sequence).put(prochain, probabilite);
				}
			}

			hashMap.get(sequence).remove("total");
		}
	}
	
	public ArrayList<String> creeNoms() {
		ArrayList<String> nouveauNoms = new ArrayList<String>();
		String trigger = "_".repeat(this.ordre);
		int vraiMin = this.min - this.ordre*2;
		int vraiMax = this.max - this.ordre*2;
		
		for(int i=0; i<noNoms;)
		{
			String nouveauNom = faireNom(trigger).replace("_", "");
			if(nouveauNom.length()>= vraiMin && nouveauNom.length() <= vraiMax && !(nomExistant.contains(nouveauNom))) {
				nouveauNoms.add(nouveauNom);
				i++;
			}
		}
		return nouveauNoms;
	}
	
	public String faireNom(String trigger) {
		String nomTemp = trigger;
		int compteur2=0;
		while(nomTemp.length()<this.min||(!(nomTemp.substring(nomTemp.length()-this.ordre, nomTemp.length()).equals(trigger)))) {
			
			String debut = nomTemp.substring(compteur2, compteur2+this.ordre);
			
			double randNo = Math.random();
			double cumulatif = 0;
			
			if(hashMap.get(debut) != null) {
				HashMap<String,Double> prochainChars = hashMap.get(debut); // hashmap des caracteres possibles et des probabilites
				
				for(String prochain: prochainChars.keySet()) {
					cumulatif += prochainChars.get(prochain);
					
					if(randNo <= cumulatif) {
						if(prochain.equals("_")&& nomTemp.length()< (this.min-this.ordre)) {
							nomTemp+="";
						}
						else {
							nomTemp += prochain;
							compteur2++;
							break;
						}
					}
				}

			}
			else {
				nomTemp += "__";
				return nomTemp;
			}
		}
		
		
		return nomTemp;
		
	}
	// THANKS FOR WATCHING <3 big love
	
}
