#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "skip_list.h"

static void find_errors(FILE *dictfile, FILE *textfile, size_t max_height);
static int compar_string(const void *val1, const void *val2);
static void to_lower_case(char* str);

#define ARGUMENT_NUMBER 4

int main(int argc, char *argv[]) {
    if (argc != ARGUMENT_NUMBER) {
        fprintf(stderr, "main: wrong ammount of arguments");
        exit(EXIT_FAILURE);
    }

    FILE *dictFile = fopen(argv[1], "r");
    if (dictFile == NULL) {
        fprintf(stderr, "main: unable to open the dic file");
        exit(EXIT_FAILURE);
    }

    FILE *textFile = fopen(argv[2], "r");
    if (textFile == NULL) {
        fprintf(stderr, "main: unable to open the txt file");
        exit(EXIT_FAILURE);
    }

    setvbuf(stdout, NULL, _IONBF, 0);
    find_errors(dictFile, textFile, (size_t)atol(argv[3]));

    fclose(dictFile);
    fclose(textFile);

    return 0;
}

void find_errors(FILE *dictfile, FILE *textfile, size_t max_height) {
    struct SkipList *list = NULL;
    new_skiplist(&list, max_height, compar_string);

    char buffer[1024];
    int bufSize = 1024;

    int i = 0;
    while (fgets(buffer, bufSize, dictfile) != NULL) {
        // I have to remove one char to avoid putting \n inside the word
        char *word = (char *)malloc((strlen(buffer)) * sizeof(char));
        memcpy(word, buffer, strlen(buffer) - 1); 
        insert_skiplist(list, word);
        if (i++ % 100 == 0) printf("\rInserted items: %i", i - 1);
    }
    printf("\n");

    char *delimiter = " .,:;!?";
    while (fgets(buffer, bufSize, textfile) != NULL) {
        char* token = strtok(buffer, delimiter);
        while (token != NULL) {
            if (strlen(token) != 1) {
                char *word = (char *)malloc((strlen(token) + 1) * sizeof(char));
                memcpy(word, token, strlen(token) + 1);
                to_lower_case(word);
                const void *existWord = search_skiplist(list, word);
                if (existWord == NULL) {
                    printf("%s\n", (char *)word);
                }
            }
            token = strtok(NULL, delimiter);
        }
    }
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

static void to_lower_case(char* str) {
    int i = 0;
    while (str[i]) {
        str[i] = (char)tolower(str[i]);
        i++;
    }
}