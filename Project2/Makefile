run:
	bash misc/run-project.sh $(MAKECMDGOALS) 

build:
	javac -d target -cp .:../lib/* ./src/main/*.java

javadocs:
	bash misc/generate-javadocs.sh

remove-javadocs:
	rm -r documentation/*

run-tests:
	bash misc/run-unit-tests.sh
	bash misc/run-system-tests.sh

run-unit-tests:
	bash misc/run-unit-tests.sh ${filename}

run-system-tests:
	bash misc/run-system-tests.sh ${filename}

tests:
	javac -d target -cp .:./lib/* ./src/main/*.java ./src/test/unit/*.java ./src/test/system/*.java 

clean:
	rm -r target/*
	rm testing/output/*.txt

help:
	@echo 'Typical usage is:'
	@echo '  > make run                                 # runs project in target folder without filename argument(s), election file(s) should be in resources folder'
	@echo '  > make run filename1 filename2 ...         # runs project in target folder with filename argument(s), election file(s) should be in resources folder'
	@echo '  > make build                               # builds project classes'
	@echo '  > make javadocs                            # generates javadocs in documentation folder'
	@echo '  > make remove-javadocs                     # deletes javadocs in documentation folder'
	@echo '  > make run-tests                           # runs test suite'
	@echo '  > make run-unit-tests filename=FILENAME    # runs FILENAME unit test'
	@echo '  > make run-system-tests filename=FILENAME  # runs FILENAME system test'
	@echo '  > make tests                	            # builds project test classes'
	@echo '  > make clean	                            # deletes compiled build classes'
