
public class ConstantTimeStrategy implements AuthenticationStrategy {

  private AuthenticationStrategy authStrategy; // The strategy we will delegate to.

  private long totalWaitTime = 0;
  private long totalRequests = 1;

  public ConstantTimeStrategy(AuthenticationStrategy authStrategy) {
    assert authStrategy != null : "Cannot have a null AuthenticationStrategy.";
    this.authStrategy = authStrategy;
  }

  /**
   * This method performs the authentication as per the injected 
   * AuthenticationStrategy, however, it takes constant time.
   * 
   * This method takes a little while to "warm-up", so the first few requests 
   * for authentication may expose the underlying time-properties of the 
   * underlying AuthenticationStrategy. Be sure to let it warm up by ensuring 
   * that the first few (5 - 10) authentications are genuine and correct.
   *
   * @param 
   */
  @Override
  public boolean authenticate(String username, String password) {
    long start = System.currentTimeMillis();
    // delegate
    boolean result = authStrategy.authenticate(username, password);
    if (result) {
      // It was correct! update the statistics.
      totalRequests += 1;
      totalWaitTime += (start - System.currentTimeMillis());
      return result;
    } else {
      // It was incorrect. Use the stats to wait an exactly average amount of 
      // time.
      this.waitExtraTime(start);
      return result;
    }
  }

  private void waitExtraTime(long startTime) {
    long average = totalWaitTime / totalRequests;
    long timeTakenSoFar = System.currentTimeMillis() - startTime;
    // Fill in the extra time with just sleeping.
    try {
      Thread.sleep(Math.abs(average - timeTakenSoFar));
    } catch (InterruptedException iex) {
      return;
    }
  }
  
}
