#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
//#include <windows.h>
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

struct ReportData {
    size_t nitems;
    int col_index_sort;
    double time;
    size_t k;
};

static struct ArrayInfo extract_data(char *csvPath);
static void write_header(char *fileOutputPath);
static void save_data(struct ReportData reportData, char *fileOutputPath);
static int compar_int(const void *val1, const void *val2);
static int compar_float(const void *val1, const void *val2);
static int compar_string(const void *val1, const void *val2);

int main() {
    //char path[MAX_PATH];
    //GetCurrentDirectory(MAX_PATH, path);
    //printf("Current directory: %s\n", path);

    char *dataSource = ".\\data\\records_light.csv";
    char *dataFinish = ".\\report\\report_tests.csv";

    size_t case1 = 15; 

    // Arrange
    clock_t start, end;
    double cpuTimeUsed;
    struct ArrayInfo arrayInfo = extract_data(dataSource);
    write_header(dataFinish);
    int col = 2;
    size_t k = 0;
    
    for (k = 0; k < case1; k++) {
        printf("Sorting case 1 - %lli/%lli\n", k + 1, case1);
        fflush(stdout);

        // Copy the array
        struct Record *copyArray = (struct Record *)malloc(sizeof(struct Record) * arrayInfo.nitems);
        memcpy(copyArray, arrayInfo.base, sizeof(struct Record) * arrayInfo.nitems);

        start = clock();
        switch (col)
        {
            case 1:
                merge_binary_insertion_sort(copyArray, arrayInfo.nitems, arrayInfo.size, k, compar_string);
                break;
            case 2:
                merge_binary_insertion_sort(copyArray, arrayInfo.nitems, arrayInfo.size, k, compar_int);
                break;
            case 3:
                merge_binary_insertion_sort(copyArray, arrayInfo.nitems, arrayInfo.size, k, compar_float);
                break;
        }
        end = clock();
        cpuTimeUsed = ((double) (end - start)) / CLOCKS_PER_SEC; //%.2f
        
        struct ReportData reportData;
        reportData.nitems = arrayInfo.nitems;
        reportData.col_index_sort = col;
        reportData.time = cpuTimeUsed;
        reportData.k = k;

        save_data(reportData, dataFinish);
        free(copyArray);
    }

    printf("Finish collecting data!\n");
    return 0;
}

static struct ArrayInfo extract_data(char *csvPath) {
    FILE* file = fopen(csvPath, "r");
    if (file == NULL) {
        fprintf(stderr, "extract_data: unable to open the file");
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

static void write_header(char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    fprintf(file, "%s,%s,%s,%s\n", "nline", "col_index", "time", "k");

    fclose(file);
}

static void save_data(struct ReportData reportdata, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "a");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    fprintf(file, "%lli,%i,%f,%lli\n", 
        reportdata.nitems, reportdata.col_index_sort, reportdata.time, reportdata.k);

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