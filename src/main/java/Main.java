import model.Brain;
import model.Native;
import view.LoginFrame;

public class Main
{
  public static void main(String[] args)
  {
    Native.Build();
    Brain brain = new Brain();
    if (brain.test().booleanValue()) {
      LoginFrame.getInstance();
    }
  }
}
