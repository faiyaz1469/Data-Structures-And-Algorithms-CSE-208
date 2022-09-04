import java.util.Scanner;

class main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] ch = new char[9];
        for(int i=0; i<9; i++){
            ch[i] = sc.next().charAt(0);
        }
        int countX = 0;
        int countO = 0;
        int count = 0;
        for(int i=0; i<9; i++){
            if(ch[i] == 'X' || ch[i] == 'x'){
                countX++;
            }
            else if(ch[i] == 'O' || ch[i]== 'o'){
                countO++;
            }
            else
                count++;
        }


            if (countO == countX - 1 || countX == countO-1) {
                if (ch[0] == ch[1] && ch[1] == ch[2])
                    System.out.println("1W");
                else if (ch[3] == ch[4] && ch[4] == ch[5])
                    System.out.println("1W");
                else if (ch[6] == ch[7] && ch[7] == ch[8])
                    System.out.println("1W");
                else if (ch[0] == ch[3] && ch[3] == ch[6])
                    System.out.println("1W");
                else if (ch[1] == ch[4] && ch[4] == ch[7])
                    System.out.println("1W");
                else if (ch[2] == ch[5] && ch[5] == ch[8])
                    System.out.println("1W");
                else if (ch[0] == ch[4] && ch[4] == ch[8])
                    System.out.println("1W");
                else if (ch[2] == ch[4] && ch[4] == ch[6])
                    System.out.println("1W");
                else {
                    if(count == 0)
                        System.out.println("1D");
                    else
                       System.out.println("2");
                }
            }
            else if(countO == countX) {
                if (ch[0] == ch[1] && ch[1] == ch[2]){
                    if((ch[3] == ch[4] && ch[4] == ch[5]) || ( ch[6] == ch[7] && ch[7] == ch[8] ))
                        System.out.println("3");
                    else
                        System.out.println("1W");
                }
                else if(ch[3] == ch[4] && ch[4] == ch[5]){
                    if(( ch[6] == ch[7] && ch[7] == ch[8])) {
                        System.out.println("3");
                    } else
                        System.out.println("1W");
                }
                else if(ch[6] == ch[7] && ch[7] == ch[8])
                    System.out.println("1W");
                else if((ch[0] == ch[4] && ch[4] == ch[8]) || (ch[2] == ch[4] && ch[4] == ch[6]))
                    System.out.println("1W");
                else
                    System.out.println("2");
            }
            else
                System.out.println("3");


    }
}


