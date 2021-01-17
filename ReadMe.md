# GCompiler

A Compiler for the G programming language.

An attempt at writing my own programming language. Inspirations are C and Java. The language is functional, but attempts to support complex POGO (Plain Old G Objects) in the same manor as most object oriented languages. By being functional, the requirements of service injection frameworks will be negated. By allowing methods to be redefined, mocking frameworks will be unnecessary. With objects not having "methods" and some nice auto-generated functions, boilerplate removal frameworks like Lombok should be unnecessary.

Yes, it's G because I am a little narcissistic.

Major features:
* A file can have any name and path, as long as it's under the project base path. Source code extension is ".g"
* ala Java, each file is defined in the context of a package.
* each file can have any combination of struct and function definitions
* memory will be managed by reference counting. If it exists, a delete method will be called to remove any internal circular references.

* ala C, structs are a collection data items. There is no access limitation (similar to java public fields)
* ala Java, structs can have single inheritance. If not explicitly defined, each struct inherits a base Object
* for each struct, methods equals, hashcode, deepCopy and toString will be created, which work against all fields recursively. Attempts to mimic smarter reflection based versions of their java counterparts.
* ala Java, structs are instantiated with new, and instances are inherently references. References will be automatically deleted when they go out of scope.

* functions are like c functions
* functions should attempt to be null safe. For example List.size(null) == 0, not an exception
* functions should return "this" instead of void, if possible.
* even if used in a method with a simpler data type, the reference type will be maintained. For example


* There are *NO* base data types. All data storage is by struct. There 6 "basic" structs, that will be hand coded. 
** Object, which is empty
** Void, a special case filler, used to explicitly represent no data during function definition
** Boolean, which contains a single true/false value. Literals are defined by keywords "true" and "false".
** Number, which will separately store a integer, fractional, and imaginary coefficients as arbitrary precision integers. Attempts to duplicate the functionality of java's big number. 
** Char
** String



# RoadMap
## V1
Reads G files, and compiles to C files. Compiler Written in Java.

### Language Spec
FileSpec => PackageSpec
        ImportSpec*
        (
          FunctionSpec ||
          StructSpec ||
          Declaration
        )+
eg: /GCompiler/src/main/resources/TestProgram/GTest.g
        
PackageSpec => PackageKeyword
               (Identifier PackageSeperator)* 
               Identifier
               CommandSeperator
eg: package net.grantwagner.gtest;
               
ImportSpec => ImportKeyWord 
              (Identifier PackageSeperator)* 
              TypeIdentifier
              CommandSeperator
eg: import System.IO.Log;

FunctionSpec => TypeIdentifier 
                FunctionIdentifier
                ArgStart
                (TypeIdentifier ParameterIdentifier ArgSeperator)*
                (TypeIdentifier ParameterIdentifier)?
                ArgStop
                ScopeStart
                (Command CommandSeperator)*
                ScopeStop
eg:                 
               
StructSpec => StructKeyword
              TypeIdentifier
              (ExtendsKeyword TypeIdentifier)?
              ScopeStart
              (TypeIdentifier ParameterIdentifier CommandSeperator)*
              ScopeStop
eg: struct LinkedList extends Collection {
  ListNode head;
}
  
Command => Declaration ||
           Assignment ||
           WhileLoop ||
           IfStatement ||
           Return
           Expression || 
  
Declaration => TypeIdentifier
               VariableIdentifier
               (
                 AssignmentOperator
                 Expression
               )?
               CommandSeperator
eg: Number pi = 3.1415;

Assignment => VariableIdentifier 
              AssignmentOperator
              Expression
              CommandSeperator
eg: pi = 3.1415;

WhileLoop => WhileKeyword
             ArgStart
             BooleanExpression
             ArgStop
             ScopeStart
             (Command CommandSeperator)*
             ScopeStop
eg: while( continue == true ) {
  //do stuff
}

IfStatement => IfKeyword
               ArgStart
               BooleanExpression
               ArgStop
               ScopeStart
               (Command CommandSeperator)*
               ScopeStop
               (
                 ElseKeyword
                 IfKeyword
                 ArgStart
                 BooleanExpression
                 ArgStop
                 ScopeStart
                 (Command CommandSeperator)*
                 ScopeStop
               )*
               (
                 ElseKeyword
                 ScopeStart
                 (Command CommandSeperator)*
                 ScopeStop
               )*
eg: if( state == 1 ) {
  color = red;
else if( state == 2 ) {
  color = green; 
} else {
  color = yellow;
} 

Return => ReturnKeyword
          Expression
          CommandSeperator
eg: return true;

Expression  => ( Expression )
               Expression * Expression 
               Expression / Expression 
               Expression + Expression 
               Expression - Expression 
               ! Expression
               Expression & Expression 
               Expression | Expression 
               Expression == Expression 
               FunctionCall
               VarIdentifier
               Literals*
Listed in order of precedence

FunctionCall => FunctionIdentifier
                ArgStart
                (Expression ArgSeperator)*
                Expression?
                ArgStop
                CommandSeperator
           
### Tokenizer Spec
By Convention, all Strings are "camelCased". packages, variables, and functions start lower case, constants and types start uppercase
Identifier       => [a-zA-Z][a-zA-Z0-9_]+ && ^KeyWord

Keywords => "package"
            "import"
            "struct"
            "extends"
            "while"
            "if"
            "else"
            "return"
            "true"
            "false"

Comments are started by "//", and are ended by "\n"

PackageSeperator => "."
CommandSeperator => "."
ArgStart         => "("
ArgStop          => ")"
ArgSeperator     => ","
ScopeStart       => "{"
ScopeStop        => "}"

### Literals
UTF-8 is the only form of text encoding supported. 
C style escape sequences https://en.cppreference.com/w/c/language/escape.

booleanLiterals => "true" || "false"
charLiteral => any char, encased in single quotes. '\' to escape a single quote only
stringLiteral => any string of chars, encsed in double quotes
numeric literal => [0-9]+ 
                   ([./][0-9]+)?
                   ([e][0-9]+)?
                   [i]?

### Standard Library, with C implementations
Any function that an operator is an alias for
Math.sin, cos, tan, pow, and root
Log.print
Console.readLine
Collection.length
List.set, .insert, .get
String.length, .format

?Java 8 style streams?

## V2
Rewrite complier into G code, still compiler to C

Possible Language extensions
* VarArgs
* FunctionReferences
* Generics
* Jar style libraries

StdLib extensions:
* More Collections
** Character Trie
* FrameBuffer graphics
* AudioBuffer sound
* UI
* Markdown parsing and rendering
* Spellchecking
* Regex
* "GSon" parsing,writing and formating (Json, no comma, extended numeric representation, comments
  
## V3
Compile to GAsm, and target the GComp architecture

Changes to Support Projects
* GKernel, Framework, Thread Manager, File Manager, Hardware Abstraction 
* GSH, shell
* GDisplay, display manager
* GWin, window manager
* GTerm, graphical terminal emulator

