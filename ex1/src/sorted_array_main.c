#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "merge_binary_insertion_sort.h"

struct Record {
    int id;
    char* string_field;
    int integer_field;
    float float_field;
};

struct ArrayInfo {
    struct Record *base;
    size_t size;
    size_t nitems;
};

static struct ArrayInfo extract_data(char *csvPath);
static void save_data(struct ArrayInfo arrayInfo, char *fileOutputPath);
static int compar_int(const void *val1, const void *val2);
static int compar_float(const void *val1, const void *val2);
static int compar_string(const void *val1, const void *val2);

#define ARGUMENT_NUMBER 5
#define RECORD_FIELDS_NUMBER 4

int main(int argc, char *argv[]) {
    if (argc != ARGUMENT_NUMBER) {
        fprintf(stderr, "main: wrong ammount of arguments");
        exit(EXIT_FAILURE);
    }

    if (atoi(argv[3]) >= RECORD_FIELDS_NUMBER) {
        fprintf(stderr, "main: recrod column index wrong (0 to 3)");
        exit(EXIT_FAILURE);
    }

    clock_t start, end;
    double cpu_time_used;

    printf("Extracting data from %s...\n", argv[1]);
    fflush(stdout);
    start = clock();
    struct ArrayInfo arrayInfo = extract_data(argv[1]);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of extraction: %.2f seconds\n", cpu_time_used);
    printf("Extracted %lli items, the size of each item is %lli byts\n", arrayInfo.nitems, arrayInfo.size);
    fflush(stdout);

    printf("Starting to sort the array...\n");
    start = clock();
    switch (atoi(argv[3]))
    {
        case 0:
        case 2:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_int);
            break;
        case 1:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_string);
            break;
        case 3:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_float);
            break;
    }
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of sorting: %.2f seconds\n", cpu_time_used);


    printf("Start saving data...\n");
    fflush(stdout);
    start = clock();
    save_data(arrayInfo, argv[2]);
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    printf("Time of saiving: %.2f seconds\n", cpu_time_used);
    printf("Succesfully saived the data int the file: %s\n", argv[2]);
    fflush(stdout);

    return 0;
}

static struct ArrayInfo extract_data(char *csvPath) {
    FILE* file = fopen(csvPath, "r");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    char buffer[1024];
    int bufSize = 1024;

    size_t nitems = 0;
    while (fgets(buffer, bufSize, file) != NULL) {
        nitems++;
    }

    fseek(file, 0, SEEK_SET);
    
    struct Record *array = (struct Record *)malloc(sizeof(struct Record) * nitems);

    unsigned long i = 0;
    while (fgets(buffer, bufSize, file) != NULL) {
        char* token = strtok(buffer, ",");
        
        array[i].id = atoi(token);
        token = strtok(NULL, ",");

        array[i].string_field = malloc((strlen(token) + 1) * sizeof(char));
        token = strtok(NULL, ",");
        
        array[i].integer_field = atoi(token);
        token = strtok(NULL, ",");

        char *ptr;
        array[i].float_field = strtof(token, &ptr);

        i++;
    }

    fclose(file);

    struct ArrayInfo arrayInfo;
    arrayInfo.base = array;
    arrayInfo.size = sizeof(struct Record);
    arrayInfo.nitems = nitems;
    return arrayInfo;
}

static void save_data(struct ArrayInfo arrayInfo, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    unsigned long i;
    for (i = 0; i < arrayInfo.nitems; i++) {
        fprintf(file, "%i,%s,%i,%f\n", 
            arrayInfo.base[i].id, arrayInfo.base[i].string_field, 
            arrayInfo.base[i].integer_field, arrayInfo.base[i].float_field);
    }

    fclose(file);
}

static int compar_int(const void *val1, const void *val2) {
    if(val1 == NULL){
        fprintf(stderr,"compar_int: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(val2 == NULL){
        fprintf(stderr,"compar_int: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if (*(int *)val1 > *(int *)val2)
        return 1;
    else if (*(int *)val1 < *(int *)val2)
        return -1;
    else
        return 0;
}

static int compar_float(const void *val1, const void *val2) {
    if(val1 == NULL){
        fprintf(stderr,"compar_float: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(val2 == NULL){
        fprintf(stderr,"compar_float: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if (*(float *)val1 > *(float *)val2)
        return 1;
    else if (*(float *)val1 < *(float *)val2)
        return -1;
    else
        return 0;
}

static int compar_string(const void *val1, const void *val2) {
    if(val1 == NULL){
        fprintf(stderr,"compar_string: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(val2 == NULL){
        fprintf(stderr,"compar_string: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    return strcmp((char *)val1, (char *)val2);
}