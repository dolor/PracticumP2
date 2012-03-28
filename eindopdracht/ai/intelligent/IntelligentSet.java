package eindopdracht.ai.intelligent;

import eindopdracht.ai.IntelligentAI;
import eindopdracht.ai.Position;
import eindopdracht.model.Board;

public class IntelligentSet {

	int score;
	Position pos;
	boolean otherplayermove;
	int chainSameBlock = 0;
	int chainDiagonal = 0;
	int chainHorizontal = 0;
	int chainVertical = 0;
	int sameBlock = 0;
	boolean centerCenter = false;
	boolean centerOther = false;
	boolean instantWin = false;
	
	public IntelligentSet(Position pos, Board b)
	{
		this.pos = pos;
	}
	
	public void setChainSameBlock(int chain)
	{
		this.chainSameBlock = chain;
	}
	public int getChainSameBlock()
	{
		return this.chainSameBlock;
	}
	
	public void setSameBlock(int count)
	{
		this.sameBlock = count;
	}
	public int getSameBlock()
	{
		return this.sameBlock;
	}
	
	public void setChainDiagonal(int chain)
	{
		this.chainDiagonal = chain;
	}
	public int getChainDiagonal()
	{
		return this.chainDiagonal;
	}
	
	public void setChainHorizontal(int chain)
	{
		this.chainHorizontal = chain;
	}
	public int getChainHorizontal()
	{
		return this.chainHorizontal;
	}
	
	public void setChainVertical(int chain)
	{
		this.chainVertical = chain;
	}
	public int getChainVertical()
	{
		return this.chainVertical;
	}
	
	public void setCenterCenter()
	{
		this.centerCenter = true;
	}
	public boolean isCenterCenter()
	{
		return this.centerCenter;
	}
	
	public void setCenterOther()
	{
		this.centerOther = true;
	}
	public boolean isCenterOther()
	{
		return this.centerOther;
	}
	
	public void setInstantWin()
	{
		this.instantWin = true;
	}
	public boolean isInstantWin()
	{
		return this.instantWin;
	}
	
	
	
	public void setOtherPlayerMove(boolean otherplayermove)
	{
		this.otherplayermove = otherplayermove;
	}
	public boolean isOtherPlayerMove()
	{
		return this.otherplayermove;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	public void calculateScore()
	{
		this.setScore(0);
		
		if (this.getChainDiagonal() > 1)
			this.addScore(this.getChainDiagonal() * IntelligentAI.CHAIN_DIAGONAL);
		
		if (getChainVertical() > 1)
			this.addScore(getChainVertical() * IntelligentAI.CHAIN_VERTICAL);
		
		if (getChainHorizontal() > 1)
			this.addScore(getChainHorizontal() * IntelligentAI.CHAIN_HORIZONTAL);

		if (this.getChainSameBlock()> 1)
			this.addScore(getChainSameBlock() * IntelligentAI.CHAIN_SAME_BLOCK);
		
		if (this.getSameBlock() > 1)
			this.addScore(this.getSameBlock() * IntelligentAI.SAME_BLOCK);
		
		if (this.isInstantWin())
			this.addScore(IntelligentAI.INSTANT_WIN);
		
		if (this.isCenterCenter())
			this.addScore(IntelligentAI.CENTER_CENTER);
		
		if (this.isCenterOther())
			this.addScore(IntelligentAI.CENTER_OTHER);
		
		if (this.isOtherPlayerMove())
			this.addScore(IntelligentAI.OTHER_PLAYER_MOVE);
		
	}
	public int getScore()
	{
		

		return score;
	}
	public void addScore(int add)
	{
		setScore(this.getScore() + add);
	}
	
	public Position getPosition()
	{
		return pos;
	}
}
