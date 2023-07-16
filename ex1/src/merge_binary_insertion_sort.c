#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "merge_binary_insertion_sort.h"

static void args_check(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
static void merge_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));
static void merge(void *base, void *arrayFirstHalf, size_t arrayFirstHalfLength, void *arraySecondHalf, size_t arraySecondHalfLength, size_t size, int (*compar)(const void*, const void*));
static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
static size_t binary_search(void *searchArray, void *item, size_t size, size_t low, size_t high, int (*compar)(const void*, const void*));
static void swap(void *val1, void *val2, size_t size);
static void move_pointer(void **base, int ammount);

static int argsChecked = 0;

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)){
    if (argsChecked == 0) {
        args_check(base, nitems, size, compar);
    }

    if (nitems == 1) {
        return;
    }

    if (nitems >= k) {
        merge_sort(base, nitems, size, k, compar);
    } else {
        binary_insertion_sort(base, nitems, size, compar);
    }
}

// Validate the args 
static void args_check(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    if (base == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: the pointer of the array can't be NULL");
      exit(EXIT_FAILURE);
    }

    if (nitems == 0) {
      fprintf(stderr, "merge_binary_insertion_sort: the number of items can't be 0");
      exit(EXIT_FAILURE);
    }

    if (size == 0) {
      fprintf(stderr, "merge_binary_insertion_sort: the size can't be 0");
      exit(EXIT_FAILURE);
    }

    if (compar == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: the pointer of the compar function can't be NULL");
      exit(EXIT_FAILURE);
    }

    argsChecked = 1;
}

// The merge sort algorithm
static void merge_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)) {
    size_t firstHalfLength = nitems / 2;
    size_t secondHalfLength = nitems - firstHalfLength;

    void *arrayFirstHalf = (void *) malloc(firstHalfLength * size);
    if (arrayFirstHalf == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: unable to allocate memory for merge sort");
      exit(EXIT_FAILURE);
    }
    memcpy(arrayFirstHalf, base, firstHalfLength * size);
    
    void *arraySecondHalf = (void *) malloc(secondHalfLength * size);
    if (arraySecondHalf == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: unable to allocate memory for merge sort");
      exit(EXIT_FAILURE);
    }
    void *arraySecondHalfPointer = base;
    move_pointer(&arraySecondHalfPointer, (int)(firstHalfLength * size));
    memcpy(arraySecondHalf, arraySecondHalfPointer, secondHalfLength * size);
    
    merge_binary_insertion_sort(arrayFirstHalf, firstHalfLength, size, k, compar);
    merge_binary_insertion_sort(arraySecondHalf, secondHalfLength, size, k, compar);
        
    merge(base, arrayFirstHalf, firstHalfLength, arraySecondHalf, secondHalfLength, size, compar); 
    
    free(arrayFirstHalf);
    free(arraySecondHalf);
}

// The merge algorithm of the merge sort
static void merge(void *base, void *arrayFirstHalf, size_t arrayFirstHalfLength, void *arraySecondHalf, size_t arraySecondHalfLength, size_t size, int (*compar)(const void*, const void*)) {
    unsigned long i = 0, j = 0;
    while (i < arrayFirstHalfLength && j < arraySecondHalfLength) {
        int compareResult = compar(arrayFirstHalf, arraySecondHalf);

        if (compareResult < 0) {
            memcpy(base, arrayFirstHalf, size);
            move_pointer(&arrayFirstHalf, (int)size);
            i++;
        } else {
            memcpy(base, arraySecondHalf, size);
            move_pointer(&arraySecondHalf, (int)size);
            j++;
        }

        move_pointer(&base, (int)size);
    }

    while (i < arrayFirstHalfLength) {
        memcpy(base, arrayFirstHalf, size);
        move_pointer(&arrayFirstHalf, (int)size);
        move_pointer(&base, (int)size);
        i++;
    }

    while (j < arraySecondHalfLength) {
        memcpy(base, arraySecondHalf, size);
        move_pointer(&arraySecondHalf, (int)size);
        move_pointer(&base, (int)size);
        j++;
    }

    // reset pointer to the start of the memory
    move_pointer(&arrayFirstHalf, -(int)(i * size));
    move_pointer(&arraySecondHalf, -(int)(j * size));
    move_pointer(&base, -(int)((i + j) * size));
}

// The binary insertion sort algorithm
static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    unsigned long i = 1, j = 0;
    void *currentPointer = base;
    move_pointer(&currentPointer, (int)size);
    void *pointerFirstElement = base;
    void *pointerSecondElement = base;

    while (i < nitems) {
        size_t indexNewValue = binary_search(base, currentPointer, size, 0, i - 1, compar);

        j = i;
        while (j != indexNewValue) {
            pointerFirstElement = base;
            move_pointer(&pointerFirstElement, (int)(j * size));
            
            pointerSecondElement = base;
            move_pointer(&pointerSecondElement, (int)((j - 1) * size));
            
            swap(pointerFirstElement, pointerSecondElement, size);
            j--;
        }

        move_pointer(&currentPointer, (int)size);
        i++;
    }

}

// Return the position where have to put the value
static size_t binary_search(void *searchArray, void *item, size_t size, size_t low, size_t high, int (*compar)(const void*, const void*)) {
    if (low >= high) {
        void *finalItem = searchArray;
        move_pointer(&finalItem, (int)(low * size));

        if (compar(finalItem, item) >= 0) {
            return low;
        }
        return low + 1;
    }

    size_t mid = (low + high) / 2;
    void *currentItem = searchArray;
    move_pointer(&currentItem, (int)(mid * size));

    if (compar(currentItem, item) < 0) {
        return binary_search(searchArray, item, size, mid + 1, high, compar);
    } else {
        return binary_search(searchArray, item, size, low, (mid > 0) ? (mid - 1) : 0, compar);
    }
}

// Swap 2 generic value given the size
static void swap(void *val1, void *val2, size_t size) {
    void *tmp = (void *) malloc(size);
    memcpy(tmp, val1, size);
    memcpy(val1, val2, size);
    memcpy(val2, tmp, size);
    free(tmp);
}

// move a pointer of x ammount
static void move_pointer(void **base, int ammount) {
    unsigned char *basePointer = *base;
    basePointer += ammount;
    *base = (void *)basePointer;
}