package package_ahorcado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Principal {
	
	JFrame frame;
	JLabel palabras, intentos, mensajes, dibujo;
	JButton btt;
	JLabel[] dibujos=new JLabel[6];
	String advPalabra, palabraaDesvelar, letra, rutaEscogida, letrasIntentos="- - - - - - ";
	JComboBox cbPalabras;
	String[] arraypalabraaDesvelar;
	boolean encontrado = false;
	int contadorIntentos=0, contadorImages=0, aciertos=0;
	String[] rutaDibujos= {"lib/1.gif","lib/2.gif","lib/3.gif","lib/4.gif","lib/5.gif","lib/6.gif","lib/7.gif"};

	public String creaPalabra() {
		String[] palabras = {"VALENCIA", "ZARAGOZA", "TERUEL", "CALATAYUD", "FERROL", "BILBAO", "BADAJOZ", 
				"VITORIA", "BADALONA", "CORDOBA", "SALAMANCA"}; //se pueden añadir más items
		int nciudades = palabras.length-1; //para pasar a la función de generar número random
		int ordPalabra = (int) (Math.random() * nciudades); //genera número al azar entre el 0 y el número de ciudades menos 1
		
		advPalabra = palabras[ordPalabra]; //palabra seleccionada al azar entre el array
		arraypalabraaDesvelar=new String[advPalabra.length()]; //array con las letras a descubrir
		for (int i=0; i<advPalabra.length(); i++) {
			arraypalabraaDesvelar[i]="*";		
		}
		palabraaDesvelar=convierteArrayenString(arraypalabraaDesvelar); //se pasa a string mediante un método para incorporarlo al label
		
		return advPalabra;	
	}
	
	public class ActionListenerButton implements ActionListener {//listener del botón

		@Override
		public void actionPerformed(ActionEvent arg0) {
			letra = (String) cbPalabras.getSelectedItem(); //coge la letra seleccionada del combobox
			buscarletra(); //método para buscar y desvelar en su caso las letras de la palabra encubierta
			actualizarintentos(); //escribe en intentos fallidos y actualiza contadores
		}
	}
	
	public void buscarletra() {		
		String[] arr=advPalabra.split("");//convertimos en array la ciudad selecciona para descubir
		encontrado=false;//boolean para cambiar imágenes
			for (int i =0; i<arr.length; i++) {//recorre la palabra
				if (arr[i].equals(letra)){
					encontrado=true;//si la letra está en la palabra seleccionada se cambia a encontrado
					if (!arraypalabraaDesvelar[i].equals(letra)) {//para controlar que no sobreescribamos una letra encontrada
						arraypalabraaDesvelar[i]=letra;	//escribimos sobre la palabra a desvelar		
						aciertos++; //contador de aciertos para saber cuando lanzar el mensaje de victoria						
						if (aciertos==arr.length){//si hay tantos aciertos como letras mensaje de victoria							
							btt.setEnabled(false);//se inhabilita el botón tras la victoria
							mensajes.setText("¡¡Has ganado!!");
							mensajes.setFont(new Font("Tahoma", Font.BOLD, 20));
							mensajes.setForeground(Color.BLUE); 
						}
					}
				}
			}
									
			if (encontrado ==false) {//si no encuentra la letra en la palabra a desvelar
				contadorImages++; //para recorrer el array de imágenes
				dibujo.setIcon(new ImageIcon(rutaDibujos[contadorImages])); //se actualiza la imagen a mostrar
			}
					
			palabraaDesvelar=convierteArrayenString(arraypalabraaDesvelar); //se pasa a string mediante un método para incorporarlo al label
			palabras.setText(palabraaDesvelar);//se actualiza la palabra a desvelar
		}		

	
	public void actualizarintentos() {
		String[] arr2=letrasIntentos.split("");	
		
		if (encontrado == false) {
			if (contadorIntentos<10) {//se cuenta letras y espacios		
				arr2[contadorIntentos]=letra;
				contadorIntentos+=1;
				arr2[contadorIntentos]=" "; //para espaciar entre letras intentadas
				letrasIntentos=convierteArrayenString(arr2);
				intentos.setText(letrasIntentos);//actualiza el texto de intentos	
				contadorIntentos+=1;			
			} else {//en caso de agotar los 6 intentos fallidos
				arr2[contadorIntentos]=letra;
				letrasIntentos=convierteArrayenString(arr2);
				intentos.setText(letrasIntentos);	
				btt.setEnabled(false);
				mensajes.setText("¡¡PERDISTE!!");
				mensajes.setFont(new Font("Tahoma", Font.BOLD, 20));
				mensajes.setForeground(Color.RED); 
			}
		}	
	}

	public String convierteArrayenString(String[] miarray) { //método para pasar de valor de un array de strings a string
		StringBuffer cadena = new StringBuffer();
		for (int x=0;x<miarray.length;x++){
			cadena =cadena.append(miarray[x]);
			}
		return cadena.toString();
		}
	
	public void diseño() {

		TitledBorder tldpalabras, tldintentos, tldletras, tldmensajes;
		
		frame = new JFrame();
		frame.setSize(540,300);
		
		frame.setIconImage (new ImageIcon("lib/icono.gif").getImage());
		//ImageIcon icono = new ImageIcon("lib/icono.gif");
		//frame.setIconImage(icono.getImage());

		frame.setTitle("Juego del ahorcado");	
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();//barra menú
		JMenu menu = new JMenu("Menú");//opción menú
		menubar.add(menu);
		JMenuItem reiniciar=new JMenuItem("Reiniciar");
		JMenuItem salir = new JMenuItem("Salir");
		menu.add(reiniciar);
		menu.add(salir);
		frame.setJMenuBar(menubar);
		
		reiniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {//botón reiniciar reinicia contadores
				System.out.println("REINICIAR");
				creaPalabra();
				palabras.setText(palabraaDesvelar);
				aciertos=0;
				mensajes.setText("Ciudades españolas");
				mensajes.setFont(new Font("Tahoma", Font.ITALIC, 20));
				mensajes.setForeground(Color.BLACK); 
				contadorIntentos=0;
				contadorImages=0;
				letrasIntentos="- - - - - - ";
				intentos.setText(letrasIntentos);
				btt.setEnabled(true);
							
			}
			
		});
		
		salir.addActionListener(new ActionListener(){//opción salir

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
				
			});
		
		JPanel pan1= new JPanel(new GridLayout(4,1));
		pan1.setBorder(new EmptyBorder(10, 10, 10, 10));//padding para el panel
		
		tldpalabras= BorderFactory.createTitledBorder("PALABRAS"); //Añadiendo borde con texto a cada mensaje;
		tldintentos= BorderFactory.createTitledBorder("INTENTOS"); 
		tldletras= BorderFactory.createTitledBorder("LETRAS"); 
		tldmensajes= BorderFactory.createTitledBorder("MENSAJES");
					
		palabras = new JLabel(palabraaDesvelar);
		palabras.setFont(new Font("Tahoma", Font.BOLD, 20)); 
		palabras.setForeground(Color.BLUE); 
		palabras.setHorizontalAlignment(JLabel.CENTER);
		palabras.setBorder(tldpalabras);
		pan1.add(palabras);
		
		intentos= new JLabel(letrasIntentos);
		intentos.setFont(new Font("Tahoma", Font.BOLD, 20)); 
		intentos.setForeground(Color.RED); 
		intentos.setHorizontalAlignment(JLabel.CENTER);
		intentos.setBorder(tldintentos);
		pan1.add(intentos);
		
		JPanel pnletras = new JPanel(new BorderLayout());
		String[] list = {"A","B","C","D","E","F","G","H","I","J","K",
				"L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; // añadiendo items al combobox
		cbPalabras =new JComboBox(list);
		pnletras.add(cbPalabras, BorderLayout.CENTER);
			
		btt = new JButton("Aceptar");
		pnletras.add(btt, BorderLayout.LINE_END);
		pnletras.setBorder(tldletras);
		btt.addActionListener(new ActionListenerButton());
		
		pan1.add(pnletras);
		
		mensajes= new JLabel("Ciudades españolas");
		mensajes.setFont(new Font("Tahoma", Font.ITALIC, 20));
		mensajes.setForeground(Color.BLACK); 
		mensajes.setHorizontalAlignment(JLabel.CENTER);
		mensajes.setBorder(tldmensajes);
		pan1.add(mensajes);

			
		JPanel pan2= new JPanel();
			
		dibujo=new JLabel("");
		dibujo.setIcon(new ImageIcon(rutaDibujos[contadorImages]));
		
		pan2.add(dibujo);
		
		frame.getContentPane().add(pan1, BorderLayout.CENTER);
		frame.getContentPane().add(pan2, BorderLayout.EAST);
		
		frame.setVisible(true);	
	}
	
	public static void main(String[] args) { //y lo lanzamos todo desde el main
		try
		{
			UIManager.setLookAndFeel("napkin.NapkinLookAndFeel");	
		}
		catch (Exception ex) { }
		
		Principal prueba = new Principal();
		prueba.creaPalabra();
		prueba.diseño();
	}
}