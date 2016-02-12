# Hill-Climber

The tweak method we decided to implement for our solution was to randomly(*potentially*)
change, anywhere from one to five (5% to 25%), choices from the parent to the child:

- 1. Pick 1% to 5% (call this x) candidates to  be change from parent to child.
- 2. For each x, randomly make the value of x, to a 1 or a 0.

We don't check for the initial value of x, hence the (*potentially*) since x has
a 50% of remaining the same, there's a very low chance you actually get a 25% change
from parent to child.
We chose the 5% to 25% value because we were testing with the size-20 samples, and thought
changing anywhere from 1 to 5 would be a good amount of change. We then converted the number
to a percentage in order to work with any number of item. When we're calculating the percentage
we round it up in order to get a whole number.

# Hill-Climber with Random Restart

Takes the hill-climber function and the number of restarts you want it to have. 
Performance difference between this the the regular hill-climber is most notable with 
the large item sets.

## Results: Small Item-set (20)

|                | Sack                                   | Capacity | Weight | Score |
|----------------|----------------------------------------|----------|--------|-------|
| Hill-Climber   | knapPI_11_20_1000_1 flip-choices 10000 | 970      | 924    | 1428  |
|                | knapPI_13_20_1000_1 flip-choices 10000 | 873      | 821    | 1521  |
|                | knapPI_16_20_1000_1 flip-choices 10000 | 994      | 936    | 2093  |
| Random-Restart | knapPI_11_20_1000_1 flip-choices 10000 | 970      | 924    | 1428  |
|                | knapPI_13_20_1000_1 flip-choices 10000 | 873      | 863    | 1443  |
|                | knapPI_16_20_1000_1 flip-choices 10000 | 994      | 969    | 2172  |

