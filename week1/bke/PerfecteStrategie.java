package week1.bke;

public class PerfecteStrategie implements Strategie{

	private Mark myMark;
	private Mark hisMark;
	
	@Override
	public int bepaalZet(Bord bord, Mark mark) {
		myMark = mark;
		hisMark = mark.other();
		Zet zet = simuleerZet(bord, mark);
		return zet.getZet();
	}
	
	public Zet simuleerZet(Bord bord, Mark mark) {
		Zet besteZet = new Zet();
		besteZet.setKwal(Zet.VERLIEZEND);
		
		for (int i = 0; i < 9; i++) {
			if(bord.isLeegVakje(i)) {
				Zet testZet = new Zet();
				testZet.setZet(i);
				testZet.setKwal(Zet.NEUTRAAL);
				bord.setVakje(i, mark);
				if (bord.heeftWinnaar()) {
					besteZet.setKwal(Zet.WINNEND);
					besteZet.setZet(i);
				}	
				
				Zet anderTest = simuleerZet(bord, mark.other());
				if (anderTest.getKwal() == Zet.WINNEND) {
					testZet.setKwal(Zet.VERLIEZEND);
				}

				bord.setVakje(i, Mark.LEEG);
				if (testZet.getKwal() > besteZet.getKwal())
					besteZet = testZet;
				else if (testZet.getKwal() == besteZet.getKwal() && (int)3*Math.random() == 1)
					besteZet = testZet;
			}
		}
		return besteZet;
	}

	@Override
	public String getNaam() {
		return "BKE-Koning";
	}

}
