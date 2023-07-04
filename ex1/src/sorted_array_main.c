#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "merge_binary_insertion_sort.h"

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

static ArrayInfos extract_data(char csvPath[], int columnNumber);
static Type get_value_type(char value[]);
static int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath);
static int compar(const void *val1, const void *val2);

#define ARGUMENT_NUMBER 5

int main(int argc, char *argv[]) {
    if (argc != ARGUMENT_NUMBER) {
        printf("Not enough arguments %i\n", argc);
        return -1;
    }

    printf("Extracting data from %s...\n", argv[1]);
    fflush(stdout);

    clock_t start, end;
    double cpu_time_used;

    start = clock();
    ArrayInfos arrayInfos = extract_data(argv[1], atoi(argv[3]));
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of extraction: %.2f seconds\n", cpu_time_used);

    if (arrayInfos.type == NONE) {
        printf("Error while extracting data\n");
        return -1;
    }
    printf("Extracted %lli items, the size of each item is %lli byts\n", arrayInfos.nitems, arrayInfos.size);
    fflush(stdout);
    

    start = clock();
    merge_binary_insertion_sort(arrayInfos.head, arrayInfos.nitems, arrayInfos.size, (size_t)atoi(argv[4]), compar);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of sorting: %.2f seconds\n", cpu_time_used);

    printf("Start saving data...\n");
    fflush(stdout);

    start = clock();
    int response = save_data(arrayInfos.head, arrayInfos, argv[2]);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of saiving: %.2f seconds\n", cpu_time_used);

    if (response == -1) {
        printf("Error while saving data\n");
        return -1;
    }
    printf("Succesfully saived the data int the file: %s\n", argv[2]);
    fflush(stdout);

    return 0;
}

// return the number of items in the array
static ArrayInfos extract_data(char csvPath[], int columnNumber) {
    ArrayInfos arrayInfos;
    FILE* file = fopen(csvPath, "r");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        arrayInfos.type = NONE;
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
            arrayInfos.type = NONE;
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
                char *ptr;
                switch (valueType)
                {
                    case NONE:
                        printf("Error, valueType = NONE\n");
                        arrayInfos.type = NONE;
                        return arrayInfos;
                    case INT:
                        ((int *)arrayInfos.head)[i] = atoi(token);
                        break;
                    case DOUBLE:
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
static Type get_value_type(char value[]) {

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

static int save_data(void *base, ArrayInfos arrayInfos, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        printf("Failed to open the file.\n");
        return -1;
    }
     unsigned long i;
    for (i = 0; i < arrayInfos.nitems; i++) {
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

// val1 > val2 = 1, val1 < val2 = -1, val1 == val2 = 0
static int compar(const void *val1, const void *val2) {
    if (*(int *)val1 > *(int *)val2)
        return 1;
    else if (*(int *)val1 < *(int *)val2)
        return -1;
    else
        return 0;
}