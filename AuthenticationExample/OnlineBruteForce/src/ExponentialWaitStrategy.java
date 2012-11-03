public class ExponentialWaitStrategy implements WaitStrategy {

  private long timeLastSeen;
  private long currentScore;
  private long stepDownMillis;

  public ExponentialWaitStrategy(long timeSeen, long stepDownMillis) {
    assert stepDownMillis != 0 : "Cannot reduce penalty by 0.";
    this.timeLastSeen = timeSeen;
    this.currentScore = 0;
    this.stepDownMillis = stepDownMillis;
  }
  
  public void requestWait() {
    try {
      Thread.sleep((long)Math.exp(currentScore));
    } catch (InterruptedException iex) {
      return;
    } finally {
      long time = System.currentTimeMillis();
      long stepsDown = (long)Math.floor((time - timeLastSeen) / stepDownMillis);
      if (stepsDown == 0) {
        currentScore++;
      } else {
        currentScore += stepsDown;
      }
      this.timeLastSeen = System.currentTimeMillis();
    }
  }
}
