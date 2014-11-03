/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author gwen
 */
public class WordGame extends Game {
    Scanner sc = new Scanner(System.in);
    Random generator = new Random();

    String temp = "";
    private String rank;
    private ArrayList<Quiz> wordList = new ArrayList<Quiz>();
    private ArrayList <Score> scoreList = new ArrayList<Score>();
    private ArrayList <String> oldscoreList = new ArrayList<String>();

    public WordGame(String name, Double score, String rank)
    {
        super(name, score);
        this.rank = rank;   
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ArrayList<Quiz> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<Quiz> wordList) {
        this.wordList = wordList;
    }

    public boolean importWordList (String filename)throws FileNotFoundException
    {
        //number of lines check
        File imported = new File(filename);
        System.out.println(filename);
        if(imported.exists())
        {
            //clear list
            if(wordList.size()!= 0)
            {
                wordList.clear();
            }

            //write it into arraylist
            int counter2 = 0;
            Scanner c = new Scanner(imported);
            while(c.hasNextLine())
            {
                String line = c.nextLine();
                Scanner x = new Scanner(line).useDelimiter(";");
                while(x.hasNext())
                {
                    String hint = x.next().trim();
                    String answer = x.next().trim();
                    Quiz temp = new Quiz(hint, answer);
                    wordList.add(temp);
                    counter2++;
                }
                x.close();
            }
            c.close();

//            for(int i = 0; i < wordList.size(); i++)
//            {
//                int random = generator.nextInt(wordList.size());
//                Quiz temp = wordList.get(i);
//                wordList.remove(i);
//                wordList.add(random, temp);
//            }
            return true;
        }
        else
        {
            return false;
        }
        
    }

