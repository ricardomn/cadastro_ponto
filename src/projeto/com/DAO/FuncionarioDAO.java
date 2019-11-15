/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.com.DAO;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import projeto.com.model.Funcionario;
import projeto.com.util.HibernateUtil;

/**
 *
 * @author GerenteTI
 */
public class FuncionarioDAO {
	
	/**
	 * Retorna uma sessao do hibernate
	 * @return
	 */
	protected Session getSession() {
		return HibernateUtil.getInstance().getSession();
	}

	/**
	 * Salva o funcionario passado por parametro
	 * @param funcionario
	 */
    public void salvar(Funcionario funcionario) {
    	Session session = getSession();
        session.saveOrUpdate(funcionario);
    	session.getTransaction().commit();
    }


    /**
     * Retorna todos os registros de alunos do banco
     * @return
     */
	@SuppressWarnings("unchecked")
	public ArrayList<Funcionario> getTodos(){		
		return (ArrayList<Funcionario>) getSession().createCriteria(Funcionario.class).addOrder(Order.asc("id")).list();       
    }   
      
}
