# Refutation resolution algorithm

## Implementation of:
+ Breadth first search (BFS)
+  Uniform cost search (UCS)
+ The A* algorithm

## Arguments from command line: 
* When running the refutation resolution algorithm, your code should accept two arguments. First argument will be a keyword “resolution” which indicates that refutation resolution algorithm should be run, and the second argument will be the path to the file with the list of clauses.

```bash
java -cp target/classes ui.Solution resolution resolution_examples/small_example.txt
```

## File input
* The List of clauses file contains a list of clauses that will be used in our reasoning algorithm. Each line of the file contains a single clause in CNF format. Disjunction is defined with symbol v, which contains a single whitespace on each side. Literals are represented as sequences of symbols.
```
a 
~a v b
c v ~b
~c
```

* Output:
```
1. a 
2. ~a v b 
3. c v ~b 
4. ~c
5. ~b (3, 4)
6. ~a (2, 5) 
7. NIL (1, 6)
 [CONCLUSION]: c is true
```
