/***********************************************************************
*Nome: CLog
*Descri��o: Classe respons�vel pelo armazenamento de informa��es do motor
*Autor: Silvano Malfatti
*Data: 27/05/08
**************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.io.FileWriter;
import javax.swing.JOptionPane;

public class CLog 
{
	/***********************************************************
	*Nome: CLog()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CLog()
	{
		
	}
	
	/***********************************************************
	*Nome: inicializaLog()
	*Descri��o: Cria o cabe�alho do arquivo de LOG
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public static void inicializaLog()
	{
		try
		{
			FileWriter file = new FileWriter("LOG.doc");
			file.write("*************************************************\n");
			file.write("                   LOG                        \n");
			file.write("*************************************************\n\n");
			file.flush();
			file.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"LOG - Imposs�vel abrir o arquivo de LOG");
		}
	}
	
	/***********************************************************
	*Nome: escreveLog()
	*Descri��o: Escreve uma informa��o no LOG
	*Parametros: String
	*Retorno: Nenhum
	************************************************************/
	public static void escreveLog(String pString)
	{
		try
		{
			FileWriter arquivo = new FileWriter("LOG.doc",true);
			arquivo.write(pString);
			arquivo.flush();
			arquivo.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"LOG - Imposs�vel escrever no arquivo de LOG");
		}
	}
	
	/***********************************************************
	*Nome:liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		
	}
}
