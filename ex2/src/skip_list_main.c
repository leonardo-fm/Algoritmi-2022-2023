#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

int main(int argc, char argv[]) {
    char **test = (char **)malloc(sizeof(char) * 2);
    test[0] = "Hello";
    test[1] = "World";
    
    printf("%s %s!\n", test[0], test[1]);
    printf("Global P:%u, P0:%u, P1:%u\n", **test, *test[0], *test[1]);

    return 0;
}
