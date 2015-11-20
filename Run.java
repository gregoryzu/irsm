import javax.swing.JFrame;
public class Run 
{
	// matrice de connectivit� du produit.
	static private boolean DEBUG 		= false;
	static private int[] connectivite 	= null; 
	// un composant applicatif commence ou ferme un chemin technique
	// j'indique les sommets qui sont un composant technique. Ce qui n'est pas 
	// technique, est applicatif
	static private int[] composantsTech = null;
	
	// cr�ation de la matrice de connectivit�
	static private Matrices T0			= null; 			
	static private Arbre arbre 			= null; 
	
	public static void main(String[] args) 
	{
		// je lance la fen�tre de s�lection de fichier contenant la matrice 
		// et les composants techniques
		ChoixFichier fc 	= new ChoixFichier();
		// J'ai regroup� dans cette m�me classe les fonctions pour 
		// lire le fichier texte et mettre les informations 
		// dans des tableaux d'int.
		connectivite 		= fc.getConnectivite();
		composantsTech 		= fc.getComposantsTechs();
		// Je peux maintenant cr�er la matrice de connectivit�
		T0 = new Matrices(connectivite, composantsTech);
		if (DEBUG)
			T0.print();
		// obtenir la liste des composants qui commencent un chemin
		// ce sont ceux dont les colonnes sont toutes � 0
		// Ces composants ne sont que des racines ou que des feuilles,
		// pas les deux 
		T0.setRacinesAbsolues();
		T0.setFeuillesAbsolues();
		// on ajoute ces informations sur les sommets de la matrice
		// et je compl�te en d�finissant aussi les composants qui sont � la 
		// fois racine et feuilles.
		T0.setSommetsAsRootOrLeaf();
		if (DEBUG)
			T0.printRootsAndLeafs();
		// d�clenche la recherche des chemins technique qui seront regroup�s en un 
		// arbre. (JTree) L'arbre n'est pas r�ellement cr�� ici, seuls les liens de 
		// entre les noeuds sont pris en compte, et la future arborescence de l'arbre
		// pr�par�e.
		arbre 				= T0.creerCheminsTech();
		//Planifie une t�che pour la file d'ex�cution event dispatch:
        //Cr�� et affiche l'interface graphique.
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
     * Cr�e l'interface graphique et l'affiche.
     */
    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("Chemins techniques");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // je cr�e r�ellement l'arbre ici, une fois que je suis 
        // s�re qu'on a trouv� tous les enfants de tous les noeuds.
        arbre.afficheArbre();
        //Ajoute le contenu � la fen�tre
        frame.add(arbre);

        //Affiche la fen�tre
        frame.pack();
        frame.setVisible(true);
    }
}
