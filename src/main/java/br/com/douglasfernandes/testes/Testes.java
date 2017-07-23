package br.com.douglasfernandes.testes;

public class Testes {
	public static void main(String[] args) {
		try{
			String frase = null;
			
			if(!"".equals(frase))
				System.out.println("é diferente");
			else
				System.out.println("é igual");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
