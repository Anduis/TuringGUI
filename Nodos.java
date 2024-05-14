import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class Nodos extends JPanel {
  static int radius = 50;// diametro
  private ArrayList<Par> nodes;
  private ArrayList<Par> transitions;
  private ArrayList<Par> transToRemove;
  private Ellipse2D dragged;
  private Par hold;
  Par hold_2;
  private Point offset;
  public boolean draw_nodes = false;
  public boolean draw_transitions = false;
  public boolean erase = false;
  public boolean move = true;
  public boolean check_transitions = false;
  boolean flag = true;
  LinkedList<Integer> proximo_id_nodo;
  int contador_edos = 1;
  JPanel transPanel;

  public Nodos() {
    nodes = new ArrayList<>();
    transitions = new ArrayList<>();
    transToRemove = new ArrayList<>();
    proximo_id_nodo = new LinkedList<>();
    nodes.add(new Par(new Ellipse2D.Float(50 - (radius / 2), 100 - (radius / 2), radius, radius), 0));
    nodes.add(new Par(new Ellipse2D.Float(350 - (radius / 2), 100 - (radius / 2), radius, radius), -1));

    transPanel = new JPanel();
    transPanel.setLayout(null);
    transPanel.setBounds(600, 0, 199, 507);
    transPanel.setBackground(Color.white);
    transPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    transPanel.setVisible(false);
    ;
    add(transPanel);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (!draw_transitions)
          hold = null;
        if (move) {
          for (Par par : nodes)
            if (par.getNodo().contains(e.getPoint())) {
              dragged = par.getNodo();
              offset = new Point(par.getNodo().getBounds().x - e.getX(), par.getNodo().getBounds().y - e.getY());
              break;
            }
        } else if (draw_nodes) {
          if (proximo_id_nodo.isEmpty()) {
            nodes.add(new Par(new Ellipse2D.Float(e.getX() - 25, e.getY() - 25, radius, radius), contador_edos));
            ++contador_edos;
          } else {
            Collections.sort(proximo_id_nodo);
            nodes.add(
                new Par(new Ellipse2D.Float(e.getX() - 25, e.getY() - 25, radius, radius), proximo_id_nodo.getFirst()));
            proximo_id_nodo.removeFirst();
          }
        } else if (erase) {
          for (Par par : transitions) {
            boolean flag = false;
            for (int i = 0; i < 3; i++) {
              if (par.set_transitions[i] != null) {
                flag = true;
                if (par.set_transitions[i].type == 0) {
                  double distance = par.set_transitions[i].body_0.ptSegDist(e.getPoint());
                  if (distance <= 5) {
                    par.set_transitions[i] = null;
                  }
                } else {
                  if (par.set_transitions[i].body_1.contains(e.getX(), e.getY()))
                    par.set_transitions[i] = null;
                }
              }
            }
            if (par.set_transitions[0] == null && par.set_transitions[1] == null && par.set_transitions[2] == null
                && flag) {
              transToRemove.add(par);
              flag = false;
            }
          }
          for (Par par_array : transToRemove) {
            transitions.remove(par_array);
          }
          transToRemove.clear();
          for (Par node : nodes)
            if (node.getNodo().contains(e.getPoint()) && node.getNumEstado() != -1 && node.getNumEstado() != 0) {
              for (Par trans : transitions) {
                if (trans.duo.num_start == node.getNumEstado() || trans.duo.num_target == node.getNumEstado()) {
                  transToRemove.add(trans);
                }
              }
              for (Par trans : transToRemove) {
                transitions.remove(trans);
              }
              transToRemove.clear();
              proximo_id_nodo.add(node.getNumEstado());
              nodes.remove(node);
              break;
            }
        } else if (draw_transitions) {
          boolean isClicked = false;
          for (Par node : nodes) {
            if (node.getNodo().contains(e.getPoint())) {
              isClicked = true;
              if (hold != null) {
                int i = -1;
                for (Par trans : transitions) {
                  if (hold.getNumEstado() == trans.duo.num_start && node.getNumEstado() == trans.duo.num_target) {
                    i = transitions.indexOf(trans);
                    break;
                  }
                }
                if (hold.getNumEstado() != node.getNumEstado()) {
                  if (i == -1) {
                    i = transitions.size();
                    transitions.add(new Par(new Transition[3], new Duo(hold.getNumEstado(), node.getNumEstado())));
                    transitions.get(i).set_transitions[0] = new Transition(hold, node, 0);
                    hold = null;
                    break;

                  } else {
                    for (int index = 0; index < 3; index++) {
                      if (transitions.get(i).set_transitions[index] == null) {
                        transitions.get(i).set_transitions[index] = new Transition(hold, node, index);
                        break;
                      }
                    }

                    hold = null;
                    break;
                  }
                } else {
                  if (i == -1) {
                    i = transitions.size();
                    transitions.add(new Par(new Transition[3], new Duo(hold.getNumEstado(), node.getNumEstado())));
                    transitions.get(i).set_transitions[0] = new Transition(hold, node, 3);
                    hold = null;
                    break;

                  } else {
                    for (int index = 0; index < 3; index++) {
                      if (transitions.get(i).set_transitions[index] == null) {
                        transitions.get(i).set_transitions[index] = new Transition(hold, node, index + 3);
                        break;
                      }
                    }

                    hold = null;
                    break;
                  }
                }
              }
              if (node.getNumEstado() != -1)
                hold = node;
            }
          }
          if (!isClicked && hold != null)
            hold = null;
        } else if (check_transitions) {
          for (Par node : nodes) {
            if (node.getNodo().contains(e.getPoint())) {
              hold_2 = node;
              transPanel.setVisible(true);
              implementTransPanel(hold_2.getNumEstado());
            }
          }
        }
        repaint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (dragged != null)
          repaint();
        dragged = null;
        offset = null;
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        String L = "./iconos/Eraser_icon.png";// cursor location
        if (erase)
          setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(L).getImage(), new Point(0, 14), "E"));
        else if (draw_transitions || check_transitions)
          setCursor(new Cursor(Cursor.HAND_CURSOR));
        else
          setCursor(Cursor.getDefaultCursor());/* */
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        if (move)
          if (dragged != null && offset != null) {
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
            Point to = e.getPoint();
            to.x += offset.x;
            to.y += offset.y;
            Rectangle bounds = dragged.getBounds();
            bounds.setLocation(to);
            dragged.setFrame(bounds);
            repaint();
          }
      }
    });
  }

  public void setBooleanTransPanel(boolean bool) {
    if (bool) {
      transPanel.setVisible(true);
    } else {
      transPanel.setVisible(false);
    }
  }

  public void implementTransPanel(int num_estado) {
    transPanel.removeAll();
    JLabel title = new JLabel("Change the transitions: ");
    title.setBounds(10, 5, 180, 30);
    transPanel.add(title);
    int y = 20;
    if (transitions != null) {
      for (Par par : transitions) {
        if (par.duo.num_start == num_estado) {
          String string_1 = String.valueOf(par.duo.num_start);
          if (string_1.equals("-1"))
            string_1 = "h";
          String string_2 = String.valueOf(par.duo.num_target);
          if (string_2.equals("-1"))
            string_2 = "h";
          String string_f = string_1 + " -> " + string_2;
          JLabel nodes = new JLabel(string_f);
          nodes.setBounds(10, y, 100, 40);
          y += 30;
          transPanel.add(nodes);

          for (int i = 0; i < 3; i++) {
            if (par.set_transitions[i] != null) {
              boolean isFree = true;
              JComboBox<Character> box_1 = new JComboBox<>();
              box_1.setSize(20, 20);
              box_1.setBounds(20, y, 40, 20);
              box_1.addItem(par.set_transitions[i].read);
              box_1.setLightWeightPopupEnabled(false);
              for (int j = 0; j < 3; j++) {
                if (j != i && par.set_transitions[j] != null) {
                  if (par.set_transitions[j].read == 'a')
                    isFree = false;
                }
              }
              if ('a' != par.set_transitions[i].read && isFree) {
                box_1.addItem('a');
              }
              isFree = true;
              for (int j = 0; j < 3; j++) {
                if (j != i && par.set_transitions[j] != null) {
                  if (par.set_transitions[j].read == 'b')
                    isFree = false;
                }
              }
              if ('b' != par.set_transitions[i].read && isFree) {
                box_1.addItem('b');
              }
              isFree = true;
              for (int j = 0; j < 3; j++) {
                if (j != i && par.set_transitions[j] != null) {
                  if (par.set_transitions[j].read == '#')
                    isFree = false;
                }
              }
              if ('#' != par.set_transitions[i].read && isFree) {
                box_1.addItem('#');
              }
              int n = i;
              box_1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                  Object selected = box_1.getSelectedItem();
                  if ('a' == selected.toString().charAt(0)) {
                    par.set_transitions[n].read = 'a';
                  } else if ('b' == selected.toString().charAt(0)) {
                    par.set_transitions[n].read = 'b';
                  } else if ('#' == selected.toString().charAt(0)) {
                    par.set_transitions[n].read = '#';
                  } else {
                    par.set_transitions[n].read = 'r';
                  }
                  repaint();
                }
              });
              transPanel.add(box_1);

              JComboBox<Character> box_2 = new JComboBox<>();
              box_2.setSize(20, 20);
              box_2.setBounds(80, y, 40, 20);
              box_2.setLightWeightPopupEnabled(false);
              y += 25;
              box_2.addItem(par.set_transitions[i].write);
              if ('<' != par.set_transitions[i].write) {
                box_2.addItem('<');
              }
              if ('>' != par.set_transitions[i].write) {
                box_2.addItem('>');
              }
              if ('a' != par.set_transitions[i].write) {
                box_2.addItem('a');
              }
              if ('b' != par.set_transitions[i].write) {
                box_2.addItem('b');
              }
              if ('#' != par.set_transitions[i].write) {
                box_2.addItem('#');
              }
              box_2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                  Object selected = box_2.getSelectedItem();
                  if ('<' == selected.toString().charAt(0)) {
                    par.set_transitions[n].write = '<';
                  } else if ('>' == selected.toString().charAt(0)) {
                    par.set_transitions[n].write = '>';
                  } else if ('a' == selected.toString().charAt(0)) {
                    par.set_transitions[n].write = 'a';
                  } else if ('b' == selected.toString().charAt(0)) {
                    par.set_transitions[n].write = 'b';
                  } else if ('#' == selected.toString().charAt(0)) {
                    par.set_transitions[n].write = '#';
                  } else {
                    par.set_transitions[n].write = 'w';
                  }
                  repaint();
                }
              });
              transPanel.add(box_2);
            }
          }
        }
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(3f));

    if (transitions != null)
      for (Par trans_array : transitions) {
        for (Transition trans : trans_array.set_transitions) {
          if (trans != null) {
            trans.draw(g);
          }
        }
      }

    for (Par par : nodes) {
      Ellipse2D node = par.getNodo();
      Point to = node.getBounds().getLocation();
      to.x += radius / 2;
      to.y += radius / 2;
      g2d.setColor(Color.decode("#22a6b3"));
      if (par.getNumEstado() != -1 && par.getNumEstado() != 0)
        g2d.setColor(Color.decode("#22a6b3"));
      else if (par.getNumEstado() == -1) {
        g2d.setColor(Color.decode("#eb3d3b"));
        g2d.drawOval(to.x - radius / 2 - 5, to.y - radius / 2 - 5, radius + 10, radius + 10);
      } else if (par.getNumEstado() == 0) {
        g2d.setColor(Color.decode("#3cd137"));
        g2d.drawPolyline(new int[] { to.x - 35, to.x - 25, to.x - 35 }, new int[] { to.y - 15, to.y, to.y + 15 }, 3);
      }
      g2d.fill(node);
      if (hold != null)
        if (node == hold.getNodo()) {
          g2d.setColor(Color.decode("#130f30"));
          g2d.draw(node);
        }
      if (hold_2 != null)
        if (node == hold_2.getNodo()) {
          g2d.setColor(Color.decode("#2706f7"));
          g2d.draw(node);
          implementTransPanel(hold_2.getNumEstado());
        }
      if (node == dragged) {
        g2d.setColor(Color.decode("#f79f1f"));
        g2d.draw(node);
      }

      String text;
      if (par.getNumEstado() == -1)
        text = "h";
      else
        text = String.valueOf(par.getNumEstado());

      FontMetrics fm = g.getFontMetrics();
      int textWidth = fm.stringWidth(text);
      int x = node.getBounds().x;
      int y = node.getBounds().y;
      int width = node.getBounds().width;
      int height = node.getBounds().height;
      g.setFont(new Font("default", Font.BOLD, 16));
      g.drawString(text,
          x + ((width - textWidth)) / 2,
          y + ((height - fm.getHeight()) / 2) + fm.getAscent());
    }
    g2d.dispose();
  }

  String saveTransitions() {
    String ans = "";
    if (transitions != null)
      for (Par trans_array : transitions)
        for (Transition trans : trans_array.set_transitions)
          if (trans != null) {
            if (trans.target.getNumEstado() == -1) {
              if (trans.write == '<')
                ans += trans.start.getNumEstado() + "," + trans.read + ",h," + "i\n";
              else if (trans.write == '>')
                ans += trans.start.getNumEstado() + "," + trans.read + ",h," + "d\n";
              else
                ans += trans.start.getNumEstado() + "," + trans.read + ",h," + trans.write + "\n";
            } else if (trans.write == '<')
              ans += trans.start.getNumEstado() + "," + trans.read + "," + trans.target.getNumEstado() + ","
                  + "i\n";
            else if (trans.write == '>')
              ans += trans.start.getNumEstado() + "," + trans.read + "," + trans.target.getNumEstado() + ","
                  + "d\n";
            else
              ans += trans.start.getNumEstado() + "," + trans.read + "," + trans.target.getNumEstado() + ","
                  + trans.write + "\n";
          }
    return ans;
  }
}
