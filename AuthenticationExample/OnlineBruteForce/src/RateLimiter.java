import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

  private Map<String, WaitStrategy> requestHistory; // A map of IP Addresses to a request history object
  private WaitStrategyFactory waitStrategyFactory;  

  public RateLimiter(WaitStrategyFactory waitStrategyFactory) {
    assert waitStrategyFactory != null : "Cannot wait with no strategy.";
    this.waitStrategyFactory = waitStrategyFactory;
  }

  /**
   * Limit the rate at which a user can make requests to this method.
   * 
   * @param ipAddress Some identifying information about the requesting user -- typically the ip Address.
   */
  public void limit(String ipAddress) {
    String remoteAddr = ipAddress;
    if (getRequestHistory().containsKey(remoteAddr)) {
      // Make the user wait
      // Update their RequestHistory object.
      WaitStrategy waitStrategy = getRequestHistory().get(remoteAddr);
      waitStrategy.requestWait();
    } else {
      // We've never seen this user before.
      long time = System.currentTimeMillis();
      // Get a new wait strategy.
      WaitStrategy waitStrategy = waitStrategyFactory.getInstance();
      waitStrategy.requestWait();
      // Save it.
      getRequestHistory().put(remoteAddr, waitStrategy);
    }
  }

  private Map<String, WaitStrategy> getRequestHistory() {
    if (requestHistory == null) {
      requestHistory = new HashMap<String,WaitStrategy>();
    }
    return requestHistory;
  }
}
