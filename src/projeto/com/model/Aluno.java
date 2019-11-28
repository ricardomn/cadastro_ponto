/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto.com.model;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author GerenteTI
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable{
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 100)
	private String nome;
	@Lob
	private byte[] digital;
        private Boolean acesso;

        
	
	public Aluno(){}
	
	public Aluno(String nome){
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "id: " + this.getId() + " nome: " + this.getNome();
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
            Integer oldId = this.id;
		this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
            String oldNome = this.nome;
		this.nome = nome;
        changeSupport.firePropertyChange("nome", oldNome, nome);
	}

	public DPFPTemplate getDigital() {	
		return DPFPGlobal.getTemplateFactory().createTemplate(this.digital);
	}

	public void setDigital(DPFPTemplate digital) {
		this.digital = digital.serialize();
	}	    

        public Boolean getAcesso() {
            return acesso;
        }

        public void setAcesso(Boolean acesso) {
            this.acesso = acesso;
        }
        
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
