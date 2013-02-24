#
# Homework 4 Makefile
#

CC     = gcc
CFLAGS = -g -Wall -pedantic -Werror
LIBS   = -lpthread
ALL    = hw4

hw4:	hw4.c
	$(CC) $(CFLAGS) -o $@ $< $(LIBS)

clean:
	rm -f $(ALL) *.o
