MAKEFLAGS = --no-print-directory --always-make --silent
MAKE = make $(MAKEFLAGS)

LEIN = /usr/local/bin/lein-2.9.3

repl:
	@echo "Connecting local repl..."
	$(LEIN) repl
