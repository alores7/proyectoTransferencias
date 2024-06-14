package interfaz;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import clases.Cabecera;
import clases.Cabecera02;
import clases.Cuerpo;
import clases.Cuerpo02;
import clases.Pago;
import xml.GeneradorXMLdesdeExcel;
import xml.MensajeDAO;

public class FrmCreadorXML extends JFrame {
	private JLabel lblTitulo, lblRuta, lblSeleccionado, lblBDoExcelInfo, lblElegir, lblElegirFormato, lblDestinoInfo,
	lblIdCabecera, lblOrdenante, lblFecha, lblMetodoPago;
	private JTextField txtIdCabecera, txtNombre, txtNif, txtIban, txtBic, txtFecha;
	private JButton btnBDoExcel, btnRuta, btnGenerar, btnSalir;
	private JComboBox<String> cmbTipo, cmbFormato, cmbMetodoPago;
	private String [] tipos = {"Base de datos", "Excel"};
	private String [] formatos = {"ISO 20022", "Cuaderno 34", "Cuaderno 34_14"};
	private String [] metodosPago = {"Transferencia", "Cheque", "PayPal"};
	int puertoSQL = 0;
	String userSQL = "", passwordSQL = "", bdSQL = "";
	String rutaOrigen = "", rutaDestino = "";
	
