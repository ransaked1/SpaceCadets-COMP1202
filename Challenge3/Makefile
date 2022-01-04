NAME = BareBones.class

CC = javac

SRCDIR = src

RM = rm -rf

SRC = src/*.java

all: $(NAME)
	@echo "MAKE ALL IS DONE"

$(NAME):
	@$(CC) $(SRC)
	@mv $(SRCDIR)/*.class .

clean:
	@$(RM) *.class
	@echo "MAKE CLEAN IS DONE"

re: clean all
	@echo "MAKE RE IS DONE"

.PHONY: clean