javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/unit/*.java ./src/test/system/*.java 
cd target

echo "Running Unit Tests"
numUnitTest=$(ls -1 Project1/src/test/unit | wc -l)
ls Project1/src/test/unit | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh Project1.src.test.unit. ${numUnitTest} | xargs java -cp .:../../lib/*:Project1/src/* org.junit.runner.JUnitCore 

echo "Running System Tests"
numUnitTest=$(ls -1 Project1/src/test/system | wc -l)
ls Project1/src/test/system | bash ../misc/read-class-names.sh .class ${numUnitTest} | bash ../misc/append-strings.sh Project1.src.test.system. ${numUnitTest} | xargs java -cp .:../../lib/*:Project1/src/* org.junit.runner.JUnitCore