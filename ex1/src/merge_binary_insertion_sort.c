#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "merge_binary_insertion_sort.h"

static void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*));
static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
static size_t binary_search(void *searchArray, void *item, size_t size, size_t low, size_t high, int (*compar)(const void*, const void*));
static void copy_array(const void *base, void *newArray, size_t nitems, size_t size);
static void swap(void *val1, void *val2, size_t size);
static void move_pointer(void **base, int ammount);

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)){
    if (nitems <= 1) {
        return;
    }

    if (nitems >= k) {
        size_t firstHalfNitems = nitems / 2;
        size_t secondHalfNitems = nitems - firstHalfNitems;

        void *arr1 = (void *) malloc(firstHalfNitems * size);
        copy_array(base, arr1, firstHalfNitems, size);
        void *arr2 = (void *) malloc(secondHalfNitems * size);
        unsigned char *basePointer = base;
        copy_array(basePointer + (firstHalfNitems * size), arr2, secondHalfNitems, size);
        
        merge_binary_insertion_sort(arr1, firstHalfNitems, size, k, compar);
        merge_binary_insertion_sort(arr2, secondHalfNitems, size, k, compar);

        merge(base, arr1, firstHalfNitems, arr2, secondHalfNitems, size, compar); 
        
        free(arr1);
        free(arr2);
    } else {
        binary_insertion_sort(base, nitems, size, compar);
    }
}

static void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*)) {
    
    unsigned long i = 0, j = 0;
    void *arr1val = (void *)malloc(size);
    void *arr2val = (void *)malloc(size);

    while (i < arr1Nitems && j < arr2Nitems) {
        if (i == 0 && j == 0) {
            memcpy(arr1val, arr1, size);
            memcpy(arr2val, arr2, size);
        }

        int compareResult = compar(arr1val, arr2val);

        if (compareResult < 0) {
            memcpy(base, arr1val, size);
            i++;
            if (i < arr1Nitems) {
                move_pointer(&arr1, (int)size);
                memcpy(arr1val, arr1, size);
            }
        } else {
            memcpy(base, arr2val, size);
            j++;
            if (j < arr2Nitems) {
                move_pointer(&arr2, (int)size);
                memcpy(arr2val, arr2, size);
            }
        }

        move_pointer(&base, (int)size);
    }

    while (i < arr1Nitems) {
        memcpy(base, arr1val, size);
        i++;
        if (i < arr1Nitems) {
            move_pointer(&arr1, (int)size);
            memcpy(arr1val, arr1, size);
        }

        move_pointer(&base, (int)size);
    }

    while (j < arr2Nitems) {
        memcpy(base, arr2val, size);
        j++;
        if (j < arr2Nitems) {
            move_pointer(&arr2, (int)size);
            memcpy(arr2val, arr2, size);
        }

        move_pointer(&base, (int)size);
    }

    free(arr1val);
    free(arr2val);

    // reset base pointer
    move_pointer(&arr1, -(int)(i * size));
    move_pointer(&arr2, -(int)(j * size));
    move_pointer(&base, -(int)((i + j) * size));
}

static void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    unsigned long i = 1;
    void *currentPointer = base;
    move_pointer(&currentPointer, (int)size); // Skip the first


    while (i < nitems) {
        size_t indexNewValue = binary_search(base, currentPointer, size, 0, i - 1, compar);

        unsigned long j = i;
        while (j != indexNewValue) {
            void *comparePointer1 = base;
            void *comparePointer2 = base;
            move_pointer(&comparePointer1, (int)(j * size));
            move_pointer(&comparePointer2, (int)((j - 1) * size));

            swap(comparePointer1, comparePointer2, size);
            j--;
        }

        move_pointer(&currentPointer, (int)size);
        i++;
    }
}

// Return the position where have to put the value
static size_t binary_search(void *searchArray, void *item, size_t size, size_t low, size_t high, int (*compar)(const void*, const void*)) {
    if (low >= high) {
        void *currentPointer = searchArray;
        move_pointer(&currentPointer, (int)(low * size));
        
        int compareResult = compar(currentPointer, item);
        if (compareResult >= 0) {
            return low;
        }
        return low + 1;
    }

    size_t mid = (low + high) / 2;
    void *currentPointer = searchArray;
    move_pointer(&currentPointer, (int)(mid * size));

    int compareResult = compar(currentPointer, item);

    if (compareResult < 0) { // If item is greater of current item
        return binary_search(searchArray, item, size, mid + 1, high, compar);
    } else {
        return binary_search(searchArray, item, size, low, mid - 1, compar);
    }
}

static void copy_array(const void *base, void *newArray, size_t nitems, size_t size) {
    memcpy(newArray, base, nitems * size);
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