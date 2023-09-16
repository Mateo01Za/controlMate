package Lista;
import Excepciones.*;
import java.util.*;

public class ElementIterator<E> implements Iterator<E> {

	protected PositionList<E> list; // Lista a iterar
	protected Position<E> cursor; // Posición del elemento corriente

	/**
	 * Crea un iterador para una lista de elementos de tipo E.
	 * @param l la lista a iterar
	 */
	public ElementIterator(PositionList<E> l) {
		list = l; // Guardo la referencia a la lista a iterar
		if (list.isEmpty())
			cursor = null; // Si la lista está vacía, la posición corriente es nula
		else
			try {
				// Sino la posición corriente es la primera de la lista
				cursor = list.first();
			} catch (EmptyListException e) {
				e.printStackTrace();
			}
	}

	/**
	  * Verifica si hay un elemento siguiente en el iterador.
	  * @return true si hay un elemento siguiente, false si no
	 */
	public boolean hasNext() {
		// Hay siguiente si el cursor no está más allá de la última posición
		return cursor != null;
	}

	/**
	 * Devuelve el elemento siguiente en el iterador y avanza el cursor.
	 * @return el elemento siguiente en el iterador
	 * @throws NoSuchElementException si no hay un elemento siguiente
	 */
	public E next() throws NoSuchElementException {
		if (cursor == null) // Si el cursor es null, el cliente no testeó que hasNext fuera true
			throw new NoSuchElementException("Error: No hay siguiente");
		E elem = cursor.element(); // Salvo el elemento corriente
		try {
			// Avanzo a la siguiente posición
			cursor = (cursor == list.last()) ? null : list.next(cursor);

		} catch (EmptyListException e) {
			e.printStackTrace();
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (BoundaryViolationException e) {
			e.printStackTrace();
		}
		return elem; // Retorno el elemento salvado
	}
}