package bbs_problem;

public class Bbs_problem {
	
	private int bbsID;
	private String userID;
	private String bbsDate;
	private String bbsAnswer;
	private int bbsAvailable;
	private String questionData;
	private byte[] questionImage;
	
	public int getBbsID() {
		return bbsID;
	}
	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBbsDate() {
		return bbsDate;
	}
	public void setBbsDate(String bbsDate) {
		this.bbsDate = bbsDate;
	}
	public String getBbsAnswer() {
		return bbsAnswer;
	}
	public void setBbsAnswer(String bbsAnswer) {
		this.bbsAnswer = bbsAnswer;
	}
	public int getBbsAvailable() {
		return bbsAvailable;
	}
	public void setBbsAvailable(int bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}
	public String getQuestionData() {
		return questionData;
	}
	public void setQuestionData(String questionData) {
		this.questionData = questionData;
	}
	public byte[] getQuestionImage() {
		return questionImage;
	}
	public void setQuestionImage(byte[] questionImage) {
		this.questionImage = questionImage;
	}

	
	
}
