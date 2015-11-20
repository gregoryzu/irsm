import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ChoixFichier
{
	private boolean DEBUG 			= true;
	private String cheminFichier 	= "";
	String[] composantsTech 		= null;
	String[] matriceConnectivite	= null;
	int[] connectivite 				= null;
	int[] techs 					= null;
	
	public ChoixFichier() 
	{
	    try
	    {  
UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    }
	    	catch (Exception e) 
	    { 
	    		e.printStackTrace();
	    }
		JFileChooser chooser = new JFileChooser();
FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier txt seulement!","txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Ouvrir le fichier txt matrice");
		chooser.setApproveButtonText("Ouvrir"); 
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{	
			if (DEBUG)
		  		System.out.println(chooser.getSelectedFile()
.getAbsolutePath()); 
			cheminFichier = chooser.getSelectedFile().getAbsolutePath();
			lireLignes();
		}
		else
		{
			System.exit(0);
		}
	}
	
	private void lireLignes()
	{
		List<String> lignesMatrice  = new ArrayList<String>();
		String ligneComposantsTechs = null;
		int nbS =0;
		try 
		{
			FileReader fr 		= new FileReader(cheminFichier);
			BufferedReader br 	=  new BufferedReader(fr);
			String ligne			= "";
			
			// je lis les lignes et supprime tout éventuel espace avant
			// de les stoquer dans ligne
			// si ligne est vide, c'est que je suis tombé sur la ligne vide     qui sépare 
			// la matrice des composants techniques.
			// je lis donc la ligne suivante et la stocke dans le String ligneComposantsTechs.
			while (( ligne = br.readLine()) != null)
			{
				ligne=ligne.trim();
				if (ligne.isEmpty())
				{
					ligneComposantsTechs = br.readLine();
				}
				else
				{
					nbS++;
					lignesMatrice.add(ligne);
				}
				
			}
			br.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		// j'ai mon ArrayListe de lignes de la matrice et
		// le string de composants Techs; Je veux obtenir un 
		// tableau d'int pour chaque (les lignes comportent des numéros 
		// séparés par des virgules.
		// 1. j'accole les lignes dans une même variable pour obtenir un 
		// String plat de l'ArrayList
		String temp;
		StringBuilder bd = new StringBuilder();
		int i=0;
		for (String ligne:lignesMatrice)
		{
			i++;
			if(i!=nbS)
			{
				bd.append(ligne+",");
			}
			else
			{
				bd.append(ligne);
			}
			
		}
		temp=bd.toString();
		matriceConnectivite = temp.split(",");
		if (DEBUG)
		{
			System.out.println("\n\n temp: " + temp);
			for  (int z = 1; z <= nbS; z++)
			{
				for(int j = 1; j<=nbS;j++)
				{
					System.out.print(matriceConnectivite[(j*z)-1]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
		
		//2. plus simple pour la ligne de composants tech
		connectivite = new int[matriceConnectivite.length];
		for(int j =0;j <connectivite.length;j++)
		{
			connectivite[j] = Integer.parseInt(matriceConnectivite[j]);
		}
		
		composantsTech =ligneComposantsTechs.split(",");
		if (DEBUG)
		{
			for  (int z = 0; z < nbS; z++)
			{
					System.out.print(composantsTech[z]);
			}
			System.out.println("\n\n");
		}
		techs = new int[composantsTech.length];
		for(int j =0;j <techs.length;j++)
		{
			techs[j] = Integer.valueOf(composantsTech[j]);
		}
	}

	public int[] getConnectivite()
	{
		if(DEBUG)
		{
			int nbS = (int) Math.sqrt((double)connectivite.length);
			System.out.print("\n getconnectivite \n");
			for  (int z = 1; z <= nbS; z++)
			{
				for(int j = 1; j<=nbS;j++)
				{
					System.out.print(connectivite[(j*z)-1]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
		return connectivite;
	}
	
	public int[] getComposantsTechs()
	{
		if(DEBUG)
		{
			int nbS = (int) Math.sqrt((double)connectivite.length);
			System.out.print("\n getTechs \n");
			for  (int z = 0; z < nbS; z++)
			{
				System.out.print(techs[z]);
			}
			System.out.println("");
		}
		return techs;
	}
}
