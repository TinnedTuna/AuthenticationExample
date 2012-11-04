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
      // Impose the waiting penalty on the user.
      Thread.sleep((long)Math.exp(currentScore));
    } catch (InterruptedException iex) {
      return;
    } finally {
      long time = System.currentTimeMillis();
      // Calculate the new penalty. If it's been a while since we saw the user,
      // Step their penalty down by a lot.
      long stepsDown = (long)Math.floor((time - timeLastSeen) / stepDownMillis);
      if (stepsDown == 0) {
        //  The queried too fast, increase the penalty.
        currentScore++;
      } else {
        // They queried nicely, decrease their penalty.
        currentScore -= stepsDown;
        // Never decrease their score below 0.
        if (currentScore < 0) {
          currentScore = 0;
        }
      }
      // update the last-seen time.
      this.timeLastSeen = System.currentTimeMillis();
    }
  }
}
