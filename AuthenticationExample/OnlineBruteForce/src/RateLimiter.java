public class RateLimiter {

  private AuthenticationStrategy authStrategy; // The authentication strategy we will delegate to
  private Map<String, WaitStrategy> requestHistory; // A map of IP Addresses to a request history object
  private long millisStepDown;
  
  public RateLimiter(long stepDown) {
    
  }

  public void limit(HttpServletRequest request) {
    String remoteAddr = request.getRemoteAddr();
    if (getRequestHistory().contains(remoteAddr)) {
      // Make the user wait
      // Update their RequestHistory object.
      WaitStrategy waitStrategy = getRequestHistory().get(remoteAddr);
      waitStrategy.requestWait();
    } else {
      long time = System.currentTimeMillis();
      WaitStrategy waitStrategy = new WaitStrategy(time);
      waitStrategy.requestWait();
    }
  }
}

