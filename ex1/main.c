// $ ./main_ex1 /tmp/data/records.csv /tmp/data/sorted.csv 27 1 INPUT
#include <stdio.h>

int main(int argc, char* argv[]){
    if (argc != 5) {
        printf("Not enough arguments (%i)", argc);
        return -1;
    }

    printf("%s, %s, %s, %s", argv[1], argv[2], argv[3], argv[4]);
    return 0;
}