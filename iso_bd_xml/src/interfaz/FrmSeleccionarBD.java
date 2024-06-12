package interfaz;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Base64;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xml.Conexion;

public class FrmSeleccionarBD extends JFrame {
	private JLabel lblTitulo, lblPuerto, lblUser, lblPass, lblBD;
	private JTextField txtPuerto, txtUser, txtBD;
	private JPasswordField txtPass;
	private JButton btnCancelar, btnGuardar;
	private FrmCreadorXML frmCreador;
	private static final String PREF_PUERTO = "puerto";
	private static final String PREF_USERDB = "usuario";
	private static final String PREF_PASSDB = "contraseña";
	private static final String PREF_DB = "basedatos";
//	private static final String SECRET_KEY = "XSCF";
	
	public FrmSeleccionarBD() {
		setTitle("Seleccionar base de datos");
		setSize(380,250);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		lblTitulo = new JLabel("SELECCIONA LA BASE DE DATOS");
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
		lblPuerto = new JLabel("Puerto:");
		lblUser = new JLabel("Usuario:");
		lblPass = new JLabel("Contraseña:");
		lblBD = new JLabel("Base de datos:");
		
		Preferences prefs = Preferences.userNodeForPackage(FrmSeleccionarBD.class);
		String puertoDBGuardado = prefs.get(PREF_PUERTO, "");
	//	String puertoDBGuardado = decrypt(prefs.get(PREF_PUERTO, ""));
		txtPuerto = new JTextField(puertoDBGuardado);
		String userDBGuardado = prefs.get(PREF_USERDB, "");
	//	String userDBGuardado = decrypt(prefs.get(PREF_USERDB, ""));
		txtUser = new JTextField(userDBGuardado);
		String passwordDBGuardado = prefs.get(PREF_PASSDB, "");
	//	String passwordDBGuardado = decrypt(prefs.get(PREF_PASSDB, ""));
		txtPass = new JPasswordField(passwordDBGuardado);
		String DBGuardada = prefs.get(PREF_DB, "");
	//	String DBGuardada = decrypt(prefs.get(PREF_DB, ""));
		txtBD = new JTextField(DBGuardada);
		
		btnCancelar = new JButton("Cancelar");
		btnGuardar = new JButton("Guardar");
		
		add(lblTitulo); add(lblPuerto); add(lblUser); add(lblPass); add(lblBD);
		add(txtPuerto); add(txtUser); add(txtPass); add(txtBD);
		add(btnCancelar); add(btnGuardar);
		
		lblTitulo.setBounds(60, 10, 250, 25);
		
		lblPuerto.setBounds(10, 40, 100, 25);
		txtPuerto.setBounds(100, 40, 60, 25);
		
		lblUser.setBounds(10, 70, 100, 25);
		txtUser.setBounds(100, 70, 80, 25);
		
		lblPass.setBounds(10, 100, 100, 25);
		txtPass.setBounds(100, 100, 80, 25);
		
		lblBD.setBounds(10, 130, 100, 25);
		txtBD.setBounds(100, 130, 80, 25);
		
		btnCancelar.setBounds(70, 170, 100, 25);
		btnGuardar.setBounds(190, 170, 100, 25);
		
		txtBD.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGuardarActionPerformed(null);
                }
            }
        });
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancelarActionPerformed(evt);
			}
		});
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGuardarActionPerformed(evt);
			}
		});
	}

	private void btnCancelarActionPerformed(ActionEvent evt) {
		this.dispose();
	}

	private void btnGuardarActionPerformed(ActionEvent evt) {
		if(txtPuerto.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El puerto es obligatorio", "Puerto vacío", JOptionPane.WARNING_MESSAGE);
			txtPuerto.requestFocusInWindow();
			return;
		}
		if(txtUser.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El nombre de usuario es obligatorio", "Usuario vacío", JOptionPane.WARNING_MESSAGE);
			txtUser.requestFocusInWindow();
			return;
		}
		if(txtBD.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "El nombre de la base de datos es obligatorio", "Base de datos vacía", JOptionPane.WARNING_MESSAGE);
			txtBD.requestFocusInWindow();
			return;
		}
		Preferences prefs = Preferences.userNodeForPackage(FrmSeleccionarBD.class);
	/*	prefs.put(PREF_PUERTO, encrypt(txtPuerto.getText()));
        prefs.put(PREF_USERDB, encrypt(txtUser.getText()));
        prefs.put(PREF_PASSDB, encrypt(new String(txtPass.getPassword())));
        prefs.put(PREF_DB, encrypt(txtBD.getText())); */
        prefs.put(PREF_PUERTO, txtPuerto.getText());
        prefs.put(PREF_USERDB, txtUser.getText());
        prefs.put(PREF_PASSDB, txtPass.getText());
        prefs.put(PREF_DB, txtBD.getText()); 
        Conexion conex = new Conexion();
        int seConecta = conex.seConecta(Integer.parseInt(txtPuerto.getText()), txtUser.getText(), txtPass.getText(), txtBD.getText());
        if(seConecta == 1) {
        	JOptionPane.showMessageDialog(null, "Conectado a la base de datos " + txtBD.getText() + " con éxito", "Conectado", JOptionPane.INFORMATION_MESSAGE);
        	frmCreador.setCredencialesSQL(Integer.parseInt(txtPuerto.getText()), txtUser.getText(), txtPass.getText(), txtBD.getText());
        } else {
        	JOptionPane.showMessageDialog(null, "Error al conectar con " + txtBD.getText(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.dispose();
	}
	
/*	private String encrypt(String data) {
        try {
            byte[] bytes = data.getBytes("UTF-8");
            byte[] keyBytes = SECRET_KEY.getBytes("UTF-8");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= keyBytes[i % keyBytes.length];
            }
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    } 

    private String decrypt(String encryptedData) {
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptedData);
            byte[] keyBytes = SECRET_KEY.getBytes("UTF-8");
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] ^= keyBytes[i % keyBytes.length];
            }
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    } */
	
	public void setFrmCreador(FrmCreadorXML frmCreador) {
        this.frmCreador = frmCreador;
    }
}
