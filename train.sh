#/bin/bash
mvn exec:java -Dexec.mainClass="ciir.umass.edu.eval.Evaluator" -Dexec.args="-train src/main/resources/ranklib/input.txt -ranker 4 -save result-model.txt"