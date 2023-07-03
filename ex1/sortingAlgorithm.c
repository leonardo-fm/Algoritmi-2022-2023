// $ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv 27 1 INPUT
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_LINE_LENGTH 1000
#define MAX_COLUMN_LENGTH 100

typedef enum {
    NONE,
    INT,
    DOUBLE,
    STRING
} Type;

typedef struct {
    void *head;
    size_t nitems;
    size_t size;
    Type type;
} ArrayInfos;

int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath);
ArrayInfos extract_data(char csvPath[], int columnNumber);
Type get_value_type(char value[]);
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));
void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*));
void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
int binary_search(void *searchArray, void *item, size_t size, int low, int high, int (*compar)(const void*, const void*));
void copy_array(const void *base, void *newArray, size_t nitems, size_t size);
void swap(void *val1, void *val2, size_t size);
int compar(const void *val1, const void *val2);
void move_pointer(void **base, int ammount);

int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        return -1;
    }

    for (int i = 0; i < arrayInfos.nitems; i++) {
        switch (arrayInfos.type)
        {
            case NONE:
                printf("Error, valueType = NONE\n");
                return -1;
            case INT:
                fprintf(file, "%i\n", ((int *)base)[i]);
                break;
            case DOUBLE:
                fprintf(file, "%f\n", ((double *)base)[i]);
                break;
            case STRING:
                fprintf(file, "%s\n", ((char **)base)[i]);
                break;
        }
    }

    fclose(file);
    return 0;
}

// return the number of items in the array
ArrayInfos extract_data(char csvPath[], int columnNumber) {
    ArrayInfos arrayInfos;
    FILE* file = fopen(csvPath, "r");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        arrayInfos.nitems = -1;
        return arrayInfos;
    }

    size_t nitems = 0;
    size_t size = 0;
    Type valueType = NONE;

    char line[MAX_LINE_LENGTH];
    char column[MAX_COLUMN_LENGTH];
    while (fgets(line, sizeof(line), file) != NULL) {
        if (valueType == NONE) {
            char* token = strtok(line, ",");
            int columnCount = 0;
            while (token != NULL) {
                if (columnCount == columnNumber) {
                    strcpy(column, token);
                    valueType = get_value_type(column);
                    break;
                }
                token = strtok(NULL, ",");
                columnCount++;
            }
        }

        nitems++;
    }
    fseek(file, 0, SEEK_SET);

    switch (valueType)
    {
        case NONE:
            printf("Error, the input value has not been recognize\n");
            arrayInfos.nitems = -1;
            return arrayInfos;
        case INT:
            arrayInfos.head = malloc(sizeof(int) * nitems);
            size = sizeof(int);
            break;
        case DOUBLE:
            arrayInfos.head = malloc(sizeof(double) * nitems);
            size = sizeof(double);
            break;
        case STRING:
            arrayInfos.head = malloc(sizeof(char *) * nitems);
            size = sizeof(char *);
            break;
    }

    int i = 0;
    while (fgets(line, sizeof(line), file) != NULL) {
        char* token = strtok(line, ",");
        int columnCount = 0;
        while (token != NULL) {
            if (columnCount == columnNumber) {
                switch (valueType)
                {
                    case NONE:
                        printf("Error, valueType = NONE\n");
                        arrayInfos.nitems = -1;
                        return arrayInfos;
                    case INT:
                        ((int *)arrayInfos.head)[i] = atoi(token);
                        break;
                    case DOUBLE:
                        char *ptr;
                        ((double *)arrayInfos.head)[i] = strtod(token, &ptr);
                        break;
                    case STRING:
                        ((char **)arrayInfos.head)[i] = (char *)malloc((strlen(token) + 1) * sizeof(char));
                        strcpy(arrayInfos.head, token);
                        break;
                }

                i++;
                break;
            }
            token = strtok(NULL, ","); // take next token
            columnCount++;
        }
    }

    fclose(file);
    arrayInfos.nitems = nitems;
    arrayInfos.size = size;
    arrayInfos.type = valueType;
    return arrayInfos;
}

