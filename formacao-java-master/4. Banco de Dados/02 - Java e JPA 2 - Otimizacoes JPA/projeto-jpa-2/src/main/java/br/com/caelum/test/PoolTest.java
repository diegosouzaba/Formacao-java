package br.com.caelum.test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.com.caelum.JpaConfigurator;

public class PoolTest {

	public static void main(String[] args) throws PropertyVetoException, SQLException {

		ComboPooledDataSource dataSource = (ComboPooledDataSource) new JpaConfigurator().getDataSource();

		//Testando a conex�o, porem quando n�o ouver conex�es
		//o for para de executar porque chegou no m�ximo de conex�es do pool
		//e o usu�rio est� no aguardo.
		for (int i = 0; i < 10; i++) {
			@SuppressWarnings("unused")
			Connection connection = dataSource.getConnection();

			System.out.println("----------------------------------------");
			System.out.println("------------------- "+i+" ------------------");
			System.out.println("Threads: " + dataSource.getNumHelperThreads());
			System.out.println("M�ximo n�meno pool: " + dataSource.getMaxPoolSize());
			System.out.println("M�nimo n�mero pool: " + dataSource.getMinPoolSize());
			System.out.println("Total conex�es: " + dataSource.getNumConnections());
			
			System.out.println("\nConex�es ocupadas: " + dataSource.getNumBusyConnections());
			System.out.println("Conex�es ociosas: " + dataSource.getNumIdleConnections());
			System.out.println("\n----------------------------------------");

		}
	}

}
