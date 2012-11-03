
public class RateLimitingExample {

  public static void main(String args[]) {
  
    RateLimiter rateLimiter = new RateLimiter(50);

    for (int i = 0; i < 100; i++) {
      Long start = Long.valueOf(System.currentTimeMillis());
      rateLimiter.limit("1.2.3.4"); // Pretend we're attacking from one ip.
      System.out.println("Waited: "+(Long.valueOf(System.currentTimeMillis()) - start));
    }
    
  }
}
