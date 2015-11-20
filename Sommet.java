/****
* Classe repr�sentant les composants
* Leur identifiant est l'indice qu'ils ont
* dans la matrice de connectivit�
*/

public class Sommet 
{
	private int numero;
	private String nom="";
	private boolean fin=false,debut=false;
	// doit rester protected, je n'ai pas cr�� de setter et getter
	// pour isTech et on y acc�de depuis Matrices.
	boolean isTech = false;
	static private Sommet listeSommets[];
	
	/****
	* constructeurs
	*/
	public Sommet(int numero,String nom)
	{
		this.numero 	= numero;
		this.nom=nom;
	}
	
	public Sommet(int numero)
	{
		this.numero 	= numero;
		this.nom 		= String.valueOf(numero);
	}
	
	public Sommet(String nom)
	{
		this.nom		= nom;
	}
	
	// fonction statique que j'utiliserai sans instancier 
	// la classe Sommet
	// cr�er et retourne la liste des sommets en indiquant
	// s'ils sont des composants techniques ou non
	static public Sommet[] setSommets(int nbS, int[] composantsTech)
	{
		listeSommets 	= new Sommet[nbS];
		for (int i =0;i<nbS;i++)
		{
			//i+1 pour les noms commencent � 1 jusqu'�..nbS
			listeSommets[i]	= new Sommet(i+1);
			// on d�finit � true si un composant est un composant technique
			// composantsTech contient une liste de nbS �l�ments
			// chacun repr�sente un sommet. Le sommet indiqu� � l'indice i 
			// vaut 1 quand c'est un composant technique. 0 sinon
			if (composantsTech[i]==1)
			{
				listeSommets[i].setIsTechnique(true);
			}
			else
			{
				listeSommets[i].setIsTechnique(false);
			}
		}
		return listeSommets;
	}
	
	public void setIsTechnique(boolean b)
	{
		this.isTech = b;
	}
	
	/***
	* Est-ce qu'un composant ouvre ou
	* ferme un chemin technique
	*/
	public void setLeaf(boolean is)
	{
		this.fin	= is;
	}
	
	public boolean isLeaf()
	{
		return fin;
	}
	
	public void setRoot(boolean is)
	{
		this.debut	= is;
	}
	
	public boolean isRoot()
	{
		return debut;
	}
	
	/***
	* quelques getters ,setters
	*/
	public int getNumero() 
	{
		return numero;
	}
	
	public void setNumero(int numero) 
	{
		this.numero = numero;
	}
	
	public String toString()
	{
		//System.out.print(String.valueOf(this.valeur));
		return String.valueOf(this.nom);
	}
	
}
