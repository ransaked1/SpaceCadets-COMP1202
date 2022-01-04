
all: server client
	@echo "MAKE ALL IS DONE"

server:
	@javac server/*.java
	@echo "MAKE SERVER IS DONE"

client:
	@javac client/*.java
	@echo "MAKE CLIENT IS DONE"

clean:
	-@rm server/*.class
	-@rm client/*.class
	@echo "MAKE CLEAN IS DONE"

re: clean server client
	@echo "MAKE RE IS DONE"

demo: client
	@java client.Client

.PHONY: clean server client