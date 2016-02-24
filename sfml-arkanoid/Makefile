
PROG=sfml-arkanoid
SRCS=sfml.cpp
LIB=/usr/local/SFML-2.3.2/lib
LINK=-lsfml-system -lsfml-graphics -lsfml-window -lsfml-audio

all:
	rm -f $(PROG)
	clang++ -o $(PROG) -std=c++11 -O3 -L -L$(LIB) $(LINK) $(SRCS)

check-syntax:
	clang++ -std=c++11 -o .nul -S ${CHK_SOURCES}

