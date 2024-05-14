import java.awt.geom.*;

public class Par {
  private Ellipse2D nodo;
  private int num_estado;

  Transition[] set_transitions;
  Duo duo;

  public Par(Ellipse2D nodo, int num_estado) {
    this.nodo = nodo;
    this.num_estado = num_estado;
  }

  public Par(Transition[] set_transitions, Duo duo) {
    this.set_transitions = set_transitions;
    this.duo = duo;
  }

  public Ellipse2D getNodo() {
    return nodo;
  }

  public int getNumEstado() {
    return num_estado;
  }
}
