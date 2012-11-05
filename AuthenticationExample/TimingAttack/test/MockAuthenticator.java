public class MockAuthenticator implements AuthenticationStrategy {
  private String validName;
  private String passphrase;

  public MockAuthenticator(String validName, String pass) {
    assert validName != null : "name cannot be null.";
    assert pass != null : "pass cannot be null.";
    this.validName = validName;
    this.passphrase = passphrase;
  }

  public boolean authenticate(String username, String pass) {
    if (this.validName.equals(username) && this.passphrase.equals(pass)) {
      // Simulate working for a long time to hash the pass.
      try {
        Thread.sleep(50);
      } catch (InterruptedException iex) {
        return true;
      }
    } else {
      // Simulate hitting the database only -- quick.
      try {
        Thread.sleep(10);
      } catch (InterruptedException iex) {
        return false;
      }
    }
  }
}

