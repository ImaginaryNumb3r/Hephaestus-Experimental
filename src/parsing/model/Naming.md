# Naming
 - **Terminal:** An atomic element with a fixed length. It is either parsed as a whole or not at all.
 - **Token:** An atomic element with arbitrary length. It can be empty, parsed and unparsed.
 - **Node:** A node is a element which contains other elements. It represents a pattern and not an elemental structure.
         For example, repeating sequences of the same element with arbitrary length is the `SequenceNode`.
 - **Consumer:** A element of arbitrary length. Accepts characters until its predicate is violated.
