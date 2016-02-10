The tweak method we decided to implement for our solution was to randomly(*potentially*)
change, anywhere from one to five (5% to 25%), choices from the parent to the child:
	1. Pick 1 to 5 (call this x) candidates to  be change from parent to child.
	2. For each x, randomly make the value of x, to a 1 or a 0.
We don't check for the initial value of x, hence the (*potentially*) since x has 
a 50% of remaining the same. 
We chose 5 because we were testing with the size-20 samples. We'll change this to 25%.
  
