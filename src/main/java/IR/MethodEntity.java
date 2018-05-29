package IR;

import IR.IRCode.*;
import IR.IRCode.Operand.*;

import java.util.*;

public class MethodEntity {

    public String methodName;
    public LinkedList<Variable> formalParaVarList = new LinkedList<Variable>();
    public LinkedList<IRCode> codeList = new LinkedList<IRCode>();
    public LinkedList<BasicBlock> basicBlockList = new LinkedList<BasicBlock>();

    public void printInformation() {
        System.out.print(methodName + " ");
        for (Operand para : formalParaVarList)
            System.out.print(para.getName() + " ");
        System.out.print("{\n");
        if (!basicBlockList.isEmpty()) {
            for (BasicBlock bb : basicBlockList)
                bb.printInformation();
        } else {
            for (IRCode ins : codeList) {
                if (ins.label != null)
                    System.out.print("\t" + ins.label + "\n");
                System.out.print("\t\t");
                ins.printInformation();
            }
        }
        System.out.print("}\n");
    }
}