javac -d target -cp .:../lib/* ./src/main/*.java
cd resources
java -cp ../target Project1.src.Main $1