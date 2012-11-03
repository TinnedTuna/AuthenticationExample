import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

  private Map<String, WaitStrategy> requestHistory; // A map of IP Addresses to a request history object
  private long millisStepDown;
  
  public RateLimiter(long stepDown) {
    this.millisStepDown = stepDown;  
  }

  public void limit(String ipAddress) {
    String remoteAddr = ipAddress;
    if (getRequestHistory().containsKey(remoteAddr)) {
      // Make the user wait
      // Update their RequestHistory object.
      WaitStrategy waitStrategy = getRequestHistory().get(remoteAddr);
      waitStrategy.requestWait();
    } else {
      long time = System.currentTimeMillis();
      WaitStrategy waitStrategy = new ExponentialWaitStrategy(time, millisStepDown);
      waitStrategy.requestWait();
    }
  }

  private Map<String, WaitStrategy> getRequestHistory() {
    if (requestHistory == null) {
      requestHistory = new HashMap<String,WaitStrategy>();
    }
    return requestHistory;
  }
}
