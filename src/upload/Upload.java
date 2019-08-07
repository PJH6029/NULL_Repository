package upload;

import java.io.File;

import bbs_problem.Problem_upload;
import bbs_solve.Solve_upload;

public class Upload {

	public static void main(String[] args) {
			String imagename = "1 2018 11 0 확통 18 42 003";
			String Imagepath_problem = "WebContent/problem_images/"+ imagename +".JPG";
			String Imagepath_solve = "WebContent/solve_images/"+ imagename +".JPG";
			int bbsID = 2; //넣을때마다 하나씩 증가
			int bbsID_solve = 2; //넣을때마다 하나씩 증가
			
			File f = new File(Imagepath_problem);

			String path = "";
			String filename = "";
			path = f.getParentFile().toString();
			filename = f.getName();
			filename = filename.substring(0, filename.length()-4);
			String[] file_array = filename.split(" ");
			
			String questionSource = file_array[0];
			String questionYear = file_array[1];
			String questionMonth = file_array[2];
			String questionType = file_array[3];
			String questionSubject = file_array[4];
			String questionNumber = file_array[5];
			int questionCorrect = Integer.parseInt(file_array[6]);
			String questionAnswer = file_array[7];


			
			String userID = "top321902";
			String bbsTitle = Integer.toString(bbsID) + "번의 1번풀이";
			String bbsContent = "내맘대로";
			int problemID = bbsID; //어디의 풀이인지 알려줌
			
			try {
			Problem_upload.open(); // 스태틱이라 인스턴스 생성 안하고 바로 실행
			Problem_upload.write(bbsID, userID, questionSource, questionYear, questionMonth, questionType,
				  questionSubject, questionNumber, questionCorrect, questionAnswer, Imagepath_problem);
			
			Solve_upload.open();
			Solve_upload.write(bbsID_solve, bbsTitle, userID, bbsContent, problemID, Imagepath_solve); // problemID자리에 문제의bbsID
			
			System.out.println("upload complete");
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}
