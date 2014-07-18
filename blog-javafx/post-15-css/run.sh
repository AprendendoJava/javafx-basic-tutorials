JAVA_HOME=/opt/java/jdk1.8.0/
rm *class
comp=$($JAVA_HOME/bin/javac TesteCSS.java)
if [ "$comp" == "" ]; then
	echo "Compilado com sucesso! executando a aplicação..."	
	$JAVA_HOME/bin/java TesteCSS
else
	echo "Falha na Compilação!"	
fi
