
public class MockAuthenticationStrategy implements AuthenticationStrategy {

  private int count = 0;

  /**
   * This is a very simple auth method: we ignore the input and randomly 
   * return true or false. The first 5 requests are always true to let things
   * warm up.
   */
  @Override
  public boolean authenticate(String username, String password) {
    if (count < 5) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException iex) { 
        return true;
      }
      count++;
      return true;
    }
    boolean result = (Math.floor(Math.random() * 10) % 2) == 0;
    if (result) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException iex) {
        return result;
      }
      return result;
    } else {
      return result;
    }
  }
}