// Check the type of a string, 0 = int, 1 = double, 2 = string
Type get_value_type(char value[]) {

    int intValue;
    double doubleValue;

    if (sscanf(value, "%d", &intValue) == 1) { // check int
        return INT;
    } else if (sscanf(value, "%lf", &doubleValue) == 1) { // check double
        return DOUBLE;
    } else { // string
        return STRING;
    }
}

void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*)){
    if (nitems <= 1) {
        return;
    }

    if (nitems >= k) {
        int firstHalfNitems = nitems / 2;
        int secondHalfNitems = nitems - firstHalfNitems;

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

void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*)) {
    
    int i = 0, j = 0;
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
                move_pointer(&arr1, size);
                memcpy(arr1val, arr1, size);
            }
        } else {
            memcpy(base, arr2val, size);
            j++;
            if (j < arr2Nitems) {
                move_pointer(&arr2, size);
                memcpy(arr2val, arr2, size);
            }
        }

        move_pointer(&base, size);
    }

    while (i < arr1Nitems) {
        memcpy(base, arr1val, size);
        i++;
        if (i < arr1Nitems) {
            move_pointer(&arr1, size);
            memcpy(arr1val, arr1, size);
        }

        move_pointer(&base, size);
    }

    while (j < arr2Nitems) {
        memcpy(base, arr2val, size);
        j++;
        if (j < arr2Nitems) {
            move_pointer(&arr2, size);
            memcpy(arr2val, arr2, size);
        }

        move_pointer(&base, size);
    }

    free(arr1val);
    free(arr2val);

    // reset base pointer
    move_pointer(&arr1, -(i * size));
    move_pointer(&arr2, -(j * size));
    move_pointer(&base, -(i + j) * size);
}

void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*)) {
    int i = 1;
    void *currentPointer = base;
    move_pointer(&currentPointer, size); // Skip the first


    while (i < nitems) {
        int indexNewValue = binary_search(base, currentPointer, size, 0, i - 1, compar);

        int j = i;
        while (j != indexNewValue) {
            void *comparePointer1 = base;
            void *comparePointer2 = base;
            move_pointer(&comparePointer1, j * size);
            move_pointer(&comparePointer2, (j - 1) * size);

            swap(comparePointer1, comparePointer2, size);
            j--;
        }

        move_pointer(&currentPointer, size);
        i++;
    }
}

// Return the position where have to put the value
int binary_search(void *searchArray, void *item, size_t size, int low, int high, int (*compar)(const void*, const void*)) {
    if (low >= high) {
        void *currentPointer = searchArray;
        move_pointer(&currentPointer, low * size);
        
        int compareResult = compar(currentPointer, item);
        if (compareResult >= 0) {
            return low;
        }
        return low + 1;
    }

    int mid = (low + high) / 2;
    void *currentPointer = searchArray;
    move_pointer(&currentPointer, (mid * size));

    int compareResult = compar(currentPointer, item);

    if (compareResult < 0) { // If item is greater of current item
        int l = mid + 1;
        int h = high;
        return binary_search(searchArray, item, size, l, h, compar);
    } else {
        int l = low;
        int h = mid - 1;
        if (h < 0) h = 0;
        return binary_search(searchArray, item, size, l, h, compar);
    }
}

void copy_array(const void *base, void *newArray, size_t nitems, size_t size) {
    memcpy(newArray, base, nitems * size);
}

// Swap 2 generic value given the size
void swap(void *val1, void *val2, size_t size) {
    void *tmp = (void *) malloc(size);
    memcpy(tmp, val1, size);
    memcpy(val1, val2, size);
    memcpy(val2, tmp, size);
    free(tmp);
}

// val1 > val2 = 1, val1 < val2 = -1, val1 == val2 = 0
int compar(const void *val1, const void *val2) {
    if (*(int *)val1 > *(int *)val2)
        return 1;
    else if (*(int *)val1 < *(int *)val2)
        return -1;
    else
        return 0;
}

// move a pointer of x ammount
void move_pointer(void **base, int ammount) {
    unsigned char *basePointer = *base;
    basePointer += ammount;
    *base = (void *)basePointer;
}