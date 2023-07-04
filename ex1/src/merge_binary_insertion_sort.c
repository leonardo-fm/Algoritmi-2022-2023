#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "merge_binary_insertion_sort.h"

static void merge_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));
static void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*));
static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
static size_t binary_search(void *searchArray, void *item, size_t size, size_t low, size_t high, int (*compar)(const void*, const void*));
static void swap(void *val1, void *val2, size_t size);
static void move_pointer(void **base, int ammount);

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)){
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

    if (nitems >= k) {
        merge_sort(base, nitems, size, k, compar);
    } else {
        binary_insertion_sort(base, nitems, size, compar);
    }
}

static void merge_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)) {
    size_t firstHalfNitems = nitems / 2;
    size_t secondHalfNitems = nitems - firstHalfNitems;

    void *arr1 = (void *) malloc(firstHalfNitems * size);
    if (arr1 == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: unable to allocate memory for merge sort");
      exit(EXIT_FAILURE);
    }
    memcpy(arr1, base, firstHalfNitems * size);
    
    void *arr2 = (void *) malloc(secondHalfNitems * size);
    if (arr2 == NULL) {
      fprintf(stderr, "merge_binary_insertion_sort: unable to allocate memory for merge sort");
      exit(EXIT_FAILURE);
    }
    void *arr2Pointer = base;
    move_pointer(&arr2Pointer, (int)(firstHalfNitems * size));
    memcpy(arr1, arr2Pointer, secondHalfNitems * size);
    
    merge_binary_insertion_sort(arr1, firstHalfNitems, size, k, compar);
    merge_binary_insertion_sort(arr2, secondHalfNitems, size, k, compar);

    merge(base, arr1, firstHalfNitems, arr2, secondHalfNitems, size, compar); 
    
    free(arr1);
    free(arr2);
}

static void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*)) {
    unsigned long i = 0, j = 0;

    while (i < arr1Nitems && j < arr2Nitems) {

        int compareResult = compar(arr1, arr2);

        if (compareResult < 0) {
            memcpy(base, arr1, size);
            move_pointer(&arr1, (int)size);
            i++;
        } else {
            memcpy(base, arr2, size);
            move_pointer(&arr2, (int)size);
            j++;
        }

        move_pointer(&base, (int)size);
    }

    while (i < arr1Nitems) {
        memcpy(base, arr1, size);
        move_pointer(&arr1, (int)size);
        move_pointer(&base, (int)size);
        i++;
    }

    while (j < arr2Nitems) {
        memcpy(base, arr2, size);
        move_pointer(&arr2, (int)size);
        move_pointer(&base, (int)size);
        j++;
    }

    // reset base pointer
    move_pointer(&arr1, -(int)(i * size));
    move_pointer(&arr2, -(int)(j * size));
    move_pointer(&base, -(int)((i + j) * size));
}

static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    unsigned long i = 1, j;
    void *currentPointer = base;
    void *compareItem1 = base;
    void *compareItem2 = base;

    move_pointer(&currentPointer, (int)size); // Skip the first

    while (i < nitems) {
        size_t indexNewValue = binary_search(base, currentPointer, size, 0, i - 1, compar);

        j = i;
        while (j != indexNewValue) {
            compareItem1 = base;
            move_pointer(&compareItem1, (int)(j * size));
            
            compareItem2 = base;
            move_pointer(&compareItem2, (int)((j - 1) * size));

            swap(compareItem1, compareItem2, size);
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

    if (compar(currentItem, item) < 0) { // If item is greater of current item
        return binary_search(searchArray, item, size, mid + 1, high, compar);
    } else {
        return binary_search(searchArray, item, size, low, mid - 1, compar);
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