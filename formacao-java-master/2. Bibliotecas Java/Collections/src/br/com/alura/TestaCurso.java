package br.com.alura;

public class TestaCurso {

	public static void main(String[] args) {
		
		Curso javaColecoes = new Curso("Dominando as cole��es do Java", 
					"Paulo Silveira");
		
		//Igualando as referencias
		//Isso poderia ser feito de outra forma
		/*List<Aula> aulas = javaColecoes.getAulas();
		System.out.println(aulas);
		System.out.println(aulas == javaColecoes.getAulas());
		System.out.println(aulas);*/
		
		//A outra forma de manipular a lista
		//O getAulas retorna um List e 
		//Todo o list tem o m�todo add
		//Porem como foi modificado para 
		//return Collections.unmodifiableList(aulas);
		//Nenhum metodo pode ser acessado ou modificado
		//Fazendo que que a classe javaColecoes tenha 
		//total responsabilidade de adicionar ou remover
		//javaColecoes.getAulas().add(new Aula("Trabalhando com ArrayList", 21));
		
		//Fazendo com que a classe adicione
		//Para que n�o seja tratado com a lista
		//a lista n�o pode ser manipulada
		javaColecoes.adiciona(new Aula("Trabalhando com ArrayList", 21));
		javaColecoes.adiciona(new Aula("Criando uma aula", 20));
		javaColecoes.adiciona(new Aula("Relacionamento entre cole��es", 23));
		
		
		//N�o est� vazio pois a refenrencia � a mesma
		System.out.println(javaColecoes.getAulas());
		
	}

}
