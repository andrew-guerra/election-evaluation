run:
	bash misc/run-project.sh

build:
	javac -d target -cp .:../lib/* ./src/*.java

javadocs:
	bash misc/generate-javadocs.sh

remove-javadocs:
	rm -r documentation/*

run-tests:
	bash misc/run-tests.sh

tests:
	javac -d target -cp .:../lib/* ./src/*.java ./src/test/unit/*.java ./src/test/system/*.java 

clean:
	rm -r target/*

help:
	@echo 'Typical usage is:'
	@echo '  > make run                      # runs project in target folder'
	@echo '  > make build                    # builds project classes'
	@echo '  > make javadocs                 # generates javadocs in documentation folder'
	@echo '  > make remove-javadocs          # deletes javadocs in documentation folder'
	@echo '  > make run-tests                # runs test suite'
	@echo '  > make tests                	 # builds project test classes'
	@echo '  > make clean	                 # deletes compiled build classes'