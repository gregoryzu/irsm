import javax.swing.JFrame;
public class Run 
{
	// matrice de connectivité du produit.
	static private boolean DEBUG 		= false;
	static private int[] connectivite 	= null; 
	// un composant applicatif commence ou ferme un chemin technique
	// j'indique les sommets qui sont un composant technique. Ce qui n'est pas 
	// technique, est applicatif
	static private int[] composantsTech = null;
	
	// création de la matrice de connectivité
	static private Matrices T0			= null; 			
	static private Arbre arbre 			= null; 
	
	public static void main(String[] args) 
	{
		// je lance la fenêtre de sélection de fichier contenant la matrice 
		// et les composants techniques
		ChoixFichier fc 	= new ChoixFichier();
		// J'ai regroupé dans cette même classe les fonctions pour 
		// lire le fichier texte et mettre les informations 
		// dans des tableaux d'int.
		connectivite 		= fc.getConnectivite();
		composantsTech 		= fc.getComposantsTechs();
		// Je peux maintenant créer la matrice de connectivité
		T0 = new Matrices(connectivite, composantsTech);
		if (DEBUG)
			T0.print();
		// obtenir la liste des composants qui commencent un chemin
		// ce sont ceux dont les colonnes sont toutes à 0
		// Ces composants ne sont que des racines ou que des feuilles,
		// pas les deux 
		T0.setRacinesAbsolues();
		T0.setFeuillesAbsolues();
		// on ajoute ces informations sur les sommets de la matrice
		// et je complète en définissant aussi les composants qui sont à la 
		// fois racine et feuilles.
		T0.setSommetsAsRootOrLeaf();
		if (DEBUG)
			T0.printRootsAndLeafs();
		// déclenche la recherche des chemins technique qui seront regroupés en un 
		// arbre. (JTree) L'arbre n'est pas réellement créé ici, seuls les liens de 
		// entre les noeuds sont pris en compte, et la future arborescence de l'arbre
		// préparée.
		arbre 				= T0.creerCheminsTech();
		//Planifie une tâche pour la file d'exécution event dispatch:
        //Créé et affiche l'interface graphique.
        javax.swing.SwingUtilities.invokeLater
		(
       		new Runnable() 
		   {
				public void run() 
				{
					createAndShowGUI();
				}
		    }
        );
	}
	
	static boolean fromIntToBool(int val)
	{
		return  (val!=0);
	}
	 /**
     * Crée l'interface graphique et l'affiche.
     */
    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("Chemins techniques");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // je crée réellement l'arbre ici, une fois que je suis 
        // sûre qu'on a trouvé tous les enfants de tous les noeuds.
        arbre.afficheArbre();
        //Ajoute le contenu à la fenêtre
        frame.add(arbre);

        //Affiche la fenêtre
        frame.pack();
        frame.setVisible(true);
    }
}
