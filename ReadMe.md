# GCompiler

A Compiler for the G programming language.

An attempt at writing my own programming language. Inspirations are C and Java. 

Yes, it's G because I am a little narcissistic.

Goals:
* The language is functional, but attempts to support complex POGOs (Plain Old G Objects) in the same manor as most object oriented languages.
* By being functional, the requirements of service injection frameworks will be negated.
* By allowing methods to be redefined, mocking frameworks will be unnecessary.
* With objects not having "methods" and some nice auto-generated functions, boilerplate removal frameworks like Lombok should be unnecessary.


Major features:
* A file can have any name and path, as long as it's under the project base path. Source code extension is ".g"
* ala Java, each file is defined in the context of a package.
* each file can have any combination of struct and function definitions.
* memory will be managed by reference counting. If it exists, a delete method will be called to remove any internal circular references.

* The only base objects are:
  * String: a memory managed, immutable collection of characters. There is no character object, only strings of length one.
  * Number: a memory managed, immutable value. It's stored internally as 3 varable length integers (java BigInts like), for the numerator, denominator, and imaginary portions. Irrational numbers (eg pi, root(2)) will still need to be approximated, with their precision TBD. Maybe I want to add flags for multiples of known https://en.wikipedia.org/wiki/Transcendental_number and a seperate root portion. Literals have the form of (-)[0-9]+(o[1-9][0-9]*)?(i[1-9][0-9]*)?
  * Boolean: either True or False
  * Void: a filler for no object, which is only valid for method returns
* ala C, structs are a collection of data items. There is no access limitation (similar to java public fields)
  * If there exists a get*Field*(struct) or set*Field*(Struct, Model) method, they will be called on access
  * the methods equals, hash, toString are generated at compile time using recursive object reflection.
  * .function(param) (leading period) is a shortcut for void function(struct, param). and will return the struct for chaining.
* ala Java, structs can have single inheritance. If not explicitly defined, each struct inherits a empty struct named Object
* ala Java, structs are instantiated with new, and instances are inherently references. References will be automatically deleted when they go out of scope.
  * if they exist, Void new(Struct) and Void delete(Struct) will be called.
  * Void new(Struct, params...) will enforce some parameters. 

* functions have the same structor are c style functions
  * decliration: <GenericType1...> ReturnType name(ParamType paramName1...)
* functions should attempt to be null safe. For example List.size(null) == 0, not an exception
* functions should return "this" instead of void, if possible.
* even if used in a method with a simpler data type, the reference type will be maintained. For example
* Generics are supported, with a syntax that mirrors java. More specific values are always allowed, and their type will be maintained respected.
  * for example, a method of <Type> Type doSomething(Type input) { return input;}, if called with a struct of SubType extends Type, will still return an instance of SubType, not just Type. This is more simlar to java's <? extends Type>.

* Standard Library will have the following packages:
  * System.IO, methods to work with enhanced style serial terms. Includes methods to get and set cursor location, and to get or set current colors for background and forgrounds, allowing for rich text displays (
  * System.Collection, Structs and methods for working with collections. Will support List, Sets and Maps
  * System.OS, methods and constants for working with the OS and Threads.
  * System.File, Tools for working with the file system, including the listing of directories and the streaming of both binary and text files.
  * system.UI, Tools for working with UI Windows and Widgets. The frame buffer widget needed for System.Graphics will be part of this.
  * System.Graphics, Tools for working with frame buffers, especially image loading, saving, display parameters setting and querying, and dynamic blitting.
    * System.GraphicsV2 will be added if/when we have tilemap/sprite support
    * System.GraphicsV3 will be added if/when we have 3D support, with methods for defining triangles, materials, and shaders.
  * System.Audio, loadSound, startSound, soundStatus functions. Maybe expand into synths? FM or Midi?

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

