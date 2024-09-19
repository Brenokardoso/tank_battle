/***********************************************************************
*Nome: UGEVector2D
*Descri��o: Classe responsavel pela representa��o de um vetor2D
*Autor: Silvano Malfatti
*Data: 17/02/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

public class CVetor2D 
{
	/*Atributos da classe*/
	private double dX;
	private double dY;
	
	/***********************************************************
	*Nome: CVetor2D()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CVetor2D()
	{
		dX = 0.0;
		dY = 0.0;
	}
	
	/***********************************************************
	*Nome: CVetor2D()
	*Descri��o: Construtor da classe
	*Parametros: double, double
	*Retorno: Nenhum
	************************************************************/
	public CVetor2D(double pX, double pY)
	{
		dX = pX;
		dY = pY;
	}
	
	/***********************************************************
	*Nome: getX()
	*Descri��o: retorna o valor de x
	*Parametros: Nenhum
	*Retorno: double
	************************************************************/
	public double getX()
	{
		return dX;
	}
	
	/***********************************************************
	*Nome: getY()
	*Descri��o: retorna o valor de y
	*Parametros: Nenhum
	*Retorno: double
	************************************************************/
	public double getY()
	{
		return dY;
	}
	
	/***********************************************************
	*Nome: setX()
	*Descri��o: configura o valor de x
	*Parametros: double
	*Retorno: Nenhum
	************************************************************/
	public void setX(double pX)
	{
		dX = pX;
	}
	
	/***********************************************************
	*Nome: setY()
	*Descri��o: configura o valor de y
	*Parametros: double
	*Retorno: Nenhum
	************************************************************/
	public void setY(double pY)
	{
		dY = pY;
	}
	
	/***********************************************************
	*Nome: setXY()
	*Descri��o: configura o valor de x e y
	*Parametros: double, double
	*Retorno: Nenhum
	************************************************************/
	public void setXY(double pX, double pY)
	{
		dX = pX;
		dY = pY;
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		
	}
}
