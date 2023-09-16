package Lista;
import Excepciones.*;
import java.util.Iterator;

public class DoubleLinkedList<E> implements PositionList<E> {
	protected DNodo<E> head;
	protected DNodo<E> tail;
	protected int size;
	
	/**
	  * Constructor de la clase DoubleLinkedList.
	  * Crea una lista doblemente enlazada vacía con dos nodos centinelas: head y tail.
	  * Inicializa el tamaño de la lista a cero.
	 */
	public DoubleLinkedList() {
		head = new DNodo<E>(); //creo el nodo centinela a la cabeza
		tail = new DNodo<E>(); //Creo el nodo centinela a la cola de la lista
		head.setNext(tail);
		tail.setPrev(head);
		size=0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException{
		if (this.isEmpty()) throw new EmptyListException ("Error: lsita vacia");
		return head.getNext();
	}
	
	public Position<E> last() throws EmptyListException{
		if (this.isEmpty()) throw new EmptyListException ("Error: lsita vacia");
		return tail.getPrev();
	}
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		DNodo<E> aux = checkPosition(p);
		if(aux == tail.getPrev() ) throw new BoundaryViolationException("Error: p es la ultima posicon de la lista");
		return aux.getNext();
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		DNodo<E> aux = checkPosition(p);
		if(aux == this.head.getNext() ) throw new BoundaryViolationException("Error: p es la primera posicon de la lista");
		return aux.getPrev();
	}
	
	public void addFirst(E element) {
		DNodo<E> aux = new DNodo<E>(element,head,head.getNext());;
			head.getNext().setPrev(aux);
			head.setNext(aux);			
		size++;
	}
	
	public void addLast(E element) {
		DNodo<E> aux = new DNodo<E>(element);
			aux.setNext(tail);				
			aux.setPrev(tail.getPrev());
			tail.getPrev().setNext(aux);
			tail.setPrev(aux);
		size++;
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException{
		DNodo<E> aux = checkPosition(p);
		DNodo<E> nuevo = new DNodo<E>(element,aux,aux.getNext());
		aux.getNext().setPrev(nuevo);
		aux.setNext(nuevo);
		size++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException{
		DNodo<E> aux = checkPosition(p);
		DNodo<E> nuevo = new DNodo<E>(element,aux.getPrev(),aux);
		aux.getPrev().setNext(nuevo);
		aux.setPrev(nuevo);
		size++;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException{
		DNodo<E> aux = checkPosition(p);
		E dev = aux.element();
		aux.getNext().setPrev(aux.getPrev());
		aux.getPrev().setNext(aux.getNext());
		aux.setElement(null);
		size--;
		return dev;
	}
	
	public E set(Position<E> p, E element) throws InvalidPositionException{
		DNodo<E> aux = checkPosition(p);
		E dev = aux.element();
		aux.setElement(element);
		return dev;
	}
	
	public Iterator<E> iterator(){
		return new ElementIterator<E>(this);
	}
	
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> dev = new DoubleLinkedList<Position<E>>();
		if (!isEmpty()) {
			try {
				Position<E> pos = first();
				boolean seguir = true;
				while (seguir) {
					dev.addLast(pos);
					if (pos == last())
						seguir = false;
					else
						pos = next(pos);
				}
			} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			}
		}
		return dev;
	}
	
	
	/**
	 * Verifica si una posición es válida y la convierte en un nodo .
	 * Una posición es válida si no es nula, no ha sido eliminada y pertenece a la lista.
	 * @param p la posición a verificar
	 * @return la posicion convertida en nodo
	 * @throws InvalidPositionException
	 */
	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		try {
			if ( p ==  null) throw new InvalidPositionException("Posicion nula");
			if (p.element() == null) throw new InvalidPositionException ("p eliminada previamente");
			return (DNodo<E>) p;
		} catch (ClassCastException e) {
			throw new InvalidPositionException("p no es un nodo de la lista");
		}
		
	}
	
	@SuppressWarnings("hiding")
	private class DNodo<E> implements Position<E>{
		private E element;
		private DNodo<E> next;
		private DNodo<E> prev;
		
		public DNodo () {
			next = prev = null;
			element = null;
		}
		public DNodo (E e) {
			next = prev = null;
			element = e;
		}
		
		public DNodo (E e, DNodo<E> p, DNodo<E> n) {
			next = n;
			prev = p;
			element = e;
		}
		public E element () {
			return element;
		}
		
		/**
		 * Devuelve el nodo siguiente a este .
		 * @return el nodo siguiente o null si este es el último 
		 */
		public DNodo<E> getNext() {
			return next;
		}
		
		/**
		 * Devuelve el nodo anterior a este .
		 * @return el nodo anterior o null si este es el primero 
		 */
		public DNodo<E> getPrev() {
			return prev;
		}
		/**
		 * Establece el nodo siguiente .
		 * @param el nuevo nodo siguiente.
		 */
		public void setNext(DNodo<E> n) {
			next = n;
		}
		
		/**
		 * Establece el nodo anterior a este.
		 * @param p p el nuevo nodo anterior. 
		 */
		public void setPrev(DNodo<E> p) {
			prev = p;
		}
		
		/**
		 * Establece el elemento almacenado en este nodo.
		 * @param e el nuevo elemento a almacenar.
		 */
		public void setElement(E e) {
			element =e;
		}
	}
	
	
    
}
