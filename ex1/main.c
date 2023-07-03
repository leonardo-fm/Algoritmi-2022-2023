// $ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv 27 1 INPUT
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include "sortingAlgorithm.c"

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

    if (arrayInfos.nitems == -1) {
        printf("Error while extracting data\n");
        return -1;
    }
    printf("Extracted %lli items, the size of each item is %lli byts\n", arrayInfos.nitems, arrayInfos.size);
    fflush(stdout);
    

    start = clock();
    merge_binary_insertion_sort(arrayInfos.head, arrayInfos.nitems, arrayInfos.size, atoi(argv[4]), compar);
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