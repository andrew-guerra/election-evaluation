javac -d target -cp .:../lib/* ./src/main/*.java
cd resources
java -cp ../target main.Main $1