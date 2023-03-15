
import java.util.Scanner;

public class IR_Election {
    private int hello;
    public IR_Election() {
        this.hello = 8;
    }
    public int getHello() {
        return this.hello;
    }

    public void setHello(int hi) {
        this.hello = hi;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        IR_Election hello = new IR_Election();
        int temp = input.nextInt();
        hello.setHello(temp);
        System.out.println(temp);
        System.out.println(hello.getHello());
        input.close();
    }
    
}
