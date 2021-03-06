package org.apache.giraph.io.formats;

import org.apache.giraph.io.EdgeReader;
import org.apache.giraph.utils.IntPair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.util.regex.Pattern;
/**
 * Created by khalefa on 6/12/2016.
 */
public class GTgraphInputFormat  extends
            TextEdgeInputFormat<LongWritable, FloatWritable> {
        /** Splitter for endpoints */
        private static final Pattern SEPARATOR = Pattern.compile("[\t ]");

        @Override
        public EdgeReader<LongWritable, FloatWritable> createEdgeReader(
                InputSplit split, TaskAttemptContext context) throws IOException {
            return new org.apache.giraph.io.formats.GTgraphInputFormat.GTgraphEdgeReader();
        }
        class Edge {
            public long from;
            public long to;
            public float weight;
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
                else 
{			Edge e=new Edge();
			e.from=e.to=-1;
			e.weight=0;
			return e;
            }
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
            protected FloatWritable getValue(Edge edge) throws IOException {
                return new FloatWritable(edge.weight);
            }
        }
    }



