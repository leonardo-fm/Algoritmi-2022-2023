#include <stdio.h>
#include "sortingAlgorithm.c"

int main(int argc, char *argv[]);
void test_empty_array();

int main(int argc, char *argv[]) {
    printf("------------ Start testing ------------\n");
    test_empty_array();    
    return 0;
}

void test_empty_array() {
    ArrayInfos arrayInfos;
    arrayInfos.head = malloc(0);
    arrayInfos.nitems = 0;
    arrayInfos.size = 0;
    arrayInfos.type = NONE;

    merge_binary_insertion_sort(
        arrayInfos.head, arrayInfos.nitems, arrayInfos.size, 0, compar);

    printf("Completed\n");
}

void test_single_element_array() {
    ArrayInfos arrayInfos;
    arrayInfos.head = malloc(4);
    arrayInfos.nitems = 0;
    arrayInfos.size = 0;
    arrayInfos.type = NONE;

    merge_binary_insertion_sort(
        arrayInfos.head, arrayInfos.nitems, arrayInfos.size, 0, compar);

    printf("Completed\n");
}