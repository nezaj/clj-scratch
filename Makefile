MAKEFLAGS = --no-print-directory --always-make --silent
MAKE = make $(MAKEFLAGS)

repl:
	@echo "Connecting local repl..."
	$(LEIN) repl
