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
    - For immutable Value Objects, omitting the use of getters is okay.  
    - Reflection is fine as long as you don't meddle with foreign libraries.  
    - Be cautious in comments with wording: In the Java world, words like `final` have a different meaning and should not be used lightly.
