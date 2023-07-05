typedef struct _SkipList SkipList;

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void*, const void*));

void clear_skiplist(struct SkipList **list);

void insert_skiplist(struct SkipList *list, void *item);

const void* search_skiplist(struct SkipList *list, void *item);