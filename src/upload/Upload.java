package upload;

import java.io.File;

import bbs_problem.Problem_upload;
import bbs_solve.Solve_upload;

public class Upload {

	public static void main(String[] args) {
			String imagename = "1 2018 11 0 Ȯ�� 18 42 003";
			String Imagepath_problem = "WebContent/problem_images/"+ imagename +".JPG";
			String Imagepath_solve = "WebContent/solve_images/"+ imagename +".JPG";
			int bbsID = 2; //���������� �ϳ��� ����
			int bbsID_solve = 2; //���������� �ϳ��� ����
			
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
			String bbsTitle = Integer.toString(bbsID) + "���� 1��Ǯ��";
			String bbsContent = "�������";
			int problemID = bbsID; //����� Ǯ������ �˷���
			
			try {
			Problem_upload.open(); // ����ƽ�̶� �ν��Ͻ� ���� ���ϰ� �ٷ� ����
			Problem_upload.write(bbsID, userID, questionSource, questionYear, questionMonth, questionType,
				  questionSubject, questionNumber, questionCorrect, questionAnswer, Imagepath_problem);
			
			Solve_upload.open();
			Solve_upload.write(bbsID_solve, bbsTitle, userID, bbsContent, problemID, Imagepath_solve); // problemID�ڸ��� ������bbsID
			
			System.out.println("upload complete");
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

}
