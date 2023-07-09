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

    if (atoi(argv[3]) == 0 || atoi(argv[3]) >= RECORD_FIELDS_NUMBER) {
        fprintf(stderr, "main: recorod column index wrong (1 to 3)");
        exit(EXIT_FAILURE);
    }

    struct ArrayInfo arrayInfo = extract_data(argv[1]);

    switch (atoi(argv[3]))
    {
        case 1:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_string);
            break;
        case 2:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_int);
            break;
        case 3:
            merge_binary_insertion_sort(arrayInfo.base, arrayInfo.nitems, arrayInfo.size, (size_t)atoi(argv[4]), compar_float);
            break;
    }

    save_data(arrayInfo, argv[2]);

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

        array[i].string_field = (char *)malloc((strlen(token) + 1) * sizeof(char));
        strcpy(array[i].string_field, token);
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
    const size_t nitems = arrayInfo.nitems;

    for (i = 0; i < nitems; i++) {
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
    
    struct Record *record1 = (struct Record *)val1;
    struct Record *record2 = (struct Record *)val2;
    if (record1->integer_field > record2->integer_field)
        return 1;
    else if (record1->integer_field < record2->integer_field)
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

    struct Record *record1 = (struct Record *)val1;
    struct Record *record2 = (struct Record *)val2;
    if (record1->float_field > record2->float_field)
        return 1;
    else if (record1->float_field < record2->float_field)
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

    struct Record *record1 = (struct Record *)val1;
    struct Record *record2 = (struct Record *)val2;
    return strcmp(record1->string_field, record2->string_field);
}