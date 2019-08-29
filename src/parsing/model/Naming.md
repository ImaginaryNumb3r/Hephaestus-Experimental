# Naming
 - **Terminal**: An atomic model with a fixed length. It is either parsed or not.
 - **Token:** An atomic model with arbitrary length. It can be empty, parsed and unparsed.
 - **Node:** A node is a model which contains other models. It represents a pattern and not a grammatical structure.
         For example, repeating sequences of the same element with arbitrary length is the `SequenceNode`.
 - **Consumer:** A model of arbitrary length. Accepts characters until its predicate is violated.
