// Kippum Joseph Chang         KJC190001
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void display(String name,float []stat, float BA, float OB, PrintWriter p1)
    {   //printing out the stat of each player
        p1.println(name);
        p1.printf("BA: %.3f%n"+"OB%%: %.3f%n",BA,OB);
        p1.println("H:"+" "+Math.round(stat[0]));
        p1.println("BB:"+" "+Math.round(stat[3]));
        p1.println("K:"+" "+Math.round(stat[2]));
        p1.println("HBP:"+" "+Math.round(stat[4]));
        p1.println();
    }
    public static int numberofplayers (String filename) throws IOException
    { // counting the number of player by reading each line of the inputfile
        int countplayer = 0;
        LineNumberReader L1= new LineNumberReader(new FileReader(filename));
        while ((L1.readLine())!=null) {};
        countplayer = L1.getLineNumber();
        L1.close();
        return countplayer;
    }
    public static float Findingleader(boolean[] ineligible, float[] stat, int numberofplayer)
    {   // iterating through the array to find the highest value
        float leadervalue=0;
        leadervalue = stat[0];
        for (int a = 0; a<numberofplayer; a++)
        {

            if (leadervalue<stat[a] && ineligible[a] != true)
            {
                leadervalue = stat[a];
            }
        }
        return leadervalue;
    }
    public static float FindingKleader(boolean[] ineligible, float[] kstat, int numberofplayer)
    {   //iterating through the array to find the lowest value
        float leaderKvalue=0;
        leaderKvalue = Float.POSITIVE_INFINITY;;
        for (int a = 0; a<numberofplayer; a++)
        {

            if (leaderKvalue>kstat[a] && ineligible[a] != true)
            {
                leaderKvalue = kstat[a];
            }
        }
        return leaderKvalue;
    }
    public static String displayleader(boolean[] ineligible, String[] name, float leadervalue, float[] stat, int numberofplayer)
    {   //iterating through the array to find the name that matches with the leader value

        String leadername = "";
        int j = 0;
        for (int i=0;i<numberofplayer;i++)
        {
            if (leadervalue==stat[i] && ineligible[i] != true) {
                if (j < 1) {
                    leadername=name[i];
                } else {
                    leadername = leadername + ", " + name[i];//if there is more than one leader, it prints out the names of the leader with comma between.
                }
                j++;
            }
        }
        return leadername;
    }



    public static void main(String[] args) {
        System.out.println("Please enter the name of the input file");
        Scanner scan = new Scanner(System.in);
        String inputfilename = scan.nextLine();
        scan.close();
        String line;
        int i = 0;
        String []namearray = new String[30];
        float []battingaverage = new float[30];
        float []onbasepercentage = new float [30];
        float []hitarray = new float[30];
        float []walkarray = new float[30];
        float []strikeoutarray = new float[30];
        float []hitbypitcharray = new float[30];
        boolean []ineligible = new boolean[30];
        String [] strplayerstat;
        char [] charplayerstat;
        BufferedReader br = null;
        try {
            File file1 = new File("leaders.txt");
            FileWriter f3 = new FileWriter(file1);
            PrintWriter p3 = new PrintWriter(f3);
            br = new BufferedReader (new FileReader(inputfilename));

            while ((line= br.readLine()) !=null) {         //reading the line by line till there is nothing
                float H=0,O=0,K=0,W=0,P=0,S=0;
                float inv = 0;
                strplayerstat = line.split(" ");
                if (strplayerstat.length < 2) {
                    onbasepercentage[i] = 0;
                    ineligible[i] = true;
                } else {
                    charplayerstat= strplayerstat[1].toCharArray();
                    for (int x=0;x<strplayerstat[1].length();x++) {        //using loop to fine the stat for eachplayer
                        if ( charplayerstat[x] == 'H') {H++;}
                        else if (charplayerstat[x] == 'O') {O++;}
                        else if ( charplayerstat[x] == 'K') {K++;}
                        else if ( charplayerstat[x] == 'W') {W++;}
                        else if ( charplayerstat[x] == 'P') {P++;}
                        else if	( charplayerstat[x] == 'S') {S++;}
                        else {inv++;}
                    }
                    onbasepercentage[i] =((H+W+P)/(strplayerstat[1].length()-inv)); // input validation to prevent invalid input affecting the OB%
                }

                System.out.println(H);
                namearray[i]= strplayerstat[0];
                if (H+O+K > 0) {                 //if at-bat value is zero, then set the value of battingaverage to zero
                    battingaverage[i] = (H/(H+O+K));
                } else {
                    battingaverage[i] = 0;
                }

                hitarray[i]=H;
                walkarray[i]=W;
                strikeoutarray[i]=K;
                hitbypitcharray[i]=P;
                float [] statarray = {H,O,K,W,P,S};
                display(strplayerstat[0],statarray, battingaverage[i], onbasepercentage[i], p3);
                i++;
            }

            float BAL = Findingleader(ineligible, battingaverage,numberofplayers(inputfilename));
            float OBL = Findingleader(ineligible, onbasepercentage,numberofplayers(inputfilename));
            p3.println("LEAGUE LEADERS");
            p3.printf("BA: %s %.3f%n"+"OB%%: %s %.3f%n",displayleader(ineligible, namearray,Findingleader(ineligible, battingaverage,numberofplayers(inputfilename)),battingaverage,numberofplayers(inputfilename)),BAL,displayleader(ineligible, namearray,Findingleader(ineligible, onbasepercentage,numberofplayers(inputfilename)),onbasepercentage,numberofplayers(inputfilename)),OBL);
            p3.println("H:"+" "+ displayleader(ineligible, namearray,Findingleader(ineligible, hitarray,numberofplayers(inputfilename)),hitarray,numberofplayers(inputfilename))+" "+Math.round(Findingleader(ineligible, hitarray,numberofplayers(inputfilename))));
            p3.println("BB:"+" "+ displayleader(ineligible, namearray,Findingleader(ineligible, walkarray,numberofplayers(inputfilename)),walkarray,numberofplayers(inputfilename))+" "+Math.round(Findingleader(ineligible, walkarray,numberofplayers(inputfilename))));
            p3.println("K:"+" "+ displayleader(ineligible, namearray,FindingKleader(ineligible, strikeoutarray,numberofplayers(inputfilename)),strikeoutarray,numberofplayers(inputfilename))+" "+Math.round(FindingKleader(ineligible, strikeoutarray,numberofplayers(inputfilename))));
            p3.println("HBP:"+" "+ displayleader(ineligible, namearray,Findingleader(ineligible, hitbypitcharray,numberofplayers(inputfilename)),hitbypitcharray,numberofplayers(inputfilename))+" "+Math.round(Findingleader(ineligible, hitbypitcharray,numberofplayers(inputfilename))));
            p3.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

