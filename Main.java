/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;
/**
 *
 * @author gwen
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int counter;
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        System.out.println("What is your name?");
        String name = sc.nextLine();
        WordGame wordGame = new WordGame(name, 0.0, "Null");
        System.out.println("Press Enter to continue");
        sc.nextLine();

        while(true)
        {
            try
            {               
                displayMenu();
                int choice = sc.nextInt();
                sc.nextLine();

                switch(choice)
                {
                    case 1:
                        //Import word list
                        System.out.println(",-----------------------,");
                        System.out.println("|   Import Word List    |");
                        System.out.println("`-----------------------'");
                        boolean fileBoolean = false;
                        while(true)
                        {
                            //enter destinated file
                            System.out.println("Please enter input file name (or type 'QUIT' to exit)");
                            String fileName = sc.nextLine();

                            if(fileName.equalsIgnoreCase("QUIT"))
                            {
                                System.out.println("Press enter to continue");
                                sc.nextLine();
                                break;
                            }
                            //confirm (loop)
                            File importFile = new File(fileName);

                            while(true)
                            {
                                System.out.println("Are you sure?(Y/N)");
                                String inputConfirmation = sc.nextLine();

                                if(inputConfirmation.equalsIgnoreCase("Y"))
                                {
                                        fileBoolean = wordGame.importWordList(fileName);
                                        if(fileBoolean == true)
                                        {
                                            System.out.println("Import Word File Successfully");
                                            break;
                                        }
                                        else
                                        {
                                            System.out.println("Error, file not found");
                                            break;
                                        }
                                }
                                else if (inputConfirmation.equalsIgnoreCase("N"))
                                {
                                    System.out.println("Word File not imported.");
                                    fileBoolean = true;
                                    break;
                                }
                                else
                                {
                                    System.out.println("Enter either Y or N");
                                }
                            }
                            if(fileBoolean == true)
                            {
                                System.out.println("Press enter to continue");
                                sc.nextLine();
                                break;
                            }
                        }
                        //end

                        break;

                    case 2:
                        //Play Game
                        System.out.println(",----------------,");
                        System.out.println("|   Play Game    |");
                        System.out.println("`----------------'");
                        double scoring = 0;
                        counter = 0;
                        ArrayList<Quiz> wordList= wordGame.getWordList();
//                        int noOfWords = wordList.size();
//                        int randomNum = random.nextInt(noOfWords);

                        for(int i = 0; i < wordList.size(); i++)
                        {
                            int random2 = random.nextInt(wordList.size());
                            Quiz temp = wordGame.getWordList().get(i);
                            wordList.remove(i);
                            wordList.add(random2, temp);
                        }

                        for(int i = 0; i < wordList.size(); i++)
                        {
                            Quiz temp = wordList.get(i);
                            String word = temp.getWordHint();
                            String answer = temp.getAnswer();

                            System.out.println((i+1)+ ". " + word);
                            System.out.println("Please enter answer");
                            String descrambled = sc.nextLine();
                            if(descrambled.equalsIgnoreCase(answer))
                            {
                                System.out.println("Correct!");
                                scoring++;
                            }
                            else
                            {
                                System.out.println("Wrong!");
                            }
                            counter++;

                            if(counter==wordList.size())
                            {
                                break;
                            }
                        }
                        System.out.println("Number of questions: " +counter);
                        if(wordList.size() != 0)
                        {
                            //displaying score
                            double totalScore = ((scoring / wordList.size()) * 100);
                            //update score
                            wordGame.setScore(totalScore);
                            //process ranking
                            String rank = wordGame.processRanking();
                            //set rank
                            wordGame.setRank(rank);
                            //save score
                            wordGame.saveScore(name);
                            System.out.println("Score: " + totalScore + "%");
                            System.out.println("Rank: " + rank);

                            //saving score
                            
                            
                            System.out.println("Press enter to continue");
                            sc.nextLine();
                            break;
                        }
                        else
                        {
                            System.out.println("Please import a word list!");
                            System.out.println("Press enter to continue");
                            sc.nextLine();
                            break;
                        }

                    case 3:
                        //add to word list
                        System.out.println(",-----------------------,");
                        System.out.println("|   Add to Word List    |");
                        System.out.println("`-----------------------'");
                        String addOrNot=null;
                        while(true)
                        {
                            System.out.println("Do you want to make a new list? (Y/N)");
                            String newList = sc.nextLine();

                            if(newList.equalsIgnoreCase("Y"))
                            {
                                ArrayList<Quiz> tempwordList = new ArrayList<Quiz>();
                                tempwordList.clear();
                                wordGame.setWordList(tempwordList);
                                System.out.println("Enter scrambled word");
                                String newWord = sc.nextLine();
                                System.out.println("Enter hint");
                                String newHint = sc.nextLine();
                                System.out.println("Enter correct answer");
                                String newAnswer = sc.nextLine();
                                while(true)
                                {
                                    System.out.println("Are you sure?(Y/N)");
                                    addOrNot = sc.nextLine();
                                    if(addOrNot.equalsIgnoreCase("Y"))
                                    {
                                        String wordAndHint = newWord + (" (") + newHint + (")");
                                        Quiz newQuestion = new Quiz(wordAndHint, newAnswer);
                                        boolean x = wordGame.addToWordList(newQuestion);

                                        if(x==true)
                                        {
                                            System.out.println("New question added successfully.");
                                        }
                                        else
                                        {
                                            System.out.println("Fail to add new question ):");
                                        }
                                        break;
                                    }
                                    else if(addOrNot.equalsIgnoreCase("N"))
                                    {
                                        System.out.println("Word not added.");
                                        break;
                                    }
                                    else
                                    {
                                        System.out.println("Invalid input");
                                    }
                                }
                                if(addOrNot.equalsIgnoreCase("Y") || addOrNot.equalsIgnoreCase("N"))
                                {
                                    break;
                                }
                            }
                            else if(newList.equalsIgnoreCase("N"))
                            {
                                System.out.println("Enter scrambled word");
                                String newWord = sc.nextLine();
                                System.out.println("Enter hint");
                                String newHint = sc.nextLine();
                                System.out.println("Enter correct answer");
                                String newAnswer = sc.nextLine();
                                while(true)
                                {
                                    System.out.println("Are you sure?(Y/N)");
                                    addOrNot = sc.nextLine();
                                    if(addOrNot.equalsIgnoreCase("Y"))
                                    {
                                        String wordAndHint = newWord + (" (") + newHint + (")");
                                        Quiz newQuestion = new Quiz(wordAndHint, newAnswer);
                                        boolean x = wordGame.addToWordList(newQuestion);

                                        if(x==true)
                                        {
                                            System.out.println("New question added successfully.");
                                        }
                                        else
                                        {
                                            System.out.println("Fail to add new question ):");
                                        }
                                        break;
                                    }
                                    else if(addOrNot.equalsIgnoreCase("N"))
                                    {
                                        System.out.println("Word not added.");
                                        break;
                                    }
                                    else
                                    {
                                        System.out.println("Invalid input");
                                    }
                                }
                            }
                            else
                            {
                                System.out.println("Please enter a valid response");
                            }
                        }
                        System.out.println("Press enter to continue");
                        sc.nextLine();
                        break;

                    case 4:
                        //export word list
                        System.out.println(",-----------------------,");
                        System.out.println("|   Export Word List    |");
                        System.out.println("`-----------------------'");

                        while(true)
                        {
                            System.out.println("Please enter the directory and file name");
                            String dir = sc.nextLine();
                            boolean exportedOrNot=false;
                            String confirmExport=null;
                            while(true)
                            {
                                System.out.println("Are you sure?(Y/N)");
                                confirmExport = sc.nextLine();
                                if(confirmExport.equalsIgnoreCase("Y"))
                                {
                                    dir.trim();
                                    exportedOrNot = wordGame.exportWordList(dir);
                                    if(exportedOrNot==true)
                                    {
                                        System.out.println("File exported successfully!");
                                    }
                                    else
                                    {
                                        System.out.println("Unable to export file!");
                                    }
                                    break;
                                }
                                else if(confirmExport.equalsIgnoreCase("N"))
                                {
                                    System.out.println("File not exported");
                                    break;
                                }
                                else
                                {
                                    System.out.println("Enter a valid answer");
                                }
                            }
                            if (exportedOrNot=true || confirmExport.equalsIgnoreCase("N"))
                            {
                                break;
                            }
                        }
                        System.out.println("Press enter to continue");
                        sc.nextLine();
                        break;

                    case 5:
                        //display user score
                        System.out.println(",-------------------------,");
                        System.out.println("|   Display User Score    |");
                        System.out.println("`-------------------------'");
                        wordGame.displayScore();
                        System.out.println("Press enter to continue");
                        sc.nextLine();
                        break;

                    case 6:
                        //clear score list
                        wordGame.clearScoreList();
                        System.out.println("Score list cleared!");
                        System.out.println("Press enter to continue");
                        sc.nextLine();
                        break;

                    case 7:
                        //change user
                        while(true)
                        {
                            System.out.println("What is your name?");
                            String newName = sc.nextLine();

                            System.out.println("Are you sure? (Y/N)");
                            String confirm = sc.nextLine();
                            if(confirm.equalsIgnoreCase("Y"))
                            {
                                name = newName;
                                wordGame.setName(newName);
                                System.out.println("Name changed successfully!");
                                break;
                            }
                            else if (confirm.equalsIgnoreCase("N"))
                            {
                                System.out.println("Name was not updated.");
                                break;
                            }
                            else
                            {
                                System.out.println("Please enter a valid answer.");
                            }
                        }
                        System.out.println("Press enter to continue");
                        sc.nextLine();
                        break;

                    case 8:
                        //exit
                        System.exit(0);

                    
                }
            }

            catch(FileNotFoundException e)
            {
                System.out.println("File not found!");
                sc.nextLine();
                System.out.println("Press enter to continue");
                sc.nextLine();
            }
            catch(InputMismatchException x)
            {
                System.out.println("Please enter a valid option");
                sc.nextLine();
                System.out.println("Press enter to continue");
                sc.nextLine();
            }
            catch(NoSuchElementException a)
            {
                System.out.println("'No such element' exception has occurred");
                System.out.println("Press enter to continue");
                sc.nextLine();
            }
        }
        
    }

    public static void displayMenu()
    {
        System.out.println(",---------------------------,");
        System.out.println("|     Mystery Word Game     |");
        System.out.println("|===========================|");
        System.out.println("|   1.  Import Word List    |");
        System.out.println("|   2.  Play Game           |");
        System.out.println("|   3.  Add to Word List    |");
        System.out.println("|   4.  Export Word List    |");
        System.out.println("|   5.  Display User Score  |");
        System.out.println("|   6.  Clear Score List    |");
        System.out.println("|   7.  Change User         |");
        System.out.println("|   8.  Quit                |");
        System.out.println("'---------------------------'");
        System.out.println("Enter Option: ");
    }

    public static void wordScrambler(String text)
    {
        //Realised I couldn't use this because the word and word hint was together.
        int randomNum;
        String word = text.substring(0, text.length());
        Random generator = new Random();

         while(word.length()>0)
         {
            randomNum = generator.nextInt(word.length());
            System.out.print(word.charAt(randomNum));
            if (randomNum==0)
                word = word.substring(1);
            else if (randomNum == word.length()-1 )
                word = word.substring(0,randomNum);
            else
                word = word.substring(0,randomNum) + word.substring(randomNum+1, word.length());
        }
    }
}
