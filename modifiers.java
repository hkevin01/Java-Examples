


public class modifiers{

  // int - is 32-bit  ||  long 64-bit ||  short - 16 bit
  // double - is double-precision floating point value
  //-------------------------------------
  // Variable Declaration
  //-------------------------------------
  private int priv_variable;    // only accessible by container class
  protected int prot_variable;  // only accessible by the package
  public int pub_variable;      // accessible when the class is accessible
  static int stat_variable;     // independent of instantiation (changing value will change value in all instances)
  final int fin_variable = 0;       // cannot have value changed (must have a value?)
  
  //---------------------------------------
  // Methods
  //---------------------------------------
  public void pub_method(){} // accessible by where the class ir package is accessible
  private void priv_method(){} // accessible only by the container class
 




}
