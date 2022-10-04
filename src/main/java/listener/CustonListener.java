package listener;

import java.io.Serializable;

import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

import hibernate.utils.UtilFramework;
import model.classes.Entidade;
import model.classes.InformacaoRevisao;

@Service
public class CustonListener implements RevisionListener, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public void newRevision(Object revisionEntity) {
		InformacaoRevisao informacaRevisao = (InformacaoRevisao) revisionEntity;
		Long codUser = UtilFramework.getThreadLocal().get();
		
		Entidade entidade  = new Entidade();
		if(codUser !=null && codUser !=0L) {
			entidade.setEnt_codigo(codUser);
			informacaRevisao.setEntidade(entidade);
		}
		
		
	}

}