	public FrmCreadorXML() {
		setTitle("Generar XML");
		setSize(690,450);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		lblTitulo = new JLabel("GENERADOR DE XML");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblElegir = new JLabel("¿Desde dónde vienen los datos?");
		lblElegir.setFont(new Font("Arial", Font.PLAIN, 16));
		lblElegirFormato = new JLabel("Tipo de formato:");
		lblElegirFormato.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSeleccionado = new JLabel("Base de datos:");
		lblSeleccionado.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIdCabecera = new JLabel("ID:");
		lblIdCabecera.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRuta = new JLabel("Ruta de destino:");
		lblRuta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBDoExcelInfo = new JLabel("NO SELECCIONADO");
		lblBDoExcelInfo.setForeground(Color.GRAY);
		lblDestinoInfo = new JLabel("NO SELECCIONADA");
		lblDestinoInfo.setForeground(Color.GRAY);
		lblOrdenante = new JLabel("Ordenante:");
		lblOrdenante.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMetodoPago = new JLabel("Método de pago:");
		lblMetodoPago.setFont(new Font("Arial", Font.PLAIN, 16));
		
		txtIdCabecera = new JTextField();
		txtNombre = new JTextField("Nombre");
		txtNombre.setForeground(Color.GRAY);
		txtNombre.setEnabled(false);
		txtNif = new JTextField("NIF");
		txtNif.setForeground(Color.GRAY);
		txtNif.setEnabled(false);
		txtIban = new JTextField("IBAN");
		txtIban.setForeground(Color.GRAY);
		txtIban.setEnabled(false);
		txtBic = new JTextField("BIC");
		txtBic.setForeground(Color.GRAY);
		txtBic.setEnabled(false);
		txtFecha = new JTextField("yyyy-MM-dd");
		txtFecha.setForeground(Color.GRAY);
		txtFecha.setEnabled(false);
		
		btnBDoExcel = new JButton("Seleccionar");
		btnRuta = new JButton("Destino");
		
		cmbTipo = new JComboBox<String>(tipos);
		cmbFormato = new JComboBox<String>(formatos);
		cmbMetodoPago = new JComboBox<String>(metodosPago);
		cmbMetodoPago.setEnabled(false);
		
		btnSalir = new JButton("Salir");
		btnGenerar = new JButton("Generar XML");
		
		add(lblElegirFormato); add(lblOrdenante); add(txtNombre); add(txtNif); add(txtFecha); add(txtIban); add(txtBic);
		add(lblTitulo); add(lblSeleccionado); add(lblRuta); add(lblBDoExcelInfo); add(lblElegir); add(lblDestinoInfo); add(lblIdCabecera);
		add(lblFecha); add(lblMetodoPago);
		add(btnBDoExcel); add(btnRuta); add(cmbTipo); add(btnGenerar); add(txtIdCabecera); add(cmbFormato); add(cmbMetodoPago);
		add(btnSalir);
		
		lblTitulo.setBounds(250, 10, 250, 25);
		
		lblElegir.setBounds(10, 50, 250, 25);
		cmbTipo.setBounds(250, 50, 110, 25);
		lblElegirFormato.setBounds(10, 80, 150, 25);
		cmbFormato.setBounds(130, 80, 110, 25);
		
		lblSeleccionado.setBounds(10, 110, 120, 25);
		btnBDoExcel.setBounds(130, 110, 130, 25);
		lblBDoExcelInfo.setBounds(270, 110, 280, 25);
		
		lblIdCabecera.setBounds(10, 140, 120, 25);
		txtIdCabecera.setBounds(35, 140, 40, 25);
		
		lblRuta.setBounds(10, 170, 120, 25);
		btnRuta.setBounds(130, 170, 100, 25);
		lblDestinoInfo.setBounds(240, 170, 250, 25);
		
		lblOrdenante.setBounds(10, 200, 120, 25);
		txtNombre.setBounds(140, 200, 120, 25);
		txtNif.setBounds(270, 200, 80, 25);
		txtIban.setBounds(360, 200, 190, 25);
		txtBic.setBounds(560, 200, 100, 25);
		
		lblFecha.setBounds(10, 230, 120, 25);
		txtFecha.setBounds(140, 230, 100, 25);
		
		lblMetodoPago.setBounds(10, 260, 120, 25);
		cmbMetodoPago.setBounds(140, 260, 120, 25);
		
		btnSalir.setBounds(140, 300, 200, 35);
		btnGenerar.setBounds(350, 300, 200, 35);		
		
		cmbTipo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	cambiarTipo();
            }
        });
		
		cmbFormato.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	cambiarFormato();
            }
        });
		
		btnBDoExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBDoExcelActionPerformed(evt);
			}
		});
		
		btnRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnRutaActionPerformed(evt);
			}
		});
		
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					btnGenerarActionPerformed(evt);
				} catch (NumberFormatException | IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSalirActionPerformed(evt);
			}
		});
		
		txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNombre.getText().equals("Nombre")) {
                    txtNombre.setText("");
                    txtNombre.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombre.getText().isEmpty()) {
                    txtNombre.setText("Nombre");
                    txtNombre.setForeground(Color.GRAY);
                }
            }
        });

        txtNif.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNif.getText().equals("NIF")) {
                    txtNif.setText("");
                    txtNif.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNif.getText().isEmpty()) {
                    txtNif.setText("NIF");
                    txtNif.setForeground(Color.GRAY);
                }
            }
        });
        
        txtIban.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtIban.getText().equals("IBAN")) {
                    txtIban.setText("");
                    txtIban.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtIban.getText().isEmpty()) {
                    txtIban.setText("IBAN");
                    txtIban.setForeground(Color.GRAY);
                }
            }
        });
        
        txtBic.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBic.getText().equals("BIC")) {
                    txtBic.setText("");
                    txtBic.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBic.getText().isEmpty()) {
                    txtBic.setText("BIC");
                    txtBic.setForeground(Color.GRAY);
                }
            }
        });
        
        txtFecha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtFecha.getText().equals("yyyy-MM-dd")) {
                    txtFecha.setText("");
                    txtFecha.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtFecha.getText().isEmpty()) {
                    txtFecha.setText("yyyy-MM-dd");
                    txtFecha.setForeground(Color.GRAY);
                }
            }
        });
	}

	private void cambiarFormato() {
		if(cmbFormato.getSelectedIndex() == 0) {
			txtNombre.setEnabled(false);
			txtNif.setEnabled(false);
			txtIban.setEnabled(false);
			txtBic.setEnabled(false);
			txtFecha.setEnabled(false);
			cmbMetodoPago.setEnabled(false);
			cmbTipo.setEnabled(true);
		} else if(cmbFormato.getSelectedIndex() == 1) {
			if(cmbTipo.getSelectedIndex() == 0) {
				txtNif.setEnabled(true);
				txtFecha.setEnabled(true);
			} else {
				txtNombre.setEnabled(true);
				txtNif.setEnabled(true);
				txtIban.setEnabled(true);
				txtBic.setEnabled(true);
				txtFecha.setEnabled(true);
				cmbMetodoPago.setEnabled(true);
			}
			cmbTipo.setEnabled(true);
		} else {
			cmbTipo.setSelectedIndex(0);
			cmbTipo.setEnabled(false);
			txtNombre.setEnabled(false);
			txtNif.setEnabled(false);
			txtIban.setEnabled(false);
			txtBic.setEnabled(false);
			txtFecha.setEnabled(false);
			cmbMetodoPago.setEnabled(false);
		}
	}

	private void cambiarTipo() {
		if(cmbTipo.getSelectedIndex() == 0) {
			lblSeleccionado.setText("Base de datos:");
			lblBDoExcelInfo.setText("NO SELECCIONADO");
			btnBDoExcel.setBounds(130, 110, 130, 25);
			lblBDoExcelInfo.setBounds(270, 110, 280, 25);
			txtIdCabecera.setEnabled(true);
			lblIdCabecera.setForeground(Color.BLACK);
			txtNombre.setEnabled(false);
			txtNif.setEnabled(false);
			txtIban.setEnabled(false);
			txtBic.setEnabled(false);
			txtFecha.setEnabled(false);
			cmbMetodoPago.setEnabled(false);
			if(cmbFormato.getSelectedIndex() == 1) {
				txtNif.setEnabled(true);
				txtFecha.setEnabled(true);
			}
		} else {
			lblSeleccionado.setText("Excel:");
			lblBDoExcelInfo.setText("NO SELECCIONADO");
			btnBDoExcel.setBounds(60, 110, 130, 25);
			lblBDoExcelInfo.setBounds(200, 110, 280, 25);
			txtIdCabecera.setEnabled(false);
			lblIdCabecera.setForeground(Color.GRAY);
			if(cmbFormato.getSelectedIndex() == 1) {
				txtNombre.setEnabled(true);
				txtNif.setEnabled(true);
				txtIban.setEnabled(true);
				txtBic.setEnabled(true);
				txtFecha.setEnabled(true);
				cmbMetodoPago.setEnabled(true);
			}
		}
	}

	private void btnBDoExcelActionPerformed(ActionEvent evt) {
		if(cmbTipo.getSelectedIndex() == 0) {
			FrmSeleccionarBD seleccionarBd = new FrmSeleccionarBD();
			seleccionarBd.setFrmCreador(this);
			seleccionarBd.setVisible(true);
		} else {
			JFileChooser fileChooser = new JFileChooser();
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
	        fileChooser.setFileFilter(filter);
	        
	        int seleccion = fileChooser.showOpenDialog(this);
	        if (seleccion == JFileChooser.APPROVE_OPTION) {
	            rutaOrigen = fileChooser.getSelectedFile().getAbsolutePath();
	            lblBDoExcelInfo.setText(rutaOrigen);
	        }
	        lblBDoExcelInfo.setText(rutaOrigen);
		}
	}
	
	private void btnRutaActionPerformed(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos XML", "xml");
	    fileChooser.setFileFilter(filter);
	    
	    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int seleccion = fileChooser.showSaveDialog(this);
	    if (seleccion == JFileChooser.APPROVE_OPTION) {
	        rutaDestino = fileChooser.getSelectedFile().getAbsolutePath();
	        lblDestinoInfo.setText(rutaDestino);
	    }
	}
	
	private void btnGenerarActionPerformed(ActionEvent evt) throws IOException, NumberFormatException, SQLException {
		if(lblBDoExcelInfo.getText().equals("NO SELECCIONADO")) {
			JOptionPane.showMessageDialog(null, "Selecciona el origen", "Origen no seleccionado", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(rutaDestino.equals("")) {
			JOptionPane.showMessageDialog(null, "Falta la ruta de destino", "Ruta de destino inexistente", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timestamp = sdf.format(new Date());
        String timestamp2 = sdf2.format(new Date());
        String nombreArchivo = "mensaje_" + timestamp + ".xml";
        String nombreArchivoCSV = "mensaje_" + timestamp + ".csv";
        String nombreLog = "mensaje_" + timestamp + ".log";
        String ruta2 = rutaDestino + "\\" + nombreArchivoCSV;
        rutaDestino += "\\" + nombreArchivo;
		
		if(cmbTipo.getSelectedIndex() == 0) {
			MensajeDAO mensaje = new MensajeDAO(puertoSQL, userSQL, passwordSQL, bdSQL);
			if(cmbFormato.getSelectedIndex() == 0) {
				if(txtIdCabecera.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Falta el id de mensaje", "ID de mensaje vacío", JOptionPane.WARNING_MESSAGE);
					txtIdCabecera.requestFocusInWindow();
					return;
				}
				Cabecera cabecera = mensaje.getCabeceraById(Integer.parseInt(txtIdCabecera.getText()));
				List<Cuerpo> cuerpos = mensaje.getCuerposByIdMensaje(Integer.parseInt(txtIdCabecera.getText()));
				int resultado = mensaje.generarXML_iso20022(cabecera, cuerpos, rutaDestino);
				if(resultado == 1) {
					JOptionPane.showMessageDialog(null, "XML generado con éxito!", "XML generado", JOptionPane.INFORMATION_MESSAGE);
					crearLog(nombreArchivo, rutaDestino, timestamp2);
				} else {
					JOptionPane.showMessageDialog(null, "Error creando el XML", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if(cmbFormato.getSelectedIndex() == 1) {
				Cabecera02 cabecera = mensaje.obtenerCabeceraPorNIFyFecha(txtNif.getText(), txtFecha.getText());
				List<Cuerpo02> pagos = mensaje.leerPagosC34(txtNif.getText(), txtFecha.getText());
				int resultado = mensaje.generarXML_cuaderno34(cabecera, pagos, rutaDestino);
				if(resultado == 1) {
					JOptionPane.showMessageDialog(null, "XML generado con éxito!", "XML generado", JOptionPane.INFORMATION_MESSAGE);
					crearLog(nombreLog, rutaDestino, timestamp2);
				} else {
					JOptionPane.showMessageDialog(null, "Error generando el XML", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				List<Pago> pagos = mensaje.getPagos();
				int resultado = mensaje.generarCSV_c34_14(pagos, ruta2);
				if(resultado == 1) {
					JOptionPane.showMessageDialog(null, "Excel creado con éxito en " + ruta2, "CSV creado", JOptionPane.INFORMATION_MESSAGE);
					crearLog(nombreArchivoCSV, rutaDestino, timestamp2);
				} else {
					JOptionPane.showMessageDialog(null, "Error creando excel", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			if(cmbFormato.getSelectedIndex() == 0) {
				GeneradorXMLdesdeExcel.convertirCSVaXML(rutaOrigen, rutaDestino);
				JOptionPane.showMessageDialog(null, "XML generado con éxito en " + rutaDestino, "XML generado", JOptionPane.INFORMATION_MESSAGE);
				crearLog(nombreArchivo, rutaDestino, timestamp2);
			} else {
				if(txtNombre.getText().equals("Nombre")) {
					JOptionPane.showMessageDialog(null, "Nombre obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(txtNif.getText().equals("NIF")) {
					JOptionPane.showMessageDialog(null, "NIF obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(txtIban.getText().equals("IBAN")) {
					JOptionPane.showMessageDialog(null, "IBAN obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(txtBic.getText().equals("BIC")) {
					JOptionPane.showMessageDialog(null, "BIC obligatorio", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(txtFecha.getText().equals("yyyy-MM-dd")) {
					JOptionPane.showMessageDialog(null, "Fecha obligatoria", "Advertencia", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				String nmOrdenante = txtNombre.getText();
				String nifOrd = txtNif.getText();
				String iban = txtIban.getText();
				String bic = txtBic.getText();
				String metodoPago = "";
				if(cmbMetodoPago.getSelectedIndex() == 0) {
					metodoPago = "TRF";
				} else if(cmbMetodoPago.getSelectedIndex() == 1) {
					metodoPago = "CHK";
				} else if(cmbMetodoPago.getSelectedIndex() == 2) {
					metodoPago = "PPL";
				}
				String fechaEj = txtFecha.getText();				
				GeneradorXMLdesdeExcel.convertirCSVaXML_cuaderno34(rutaOrigen, rutaDestino, nmOrdenante, nifOrd, iban, bic, metodoPago, fechaEj);
				JOptionPane.showMessageDialog(null, "XML generado con éxito en " + rutaDestino, "XML generado", JOptionPane.INFORMATION_MESSAGE);
				crearLog(nombreArchivo, rutaDestino, timestamp2);
			}
		}
	}
	
	private void btnSalirActionPerformed(ActionEvent evt) {
		System.exit(0);
	}
	
	private void crearLog(String nombreArchivo, String rutaDestino, String timestamp) {
	    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(rutaDestino.replace(".xml", ".log"))))) {
	        out.println("Archivo " + nombreArchivo + " creado a fecha de " + timestamp);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void setCredencialesSQL(int puerto, String user, String password, String bd) {
		puertoSQL = puerto;
		userSQL = user;
		passwordSQL = password;
		bdSQL = bd;
		lblBDoExcelInfo.setText(puertoSQL + " - " + bdSQL);
	}
}
