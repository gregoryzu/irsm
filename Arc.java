/***
* Cette classe représente les arcs entre 
* les composants à la ligne indiquée et la colonne indiquée
* (i,j) = 1 si i est connecté à j, 0 sinon
*/
public class Arc 
{
	private int ligne;
	private int colonne;
	private int valeur;

	
	public Arc (int ligne, int colonne, int valeur)
	{
		this.ligne= ligne;
		this.colonne=colonne;
		this.valeur = valeur;
	}

	/***** 
	* Getters et setters 
	*/
	public int getValeur() 
{
		return valeur;
	}
	
	public void setValeur(int valeur) 
{
		this.valeur = valeur;
	}
	
	public int getLigne() 
	{
		return ligne;
	}
	
	public void setLigne(int ligne) 
	{
		this.ligne = ligne;
	}
	
	public int getColonne() 
	{
		return colonne;
	}
	
	public void setColonne(int colonne) 
	{
		this.colonne = colonne;
	}
	
	/***** 
	* aide à l'impression 
	*/
	public String toString()
	{
		return String.valueOf(this.valeur);
	}
		
	public void print()
	{
		System.out.print("."+(this.ligne+1)+"."+(this.colonne+1));
	}
}
