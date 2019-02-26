package br.com.caelum.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.caelum.model.Loja;
import br.com.caelum.model.Produto;

@Repository
public class ProdutoDao {

	@PersistenceContext
	private EntityManager em;

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		//Jeito escroto de fazer
//		String jpql = "SELECT DISTINCT p FROM Produto p "
//				+ "JOIN FETCH p.categorias "
//				+ "WHERE p.id = :pId ";
//				
//		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
//		query.setParameter("pId", id);
//		
//		query.getSingleResult();
		
		return produto;
	}

	public List<Produto> getProdutosJPACriteria(String nome, Integer categoriaId, Integer lojaId) {

		//Todas as quest�es relacionadas a query como soma, m�dia
		//m�ximo, minimo
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		Predicate disjunction = criteriaBuilder.disjunction();
		
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);

		// Root - Obtem as referencias dos campos da classe
		// from - Tecnicamente funciona como um SELECT p FROM Produto 
		// e tamb�m indica a base da pesquisa e sem ele n�o funciona a busca
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		// Predicates - S�o as condi��es que vem ap�s o where, 
		// tipo equal, like, greaterThan, lessThan
		List<Predicate> predicates = new ArrayList<>();
		
		
		if (nome!= null && !nome.trim().isEmpty()) {
			// Path e get - Caminho para os atributos de uma classe no SQL
			Path<String> nomePath = root.<String>get("nome");
			Predicate nomeLikePredicate = criteriaBuilder.like(nomePath,"%" + nome + "%"); //Esqueci a merda das porcentagens :|
			disjunction = criteriaBuilder.or(disjunction ,nomeLikePredicate);
			predicates.add(nomeLikePredicate);
		}
		
		if (lojaId != null && lojaId != 0) {
			Path<Integer> lojaIdPath = root.<Loja>get("loja").<Integer>get("id");
			Predicate lojaEqualPredicate = criteriaBuilder.equal(lojaIdPath, lojaId);
			predicates.add(lojaEqualPredicate);
			disjunction = criteriaBuilder.or(disjunction ,lojaEqualPredicate);
			
		}
		if (categoriaId != null && categoriaId != 0) {
			// Necess�rio fazer o join com categorias para obter os dados
			Path<Integer> categoriaIdPath = root.join("categorias").<Integer>get("id");
			Predicate categoriaEqualPredicate = criteriaBuilder.equal(categoriaIdPath, categoriaId);
			predicates.add(categoriaEqualPredicate);
			disjunction = criteriaBuilder.or(disjunction ,categoriaEqualPredicate);
		}		
		
		if (predicates.size() > 0) {
			criteriaQuery.where( predicates.toArray(new Predicate[0]) );			
		}
//		if (disjunction != null) {
//			criteriaQuery.where( disjunction);
//		}
		
		
//		System.out.println("Entendendo");
////		System.out.println((Predicate[]) predicates.toArray()); - Erro casting
//		System.out.println((Predicate[]) predicates.toArray(new Predicate[0]));
//		System.out.println("Entendendo");
		
		// CriteriaAPI - Facilita a vida do desenvolvedor porem, 
		// no final de tudo � basicamente uma TypedQuery
		TypedQuery<Produto> typedQuery = em.createQuery(criteriaQuery);
		typedQuery.setHint("org.hibernate.cacheable", "true");

		return typedQuery.getResultList();
	}
	
//	Quest�es do Spring
/*	J� que no nosso projeto a JPA � gerenciada pelo Spring, ele pede que ao chamarmos 
	o m�todo unwrap que exista j� um EntityManager ativo. Para isso, podemos anotar o m�todo 
	com @Transactional dizendo que dentro de todo esse m�todo dever� ter uma 
	transa��o ativa e portanto um EntityManager*/
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Produto> getProdutosHibernateCriteria(String nome, Integer categoriaId, Integer lojaId) {
		
		/*No m�todo getProdutos, o primeiro passo ser� conseguir uma inst�ncia de Session (que � equivalente do EntityManager do JPA). 
		Conseguimos isso chamando o m�todo unwrap do EntityManager*/
		Session session = em.unwrap(Session.class);
		
		/*Com Criteria ao inv�s de criar um objeto Query, criamos um objeto do tipo Criteria, 
		passando em qual tabela ele buscar� os resultados*/
		Criteria criteria = session.createCriteria(Produto.class);
		
		/*O que queremos � adicionar no nosso m�todo, os filtros de busca. Para isso podemos usar a classe Restrictions*/
		
		if (!nome.trim().isEmpty() && nome != null) {
			criteria.add( Restrictions.like("nome", "%"+nome+"%") );
		}
		if (lojaId != 0 && lojaId != null) {
			criteria.add( Restrictions.like("loja.id", lojaId) );
		}
		if (categoriaId != 0 && categoriaId != null) {
			criteria.setFetchMode("categorias", FetchMode.JOIN)
					.createAlias("categorias", "c")
					.add( Restrictions.like("c.id", categoriaId) );
		}
		
		
		return criteria.list();
	}
	
	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
