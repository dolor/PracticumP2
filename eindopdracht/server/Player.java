package eindopdracht.server;

public class Player {
	private String name;
    private int preferredNumberOfPlayers;

	public void processNetworkInput(String input) {
		System.out.println(input);
	}
	
	public void setNumberOfPlayers(int number) {
		this.preferredNumberOfPlayers = number;
	}
	
	public int preferredNumberOfPlayers() {
		return this.preferredNumberOfPlayers;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String name() {
		return this.name;
	}
	
	public void join() {
		System.out.println("Now trying to join");
		//TODO write joining code
	}
}
