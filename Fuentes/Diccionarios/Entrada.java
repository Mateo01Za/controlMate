package Diccionarios;

public class Entrada<K,V> implements Entry<K,V> {

	private K clave;
	private V valor;
	
	
	
	/**
	 * Construye una nueva entrada con la clave y el valor dados.
	 * @param clave la clave de la entrada
	 * @param valor el valor de la entrada
	 */
	public Entrada(K clave,V valor) {
		this.clave=clave;
		this.valor=valor;
	}
	
	
	/**
	 * Establece la clave de la entrada a un nuevo valor.
	 * @param clave el nuevo valor de la clave.
	 */
	public void setKey(K clave) {
		this.clave=clave;
	}
	
	/**
	 * Establece el valor de la entrada a un nuevo valor.
	 * @param valor el nuevo valor del valor.
	 */
	public void setValue(V valor) {
		this.valor=valor;
	}
	
	
	
	/**
	 * Devuelve la clave de la entrada.
	 */
	@Override
	public K getKey() {
		return clave;
	}

	/**
	 *Devuelve el valor de la entrada.
	 */
	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return valor;
	}
	
	/**
	 *Devuelve una representación en cadena de la entrada en forma de "clave valor".
	 */
	public String toString() {
		return (clave+" "+valor);
	}
	

}
