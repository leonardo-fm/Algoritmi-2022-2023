#ifndef MERGE_BINARY_INSERTION_SORT_H_HJGjgJKHGk
#define MERGE_BINARY_INSERTION_SORT_H_HJGjgJKHGk

#include <stdio.h>

/**
* A generic merge binary insertion sort, return the array sorted, given the number of items, 
* size of single items a compare function and a k value whitch is use to change when the binary insertion sort 
* are use into the algorithm, the k rappresent the smollest subset.
*/
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));

#endif