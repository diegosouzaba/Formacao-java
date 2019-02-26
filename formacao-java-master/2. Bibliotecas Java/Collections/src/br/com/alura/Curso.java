package br.com.alura;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

public class Curso {

	//private int tempoTotal;
	private String nome;
	private String instrutor;
	
	// Usando ArrayList prende muito a classe
	// o correto � utilizar uma interface List
	// Usando arraylist ou linkedlist n�o faz muita
	// diferen�a pois ambas adicionam elementos na ordem
	// e deixam adicionar elementos repetidos
	// exato pelo caso da perfomance � onde as diferen�as
	// come�am
	private List<Aula> aulas = new LinkedList<Aula>();

	//Map
	//Que basicamente n�o � uma cole��o pois n�o �
	//filha de collection, funciona com o mapeamento
	//chave e valor que � passado para o map, ex:
	//Map<Integer, Aluno> localizador = new Map<>();
	//abaixo a utiliza��o do map � referente a busca
	//r�pida de alunos pela matricula.
	//Sua implementa��o mais usada � o HashMap, porem
	//h� uma implementa��o antiga e thread-safe 
	//chamada Hashtable()
	
	private Map<Integer, Aluno> matriculaParaAluno = new HashMap<>();
	
	//Usando Sets(Interface) e suas implementa��es
	//como:
	//HashSet e LinkedHashSet
	//que basicamente n�o tem muita diferen�a
	//exceto que o LinkedHashSet mesmo que 
	//n�o seja possivel acessar a enesima posic�o
	//mas ele devolve os itens da ordem que foram 
	//adicionados
	
	//TreeSet
	//s� funciona para as classes que s�o comparable
	//pois coloca os itens na sua ordem natural, 
	//de ordena��o.
	//Para entender a sua grande diferen�a
	//na performance, o ponto negativo � o entendimento
	//necess�rio com os m�todos 
	//Equals, quer serve de compara��o de atributos da 
	//classe para que seja confirmado na compara��o 
	//e Hashcode, que faz a gera��o de numeros identificadores
	//de cada instancia da classe, 
	//Lembrando que sempre tem que ser reescritos ambos
	//para que a sua utiliza��o seja realmente v�lida
	private Set<Aluno> alunos = new TreeSet<>();
	
	// ArrayList
	// Trabalha internamente como um vetor[]
	// e � muito r�pida para adicionar elementos
	// no final e obter elementos atraves do indice
	// get(n), problemas para deletar elementos,
	// pois ter� que mover os elementos

	// LinkedList
	// Lido do javaDoc, que � muito r�pido para
	// adicionar elementos no inicio da lista
	// e remover, por conta da estrutura de dados
	// porem � muito lenta para obter elementos
	// independete da posi��o

	public Curso(String nome, String instrutor) {
		this.nome = nome;
		this.instrutor = instrutor;
	}

	public String getNome() {
		return nome;
	}

	public String getInstrutor() {
		return instrutor;
	}

	public List<Aula> getAulas() {
		// A ideia de programar defensivamente
		// Ler effective java
		// Esse retorno retorna uma lista que
		// s� pode ser lida seus metodos
		// que modificam como add, remove
		// n�o ser�o acessados, gerando
		// exce��es.
		return Collections.unmodifiableList(aulas);
	}

	public void adiciona(Aula aula) {
		this.aulas.add(aula);
	}

	public int getTempoTotal() {
		// Usando Java 8, isso � muito doido!
		// Mostrando as possibilidades para resolver o mesmo problema
		return this.aulas.stream().mapToInt(Aula::getTempo).sum();
	}

	/*public void setTempoTotal(int tempoTotal) {
		this.tempoTotal = tempoTotal;
	}*/

	@Override
	public String toString() {
		return "[ Curso: " + this.nome + ", Instrutor: " + this.instrutor + ", Tempo total: " + this.getTempoTotal()
				+ " Aulas :" + this.aulas + "]";
	}
	
	//Usando o encapsulamento
	public void matricula(Aluno aluno) {
		this.alunos.add(aluno);
		this.matriculaParaAluno.put(aluno.getNumeroMatricula(), aluno);
	}
	
	//Usando a programa��o defensiva
	public Set<Aluno> getAlunos() {
		return Collections.unmodifiableSet(alunos);
	}
	
	//Delegando algumas reponsabilidades
	public boolean estaMatriculado(Aluno aluno){
		return alunos.contains(aluno);
	}

	public Aluno buscaMatriculado(int numero) {
		//pode ser feito com o for
		//e comparando porem, ser�
		//modificado para o uso 
		//dos mapas, por conta da frequencia de uso
		//e da quantidade de registros
		/*for (Aluno aluno : alunos) {
			if (aluno.getNumeroMatricula() == numero) {
				return aluno;
			}
		}
		throw new NoSuchElementException("Matr�cula n�o encontrada "+ numero);*/
		
		//Usando o mapa para facilitar a busca
		//usando a chave utilizada no primeiro 
		//parametro.
		if (!matriculaParaAluno.containsKey(numero))
			throw new NoSuchElementException("Matr�cula n�o encontrada "+ numero);
		return this.matriculaParaAluno.get(numero);
	}
	

}
