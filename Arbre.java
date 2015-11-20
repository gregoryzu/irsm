/***
 * Je ne suis pas parti de rien pour creer la classe Arbre.
 * J'utilise la classe JTree qui sert normalement pour la 
 * cr�ation d'interface d'affichage d'arbres (des syst�mes de fichiers
 * typiquement...)
 * Cette classe sert � la fois � cr�er la structure d'un arbre et 
 * contient l'essentiel du code pour l'interface graphique.
 * L'astuce repose sur un HashMap de noeuds auquel j'ajoute les composants 
 * en les identifiant par leur indice dans la matrice. 
 * Je peux ensuite r�cup�rer le composant par son indice et lui ajouter un 
 * enfant � chaque fois que j'en trouve un. 
 * Sinon je peux ajouter un composant racine � "top", l'entr�e du graphe.
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

@SuppressWarnings("serial")
public class Arbre 	extends JPanel 
					implements TreeSelectionListener
{
	private boolean DEBUG = false;
	private JEditorPane htmlPane;
	private JTree tree;
	private URL infoURL;
	private DefaultMutableTreeNode top = null;
	// c'est toute l'astuce ici dans ce Hashmap, avec lequel je pourrai retrouver 
	// un sommet par son nom qui correspond � son indice dans la matrice.
	private HashMap<Integer, DefaultMutableTreeNode> nodes;
	//Optionnel, style des lignes.  Valeurs possibles:
    //"Angled" (default), "Horizontal", et "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    public Arbre() 
    {
        super(new GridLayout(1,0));
		// cr�ation de la liste de noeuds (comprendre sommets ou 
  composants)
        // il est identifi� par la cl� Integer, qui correspond � l'indice
        // du composant dans la matrice de connectivit�
        this.nodes = new HashMap<Integer, DefaultMutableTreeNode>();
        //entr�e du graphe.
        top =  new DefaultMutableTreeNode("Entr�e du graphe");
    }// fin constructeur
    
    @Override
	/** Requis pour l'interface TreeSelectionListener. */
    public void valueChanged(TreeSelectionEvent e) 
	{
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        if (node == null) return;
        // ne sert pas ici, mais je laisse pour �ventuellement plus tard
        // Object nodeInfo = node.getUserObject();
	}
    
    public void addNode(Integer identifier) 
    {
        this.addNode(identifier, null);
    }

    public void addNode(Integer identifier, Integer parent) 
    {
    	// le Jtree fonctionne ainsi : 
		// chaque noeud peut se voir ajouter des enfants. C'est ce que je fais ici
		// Je trouve l'enfant d'un noeud depuis la classe matrices et je l'ajoute en
    	// tant qu'enfant au noeud en cours.
		// 1.J'ajoute donc le noeud dans la liste HashMap avec son identifiant (son indice, un Integer)
    	// 2.Je l'ajoute en tant qu'enfant de son parent
		// 3.Les noeuds sans parents indiqu�s, sont des racines. Ils sont les enfants de top. 
		// <*!!!!!!top est l'entr�e du graphe. Il faudra que je lance dans le graphe une fois tout fini 
    	// seulement*!!!!> 
    	
    	// pour l'affichage, comme on commence l'index des matrices
    	// de composants � 1 dans la vraie vie...
    	identifier = identifier+1;
    	
    	//1.
		DefaultMutableTreeNode node = new 
		DefaultMutableTreeNode(String.valueOf(identifier));
        nodes.put(identifier, node);
        //2.
        if (parent != null) 
        {
        	parent = parent +1;
        	System.out.println("add node :  " + (identifier+1) +" parent :            
          " + (parent+1) );
          nodes.get(parent).add(node);
        }
        //3.
        else
        {
        	// si parent est null, on a affaire � une racine 
        	System.out.println("add node :  " + (identifier+1) +" � top " );
        	top.add(node);
        }
    }
       
    private void initInfo() 
    {
        String s = "description_appli_java.htm";
        infoURL = getClass().getResource(s);
        if (infoURL == null) 
        {
            System.err.println("Couldn't open help file: " + s);
        } 
        else if (DEBUG) 
        {
            System.out.println("Help URL is " + infoURL);
        }

        displayURL(infoURL);
    }
    
    private void displayURL(URL url) 
    {
        try 
        {
            if (url != null) 
            {
                htmlPane.setPage(url);
            } 
            else 
            { //null url
            	htmlPane.setText("File Not Found");
                if (DEBUG) 
                {
                    System.out.println("Attempted to display a null     
                    URL.");
                }
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }// fin dispayUrl
    /***
     * je cr�e r�ellement l'arbre ici. Je ne peux pas le faire avant,
     * sans quoi top n'a pas tous ses enfants.
     */
    // je dois le faire apr�s avoir d�fini les nodes.
    public void afficheArbre()
    {
    	//Cr�e un arbre qui autorise une s�lection � la fois.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //�coute le changement de s�lection.
        tree.addTreeSelectionListener(this);
        if (playWithLineStyle) 
        {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }
        //Cr�e le scroll pane et y ajoute l'arbre.
        JScrollPane treeView = new JScrollPane(tree);
        //Cr�e le pane d'affichage HTML.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initInfo();
        JScrollPane htmlView = new JScrollPane(htmlPane);
        //Ajoute le scroll pane au split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);
        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));
        //Ajoute le split pane � ce panel.
        add(splitPane);
    }
}
