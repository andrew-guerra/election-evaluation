run:
	bash misc/run-project.sh $(filename)

build:
	javac -d target -cp .:../lib/* ./src/main/*.java

javadocs:
	bash misc/generate-javadocs.sh

remove-javadocs:
	rm -r documentation/*

run-tests:
	bash misc/run-tests.sh

tests:
	javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/unit/*.java ./src/test/system/*.java 

clean:
	rm -r target/*

help:
	@echo 'Typical usage is:'
	@echo '  > make run                      # runs project in target folder without filename argument, resources will hold input and output files'
	@echo '  > make run filename=FILEMAME    # runs project in target folder with FILENAME argument'
	@echo '  > make build                    # builds project classes'
	@echo '  > make javadocs                 # generates javadocs in documentation folder'
	@echo '  > make remove-javadocs          # deletes javadocs in documentation folder'
	@echo '  > make run-tests                # runs test suite'
	@echo '  > make tests                	 # builds project test classes'
	@echo '  > make clean	                 # deletes compiled build classes'