package net.grantwagner.gcompiler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import net.grantwagner.gcompiler.parser.Parser;

           
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
