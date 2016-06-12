package org.apache.giraph.io.formats;

import org.apache.giraph.io.EdgeReader;
import org.apache.giraph.utils.IntPair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * Created by khalefa on 6/12/2016.
 */
public class GTgraphInputFormat  extends
            TextEdgeInputFormat<LongWritable, IntWritable> {
        /** Splitter for endpoints */
        private static final Pattern SEPARATOR = Pattern.compile("[\t ]");

        @Override
        public EdgeReader<LongWritable, IntWritable> createEdgeReader(
                InputSplit split, TaskAttemptContext context) throws IOException {
            return new org.apache.giraph.io.formats.GTgraphInputFormat.GTgraphEdgeReader();
        }
        class Edge {
            public long from;
            public long to;
            public int weight;
        }
        /**
         * {@link org.apache.giraph.io.EdgeReader} associated with
         * {@link org.apache.giraph.io.formats.IntNullTextEdgeInputFormat}.
         */
        public class GTgraphEdgeReader extends
                TextEdgeReaderFromEachLineProcessed<Edge> {
            @Override
            protected Edge preprocessLine(Text line) throws IOException {
                String[] tokens = SEPARATOR.split(line.toString());
                String a=tokens[0];
                if(a.equals("a")){
                Edge e=new Edge();
                e.from=Long.valueOf(tokens[1]);
                e.to=Long.valueOf(tokens[2]);
                e.weight=Integer.valueOf(tokens[3]);

                return e;}
                else return null;
            }

            @Override
            protected LongWritable getSourceVertexId(Edge edge)
                    throws IOException {
                return new LongWritable(edge.from);
            }

            @Override
            protected LongWritable getTargetVertexId(Edge edge)
                    throws IOException {
                return new LongWritable(edge.to);
            }

            @Override
            protected IntWritable getValue(Edge edge) throws IOException {
                return new IntWritable(edge.weight);
            }
        }
    }


