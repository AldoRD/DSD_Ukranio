# Proyecto 2
# Ramirez Dominguez Aldo Eduardo
# 4CM14

JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	PoligonoReg.java \
	Coordenada.java \
	SimpleGui.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
run:
	java SimpleGui 4