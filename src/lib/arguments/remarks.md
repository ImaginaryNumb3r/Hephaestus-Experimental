# A case for opinionated software
 - KISS: Short, but with the potential to expand.  
 - Feature Creep: Less prone to add more and more features and lose focus.  
 - Motivation: Finish something quickly, keeps you going.  
 - Powerful APIs are not always better. If dealing with an issue outside of the API is equally or less challenging, let the user deal with it.  
 - Powerful APIs are more difficult to master.  
 - It's fun to design more challenging APIs, but often it is less fun to deal with all the edge-cases. A strong opinion finds easier solutions.  
 - What are probable use-cases? Does a simply API impose higher difficulty on a developer?  

Constraints are liberating and empowering.  
If there are solutions available, make your software more opinionated to fill a niche that other libraries don't cover.  

TODO: Code Guidelines
    - No type inference on literals (acceptable for sinel instances where all other variables are initialized as `var`)
    - Comments are sentences. They start with a capital letter and end on a punctuation mark ('.', '!', '?')
    - Wording: "get" is always an quick operation with O(1). If that is not the case, use "load" or "make".  
               "Fetch" can be used as an alternative to return Optionals.
               "Put" is an action which does not create duplicate entries in a collection.
               When a method returns a mutable collection, it must be treated as property (and not be named get, set etc.) 
    - For immutable Value Objects, omitting the use of getters is okay.  
    - Reflection is fine as long as you don't meddle with foreign libraries.  
    - Be cautious in comments with wording: In the Java world, words like `final` have a different meaning and should not be used lightly.
    - If one parameter is marked with nullability, all parameters should be. For good style, probably all methods in the class should be.
    - There is a difference between _must have_ and _good style_

For Version 1.1
 - Low-level Argument API
 - Convenience-level Parser API
 - Non-alphanumeric characters for arguments if they don't start as prefixes
 - Custom delimiters, and no delimiters if required
 - case insensitive mode
 - Custom return types: Long, Bool, Double, String <- bool interferes with "Option".
 - Custom constraints? Like "only positive numbers"
 - better exceptions
 - 

# Why This Library?
Existing parsers:
 - Snaq: https://www.snaq.net/java/JCLAP/  
   Sample: https://www.snaq.net/java/JCLAP/  
 - Commons-CLI: http://commons.apache.org/proper/commons-cli/  
   Sample:  
 - Args4J: https://github.com/kohsuke/args4j  
   Sample: https://github.com/kohsuke/args4j/blob/master/args4j/examples/SampleMain.java  
 - JSAP 2.1: http://www.martiansoftware.com/jsap/  
   Sample: http://www.martiansoftware.com/jsap/doc/ch03s08.html  

List of all parsers: http://jewelcli.lexicalscope.com/related.html

No annotations, but typesafe, simple, easy to understand and cover a wide range of use-cases.
Covers simple parsing, as well as more sophisticated usages.

# Other things

With Maps you can have key-value pairs, which means you can make joins of all sorts.
Joins are a (for the most part?) well understood concept.

Good Testing takes time, but it's worth its time in gold.