    public boolean  addToWordList (Quiz q)
    {
        wordList.add(q);

        boolean check = wordList.contains(q);
        if(check == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean exportWordList (String outFile) throws FileNotFoundException, IOException
    {
        File export = new File(outFile);
        
        if(!export.exists())
        {
            FileWriter xyz123 = new FileWriter(outFile);
        }
        int counter = 0;
        PrintWriter pw = new PrintWriter(export);

        for(int i = 0; i < wordList.size(); i++)
        {
            Quiz temp = wordList.get(i);
            String hint = temp.getWordHint();
            String answer = temp.getAnswer();           
            pw.println(hint + ";" + answer);
            counter++;
        }
        pw.close();

        if(counter == wordList.size())
        {
            return true;
        }

        return false;
    }

    public boolean saveScore (String user) throws FileNotFoundException, IOException
    {
        //write to arraylist(OLDSCORES)
        //Reads the files previous contents and stores them
        int counter2 = 0;
        File scoringFile = new File("C:\\Temp\\Score.txt");
        if(!scoringFile.exists())
        {
            FileWriter xyz123 = new FileWriter(scoringFile);
        }
        Scanner c = new Scanner(scoringFile);

        //Date
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        Date now = new Date();

        String dateToday = formatter.format(date);
        String timeToday = time.format(now);
        String today = dateToday + " " + timeToday;


        //clear previous contents of scoreList
        if(scoreList.size()!= 0)
            {
                scoreList.clear();
            }

        //new score
        Score temporaryStorage = new Score(user, rank, super.getScore(), today);

        //write old scores into arraylist
        while(c.hasNextLine())
        {
            String line = c.nextLine();
            Scanner x = new Scanner(line).useDelimiter(",");
            while(x.hasNext())
            {
                String oldName = x.next().trim();
                String oldRank = x.next().trim();
                String oldScore = x.next().trim();

                double oldScoreDouble = Double.parseDouble(oldScore);//Changes score to a double

                String oldDate = x.next().trim();
                Score oldCombined = new Score(oldName, oldRank, oldScoreDouble, oldDate);
                scoreList.add(oldCombined);
                counter2++;
            }
            x.close();
        }
        c.close();

        //after all old contents are written, add in most recent score
        scoreList.add(temporaryStorage);

        //write to file
        PrintWriter pw = new PrintWriter("C:\\Temp\\Score.txt");
        String stringtemp = "";
        for(int i = 0; i < scoreList.size(); i++)
        {
            Score x = scoreList.get(i);
            stringtemp = x.getUser() + "," + x.getRank() + "," + x.getScore() + "," + x.getDate() + "\n";
            pw.println(stringtemp);
        }
        pw.close();
        return true;
    }

    public String processRanking()
    {
        if(super.getScore()>=90)
            return "General";
        else if(super.getScore() >=80 && super.getScore() <90)
            return "Colonel";
        else if(super.getScore() >=70 && super.getScore() <80)
            return "Lieutenant";
        else if(super.getScore() >=60 && super.getScore() <70)
            return "Major";
        else if(super.getScore() >=50 && super.getScore() <60)
            return "Commander";
        else if(super.getScore() >=40 && super.getScore() <50)
            return "Leader";
        else if(super.getScore() >=30 && super.getScore() <40)
            return "Officer";
        else if(super.getScore() >=20 && super.getScore() <30)
            return "Cadet";
        else if(super.getScore() >=10 && super.getScore() <20)
            return "Rookie";

        return "Maggot";
    }

    public void displayScore() throws FileNotFoundException
    {
        File score = new File("C:\\Temp\\Score.txt");

        Scanner sc = new Scanner(score);
        int counter = 0;

        while(sc.hasNextLine())
        {
            String line = sc.nextLine();
            Scanner x = new Scanner(line).useDelimiter(",");
            while(x.hasNext())
            {
                String name = x.next();
                String rank = x.next();
                String scoring = x.next();
                String date = x.next();
                System.out.println(name + " (" + rank + ")  - " + scoring + "%  " + date );
            }
            counter++;
            x.close();
        }
        sc.close();

        if(counter == 0)
        {
            System.out.println("No scores!");
        }
    }

    public void clearScoreList() throws FileNotFoundException
    {
        PrintWriter pw = new PrintWriter("C:\\Temp\\Score.txt");
        String stringtemp = "";
        for(int i = 0; i < scoreList.size(); i++)
        pw.println("");
        pw.close();
    }

////    public void scoreListRanking()
//    {
//        double max = scoreList.get(0).
//        if(scoreList.size() > 1)
//        {
//            max = scoreList.get(0).getScore();
//            for(int i = 0; i < scoreList.size(); i++)
//            {
//                if(max < scoreList.get(i).getScore())
//                {
//                    //second place
//                    max = scoreList.get(i).getScore();
//                    rankedScoreList.remove(0);
//                    rankedScoreList.add(0, max);
//                }
//            }
//        }
//
//
//        max = scoreList.get(1).getScore();
//        for(int i = 0; i < scoreList.size(); i++)
//        {
//           if(i!= 0)
//           {
//                if(max < scoreList.get(i).getScore())
//                {
//                    //second place
//                    max = scoreList.get(i).getScore();
//                    rankedScoreList.remove(0);
//                    rankedScoreList.add(0, max);
//                }
//           }
//        }
//
//        max = scoreList.get(2).getScore();
//        for(int i = 0; i < scoreList.size(); i++)
//        {
//           if(i!= 0 || i != 1)
//           {
//                if(max < scoreList.get(i).getScore())
//                {
//                    //second place
//                    max = scoreList.get(i).getScore();
//                    Score x = scoreList.get(i);
//                    rankedScoreList.remove(2);
//                    rankedScoreList.add(2, x);
//                }
//           }
//        }
//    }

//    public void rankingstuff()
//    {
//        double max = scoreList.get(0).getScore();
//        for(int x = 0; x < scoreList.size(); x++)
//        {
//            for(int i = 0; i < scoreList.size(); i++)
//            {
//            if(scoreList.get(x).getScore() < scoreList.get(i).getScore())
//            {
//                rankedScoreList.add(scoreList.get(i));
//            }
//        }
//    }

}

