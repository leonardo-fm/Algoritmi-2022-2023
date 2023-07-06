#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static int compar_int(const void *val1, const void *val2);

int main(int argc, char *argv[]) {
    setvbuf(stdout, NULL, _IONBF, 0);

    struct SkipList *list = NULL;
    new_skiplist(&list, 10, compar_int);
    /*
    printf("Created new skiplist, %u\n", list);
    for (int i = 0; i < 10; i++) {
        printf("Main\n");
        insert_skiplist(list, rand() % 1000);
    }
    */
    insert_skiplist(list, 100);
    insert_skiplist(list, 885);

    printf("Start searching...\n");

    int *response1 = (int *)search_skiplist(list, 885);
    int *response2 = (int *)search_skiplist(list, 1100);
    printf("R1:%i, R2:%i\n", response1, response2);

    clear_skiplist(&list);
    printf("cleared skiplist\n");

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