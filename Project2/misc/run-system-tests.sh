javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/system/*.java 
cd target

echo "Running System Tests"
if [ $# -eq 0 ]
then
    # all test files
    numSystemTests=$(ls -1 test/system | wc -l)
    ls test/system | bash ../misc/read-class-names.sh .class ${numSystemTests} | bash ../misc/append-strings.sh test.system. ${numSystemTests} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore 
else 
    # specified test file
    numSystemTests=1
    echo "$1" | bash ../misc/read-class-names.sh .class ${numSystemTests} | bash ../misc/append-strings.sh test.system. ${numSystemTests} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore
fi