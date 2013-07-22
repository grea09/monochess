# Makefile Generique
 
EXT = java
CXX = javac
EXEC = class

MAIN = Main

COMPIL = ./COMPIL
CXXFLAGS = -d $(COMPIL) -Xlint
EXECFLAGS = -classpath $(COMPIL)
SRC = $(wildcard *.$(EXT))
BYTE_CODE = $(SRC:.$(EXT)=.$(EXEC))
BYTE_CODE := $(addprefix $(COMPIL)/, $(BYTE_CODE))
 
all: $(BYTE_CODE)
 
$(COMPIL)/%.$(EXEC): %.$(EXT)
	@echo "Compilation de $< vers $@ ..."
	@echo `mkdir $(COMPIL) 2>/dev/null` >/dev/null
	@$(CXX) $(CXXFLAGS) $< 

exec:
	@java $(EXECFLAGS) $(MAIN)
 
clean:
	@echo "Nettoyage de primtemps."
	@rm -rf $(COMPIL)/*.$(EXEC)
 
mrproper:
	@echo "Sa magie c'est sa puissance !"
	@rm -rf $(COMPIL)/*
	@ls $(COMPIL) || rmdir --ignore-fail-on-non-empty $(COMPIL)
