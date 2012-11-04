
public class RateLimitingExample {

  private static final long BACKOFF_TIME = 30000;

  public static void main(String args[]) {
  
    
    WaitStrategyFactory waitStrategyFactory = new ExponentialWaitStrategyFactory(BACKOFF_TIME);
    RateLimiter rateLimiter = new RateLimiter(waitStrategyFactory); // Upto a 30s penalty.

    // Ten fast requests.
    for (int i = 0; i < 10; i++) {
      Long start = Long.valueOf(System.currentTimeMillis());
      rateLimiter.limit("1.2.3.4"); // Pretend we're attacking from one ip.
      System.out.println("Waited: "+(Long.valueOf(System.currentTimeMillis()) - start));
    }
  
    // Ten slow requests.
    System.out.println("Ten slow requests...");
    for (int i = 0; i < 10; i++) {
      try { 
        Thread.sleep(BACKOFF_TIME+10);
      } catch (InterruptedException iex) {
      }
      Long start = Long.valueOf(System.currentTimeMillis());
      rateLimiter.limit("1.2.3.4"); // Pretend we're attacking from one ip.
      System.out.println("Waited: "+(Long.valueOf(System.currentTimeMillis()) - start));
    }
    
  }
}
