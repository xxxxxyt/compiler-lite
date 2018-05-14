package AstNode;

import IRCode.Register;

import static Tool.PrintTool.*;

public abstract class ExpressionStatementNode extends StatementNode {
    
    public VariableTypeNode exprType;
    public boolean leftValue;
    public Register reg;
    
    ExpressionStatementNode() {
        exprType = null;
        leftValue = false;
    }
    
    @Override public void printInformation(int tab) {
        super.printInformation(tab);
        if (exprType != null) exprType.printInformation(tab + 1);
        if (leftValue == true) printSpaceAndStr(tab, "leftValue: " + leftValue);
    }
}
