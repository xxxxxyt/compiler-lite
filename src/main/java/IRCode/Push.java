package IRCode;

import IRCode.Operand.Operand;

public class Push extends IRCode {

    public Operand src;

    @Override
    public void printInformation() {
        System.out.println("push\t" + src.getName());
    }
}
