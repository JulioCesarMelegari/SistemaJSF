package interfaceCrud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable {
	
	void save(T obj) throws Exception;
	
	void persist(T obj) throws Exception;
	
	void saveOrUpdade(T obj) throws Exception;
	
	void Updade(T obj) throws Exception;
	
	void delete(T obj) throws Exception;
	
	T merge (T obj) throws Exception;
	
	List<T> findList(Class<T> objs) throws Exception;
	
	Object findById(Class<T> entidade, Long id) throws Exception;
	
	T findByPorId(Class<T> entidade, Long id) throws Exception;
	
	List<T> findListByQueryDinamica(String s) throws Exception;
	
	void executeUpdadeQueryDinamica(String s) throws Exception;
	
	void executeUpdadeSQLDinamica(String s) throws Exception;
	
	void clearSession() throws Exception;
	
	void evict (Object objs) throws Exception;
	
	Session getSession() throws Exception;
	
	List<?> getListSQLDinamica(String s) throws Exception;
	
	JdbcTemplate getJdbcTemplate();
	
	SimpleJdbcTemplate getSimpleJdbcTemplate();
	
	SimpleJdbcInsert getSimpleJdbcInsert();
	
	SimpleJdbcCall getSimpleJdbcCall();
	
	Long totalResgistros(String table) throws Exception;
	
	Query oberQuery(String query) throws Exception;
	
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;
 
}
