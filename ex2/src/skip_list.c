#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static size_t random_level(size_t max_height);
static struct Node* create_node(void *item, size_t lvl);
static void clear_node(struct Node *node, size_t k);

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void*, const void*)) {
    if(max_height <= 0) {
        fprintf(stderr, "new_skiplist: the max_height must be greater then 0");
        exit(EXIT_FAILURE);
    }

    if(compar == NULL) {
        fprintf(stderr, "new_skiplist: the compar function can't be NULL");
        exit(EXIT_FAILURE);
    }

    *list = (struct SkipList *)malloc(sizeof(struct SkipList));
    if (*list == NULL) {
        fprintf(stderr, "new_skiplist: unable to allocate memory for creating new skiplist");
        exit(EXIT_FAILURE);
    }
    (*list)->max_height = max_height;
    (*list)->max_level = 1;
    (*list)->compare = compar;
    (*list)->heads = (struct Node **)malloc(sizeof(struct Node) * max_height);
    for (int i = 0; i < max_height; i++) {
        struct Node *defaultNode;
        defaultNode->next = NULL;
        defaultNode->size = NULL;
        defaultNode->item = NULL;
        (*list)->heads[i] = defaultNode;
    }
    if ((*list)->heads == NULL) {
        fprintf(stderr, "create_node: unable to allocate memory for creating new heands");
        exit(EXIT_FAILURE);
    }
}

void clear_skiplist(struct SkipList **list) {
    if(list == NULL){
        fprintf(stderr, "clear_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    for (size_t i = 0; i < (*list)->max_level - 1; i++) {
        clear_node((*list)->heads[i], i);
    }
    free(list);
}

void insert_skiplist(struct SkipList *list, void *item) {
    if(list == NULL){
        fprintf(stderr, "insert_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(item == NULL){
        fprintf(stderr, "insert_skiplist: the item parameter is a null pointer");
        exit(EXIT_FAILURE);
    }   
    printf("Bif create\n");
    struct Node *newNode = create_node(item, random_level(list->max_height));
    printf(":| %i\n", (int *)(newNode->item));
    if (newNode->size > list->max_level) {
        list->max_level = newNode->size;
    }
    
    struct Node **currentNode = list->heads;
    for (int i = (int)list->max_level - 1; i >= 0; i--) {
        if (currentNode[i]->next == NULL 
            || list->compare(currentNode[i]->item, item) > 0) {
            if (newNode->size > (size_t)i) {
                if (currentNode[i]->next == NULL) {
                    newNode->next[i] = NULL;
                } else {
                    newNode->next[i] = currentNode[i]->next[i];
                }
                currentNode[i]->next = newNode;
            }
        } else {
            currentNode[i] = currentNode[i]->next;
            i++;
        }
    }
}

const void* search_skiplist(struct SkipList *list, void *item) {
    if(list == NULL){
        fprintf(stderr, "search_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(item == NULL){
        fprintf(stderr, "search_skiplist: the item parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    struct Node **currentNode = list->heads;

    printf("OK %lli\n", list->max_level);
    for (int i = (int)list->max_level - 1; i > 0; i--) {
        while (currentNode[i]->next[i]->item != NULL
            && list->compare(currentNode[i]->next[i]->item, item) <= 0) {
            printf("Item:%i, next[%i].item = %i && %i comp %i = %i\n", item, i, currentNode[i]->next[i]->item, currentNode[i]->next[i]->item, item, list->compare(currentNode[i]->next[i]->item, item));
            currentNode = currentNode[i]->next;
        }
    }
    
    if (currentNode[0]->item == item) {
        return currentNode[0]->item;
    } else {
        return NULL;
    }
}

static size_t random_level(size_t max_height) {
    size_t lvl = 1;
    while ((double)rand() / (double)RAND_MAX < 0.5 && lvl < max_height) {
        lvl++;
    }
    return lvl;
}

static struct Node* create_node(void *item, size_t lvl) {
    printf("item = %i, %i\n", item, lvl);   
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    printf("OK\n");
    if (newNode == NULL) {
        fprintf(stderr, "create_node: unable to allocate memory for creating new node");
        exit(EXIT_FAILURE);
    }
    newNode->item = item;
    newNode->size = lvl;
    return newNode;
}

static void clear_node(struct Node *node, size_t k) {
    if (node->next != NULL) {
        clear_node(node->next[k], k);
    }
    free(node);
}