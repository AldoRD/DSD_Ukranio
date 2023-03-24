JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	PruebaPoligono.java \
	PoligonoIrreg.java \
	Coordenada.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
run:
	java PruebaPoligono