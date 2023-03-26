javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/unit/*.java ./src/test/system/*.java 
cd target

#echo "Running Unit Tests"
#numUnitTest=$(ls -1 test/unit | wc -l)
#ls test/unit | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh test.unit. ${numUnitTest} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore 

echo "Running System Tests"
numUnitTest=$(ls -1 test/system | wc -l)
ls test/system | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh test.system. ${numUnitTest} | xargs java -cp .:../../lib/*:src/* org.junit.runner.JUnitCore