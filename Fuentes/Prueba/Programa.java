package Prueba;
import Lista.*;
import ColaConPrioridad.*;
import Diccionarios.*;


import Excepciones.EmptyPriorityQueueException;



import Excepciones.InvalidKeyException;
import Excepciones.InvalidNotaException;
import Excepciones.InvalidPositionException;



public class Programa {
   private  String materia;
   private  PositionList<Pair<Integer,Integer>> listaAlumnos;

   
   
   Programa(String materia){
	   this.materia=materia;
	   listaAlumnos= new DoubleLinkedList<Pair<Integer,Integer>>();
   }
	
   /**
    * Agrega un alumno al programa.
    * @param l Lu del alumno.
    * @param n Nota obtenida por el alumno.
    * @throws InvalidKeyException El alumno ingresado no es valido.
   */
   public boolean  agregarAlumno(Integer l, Integer n) throws InvalidNotaException { 
	   boolean agrego = true;
		   if(n<0 || n>10) throw new InvalidNotaException("");
		   for(Pair<Integer,Integer> p: listaAlumnos)
			   if(p.getKey().equals(l))
				   agrego = false;
		   if (agrego)
			   listaAlumnos.addLast(new Pair<Integer, Integer>(l,n));
	   
	   return agrego;
    }
   /**
    * Devuelve la nota del alumno, a partir del LU pasado por parametro.
    * @param l LU del alumno.
    * @return Nota del alumno.
    * @throws InvalidKeyException Si el Lu pasado por parametro no pertenece a ningun alumno.
    * @throws NullPointerException.
   */
   public   String consultarNota(Integer l)throws InvalidKeyException,NullPointerException{
	   String s="";
	   for(Pair<Integer,Integer> p: listaAlumnos)
		   if(p.getKey().equals(l)) { 
			   s+= "La nota del alumno "+l+" es "+p.getValue();
			   return s;
			   }		   
	   throw new InvalidKeyException("");	       
   }
   
   /**
    * Elimina a un alumno dado su LU.
    * @param l LU del alumno a eliminar.
 	* @throws InvalidPositionException Si el LU no es de un alumno valido.
 	*/
   public boolean EliminarAlumno(Integer l) {
	   boolean elimino = false;
	   try {
		for(Position<Pair<Integer,Integer>> p: listaAlumnos.positions())
			   if(p.element().getKey().equals(l)) {
				   listaAlumnos.remove(p);
				   elimino = true;
			   }
	   } catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	   return elimino;
	       
   }
   
   /**
    * Muestra Muestra el LU y la nota de todos los alumnos en la materia.
    * @return  LU y la nota de todos los alumnos en la materia.
   */
   public String visualizar() {
	   String s="";
	   for(Pair<Integer,Integer> p: listaAlumnos) {
		   s+="LU: "+ p.getKey()+" Nota: "+p.getValue()+"\n";
		   
	   }
	   return s;
   }   
	   
	   
  
   /**
    * Muestra el promedio general de la materia.
    * @return Promedio general de la materia.
   */
   public float promedio() {
	   if (listaAlumnos.size() == 0)
		   return 0;
	   float numerador=0f;
	   for(Pair<Integer,Integer> p: listaAlumnos) {
		   numerador+=p.getValue();
	   }
	   return numerador/listaAlumnos.size();
	 
   }
   
   /**
    * Muestra todos los alumnos que tengan la nota pasada por parametro.
    * @param n Nota de los alumnos a buscar.
    * @return Alumnos con la n pasada por parametro.
    * @throws InvalidKeyException
   */
   public String buscarNota(Integer n)  {
	   String s="";
	   try {
		Dictionary<Integer,Integer> d= new DiccionarioHashAbierto<Integer,Integer>();
		   for(Pair<Integer,Integer> p: listaAlumnos) {
			   d.insert(p.getValue(),p.getKey());
		   }
		   Iterable<Entry<Integer,Integer>>i=d.findAll(n);
		   for(Entry<Integer,Integer> e:i) {
			   s+="LU: "+ e.getValue()+" Nota: "+e.getKey()+"\n";
		   }
	} catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return s;   
   }  
   
