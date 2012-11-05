import org.junit.Test;
import org.junit.Assert.*;

public class AverageTimeConstantTest {
  
  private AuthenticationStrategy authenticationStrategy;

  public AverageTimeConstantTest() {
    this.authenticationStrategy = new ConstantTimeStrategy(new MockAuthenticator("Good","Good"));
  }

  @org.junit.Test
  public void testConstantTime() {
    Command badAuth = new Command() {
      @Override
      public void execute() {
        AverageTimeConstantTest.this.authenticationStrategy.authenticate("Bad","Bad");
      }
    };

    Command goodAuth = new Command() {
      @Override
      public void execute() {
        AverageTimeConstantTest.this.authenticationStrategy.authenticate("Good","Good");
      }
    };

    long goodTime;
    long badTime;
    for (int i = 0; i < 100; i++) {
      goodTime += time(goodAuth);
      badTime += time(badAuth);
    }

    Assert.assertTrue(Math.abs(goodTime-badTime) < 10);


  }

  private long time(Command c) {
    long start = System.currentTimeMillis();
    c.execute();
    return System.currentTimeMillis() - start;
  }
}
