#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "skip_list.h"

static int compar_string(const void *val1, const void *val2);

int main(int argc, char *argv[]) {
    setvbuf(stdout, NULL, _IONBF, 0);

    //struct SkipList *list = NULL;
    //new_skiplist(&list, 10, compar_int);

    return 0;
}

static int compar_string(const void *val1, const void *val2) {
    if(val1 == NULL){
        fprintf(stderr,"compar_int: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(val2 == NULL){
        fprintf(stderr,"compar_int: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    
    return strcmp((char *)val1, (char *)val2);
}