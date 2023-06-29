// $ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv 27 1 INPUT
#include <stdio.h>

const int ARGUMENT_NUMBER = 5;

int main(int argc, char *argv[]){
    if (argc != ARGUMENT_NUMBER) {
        printf("Not enough arguments", argc);
        return -1;
    }

    printf("%s, %s, %s, %s", argv[1], argv[2], argv[3], argv[4]);
    return 0;
}

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)){
    if (nitems == 1) {
        return;
    }

    if (nitems >= k) {
        int firstHalfNitems = nitems / 2;
        int secondHalfNitems = nitems - firstHalfNitems;

        void *arr1 = (void *) malloc(firstHalfNitems * size);
        void *arr2 = (void *) malloc(secondHalfNitems * size);

        merge_binary_insertion_sort(arr1, firstHalfNitems, size, k, compar);
        merge_binary_insertion_sort(arr2, secondHalfNitems, size, k, compar);
    
        merge(base, arr1, firstHalfNitems, arr2, secondHalfNitems, size, compar); 

        free(arr1);
        free(arr2);
    } else {
        binary_insertion_sort(base, nitems, size, compar);
    }
}

void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*)) {
    int i = 0, j = 0;
    void *arr1val = (void *)malloc(size);
    void *arr2val = (void *)malloc(size);
    memcopy(arr1val, arr1, size);
    memcopy(arr2val, arr2, size);
    while (i < arr1Nitems || j < arr2Nitems) {
        int compareResult = compar(arr1val, arr2val);
        if (compareResult >= 0) {
            i++;
            unsigned char *basePointer = base;
            memcopy(basePointer + ((i + j) * size), arr1val, size);
            unsigned char *arrPointer = arr1;
            memcopy(arr1val, arrPointer + (i * size), size);
        } else {
            j++;
            unsigned char *basePointer = base;
            memcopy(basePointer + ((i + j) * size), arr2val, size);
            unsigned char *arrPointer = arr2;
            memcopy(arr2val, arrPointer + (j * size), size);
        }
    }

    if (i < arr1Nitems) {
        while (i < arr1Nitems || j < arr2Nitems) {
            i++;
            unsigned char *basePointer = base;
            memcopy(basePointer + ((i + j) * size), arr1val, size);
            unsigned char *arrPointer = arr1;
            memcopy(arr1val, arrPointer + (i * size), size);
        }
    } else {
        while (i < arr1Nitems || j < arr2Nitems) {
            j++;
            unsigned char *basePointer = base;
            memcopy(basePointer + ((i + j) * size), arr2val, size);
            unsigned char *arrPointer = arr2;
            memcopy(arr2val, arrPointer + (j * size), size);
        }
    }
    
    free(arr1val);
    free(arr2val);
}

void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    int i = 1;
    while (i < nitems) {
        void *arrVal = (void *)malloc(size);
        unsigned char *basePointer = base;
        memcopy(arrVal, basePointer + (i * size), size);
        int indexNewValue = binary_search(base, arrVal, i + 1, size, 0, i, compar);
        int j = i;
        while (j != indexNewValue) {
            void *arrCompVal = (void *)malloc(size);
            unsigned char *basePointer = base;
            memcopy(arrCompVal, basePointer + (i - 1 * size), size);
            swap(arrVal, arrCompVal, size);
            j--;
        }
        i++;
    }
}

// Return the position where have to put the value
int binary_search(void *searchArray, void *item, size_t nitems, size_t size, int low, int high, int (*compar)(const void*, const void*)) {

    if (low == high) {
        void *arrVal = (void *)malloc(size);
        unsigned char *basePointer = searchArray;
        memcopy(basePointer + (low * size), arrVal, size);
        int compareResult = compar(arrVal, item);
        free(arrVal);
        return low + compareResult;
    }

    int mid = nitems / 2;
    void *midArrVal = (void *)malloc(size);
    unsigned char *basePointer = searchArray;
    memcopy(basePointer + (mid * size), midArrVal, size);
    
    int compareResult = compar(midArrVal, item);

    if (compareResult >= 0) {
        binary_search(searchArray, item, high - mid, size, mid + 1, high, compar);
    } else {
        binary_search(searchArray, item, mid - low, size, low, mid - 1, compar);
    }
}

void copy_array(const void *base, void *newArray, size_t nitems, size_t size) {
    memcopy(newArray, base, nitems * size);
}

// Swap 2 generic value given the size
void swap(void *val1, void *val2, size_t size) {
    void *tmp = (void *) malloc(size);
    memcopy(tmp, val1, size);
    memcopy(val1, val2, size);
    memcopy(val2, tmp, size);
    free(tmp);
}

// val1 > val2 = 1, val1 < val2 = -1, val1 == val2 = 0
int compar(const void *val1, const void *val2){
    int intValue1 = (int)val1;
    int intValue2 = (int)val2;

    if (intValue1 > intValue2)
        return 1;
    else if (intValue1 < intValue2)
        return -1;
    else
        return 0;
}

