package exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import hibernate.session.HibernateUtil;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapperd;
	// contexto do jsf
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	// obtemos um mapa da requisicao
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	// mostra o estado atual da navegacao entre as paginas
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	// construtor passando a excessao, que sera outra classe
	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	// sobrescreve o metodo que retorna a pilha de excecoes
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	// sobrescreve o metodo responsavel por manipular as excecoes do jsf
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		// quando metodo Iterator precisa de um while - para varrer até o final
		while (iterator.hasNext()) { // enquando tiver dados na lista ele ira pegar os dados
			ExceptionQueuedEvent event = iterator.next(); // retorna objeto de erro
			// conversao de cast da classe retorna o source do evento
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource(); 
			// recupera a excecao do contexto
			Throwable exception = context.getException();
			// trabalhando a excessao
			try {// gerencia erros dos erros

				requestMap.put("exceptionMessage", exception.getMessage());
				if (exception != null && exception.getMessage() != null
						//violacao de chave estrangeira no banco de dados
						&& exception.getMessage().indexOf("ConstraintViolationException") != -1) {
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registro não pode ser removido por estar associado", ""));
				} else if (exception != null && exception.getMessage() != null
						//evita que haja erro de manipulacao de dados por dois usuarios ao mesmo tempo
						&& exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Registro foi atualizado ou excluido por outro usuario.Consulte novamente", ""));
				} else {
					// avisa o usuario do erro
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O sistema se recuperou de um erro inesperado", ""));
					// avisa usuario para continuar utilizando sistema
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Você pode continuar usando o sistema normalmente", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O erro foi causado por:\n" + exception.getMessage(), ""));

					// PrimeFaces - se o jsf nao mostrar mensagem (acima) o primefaces mostrará
					// (abaixo)
					// exibido apenas se a pagina nao redirecionar - alert javascript
					RequestContext.getCurrentInstance()
							.execute("alert('O sistema se recuperou de um erro inesperado.')");

					// mostra caixa de dialogo do primefaces
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Erro", "O sistema se recuperou de um erro inesperado.."));

					// fazer o redirecionamento para pagina de erro e expira o contexto
					navigationHandler.handleNavigation(facesContext, null,
							"/error/error.jsf?faces-redirect=true&expired=true");
				}
				// renderiza pagina de erro e exibe as mensagens
				facesContext.renderResponse();
			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				// imprime o erro no console
				exception.printStackTrace();

				iterator.remove();
			}
		}
		getWrapped().handle();// finaliza
	}

}
