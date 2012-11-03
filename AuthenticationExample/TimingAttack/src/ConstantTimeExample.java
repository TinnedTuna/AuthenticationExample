public class ConstantTimeExample {

  public static void main(String args[]) {
  
    AuthenticationStrategy authStrategy = new ConstantTimeStrategy(new MockAuthenticationStrategy());
    for (int i = 0; i < 100; i++) {
      Long start = Long.valueOf(System.currentTimeMillis());
      boolean res = attemptAuth(authStrategy);
      System.out.println("Res: " + (res ? "True" : "False") + " Time: "+(Long.valueOf(System.currentTimeMillis()) - start));
    }
    
  }

  private static boolean attemptAuth(AuthenticationStrategy authStrategy) {
    return authStrategy.authenticate("BobSmith", "ReallyGoodPassword!");
  }
}