   /**
    * Devuelve un string con los datos de los alumnos desaprobados.
    * Un alumno está aprobado si su nota es mayor que 6.
    * @return Alumnos aprobados y su nota
   */
   public String aprobados() {
	   String s="";
	   for(Pair<Integer,Integer>p:listaAlumnos)
		   if(p.getValue()>=6)
			  s+="LU: "+ p.getKey()+" Nota: "+p.getValue()+"\n";
	   return s; 
   }
   /**Devuelve un string con los datos de los alumnos desaprobados.
    * Un alumno está desaprobado si su nota es menor que 6.
    * @return Alumnos desaprobados y su nota
 	*/
   public String desaprobados() {
	   String s="";
	   for(Pair<Integer,Integer>p:listaAlumnos)
		   if(p.getValue()<6)
			  s+="LU: "+ p.getKey()+" Nota: "+p.getValue()+"\n";
	   return s; 
   }
   

   /**
    * Muestra todos los alumnos y sus notas obtenidas de mayor a menor.
    * @return Alumnos y sus notas obtenidas de mayor a menor.
   */
   public String mayorMenor() {
	   String s ="";
	   	try {
		Heap<Integer,Integer> colaAux = cCola();
		while(!colaAux.isEmpty()){
			Entry<Integer,Integer> i = colaAux.removeMin();
			s += "LU: "+ i.getValue()+" Nota: "+i.getKey()+ "\n";
		}	
		} catch (IllegalStateException | EmptyPriorityQueueException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return s;
   }
   
   /**
    * Muestra la minima nota obtenida en la materia.
    * @return minima nota obtenida en la materia.
 	* @throws EmptyPriorityQueueException No hay ningun alumno en la materia.
   */
   public String minimaNota() {
	   String s="";
	   if(listaAlumnos.size() == 0) return s;
	   try {
		PriorityQueue<Integer,Integer> cola= cColaMin();
		   s+="LU: "+cola.min().getValue()+" Nota: "+cola.min().getKey();
	} catch (InvalidKeyException  e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   catch(EmptyPriorityQueueException e) {
		  
	}
	   return s;
   }
   
   /**
    * Crea y devuelve una cola con prioridad ordenada de mayor a menor nota
    * @return Cola con prioridad
 	* @throws InvalidKeyException
 	*/
   private Heap<Integer,Integer> cCola() throws InvalidKeyException{
	   Heap<Integer,Integer> cola= new Heap<Integer,Integer>(new Comparador<Integer>().reversed());
	   for(Pair<Integer,Integer>p:listaAlumnos) {
		   cola.insert(p.getValue(),p.getKey());
	   }
	   return cola;
   }
   
   /**
    * Crea y devuelve una cola con prioridad ordenada de menor a mayor nota
    * @return Cola con prioridad
 	* @throws InvalidKeyException
 	*/
   private Heap<Integer,Integer> cColaMin() throws InvalidKeyException{
	   Heap<Integer,Integer> cola= new Heap<Integer,Integer>(new Comparador<Integer>());
	   for(Pair<Integer,Integer>p:listaAlumnos) {
		   cola.insert(p.getValue(),p.getKey());
	   }
	   return cola;
   }
     
   public String getMateria() {
	   return materia;
   }
   
   /**
 * @author Mateo
 *
 * @param <K> claves 
 * @param <V> 
 */
   private class Pair<K,V>{
	   protected K key;
	   protected V value;
	   /**
	    * Crea un par con una clave y un valor de tipo K y V respectivamente.
	    * @param k la clave del par
	    * @param v el valor del par
	   */
	   public Pair(K k, V v) {
			   key = k;
			   value = v;
		   }
		   
		/**
		 * Devuelve la clave del par.
		 * @return la clave del par
		*/
	   public K getKey() {
			   return key;
		   }
		   
		/**
		 * Devuelve el valor del par.
		 * @return el valor del par
		*/
	   public V getValue() {
			   return value;
		   }
   }
}