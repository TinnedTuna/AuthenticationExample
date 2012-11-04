public interface WaitStrategy {

  /**
   * This should wait for an amount of time, often determined by the user's
   * history.
   */
  public void requestWait();
}
