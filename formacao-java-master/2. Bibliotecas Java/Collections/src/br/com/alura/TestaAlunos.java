package br.com.alura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TestaAlunos {

	public static void main(String[] args) {
		
		//Testando a Collection Set que tem esse
		//nome Collection pois � filha de Collection
		//n�o de List
		
		//Sendo ainda mais generico poderia ser
		//utilizado a interface
		
		//Forma proposta
		//Set<String> alunos = new HashSet<>();
		
		//Forma mais generica
		Collection<String> alunos = new HashSet<>();
		alunos.add("Rodrigo Turini");
		alunos.add("Alberto Souza");
		alunos.add("Nico Steppat");
		alunos.add("Sergio Lopes");
		alunos.add("Renan Saggio");
		alunos.add("Mauricio Aniche");
		alunos.add("Mauricio Aniche");
		
		//Algumas Caracteristicas
		//N�o h� garantias da ordem de saida dos elementos
		//System.out.println(alunos.)
		//Nenhum dos Sets aceita elementos repetidos
		
		//Vantagens
		//Velocidade e performance em caso de uso
		//dessa cole��o(Set) em particular, no uso
		//do contains(), m�todo que toda cole��o tem por
		//conta da m�e Collection.
		//Muito mais performatico do que a List
		//Performance em busca
		System.out.println(alunos.contains("Paulo Silveira"));
		//Performance em remo��o
		System.out.println(alunos.contains("Sergio Lopes"));;
		//Se h� um conjunto muito grande de elementos
		//entre 10.000 a mais do que isso,
		//e faz-se buscas muito frequentes � melhor
		//utilizar o Set por conta da sua incrivel 
		//performance
		
		//Imprimindo
		//Usando Lambda
		/*alunos.forEach(string -> {
			System.out.println(string);
		});*/
		
		//Foreach
		for (String s : alunos) {
			System.out.println(s);
		}
		
		//Truque utilizado para poder utilizar metodos de 
		//outra interface sem perder os dados, passando
		//no construtor de outro objeto os dados anteriores
		//fazendo com que cada especidade de uma interface
		//seja usada quando necess�rio, porem se for muito
		//grande ser� criado duas listas muito grandes
		List<String> alunosEmLista = new ArrayList<>(alunos);
		
		
	}

}
