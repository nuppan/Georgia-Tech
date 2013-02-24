#ifndef _GTTHREAD_H_
#define _GTTHREAD_H_

/*
 * Types
 */
typedef struct
{
  int in_use;
} gtthread_mutex_t;

typedef struct
{
  int flags;
  uint8_t stack_base;
  int stack_size;
} gtthread_attr_t;

typedef unsigned int gtthread_t;
/*
 * Prototypes
 */
#ifdef __cplusplus
extern "C" {
#endif

int gtthread_attr_init(gtthread_attr_t * attr);
int gtthread_create(gtthread_t *thread, gtthread_attr_t const * attr,
                   void *(*start_routine)(void *), void * arg);
int gtthread_join(gtthread_t thid, void ** ret_val);
void gtthread_exit(void *ret_val);

int gtthread_mutex_init(gtthread_mutex_t *mutex);
int gtthread_mutex_lock(gtthread_mutex_t *mutex);
int gtthread_mutex_unlock(gtthread_mutex_t *mutex);


#ifdef __cplusplus
} /* extern "C" */
#endif


#endif /* _GTTHREAD_H_ */
