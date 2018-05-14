import AstNode.*;
import AstBuilder.*;
import AstVisitor.*;
import IRCode.IRCode;
import Scope.*;

import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {

        String path = "code/program.txt";
//        String path = "code/0.txt";
        AstBuilder astBuilder = new AstBuilder();
        ParentLinker parentLinker = new ParentLinker();
        ScopeTreeBuilder scopeTreeBuilder = new ScopeTreeBuilder();
        TypeDefinitionChecker typeDefinitionChecker = new TypeDefinitionChecker();
        StaticTypeChecker staticTypeChecker = new StaticTypeChecker();
        ClassTypeResolver classTypeResolver = new ClassTypeResolver();
        IRGenerator irGenerator = new IRGenerator();

        ProgramNode ast = astBuilder.buildAst(path);
        parentLinker.linkParent(ast);
        Scope toplevelScope = scopeTreeBuilder.buildScopeTree(ast);
        typeDefinitionChecker.checkTypeDefinition(ast);
        staticTypeChecker.checkStaticType(ast);
        classTypeResolver.resolveClassType(ast);
//        ast.printInformation(0);

//        LinkedList<IRCode> irCodeList = irGenerator.generateIR(ast);
//        for (IRCode item : irCodeList) item.printInformation();
    }
}