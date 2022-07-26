package util;

public enum StatusPersistencia {
	ERROR("Erro"), SUCESSO("Sucesso"),OBJETO_REFERENCIADO("Esse registro n�o pode ser apagado por ter conex�es com outros dados.");

	private String name;

	private StatusPersistencia(String s) {
		this.name = s;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
