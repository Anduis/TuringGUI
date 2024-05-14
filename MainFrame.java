import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JPopupMenu.*;

public class MainFrame extends JFrame {
  JPanel main_panel;
  JPanel button_panel;
  JPanel draw_panel;
  Nodos dibujar_nodos;
  JButton boton_0;
  JButton boton_1;
  JButton boton_2;
  JButton boton_3;
  JButton boton_4;
  JButton boton_5;

  public MainFrame() {
    setTitle("Máquina de Turing");
    setSize(800, 600);
    setResizable(false);
    setLocationRelativeTo(null);
    iniciarComponentes();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  private void iniciarComponentes() {
    colocarPanel();
    colocarBoton();
  }

  private void colocarPanel() {
    main_panel = new JPanel();
    main_panel.setLayout(null);
    this.getContentPane().add(main_panel);

    button_panel = new JPanel();
    button_panel.setLayout(null);
    button_panel.setBounds(0, 0, 800, 50);

    Separator sep = new Separator();
    sep.setBounds(0, 50, 800, 2);

    draw_panel = new JPanel();
    draw_panel.setLayout(null);
    draw_panel.setBounds(0, 55, 800, 545);

    main_panel.add(button_panel);
    main_panel.add(sep);
    main_panel.add(draw_panel);

    dibujar_nodos = new Nodos();
    dibujar_nodos.setLayout(null);
    dibujar_nodos.setBounds(0, 0, 800, 545);
    draw_panel.add(dibujar_nodos);
  }

  private void colocarBoton() {

    boton_0 = new JButton();
    boton_0.setBounds(10, 5, 40, 40);
    ImageIcon image_0 = new ImageIcon("./iconos/MoveCircle.png");
    boton_0.setIcon(new ImageIcon(image_0.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_0.setBackground(Color.decode("#edd65f"));
    boton_0.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dibujar_nodos.draw_nodes = false;
        dibujar_nodos.draw_transitions = false;
        dibujar_nodos.erase = false;
        dibujar_nodos.move = true;
        dibujar_nodos.check_transitions = false;
        dibujar_nodos.hold_2 = null;
        dibujar_nodos.transPanel.setVisible(false);
        repaint();
        boton_0.setBackground(Color.decode("#edd65f"));
        boton_1.setBackground(null);
        boton_2.setBackground(null);
        boton_3.setBackground(null);
        boton_5.setBackground(null);
      }
    });
    button_panel.add(boton_0);

    boton_1 = new JButton();
    boton_1.setBounds(60, 5, 40, 40);
    ImageIcon image_1 = new ImageIcon("./iconos/AddCircle.png");
    boton_1.setIcon(new ImageIcon(image_1.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dibujar_nodos.draw_nodes = true;
        dibujar_nodos.draw_transitions = false;
        dibujar_nodos.erase = false;
        dibujar_nodos.move = false;
        dibujar_nodos.check_transitions = false;
        dibujar_nodos.hold_2 = null;
        dibujar_nodos.transPanel.setVisible(false);
        repaint();
        boton_0.setBackground(null);
        boton_1.setBackground(Color.decode("#edd65f"));
        boton_2.setBackground(null);
        boton_3.setBackground(null);
        boton_5.setBackground(null);
      }
    });
    button_panel.add(boton_1);

    boton_2 = new JButton();
    boton_2.setBounds(110, 5, 40, 40);
    ImageIcon image_2 = new ImageIcon("./iconos/Arrow.png");
    boton_2.setIcon(new ImageIcon(image_2.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dibujar_nodos.draw_nodes = false;
        dibujar_nodos.draw_transitions = true;
        dibujar_nodos.erase = false;
        dibujar_nodos.move = false;
        dibujar_nodos.check_transitions = false;
        dibujar_nodos.hold_2 = null;
        dibujar_nodos.transPanel.setVisible(false);
        repaint();
        boton_0.setBackground(null);
        boton_1.setBackground(null);
        boton_2.setBackground(Color.decode("#edd65f"));
        boton_3.setBackground(null);
        boton_5.setBackground(null);
      }
    });
    button_panel.add(boton_2);

    boton_3 = new JButton();
    boton_3.setBounds(160, 5, 40, 40);
    ImageIcon image_3 = new ImageIcon("./iconos/Eraser_icon.png");
    boton_3.setIcon(new ImageIcon(image_3.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dibujar_nodos.draw_nodes = false;
        dibujar_nodos.draw_transitions = false;
        dibujar_nodos.erase = true;
        dibujar_nodos.move = false;
        dibujar_nodos.check_transitions = false;
        dibujar_nodos.hold_2 = null;
        dibujar_nodos.transPanel.setVisible(false);
        repaint();
        boton_0.setBackground(null);
        boton_1.setBackground(null);
        boton_2.setBackground(null);
        boton_3.setBackground(Color.decode("#edd65f"));
        boton_5.setBackground(null);
      }
    });
    button_panel.add(boton_3);

    boton_4 = new JButton();
    boton_4.setBounds(260, 5, 40, 40);
    ImageIcon image_4 = new ImageIcon("./iconos/save.png");
    boton_4.setIcon(new ImageIcon(image_4.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try (FileOutputStream fos = new FileOutputStream("out.txt", false)) {
          byte[] b = dibujar_nodos.saveTransitions().getBytes(); // converts string into bytes
          fos.write(b); // writes bytes into file
          fos.close(); // close the file
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    button_panel.add(boton_4);

    boton_5 = new JButton();
    boton_5.setBounds(210, 5, 40, 40);
    ImageIcon image_5 = new ImageIcon("./iconos/info.png");
    boton_5.setIcon(new ImageIcon(image_5.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
    boton_5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dibujar_nodos.draw_nodes = false;
        dibujar_nodos.draw_transitions = false;
        dibujar_nodos.erase = false;
        dibujar_nodos.move = false;
        dibujar_nodos.check_transitions = true;
        boton_0.setBackground(null);
        boton_1.setBackground(null);
        boton_2.setBackground(null);
        boton_3.setBackground(null);
        boton_5.setBackground(Color.decode("#edd65f"));
      }
    });
    button_panel.add(boton_5);
  }
}
