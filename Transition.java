public class Transition
{
  private int currState;
  private String currTape;
  private int nextState;
  private String writeTape;
  private String moveHead;

  public Transition(int currState, String currTape, int nextState, String writeTape, String moveHead)
  {
	this.currState = currState;
	this.currTape = currTape;
	this.nextState = nextState;
	this.writeTape = writeTape;
	this.moveHead = moveHead;
  }
  public int getCurrState()
  {
	return currState;
  }  
  public String getCurrTape()
  {
	  return currTape;
  }
  public int getNextState()
  {
	return nextState;
  }
  public String getWriteTape()
  {
	return writeTape;
  }
  public String getMoveHead()
  {
	return moveHead;
  }
  public String toString()
  {
	return "Transition: " + currState + " " + currTape + " " + nextState + " " + writeTape + " " + moveHead;
  }
}
