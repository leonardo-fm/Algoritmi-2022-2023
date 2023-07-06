#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static int compar_int(const void *val1, const void *val2);

int main(int argc, char *argv[]) {
    
    struct SkipList *list = NULL;
    new_skiplist(&list, 10, compar_int);
    printf("Created new skiplist, %u\n", list);
    fflush(stdout);
    for (int i = 0; i < 100; i++) {
        int *newItem = (int *)malloc(sizeof(int));
        *newItem = rand() % 1000;
        insert_skiplist(list, newItem);
        printf("Added item %i\n", *newItem);
        fflush(stdout); 
    }
    clear_skiplist(&list);
    printf("cleared skiplist\n");
    fflush(stdout);
    
    printf("Finish\n");
    return 0;
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
    
    if ((int *)val1 > (int *)val2)
        return 1;
    else if ((int *)val1 < (int *)val2)
        return -1;
    else
        return 0;
}