# GCompiler

A Compiler for the G programming language.

An attempt at writing my own programming language. Inspirations are C and Java. The language is functional, but attempts to support complex POGO (Plain Old G Objects) in the same manor as most object oriented languages. By being functional, the requirements of service injection frameworks will be negated. By allowing methods to be redefined, mocking frameworks will be unnecessary. With objects not having "methods", and some nice auto-generated methods, boilerplate removal frameworks like Lombok should be unnecessary.

Yes, it's G because I am a little narsisitic.

Major features:
* A file can have any name and path, as long as it's under the project base path. Source code extension is ".g"
* ala Java, each file is defined in the context of a package.
* each file can have any combination of struct and function definitions
* memory will be managed by reference counting. If it exists, a delete method will be called to remove any circular references.

* ala C, structs are a collection data items. There is no access limitation (similar to java public fields)
* ala Java, structs can have single inheritance. If not explicitly defined, each struct inherits a base Object
* for each struct, methods equals, hashcode, deepCopy and toString will be created, which work against all fields recusively. Attempts to mimic smarter reflection based versions of their java counterparts.
* ala Java, structs are instantiated with new, and instances are inharently references. References will be automatically deleted when they go out of scope.

* functions are like c functions
* functions should attempt to be null safe. For example List.size(null) == 0, not an exception
* functions should return "this" instead of void, if possible.
* even if used in a method with a simplier data type, the reference type will be maintained. For example




* There are *NO* base data types. All data storage is by struct. There 5 "basic" structs, that will be hand coded, and can be created by literal
** Object, which is empty
** Boolean, which contains a single true/false value
** Number, which will seperately store a integer, fractional, and imaginery coefficiants as arbitrary precision integers. Attempts to duplicate the functionality of java's big number
** Char
** String



# RoadMap
## V1
Reads G files, and compiles to C files. Compiler Written in Java.

### Language Spec
FileSpec => PackageSpec
        ImportSpec*
        (FunctionSpec|StructSpec)+
        
PackageSpec => PackageKeyword
               (Identifier PackageSeperator)* 
               Identifier
               CommandSeperator
               
ImportSpec => ImportKeyWord 
              (Identifier PackageSeperator)* 
              TypeIdentifier
              CommandSeperator
               
FunctionSpec => TypeIdentifier 
                FunctionIdentifier
                ArgStart
                (TypeIdentifier ParameterIdentifier ArgSeperator)*
                (TypeIdentifier ParameterIdentifier)?
                ArgStop
                ScopeStart
                (Command CommandSeperator)*
                ScopeStop
               
StructSpec => TypeIdentifier
              (ExtendsKeyword TypeIdentifier)?
              ScopeStart
              (TypeIdentifier ParameterIdentifier CommandSeperator)*
              ScopeStop
              
Command => Declaration ||
           FunctionCall || 
           ForLoop || 
           ForEachLoop ||
           WhileLoop ||
           IfStatement ||
           Return

### Tokenizer Spec
Identifier       => [a-zA-Z][a-zA-Z0-9]+ && ^KeyWord

ImportKeyWord    => "import"
ExtendsKeyword   => "extends"

PackageSeperator => "."
CommandSeperator => "."
ArgStart         => "("
ArgStop          => ")"
ArgSeperator     => ","
ScopeStart       => "{"
ScopeStop        => "}"

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
* FrameBuffer graphics
* AudioBuffer sound
* UI
* Markdown parsing and rendering
* "GSon" parsing,writing and formating (Json, no comma, extended numeric representation, comments
  
## V3
Compile to GAsm, and target the GComp architecture
