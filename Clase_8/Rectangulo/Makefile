JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	PruebaRectangulo.java \
	Rectangulo.java \
	Coordenada.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
run:
	java PruebaRectangulo