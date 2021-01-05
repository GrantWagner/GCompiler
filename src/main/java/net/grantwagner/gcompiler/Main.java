package net.grantwagner.gcompiler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import net.grantwagner.gcompiler.parser.Parser;

/**
 * Language Spec
 * 
 * FileSpec => PackageSpec
 *         ImportSpec*
 *         (FunctionSpec|StructSpec)+
 *         
 * PackageSpec => PackageKeyword
 *                (Identifier PackageSeperator)* 
 *                Identifier
 *                CommandSeperator
 *                
 * ImportSpec => ImportKeyWord 
 *               (Identifier PackageSeperator)* 
 *               TypeIdentifier
 *               CommandSeperator
 *                
 * FunctionSpec => TypeIdentifier 
 *                 FunctionIdentifier
 *                 ArgStart
 *                 (TypeIdentifier ParameterIdentifier ArgSeperator)*
 *                 (TypeIdentifier ParameterIdentifier)?
 *                 ArgStop
 *                 ScopeStart
 *                 (Command CommandSeperator)*
 *                 ScopeStop
 *                
 * StructSpec => TypeIdentifier
 *               (ExtendsKeyword TypeIdentifier)?
 *               ScopeStart
 *               (TypeIdentifier ParameterIdentifier CommandSeperator)*
 *               ScopeStop
 *               
 * Command => Declaration ||
 *            FunctionCall || 
 *            ForLoop || 
 *            ForEachLoop ||
 *            WhileLoop ||
 *            IfStatement ||
 *            Return
 *            
 *                
 * Identifier       => [a-zA-Z][a-zA-Z0-9]+ && ^KeyWord
 * 
 * ImportKeyWord    => "import"
 * ExtendsKeyword   => "extends"
 * 
 * PackageSeperator => "."
 * CommandSeperator => "."
 * ArgStart         => "("
 * ArgStop          => ")"
 * ArgSeperator     => ","
 * ScopeStart       => "{"
 * ScopeStop        => "}"
 *
 * TODO VarArgs
 * TODO FunctionReferences
 * TODO Generics?
 */
public class Main {
  public static void main(String[] args) throws URISyntaxException, IOException {
    
    //parse args, get basepath and output
    ClassLoader classLoader = Main.class.getClassLoader();

    URL resource = classLoader.getResource("TestProgram/GTest.g");
    List<File> files = List.of(Path.of(resource.toURI()).toFile());
    
    Parser parser = new Parser();
    //ProgramSymbols symbols = 
    parser.parseIdentifiers(files);
    
    
    System.out.println("Hello World");
  }
}
