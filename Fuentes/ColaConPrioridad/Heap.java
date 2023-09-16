package ColaConPrioridad;
import Diccionarios.Entry;

import java.util.Comparator;

import Diccionarios.Entrada;
import Excepciones.*;
	public class Heap<K,V> implements PriorityQueue<K,V>{
		private Entrada<K, V>[] elems;
		private Comparator<K> comp;
		private int size;
		
		/**
		 * Construye un nuevo heap con un comparador y una capacidad máximos dados.
		 * @param c el comparador que determina el orden de las entradas.
		 * @param max la capacidad máxima del heap.
		 */
		@SuppressWarnings("unchecked")
		public Heap(Comparator<K> c, int max) {
			elems = (Entrada<K,V>[]) new Entrada[max];
			comp = c;
			size = 0;
		}
		/**
		 * Construye un nuevo heap con un comparador dado y una capacidad de 100.
		 * @param c el comparador que determina el orden de las entradas.
		 */		
		public Heap(Comparator<K> c) {
			this(c, 100);
		}
		
		public int size() {
			return size;
		}
		
		public boolean isEmpty() {
			return size==0;
		}
		
		public Entry<K,V> min()throws EmptyPriorityQueueException{
			if(isEmpty()) throw new EmptyPriorityQueueException("Error: vacia");
			return elems[1];
		}
		
		public Entry<K,V> insert(K key,V value)throws InvalidKeyException{
			checkKey(key); //Chequeo que key sea una clave valida
			if(size == (elems.length - 1)) { // si el arreglo esta lleno, paso los elementos a un arreglo mas grande
				@SuppressWarnings("unchecked")
				Entrada<K, V>[] aux = (Entrada<K,V>[]) new Entrada[elems.length * 5];
				for (int i = 1; i < elems.length; i++)
					aux[i] = elems[i];
				elems = aux;
			}
			
			Entrada<K, V> nueva = new Entrada<K, V>(key, value); //creo una nueva entrada
			size++;
			elems[size] = nueva; // la agrego al final del arreglo
			int i = size; //seteo el indice i en la posicion corriente de arreglo, que es la ultima 
			boolean seguir = true;
			while (i > 1 && seguir) { 
				Entrada<K, V> hijo = elems[i]; 		//obtengo entrada i y a su padre
				Entrada<K, V> padre = elems[i / 2]; 
				if (comp.compare(padre.getKey(), hijo.getKey()) > 0) { 
					Entrada<K, V> aux = elems[i]; //Intercambio si estan desordenados
					elems[i] = elems[i / 2];
					elems[i / 2] = aux;
					i = i/2; // Reinicializo i con su padre
				} else // Si no pude intercambiar, la entrada ya estaba ordenada
					seguir = false;
			}				
			return nueva;
		}
		
		public Entry<K,V> removeMin()throws EmptyPriorityQueueException{
			if (size == 0)
				throw new EmptyPriorityQueueException("Error cola vacia");
			Entry<K,V> entrada = min(); //creo la entrada para devolver
			if(size==1) { // si tengo solo un elemento el arreglo queda vacio
				elems[1] = null;
				size=0;
			}else { // Si tengo mas de 1 elemento
				elems[1] = elems[size]; //muevo el ultimo elemento del arreglo a la primera posicion
				elems[size]=null; //elimino el primer elemento
				size--; //decremento la variable size en 1
				
				int i = 1; 
				boolean seguir = true;
				//comienzo con el primero elemento del arreglo(Anteriormente una "Hoja")
				while(seguir) {
					int hi =  i*2;
					int hd = (i*2) + 1;
					boolean tieneHI = hi <= size;
					boolean tieneHD = hd <= size;
					//verifico que el elemento tenga hijos izquiero y derecho
					if (!tieneHI) seguir = false;//Si no tiene hijo izquier, tampoco tiene hijo derecho
					else {
						int m;
						
						if(tieneHD) { //En caso de tener HI compruebo cal es el menor entre HI y HD
							if(comp.compare(elems[hi].getKey(),elems[hd].getKey()) < 0) m = hi;
							else m = hd;
						}
						else m = hi;//Si no tiene hijo derecho, el HI es el mayor
					
						
						if (comp.compare(elems[i].getKey(), elems[m].getKey()) > 0) {//Veo si debo intercambiar el actual con el menor de sus hijos
							Entrada<K,V> aux = elems[i];
							elems[i] = elems[m];
							elems[m]=aux;
							i=m; //Reinicializo i para que continue en la posicion m
						}
						else seguir = false;
					} 						
				}
			}
			return entrada;
		}
		
		
		/**
		 * Verifica que la clave sea válida y comparable.
	 	 * @param key la clave a verificar
	 	 * @throws InvalidKeyException si la clave es nula o no es de un tipo comparable
		 */
		private void checkKey(K key) throws InvalidKeyException {//Chequeo que la clave sea valida
			if(key==null) throw new InvalidKeyException("Error: clave nula");
			try {
				comp.compare(key, key);
			} catch (ClassCastException | NullPointerException e) {
				throw new InvalidKeyException("error: la clave no es comparable");
			}
		}
}

