$HADOOP_HOME/bin/hadoop jar giraph-example.jar org.apache.giraph.GiraphRunner -Dgiraph.logLevel=debug org.apache.giraph.examples.SimpleShortestPathsComputation -eif  org.apache.giraph.format.LongEdgeInputFormat    -eip /user/input/twitter -vof org.apache.giraph.io.formats.IdWithValueTextOutputFormat  -op twitter-output-$(date +%s) -yj giraph-example.jar,giraph-core.jar -w 10

