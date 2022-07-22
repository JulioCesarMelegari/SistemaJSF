package hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

import implementacao.crud.VariavelConexaoUtil;


/**
 * estabelece conexao com hibernate 
 **/
@ApplicationScoped
public class HibernateUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasource";
	
	private static SessionFactory sessionFactory = buildSessionFactory();
	
	
	/*responsavel por ler o arquivo de configuracao hibernate.cfg.xml*/
	
	private static SessionFactory buildSessionFactory() {
		
		try {
			if(sessionFactory == null){
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			return sessionFactory;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar conexao SessionFactory");
		}
		
	}
	
	/*retorna o SessionFactory corrente*/
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	/*retorna a sessao do SessionFactory*/
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
	/*abre una nova sessao no sessionfactory*/
	public static Session openSession() {
		if(sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
	/*obtem a conexao do provedor de conexões configurado*/
	public static Connection getConnectionProvider() throws SQLException{
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
				
	}
	
	/*retorna connection no initialContext java:/comp/env/...*/
	public static Connection getConnection() throws Exception{
		InitialContext contex = new InitialContext();
		DataSource ds = (DataSource) contex.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		return ds.getConnection();
	}
	
	/*return DataSource Jndi tomcat*/
	public DataSource getDataSourceJind() throws NamingException{
		InitialContext context = new InitialContext();
		return (DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
	}
}
