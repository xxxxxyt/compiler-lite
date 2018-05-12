package AstVisitor;

import AstNode.*;
import Scope.*;
import ErrorHandler.*;

import java.util.*;

public class ScopeTreeBuilder extends AstVisitor {

    LinkedList<Scope> scopeStack;

    public ScopeTreeBuilder() {
        this.scopeStack = new LinkedList<Scope>();
    }

    public ToplevelScope buildScopeTree(ProgramNode prog) throws Exception {
        visit(prog);
        if (scopeStack.size() != 1) throw new Exception("size of scopeStack is not 1");
        return (ToplevelScope)scopeStack.getFirst();
    }

    Scope currentScope() {
        return scopeStack.getLast();
    }

    void pushScope(LocalScope scope) throws SemanticException {
        scope.parent = currentScope();
        scope.parent.childrenList.add(scope);
//        scope.nameSet = (HashSet<String>) scope.parent.nameSet.clone();
        scopeStack.addLast(scope);
    }

    LocalScope popScope() {
        return (LocalScope)scopeStack.removeLast();
    }

    @Override void visit(ProgramNode node) throws SemanticException {
        ToplevelScope toplevelScope = new ToplevelScope();
        for (ClassDefinitionNode item : node.classDefinitionList)
            toplevelScope.define(item);
        for (MethodDefinitionNode item : node.methodDefinitionList)
            toplevelScope.define(item);
        MethodDefinitionNode main = toplevelScope.methodDefinitionMap.get("main");
        if (main == null)
            throw new SemanticException("no method name main");
        else if (!main.returnType.getTypeName().equals("int"))
            throw new SemanticException(main.line, "return type of main must be int");
        else if (!main.formalArgumentList.isEmpty())
            throw new SemanticException(main.line, "main can not have parameters");
        toplevelScope.astNode = node;
        scopeStack.addLast(toplevelScope);
        super.visit(node);
        node.scope = toplevelScope;
    }

    @Override void visit(ClassDefinitionNode node) throws SemanticException {
        LocalScope scope = new LocalScope();
        pushScope(scope);
        for (MethodDefinitionNode item : node.memberMethodList)
            scope.define(item);
        for (MethodDefinitionNode item : node.memberConstructionMethodList)
            scope.define(item);
        scope.astNode = node;
        super.visit(node);
        node.scope = popScope();
    }

    @Override void visit(MethodDefinitionNode node) throws SemanticException {
        LocalScope scope = new LocalScope();
        pushScope(scope);
        scope.astNode = node;
        super.visit(node);
        node.scope = popScope();
    }

    @Override void visit(BlockNode node) throws SemanticException {
        LocalScope scope = new LocalScope();
        pushScope(scope);
        scope.astNode = node;
        super.visit(node);
        node.scope = popScope();
    }

    @Override void visit(DefinitionExpressionNode node) throws SemanticException {
        super.visit(node);
        currentScope().define(node);
    }

    @Override void visit(MemberAccessExpressionNode node) throws SemanticException {
        visit(node.caller);
        try {
            visit(node.member);
        } catch (SemanticException exception) {}
    }

    @Override void visit(ReferenceNode node) throws SemanticException {
        AstNode definitionNode;
        try {
            definitionNode = currentScope().get(node.referenceName);
        } catch (SemanticException exception) {
            throw new SemanticException(node.line, exception.getMessage());
        }
        if (definitionNode instanceof ClassDefinitionNode)
            node.referenceType = ReferenceNode.ReferenceType.CLASS;
        else if (definitionNode instanceof MethodDefinitionNode)
            node.referenceType = ReferenceNode.ReferenceType.METHOD;
        else if (definitionNode instanceof DefinitionExpressionNode)
            node.referenceType = ReferenceNode.ReferenceType.VARIABLE;
        node.definitionNode = definitionNode;
    }
}