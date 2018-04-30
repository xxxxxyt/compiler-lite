package AstVisitor;

import AstNode.*;
import ErrorHandler.*;

public abstract class AstVisitor {

    public void visit(AstNode node) throws SemanticException {
        if (node instanceof ProgramNode) visit((ProgramNode)node);
        else if (node instanceof ClassDefinitionNode) visit((ClassDefinitionNode)node);
        else if (node instanceof MethodDefinitionNode) visit((MethodDefinitionNode)node);
        else if (node instanceof BlockNode) visit((BlockNode)node);
        else if (node instanceof StatementNode) visit((StatementNode)node);
    }

    public void visit(BinaryExpressionNode node) throws SemanticException {
        visit(node.lhs);
        visit(node.rhs);
    }

    public void visit(BlockNode node) throws SemanticException {
        for (AstNode item : node.childList) visit(item);
    }

    public void visit(BreakStatementNode node) throws SemanticException {}

    public void visit(ClassDefinitionNode node) throws SemanticException {
        for (DefinitionExpressionNode item : node.memberVariableList) visit(item);
        for (MethodDefinitionNode item : node.memberMethodList) visit(item);
        for (MethodDefinitionNode item : node.memberConstructionMethodList) visit(item);
    }

    public void visit(ConstantNode node) {}

    public void visit(ContinueStatementNode node) throws SemanticException {}

    public void visit(DefinitionExpressionNode node) throws SemanticException {
        visit(node.variableType);
        if (node.initValue != null) visit(node.initValue);
    }

    public void visit(EmptyStatementNode node) {}

    public void visit(ExpressionStatementNode node) throws SemanticException {
        if (node instanceof PrimaryExpressionNode) visit((PrimaryExpressionNode)node);
        else if (node instanceof DefinitionExpressionNode) visit((DefinitionExpressionNode)node);
        else if (node instanceof MemberAccessExpressionNode) visit((MemberAccessExpressionNode)node);
        else if (node instanceof IndexAccessExpressionNode) visit((IndexAccessExpressionNode)node);
        else if (node instanceof MethodCallExpressionNode) visit((MethodCallExpressionNode)node);
        else if (node instanceof NewExpressionNode) visit((NewExpressionNode)node);
        else if (node instanceof UnaryExpressionNode) visit((UnaryExpressionNode)node);
        else if (node instanceof BinaryExpressionNode) visit((BinaryExpressionNode)node);
        else if (node instanceof TypeNode) visit((TypeNode)node);
        if (node.exprType != null) visit((TypeNode) node.exprType);
    }

    public void visit(ForStatementNode node) throws SemanticException {
        if (node.init != null) visit(node.init);
        if (node.condition != null) visit(node.condition);
        if (node.afterBlock != null) visit(node.afterBlock);
        visit(node.block);
    }

    public void visit(IfStatementNode node) throws SemanticException {
        visit(node.condition);
        visit(node.ifBlock);
        if (node.elseBlock != null) visit(node.elseBlock);
    }

    public void visit(IndexAccessExpressionNode node) throws SemanticException {
        visit(node.caller);
        visit(node.index);
    }

    public void visit(MemberAccessExpressionNode node) throws SemanticException {
        visit(node.caller);
        visit(node.member);
    }

    public void visit(MethodCallExpressionNode node) throws SemanticException {
        visit(node.caller);
        for (ExpressionStatementNode item : node.actualParameterList) visit(item);
    }

    public void visit(MethodDefinitionNode node) throws SemanticException {
        visit(node.returnType);
        for (DefinitionExpressionNode item : node.formalArgumentList) visit(item);
        visit(node.block);
    }

    public void visit(NewExpressionNode node) throws SemanticException {
        visit(node.variableType);
        for (ExpressionStatementNode item : node.actualParameterList) visit(item);
    }

    public void visit(PrimaryExpressionNode node) throws SemanticException {
        if (node instanceof ReferenceNode) visit((ReferenceNode)node);
        if (node instanceof ConstantNode) visit((ConstantNode)node);
        if (node instanceof ThisNode) visit((ThisNode)node);
    }

    public void visit(ProgramNode node) throws SemanticException {
        for (AstNode item : node.childrenList) {
            if (item instanceof ClassDefinitionNode) visit((ClassDefinitionNode)item);
            else if (item instanceof MethodDefinitionNode) visit((MethodDefinitionNode)item);
            else visit((DefinitionExpressionNode)item);
        }
    }

    public void visit(ReferenceNode node) throws SemanticException {}

    public void visit(ReturnStatementNode node) throws SemanticException {
        if (node.returnValue != null) visit(node.returnValue);
    }

    public void visit(StatementNode node) throws SemanticException {
        if (node instanceof ExpressionStatementNode) visit((ExpressionStatementNode)node);
        else if (node instanceof IfStatementNode) visit((IfStatementNode)node);
        else if (node instanceof ForStatementNode) visit((ForStatementNode)node);
        else if (node instanceof WhileStatementNode) visit((WhileStatementNode)node);
        else if (node instanceof ReturnStatementNode) visit((ReturnStatementNode)node);
        else if (node instanceof BreakStatementNode) visit((BreakStatementNode) node);
        else if (node instanceof ContinueStatementNode) visit((ContinueStatementNode)node);
        // if (node instanceof EmptyStatementNode) visit((EmptyStatementNode)node);
    }

    public void visit(ThisNode node) {}

    public void visit(UnaryExpressionNode node) throws SemanticException {
        visit(node.inner);
    }

    public void visit(TypeNode node) throws SemanticException {
        if (node instanceof MethodTypeNode) visit((MethodTypeNode)node);
        else visit((VariableTypeNode)node);
    }

    public void visit(MethodTypeNode node) {}

    public void visit(VariableTypeNode node) throws SemanticException {
        if (node instanceof ArrayTypeNode) visit((ArrayTypeNode)node);
        else visit((NonArrayTypeNode)node);
    }

    public void visit(ArrayTypeNode node) throws SemanticException {
        visit(node.innerTypeNode);
        if (node.size != null) visit(node.size);
    }

    public void visit(NonArrayTypeNode node) throws SemanticException {
        if (node instanceof PrimitiveTypeNode) visit((PrimitiveTypeNode)node);
        else visit((ClassTypeNode)node);
    }

    public void visit(PrimitiveTypeNode node) {}

    public void visit(ClassTypeNode node) throws SemanticException {}

    public void visit(WhileStatementNode node) throws SemanticException {
        visit(node.condition);
        visit(node.block);
    }
}