/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.com.util;

import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import projeto.com.model.Funcionario;

/**
 *
 * @author gabriel
 */
public class HibernateUtil {
    private static final long serialVersionUID = 1L;

	private static HibernateUtil hibernateUtil;
	
	private SessionFactory sessionFactory;

	private AnnotationConfiguration configuration;

	public HibernateUtil() {
		setSession();
	}

	public void recriarTodasTebelas() {
		SchemaExport se = new SchemaExport(configuration);
		
		se.create(true, true); 
	}

	public void aplicarAlteracoes(){
		SchemaUpdate su = new SchemaUpdate(configuration);
		su.execute(true, true);
	}
	
	private void setSession() {
		if (sessionFactory == null) {
			try {
				configuration = new AnnotationConfiguration()
						.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
						.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
						.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/ponto")
						.setProperty("hibernate.connection.username", "root")
						.setProperty("hibernate.connection.password", "")				 
						.setProperty("hibernate.show_sql", "false")
						.setProperty("hibernate.format_sql", "true")
						.setProperty("hibernate.c3p0.acquire_increment", "1")
						.setProperty("hibernate.c3p0.idle_test_period", "100")
						.setProperty("hibernate.c3p0.maxIdleTime", "300")
						.setProperty("hibernate.c3p0.max_size", "75")
						.setProperty("hibernate.c3p0.max_statements", "0")
						.setProperty("hibernate.c3p0.min_size", "20")
						.setProperty("hibernate.c3p0.timeout", "180")
						.setProperty("hibernate.cache.user_query_cache", "true") 
						.addAnnotatedClass(Funcionario.class);

				sessionFactory = configuration.buildSessionFactory();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed."
						+ ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
	}
	
	public Session getSession() {
            try {
                setSession();
		Session toReturn = sessionFactory.openSession();
		toReturn.beginTransaction();
		return toReturn;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Sem acesso ao banco.\n\n");
                return null;
            }
		
	}

	public static HibernateUtil getInstance() {
		if (hibernateUtil == null) {
			hibernateUtil = new HibernateUtil();
		}
		return hibernateUtil;
	}    
}
