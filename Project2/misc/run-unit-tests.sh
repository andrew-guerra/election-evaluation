javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/unit/*.java 
cd target

echo "Running Unit Tests"
if [ $# -eq 0 ]
then
    # all test files
    numUnitTest=$(ls -1 test/unit | wc -l)
    ls test/unit | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh test.unit. ${numUnitTest} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore 
else 
    # specified test file
    numUnitTest=1
    echo "$1" | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh test.system. ${numUnitTest} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore
fi