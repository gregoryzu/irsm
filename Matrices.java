import java.util.List;
import java.util.ArrayList;

public class Matrices 
{
	private boolean DEBUG = false;
	// S le nombre de sommets (composants)
	private int nbS=0;
	private Arc [][] matriceConnectivite;
	private int[] composantsTech;
	// Ces deux listes ne contiennent que les sommets composants applicatifs qui ne sont
	// que des racines ou que des feuilles.
	private List<Integer> racines = new ArrayList<Integer>();
	private List<Integer> feuilles = new ArrayList<Integer>();
	// Ces listes contiennent les sommets qui sont que racines ou feuilles et ceux qui sont 
	// � la fois racine et feuille
	private List<Integer> toutesRacines = new ArrayList<Integer>();
	private List<Integer> toutesFeuilles = new ArrayList<Integer>();
	private int connectivite[];
	// Tableau de sommets, chaque sommet est identifi�.
	private Sommet listeSommets[];
	private Arbre arbre = new Arbre();
	
	//(nbS,composantsTech,connectivite )
	public Matrices(int[] connectivite,int[] composantsTech)
	{
		this.connectivite = connectivite;
		this.composantsTech = composantsTech;
		// pour connaitre le nombre de sommets, �tant donn� qu'on a une matrice 
		// carr�e il suffit de prendre la racine carr�e du nombre d'�l�ments 
		// dans la matrice 
		nbS = (int)Math.sqrt(connectivite.length);
		// La matrice est un tableau 2D d'arcs
		matriceConnectivite = new Arc[nbS][nbS];
		// on d�finit la matrice � partir du tableau
		// 1D connectivite
		this.setMatrice();
	}
	
	// d�finit la matrice de connectivit�
	// qui sera un tableau d'arcs 2D
	// Puis cr�er la liste des sommets : un tableau 1D
	public void setMatrice()
	{
		int z = 0;
		//i = ligne, j= colonnes
		for (int i=0;i<nbS; i++)
		{
			for (int j =0;j<nbS;j++)
			{
				// un arc se cr�e ainsi : 
				// new Arc(int ligne, int colonne, int valeur)
				// valeur est � 1 s�il y a connexion du composant 
				// � la ligne i vers celui de la colonne j
				Arc arc = new Arc(i,j,connectivite[z]);
				matriceConnectivite[i][j]=arc;
				z++;
				System.out.print(" "+matriceConnectivite[i][j]+" ");
				if (DEBUG)
				{
					System.out.print(" "+connectivite[z]+" "+
										" "+arc.toString()+" "+
										" "+
										matriceConnectivite[i][j].getLigne()+
										","+
										matriceConnectivite[i][j].getColonne()
);
				}
			}
			System.out.println("");
		}
		// on cr�e la liste des sommets.(liste des composants)
		listeSommets = Sommet.setSommets(nbS, composantsTech);
	}
	
	// d�finit la valeur de la matrice � la ligne i,
	// colonne j
	public void setIt(int i,int j,Integer valeur)
	{
		if ((i>nbS)||(j>nbS))
		{
			System.out.println("erreur, indice trop elev� et sup�rieur au nombre de arcs");
		}
		else
		{
			matriceConnectivite[i][j] = new Arc(i,j,valeur);
		}
	}
	
	public Arc getIt(int i,int j)
	{
		Arc valeur=null;
		if ((i>nbS)||(j>nbS))
		{
			System.out.println("erreur, indice trop elev� et sup�rieur au nombre de sommets");
			System.exit(0);
		}
		else
		{
			valeur = matriceConnectivite[i][j];
		}
		return valeur;
	}
	
	public void print()
	{
		// lignes
		for (int i = 0; i< this.nbS; i++)
		{
			{
				// colonnes
				for (int j = 0; j < this.nbS;j++)
				{
					System.out.print(this.getIt(i, j) + ", ");
				}
			}
			System.out.println(", ");
		}
		System.out.println("fin matrice");
	}
	
	public void setRacinesAbsolues()
	{
		
		for (Integer col = 0; col< nbS; col++)
		{
			int valeurColonne 	= 0;
			// quand dans la matrice un composant a sa colonne compl�tement
			// � 0 , rien ne se connecte � lui, il est une racine absolue.
			// quand un composant � la ligne i est connect� au composant
			// � la ligne j , la valeur de l'arc i,j est 1.
			// j'additionne tous les arcs (i,j), j � valeur constante indiqu�e par la var. col
			// et stoque le r�sultat dans valeurColonne
			// si valeurColonne = 0, j n'a aucun pr�d�cesseur. Il est une racine. 
			// Seuls les composants applicatifs peuvent �tre racines.(sinon il y a un probl�me de 
			// d�finition des classes par l'utilisateur.
			for(Integer ligne= 0; ligne<nbS; ligne++)
			{
				valeurColonne 		= valeurColonne + this.getIt(ligne, col).getValeur();
			}
			if (valeurColonne == 0)
			{
				// aucun composant ne s'y connecte
				// racines est une ArrayList 
				racines.add(col);
				if (DEBUG)
					System.out.print(col+1 + ".");
			}
		}
		if (DEBUG)
			System.out.println(" - fin racine");
	}
	
