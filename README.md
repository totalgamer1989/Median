# Median
A datastructure which gives the median of all the entries of generic data type. Time to find the median in O(1). Insertions are O(log n).
Internally it implements two heaps, a max heap of the elements less than equal to the median, and a min heap of the numbers greater than equal to the median. Both the heaps always have an equal number of element at all times.
