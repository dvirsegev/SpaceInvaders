# 318651627
# segevdv

compile:
	mkdir bin
	javac -d bin -cp biuoop-1.4.jar:. src/*.java
check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*/*.java
jar:
	mkdir uber-jar
	unzip biuoop-1.4.jar -d uber-jar
	rm -rf uber-jar/META-INF
	cp -r bin/* uber-jar/
	jar -cfm ass7game.jar Manifest.mf -C uber-jar/ . -C resources .
	rm -rf uber-jar
run:
	java -cp biuoop-1.4.jar:bin:resources game/Ass6Game
