javac -d target -cp .:../lib/* ./src/main/*.java
cd resources
shift
java -cp ../target main.Main $@