#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include "skip_list.h"

struct ReportData {
    double loading_time;
    size_t max_height;
    double time;
    int item_in_array;
    size_t total_items_loaded;
};

static void find_errors(FILE *dictfile, FILE *textfile, size_t max_height, char *fileOutputPath);
static int compar_string(const void *val1, const void *val2);
static void to_lower_case(char* str);
static void write_header(char *fileOutputPath);
static void save_data(struct ReportData reportData, char *fileOutputPath);

int main(int argc, char *argv[]) {
    setvbuf(stdout, NULL, _IONBF, 0);
    
    if (argc != 4) {
        fprintf(stderr, "main: not correct ammount of arguments (3)");
        exit(EXIT_FAILURE);
    }

    char *dataFinish = argv[3];

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

    write_header(dataFinish);
    for (size_t i = 50; i >= 1; i--) {
        printf("Starting %lli/50 tests\n", i);
        find_errors(dictFile, textFile, i, dataFinish);
        fseek(dictFile, 0, SEEK_SET);
        fseek(textFile, 0, SEEK_SET);
    }

    fclose(dictFile);
    fclose(textFile);

    return 0;
}

void find_errors(FILE *dictfile, FILE *textfile, size_t max_height, char *fileOutputPath) {
    struct SkipList *list = NULL;
    new_skiplist(&list, max_height, compar_string);

    char buffer[1024];
    int bufSize = 1024;

    clock_t start, end;
    double cpuTimeUsed;
    struct ReportData data;
    data.max_height = max_height;

    int j = 0;
    start = clock();
    while (fgets(buffer, bufSize, dictfile) != NULL) {
        // I have to remove one char to avoid putting \n inside the word
        char *word = (char *)malloc((strlen(buffer)) * sizeof(char));
        memcpy(word, buffer, strlen(buffer) - 1); 
        insert_skiplist(list, word);
        j++;
    }
    end = clock();
    cpuTimeUsed = ((double) (end - start)) / CLOCKS_PER_SEC;
    data.loading_time = cpuTimeUsed;
    data.total_items_loaded = (size_t)j;

    char *delimiter = " .,:;!?";
    int totWords = 0;
    while (fgets(buffer, bufSize, textfile) != NULL) {
        char* token = strtok(buffer, delimiter);
        while (token != NULL) {
            if (strlen(token) != 1) {
                totWords++;
            }
            token = strtok(NULL, delimiter);
        }
    }

    fseek(textfile, 0, SEEK_SET);

    while (fgets(buffer, bufSize, textfile) != NULL) {
        char* token = strtok(buffer, delimiter);
        while (token != NULL) {
            if (strlen(token) != 1) {
                char *word = (char *)malloc((strlen(token) + 1) * sizeof(char));
                memcpy(word, token, strlen(token) + 1);
                to_lower_case(word);
                start = clock();
                const void *existWord = search_skiplist(list, word);
                end = clock();
                cpuTimeUsed = ((double) (end - start)) / CLOCKS_PER_SEC;
                data.time = cpuTimeUsed;
                if (existWord == NULL) {
                    data.item_in_array = 0;
                } else {
                    data.item_in_array = 1;
                }
                save_data(data, fileOutputPath);
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

static void write_header(char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "w");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    fprintf(file, "%s,%s,%s,%s,%s\n", "max_height", "time", "found", "loading_time", "nitems");

    fclose(file);
}

static void save_data(struct ReportData reportdata, char *fileOutputPath) {
    FILE* file = fopen(fileOutputPath, "a");
    if (file == NULL) {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }

    fprintf(file, "%lli,%f,%i,%f,%lli\n", 
        reportdata.max_height, reportdata.time, reportdata.item_in_array,
        reportdata.loading_time, reportdata.total_items_loaded);

    fclose(file);
}