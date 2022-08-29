package listener;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@ApplicationScoped//instancia unica para todo projeto
public class ContextLoaderListenerAplicacao extends ContextLoaderListener implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//metodo retorna todo o contexto do spring
	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	//metodo que pega um objeto especifico pelo id
	@SuppressWarnings("unused")
	private static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	//metodo que pega um objeto especifico pela classe
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}
	
	
	
}
