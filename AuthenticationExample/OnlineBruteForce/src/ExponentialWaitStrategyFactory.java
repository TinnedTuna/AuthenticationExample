public class ExponentialWaitStrategyFactory implements WaitStrategyFactory {
  
  private long stepDownMillis;
 
  public ExponentialWaitStrategyFactory(long stepDownMillis) {
    assert stepDownMillis > 0 : "Cannot wait an amount of time less than 1ms.";
    this.stepDownMillis = stepDownMillis;
  }

  public WaitStrategy getInstance() {
    return new ExponentialWaitStrategy(System.currentTimeMillis(), stepDownMillis);
  }
}
