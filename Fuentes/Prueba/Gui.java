package Prueba;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

import Excepciones.InvalidKeyException;
import Excepciones.InvalidNotaException;





public class Gui {
	private Programa ProgramaPrincipal;

	private JFrame frame;
	private JTextArea Registro;
	private  JTextField Libreta;
	private JButton botonOpciones;
	private JScrollPane scrollPane;
	private JButton botonPromedio;
	private JTextField verPromedio;
	private JLabel etqtVer;

	/**
	 * Ejecuta la aplicacion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea la aplicacion
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Inicializa los contenidos de la interfaz.
	 */
	private void initialize() {
		
		
		/* creo el marco principal de la interfaz*/
		frame = new JFrame();
		frame.setBounds(100, 100, 418, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/* muestro un panel solicitando el nombre de la materia y creo el programa ingresando el nombre como parametro */
		ProgramaPrincipal= new Programa(JOptionPane.showInputDialog("Ingrese nombre de la Materia"));
		
		/* le pongo de tiulo a la interfaz el nombre de la materia ingresada*/
		frame.setTitle(ProgramaPrincipal.getMateria());
		
		/* creo todos los campos de texto y los coloco en el panel principal*/
		Libreta = new JTextField();
		Libreta.setBounds(57, 45, 96, 19);
		frame.getContentPane().add(Libreta);
		Libreta.setColumns(10);
		
		verPromedio = new JTextField();
		verPromedio.setEditable(false);
		verPromedio.setBounds(164, 297, 96, 19);
		frame.getContentPane().add(verPromedio);
		verPromedio.setColumns(10);
		
		/* creo el area de texto del registro y lo coloco en un panel con scroll */
		scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 87, 203, 200);
		frame.getContentPane().add(scrollPane);
		
		
	    Registro = new JTextArea();
	    Registro.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 15));
		Registro.setEditable(false);
		scrollPane.setViewportView(Registro);
		
		/* creo los botones, los coloco en el panel principal y les asigno la accion a realizar*/
		botonOpciones = new JButton("opciones");
		botonOpciones.setBounds(159, 44, 101, 21);
		frame.getContentPane().add(botonOpciones);
		botonOpciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaBotonOpciones();}
			});
		
		JButton botonTodos = new JButton("todos");
		botonTodos.setBounds(270, 90, 101, 21);
		frame.getContentPane().add(botonTodos);
		botonTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualizar();}
			});
		
		
		JButton botonAprobados = new JButton("aprobados");
		botonAprobados.setBounds(270, 178, 101, 21);
		frame.getContentPane().add(botonAprobados);
		botonAprobados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaAprobados();
			}
		});
		
		JButton botonDesaprobados = new JButton("desaprobados");
		botonDesaprobados.setBounds(270, 209, 101, 21);
		frame.getContentPane().add(botonDesaprobados);
		botonDesaprobados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaDesaprobados();
			}
		});
		
		JButton botonNota = new JButton("nota");
		botonNota.setBounds(270, 266, 101, 21);
		frame.getContentPane().add(botonNota);
		botonNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaBuscaNota();
			}
		});
		
		botonPromedio = new JButton("promedio");
		botonPromedio.setBounds(57, 296, 101, 21);
		frame.getContentPane().add(botonPromedio);
		botonPromedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaPromedio();
			}
		});
		
		
		JButton botonMayMen = new JButton("may-men");
		botonMayMen.setBounds(270, 121, 101, 21);
		frame.getContentPane().add(botonMayMen);
		botonMayMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaMayorMenor();
			}
		});
		
		JButton botonMinimaNota = new JButton("minima");
		botonMinimaNota.setBounds(270, 297, 101, 21);
		frame.getContentPane().add(botonMinimaNota);
		botonMinimaNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manejaMinimaNota();
			}
		});
		
		/* creo las etiquetas y las coloco en el panel principal*/
		JLabel etqtLibreta = new JLabel("Libreta Universitaria:");
		etqtLibreta.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 10));
		etqtLibreta.setBounds(57, 22, 101, 13);
		frame.getContentPane().add(etqtLibreta);
		
		etqtVer = new JLabel("              VER");
		etqtVer.setFont(new Font("Yu Gothic UI Semibold", Font.ITALIC, 10));
		etqtVer.setBounds(270, 67, 101, 13);
		frame.getContentPane().add(etqtVer);
		
	
	
		
		
	
	}
	
	   /**
	 *  Inserta los datos que devuelve el metodo visualizar del programa en el area de texto del registro.
	 */
	private void visualizar() {
		 String salida=ProgramaPrincipal.visualizar();
		 Registro.setText(salida);
	   }
	   
	   
		/**
		 * Convierte el numero del registro en un entero ,despliega un menu de opcion con botones y determina la accion segun el boton pulsado.
		 */
		private void manejaBotonOpciones() {
			try {
				// intenta convertir el String ingresado en el campo de la libreta en un entero, si no lo logra lanza un Number format Exception.
			 int LU= Integer.parseInt(Libreta.getText());
			
			 //crea un arreglo con las opciones disponibles.
			 String[]arreglo= {"consultar nota","agregar alumno","eliminar alumno"};
			 //le asigna a la variable opcion el numero del indice del arreglo al que le corresponde la opcion elegida.
			 int opcion =JOptionPane.showOptionDialog(null,"¿que desea hacer?", "Elige...", 0, JOptionPane.QUESTION_MESSAGE, null, arreglo, "agregar");
		    //segun la opcion elegida, maneja los metodos.
			 switch(opcion) {
			  case 0:
				  manejaConsultar(LU);
				  break;
			        
			  case 1:
				  manejaAgregar(LU);
				  break;
			
			  case 2:
				  manejaEliminar(LU);
				  break;
			
			}
			
		  }catch(NumberFormatException e) {
			  JOptionPane.showMessageDialog(frame, "La Libreta debe ser un Numero");  
		  };
		}
		
		/**
		 * @param l numero de libreta Universitaria.
		 * Muestra en un mensaje el String que devuelve el metodo consultarNota(int l) del Programa.
		 */
		private void manejaConsultar(int l){
			try {
			String salida=ProgramaPrincipal.consultarNota(l);
			JOptionPane.showMessageDialog(frame, salida);
			
			}catch(InvalidKeyException e) {
				JOptionPane.showMessageDialog(frame, "El alumno no se encuentra en el registro");
			}
			
		}
		/**
		 * @param l numero de libreta Universitaria.
		 * Despliega un menu de opciones con un input para ingresar una nota.
		 *  Convierte el String de la nota ingresada en entero y utiliza el metodo agregarAalumno del Programa.
		 *  Utiliza el metodo visualizar para mostrar nuevamente todos los alumnos.
		 */
		private void manejaAgregar(int l) {
			String nota = JOptionPane.showInputDialog("Ingrese nota del alumno de 0 a 10 ");
			try {
			int notaInt = Integer.parseInt(nota);
			boolean agregado = ProgramaPrincipal.agregarAlumno(l, notaInt);
			if(!agregado)
				JOptionPane.showMessageDialog(frame, "No se pudo agregar el alumno");
			visualizar();
			//muestra por mensaje las excepciones
			}catch(NumberFormatException e) {  
				JOptionPane.showMessageDialog(frame, "Debe ingresar un numero.");
			}catch(InvalidNotaException e) {
				JOptionPane.showMessageDialog(frame, "La nota no puede ser mayor que 10 o menor que 0");
			}
	 }
		/**
		 * @param l numero de libreta Universitaria.
		 *  Elimina al alumno ingresado por parametro utilizando al metodo EliminarAlumno del Programa.
		 *  Utiliza el metodo visualizar para mostrar nuevamente todos los alumnos.
		 */
		private void manejaEliminar(int l) {
			boolean elimino =ProgramaPrincipal.EliminarAlumno(l);
			if(!elimino)
				JOptionPane.showMessageDialog(frame, "Lu no encontrada, no se pudo eliminar");
			visualizar();
			
		}
		
		/**
		 * Despliega un menu de opciones con un input para ingresar una nota.
		 *  Convierte el String de la nota en entero.
		 *  Inserta los datos que devuelve el metodo buscarNota del Programa en el area de texto del registro.
		 *  
		 */
		private void manejaBuscaNota() {
			String nota = JOptionPane.showInputDialog("Ingrese nota del alumno de 0 a 10 ");
			try {
				 int notaInt = Integer.parseInt(nota);
				 String salida=ProgramaPrincipal.buscarNota(notaInt);
				 Registro.setText(salida);
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "Debe ingresar un numero.");
			};
			
			
		}
		
		/**
		 * inserta los datos que devuelve el metodo aprobados del Programa en el area de texto del registro.
		 */
		private void manejaAprobados() {
			String salida=ProgramaPrincipal.aprobados();
			Registro.setText(salida);
		}
		
		private void manejaDesaprobados() {
			String salida=ProgramaPrincipal.desaprobados();
			Registro.setText(salida);
		}
		
		/**
		 * inserta los datos que devuelve el metodo promedio del Programa en el area de texto del promedio.
		 */
		private void manejaPromedio() {
			try {
			 float salida=ProgramaPrincipal.promedio();
			 verPromedio.setText(""+salida);
			 }catch(ArithmeticException e) {
				 JOptionPane.showMessageDialog(frame, "El registro esta vacio"); 
			 }
			
		}
		/**
		 * inserta los datos que devuelve el metodo mayorMenor del Programa en el area de texto del registro.
		 */
		private void manejaMayorMenor() {
			//try {
				String salida= ProgramaPrincipal.mayorMenor();
				Registro.setText(salida);
			//}catch(InvalidKeyException|EmptyPriorityQueueException e) {
				
			//}
		}
		/**
		 * inserta los datos que devuelve el metodo minimaNota del Programa en el area de texto del registro.
		 */
		private void manejaMinimaNota()  {
			String salida= ProgramaPrincipal.minimaNota();				
			Registro.setText(salida);
			
		}
		
		
}