	public void setFeuillesAbsolues()
	{
		
		for (Integer ligne = 0; ligne< nbS; ligne++)
		{
			// m�me principe que pour getRacinesAbsolues.
			// quand dans la matrice un composant a sa ligne compl�tement
			// � 0 , il ne se connecte � rien, il est une feuille absolue
			int valeurLigne =0;
			for(Integer col = 0; col<nbS; col++)
			{
				valeurLigne = valeurLigne + this.getIt(ligne, col).getValeur();
			}
			if (valeurLigne ==0)
			{
				// ne se connecte � aucun composant
				feuilles.add(ligne);
				if (DEBUG)
					System.out.print(ligne+1 + ".");
			}
		}
		if (DEBUG)
			System.out.println(" - fin feuilles");
	}
	
	/*** d�finit quels sommets sont racines ou feuilles ou
	* les deux � la fois.
	*/
	public void setSommetsAsRootOrLeaf()
	{
		for (int i = 0; i< listeSommets.length; i++)
		{
			//seuls les composants applicatifs commencent un chemin technique
			if (!listeSommets[i].isTech)
			{
				for (int j = 0; j< racines.size(); j++)
				{
					// i indique l'indice du sommet listeSommets[i]
					// la liste racines contient directement la liste des sommets racines
					if (i == racines.get(j))
					{
						listeSommets[i].setRoot(true);
						// j'ajoute ce sommet � la liste de tous les sommets racines (qui ne sont pas forc�ment
						// que racine. Cette liste contient les sommets qui sont que racines et ceux qui sont 
						// � la fois racine et feuille
						toutesRacines.add(i);
					}
				}
				for (int j = 0; j< feuilles.size(); j++)
				{
					// i indique l'indice du sommet listeSommets[i]
					// la liste racines contient directement la liste des sommets racines
					if (i == feuilles.get(j))
					{
						listeSommets[i].setLeaf(true);
						toutesFeuilles.add(i);
					}
				}
				// enfin, si un sommet d'un composant applicatif n'a pas sa ligne tout � 0
				// ou sa colonne � tout � z�ro, il termine et commence un chemin. Il est 
				// root et leaf
				if (!listeSommets[i].isLeaf() && !listeSommets[i].isRoot())
				{
					listeSommets[i].setRoot(true);
					listeSommets[i].setLeaf(true);
					toutesRacines.add(i);
					toutesFeuilles.add(i);
				}
				
			}
		}
	}
	
	public void printRootsAndLeafs()
	{
		
		for (int i = 0; i< listeSommets.length; i++)
		{
			if ( listeSommets[i].isRoot())
			{
				System.out.println(  "*--> Sommet "+(i+1) +" est une racine d'un chemin");
			}
			if (listeSommets[i].isLeaf())
			{
				System.out.println( "<-- Sommet "+(i+1) +" est une feuille d'un chemin");
			}
		}
		/*for (int i = 0; i< toutesRacines.size();i++)
		{
			System.out.println(  "*--> Sommet "+ toutesRacines.get(i) +" est une racine d'un chemin");
		}
		for (int i = 0; i< toutesFeuilles.size();i++)
		{
			System.out.println(  "<-- Sommet "+toutesFeuilles.get(i) +" est une feuille d'un chemin");
		}*/	
	}
	
	public List<Integer> getToutesRacines()
	{
		return toutesRacines;
	}
	
	public List<Integer> getToutesFeuilles()
	{
		return toutesFeuilles;
	}
	
	public Arbre creerCheminsTech()
	{
		// je parcours la liste des racines
		// pour chacune (sommetEnCours) je l'ajoute � la racine
		// de l'arbre et je recherche ses suivants. 
		// la fonction r�cursive getSuivants, cherche les enfants des 
		// suivants.
		for (Integer i = 0 ; i < toutesRacines.size();i++)
		{
			// la m�thode addNode prend en param�tres:
			//- l'identifiant du node
			//- l'identifiant de son parent. si null: pas de parent.
			int sommetRacineEnCours = toutesRacines.get(i);
			arbre.addNode(sommetRacineEnCours);
			getSuivants(sommetRacineEnCours);
		}
		return arbre;
	}
	
	/***
	 * fonction r�cursive de suivants
	 * @param ligne
	 * @return void
	 */
	public void getSuivants(int ligne)
	{
		for (int col = 0; col < nbS; col++)
		{
			if (this.getIt(ligne, col).getValeur()==1)
			{
				// ici , s�il y a connectivit� entre i et col , le sommet i, se connecte au sommet
				// col et est donc son parent.
				//arbre.addNode((col+1), (ligne+1));
				arbre.addNode(col, ligne);
				// si le suivant est une application, c'est la fin d'un chemin 
				// technique, je dois donc stopper (exemple; QR code dans BitPool, le chemin
				// 2-5-6-1 doit disparaitre pour avoir deux chemins : 2-5 et 5-6-1
				// le corolaire, si c'est un chemin technique, je continue � chercher les suivants, sinon, non
				if (listeSommets[col].isTech)
				{
					getSuivants(col);
				}
			}
		}
	}
}
