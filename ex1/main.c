// $ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv 27 1 INPUT
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define ARGUMENT_NUMBER 5
#define MAX_LINE_LENGTH 1000
#define MAX_COLUMN_LENGTH 100

typedef enum {
    NONE,
    INT,
    DOUBLE,
    STRING
} Type;

typedef struct {
    size_t nitems;
    int size;
    Type type;
} ArrayInfos;

int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath);
ArrayInfos extract_data(void *base, char csvPath[], int columnNumber);
Type get_value_type(char value[]);
void merge_binary_insertion_sort(void *base, size_t nitems, size_t size, size_t k, int (*compar)(const void*, const void*));
void merge(void *base, void *arr1, size_t arr1Nitems, void *arr2, size_t arr2Nitems, size_t size, int (*compar)(const void*, const void*));
void binary_insertion_sort(void *base, size_t nitems, size_t size, int (*compar)(const void*, const void*));
int binary_search(void *searchArray, void *item, size_t nitems, size_t size, int low, int high, int (*compar)(const void*, const void*));
void copy_array(const void *base, void *newArray, size_t nitems, size_t size);
void swap(void *val1, void *val2, size_t size);
int compar(const void *val1, const void *val2);

int main(int argc, char *argv[]) {
    if (argc != ARGUMENT_NUMBER) {
        printf("Not enough arguments %i\n", argc);
        return -1;
    }

    printf("Extracting data from %s...\n", argv[1]);
    void *base;
    ArrayInfos arrayInfos = extract_data(&base, argv[1], atoi(argv[4]));
    if (arrayInfos.nitems == -1) {
        printf("Error while extracting data\n");
        return -1;
    }
    printf("Extracted %i items, the size of each item is %i byts\n", arrayInfos.nitems, arrayInfos.size);
    
    int response = save_data(base, arrayInfos, argv[2]);
    if (response == -1) {
        printf("Error while saving data\n");
        return -1;
    }
    return 0;
    
    merge_binary_insertion_sort(base, arrayInfos.nitems, arrayInfos.size, atoi(argv[3]), compar);
    save_data(base, arrayInfos, argv[2]);

    return 0;
}

int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        return -1;
    }
    
    printf("%i\n", ((int *)base)[0]);

    return 0;

    for (int i = 0; i < arrayInfos.nitems; i++) {
        switch (arrayInfos.type)
        {
            case INT:
                fprintf(file, "%i,", ((int *)base)[i]);
                break;
            case DOUBLE:
                fprintf(file, "%d,", ((double *)base)[i]);
                break;
            case STRING:
                fprintf(file, "%s,", ((char **)base)[i]);
                break;
        }
    }

    fclose(file);
    return 0;
}

// return the number of items in the array
ArrayInfos extract_data(void *base, char csvPath[], int columnNumber) {
    ArrayInfos arrayInfos;
    FILE* file = fopen(csvPath, "r");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        arrayInfos.nitems = -1;
        return arrayInfos;
    }

    size_t nitems = 0;
    int size = 0;
    
    char line[MAX_LINE_LENGTH];
    char column[MAX_COLUMN_LENGTH];
    Type valueType = NONE;
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
        case INT:
            base = malloc(sizeof(int) * nitems);
            size = sizeof(int);
            break;
        case DOUBLE:
            base = malloc(sizeof(double) * nitems);
            size = sizeof(double);
            break;
        case STRING:
            base = malloc(sizeof(char *) * nitems);
            size = sizeof(char *);
            break;
    }

    int i = 0;
    while (fgets(line, sizeof(line), file) != NULL) {
        char* token = strtok(line, ",");
        int columnCount = 0;
        while (token != NULL) {
            if (columnCount == columnNumber) {
                strcpy(column, token);
                switch (valueType)
                {
                    case INT:
                        printf("base *= %i, base[%i] = %i, data[%i] = %i\n", 
                            base, i, ((int *)base)[0], i, atoi(column));
                        ((int *)base)[i] = atoi(column);
                        break;
                    case DOUBLE:
                        char *ptr;
                        ((double *)base)[i] = strtod(column, &ptr);
                        break;
                    case STRING:
                        ((char **)base)[i] = (char *)malloc((strlen(column) + 1) * sizeof(char));
                        strcpy(base, column);
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
    char stringValue[100];

    if (sscanf(value, "%d", &intValue) == 1) { // check int
        return INT;
    } else if (sscanf(value, "%lf", &doubleValue) == 1) { // check double
        return DOUBLE;
    } else { // string
        return STRING;
    }
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
    memcpy(arr1val, arr1, size);
    memcpy(arr2val, arr2, size);
    while (i < arr1Nitems || j < arr2Nitems) {
        int compareResult = compar(arr1val, arr2val);
        if (compareResult >= 0) {
            i++;
            unsigned char *basePointer = base;
            memcpy(basePointer + ((i + j) * size), arr1val, size);
            unsigned char *arrPointer = arr1;
            memcpy(arr1val, arrPointer + (i * size), size);
        } else {
            j++;
            unsigned char *basePointer = base;
            memcpy(basePointer + ((i + j) * size), arr2val, size);
            unsigned char *arrPointer = arr2;
            memcpy(arr2val, arrPointer + (j * size), size);
        }
    }

    if (i < arr1Nitems) {
        while (i < arr1Nitems || j < arr2Nitems) {
            i++;
            unsigned char *basePointer = base;
            memcpy(basePointer + ((i + j) * size), arr1val, size);
            unsigned char *arrPointer = arr1;
            memcpy(arr1val, arrPointer + (i * size), size);
        }
    } else {
        while (i < arr1Nitems || j < arr2Nitems) {
            j++;
            unsigned char *basePointer = base;
            memcpy(basePointer + ((i + j) * size), arr2val, size);
            unsigned char *arrPointer = arr2;
            memcpy(arr2val, arrPointer + (j * size), size);
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
        memcpy(arrVal, basePointer + (i * size), size);
        int indexNewValue = binary_search(base, arrVal, i + 1, size, 0, i, compar);
        int j = i;
        while (j != indexNewValue) {
            void *arrCompVal = (void *)malloc(size);
            unsigned char *basePointer = base;
            memcpy(arrCompVal, basePointer + (i - 1 * size), size);
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
        memcpy(basePointer + (low * size), arrVal, size);
        int compareResult = compar(arrVal, item);
        free(arrVal);
        return low + compareResult;
    }

    int mid = nitems / 2;
    void *midArrVal = (void *)malloc(size);
    unsigned char *basePointer = searchArray;
    memcpy(basePointer + (mid * size), midArrVal, size);
    
    int compareResult = compar(midArrVal, item);

    if (compareResult >= 0) {
        binary_search(searchArray, item, high - mid, size, mid + 1, high, compar);
    } else {
        binary_search(searchArray, item, mid - low, size, low, mid - 1, compar);
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