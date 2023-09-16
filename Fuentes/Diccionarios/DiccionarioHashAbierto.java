package Diccionarios;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidEntryException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import Lista.*;
public class DiccionarioHashAbierto<K,V> implements Dictionary<K,V>{
	
	protected PositionList<Entrada<K, V>> D[];
	protected int n;
	protected int N = 13;
	protected final double fc = 0.9;

	/**
	 * Crea un diccionario vacío con un tamaño inicial de N listas.
	 * Cada lista almacena las entradas que tienen el mismo código de dispersión.
	 */
	@SuppressWarnings("unchecked")
	public DiccionarioHashAbierto() {
		D = (DoubleLinkedList<Entrada<K, V>>[]) new DoubleLinkedList[N]; //creo un arreglo de listas
		n = 0; 
		for (int i = 0; i < D.length; i++) {
			D[i] = new DoubleLinkedList<Entrada<K, V>>();//creo las listas del arreglo
		}
	}

	/**
	 * Calcula el índice del arreglo donde se debe insertar o buscar una clave.
	 * @param key la clave que se quiere insertar o buscar
	 * @return el índice del arreglo correspondiente al valor hash de la clave
	 */
	private int Hash(K key) {
		return key.hashCode() % N;
	}

	public int size() {
		return n;
	}


	public boolean isEmpty() {
		return n == 0;
	}


	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key); //compruebo que la clave no sea nula
		Entry<K, V> dev = null;
		int clave = Hash(key);
		if(!D[clave].isEmpty())
			try {
				dev = D[clave].first().element(); //primer elemento de la lista
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
		return dev;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key); //Compruebo que la clave no sea nula
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>(); // creo la lista para devolver
		int clave = Hash(key);
		for (Entry<K, V> elem : D[clave]) {
			if (key.equals(elem.getKey())) { //compruebo que la clave sea la misma
				lista.addLast(elem);
			}
		}
		return lista;
	}


	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);

		if (n / N >= fc) //compruebo factor de carga
			rehash();
		int clave = Hash(key);
		Entrada<K, V> nueva = new Entrada<K, V>(key, value); //creo la nueva entrada
		D[clave].addLast(nueva);
		n++;
		return nueva;
	}


	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada Invalida");
		int clave = Hash(e.getKey());
		PositionList<Entrada<K, V>> l = D[clave]; //asocio la lista que deberia tener la entrada a la variable l
		Position<Entrada<K, V>> cursor = null;
		Iterator<Position<Entrada<K, V>>> it = l.positions().iterator(); 
		Entry<K, V> salida = null;
		while (it.hasNext() && salida == null) {
			cursor = it.next();
			if (cursor.element().equals(e)) {
				salida = cursor.element(); //si el elemento a eliminar es igual al cursor, lo salvo en la variable a devolver
				try {
					l.remove(cursor); //elimino el elemento de la lista
				} catch (InvalidPositionException ex) {
					
				}
				n--;
			}
		}
		
		if (salida == null) throw new InvalidEntryException("la entrada no esta en el diccionario");//Si no encontre el elemento, entonces no esta en el diccionario
		return salida;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		for (int i = 0; i < N; i++) {
			for (Entry<K, V> en : D[i]) {
				lista.addLast(en);
			}
		}
		return lista;
	}

	/**
	 * Verifica si una clave es válida.
	 * Una clave es válida si no es nula.
	 * @param key la clave a verificar.
	 * @throws InvalidKeyException  si la clave es nula.
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
	}

	/**
	 * Reorganiza el diccionario al duplicar el tamaño del arreglo y reubicar las entradas.
	 * Se utiliza cuando el factor de carga supera un umbral determinado.
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		Iterable<Entry<K, V>> entradas = entries();
		N = proximo_primo(N * 2);
		D = (PositionList<Entrada<K, V>>[]) new DoubleLinkedList[N];
		n = 0;
		for (int i = 0; i < N; i++)
			D[i] = new DoubleLinkedList<Entrada<K, V>>();
		for (Entry<K, V> e : entradas)
			try {
				insert(e.getKey(), e.getValue());
			} catch (InvalidKeyException ex) {
				ex.getMessage();
			}

	}

	/**
	 * Devuelve el primer número primo mayor o igual que un número dado.
	 * @param n el número dado.
	 * @return el primer número primo mayor o igual que n.
	 */
	private int proximo_primo(int n) {
		boolean es = false;
		n++;
		while (!es) {
			if (esPrimo(n))
				es = true;
			else
				n++;

		}
		return n;
	}

	/**
	 * Determina si un número es primo o no.
	 * @param n el número a verificar.
	 * @return true si n es primo, false en caso contrario.
	 */
	private boolean esPrimo(int n) {
		boolean es = false;
		int divisor = 2;
		while (divisor < n && !es) {
			if (n % divisor == 0)
				es = true;
			else
				divisor++;

		}

		return es;
	}
		
}
