package fomat;

import org.apache.giraph.io.EdgeReader;
import org.apache.giraph.utils.LongPair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import  org.apache.giraph.io.formats.*;
import java.io.IOException;
import java.util.regex.Pattern;
/**
 * Created by khalefa on 4/30/2016.
 */
//I - Vertex id
//      E - Edge data

public class LongEdgeInputFormat  extends
        TextEdgeInputFormat<LongWritable, FloatWritable> {


        private static final Pattern SEPARATOR = Pattern.compile("[\t ]");

        @Override
        public EdgeReader<LongWritable, FloatWritable> createEdgeReader(
                InputSplit split, TaskAttemptContext context) throws IOException {
            return new LongEdgeInputReader();
        }

        /**
         * {@link org.apache.giraph.io.EdgeReader} associated with
         * {@link LongEdgeInputFormat}.
         */
        public class LongEdgeInputReader extends
                TextEdgeReaderFromEachLineProcessed<LongPair> {
            @Override
            protected LongPair preprocessLine(Text line) throws IOException {
                String[] tokens = SEPARATOR.split(line.toString());
                return new LongPair(Long.parseLong(tokens[0]),
                        Long.parseLong(tokens[1]));
            }

            @Override
            protected LongWritable getSourceVertexId(LongPair endpoints)
                    throws IOException {
                return new IntWritable(endpoints.getFirst());
            }

            @Override
            protected LongWritable getTargetVertexId(LongPair endpoints)
                    throws IOException {
                return new IntWritable(endpoints.getSecond());
            }

            @Override
            protected FloatWritable getValue(LongPair endpoints) throws IOException {
                return FloatWritable.get(1);
            }
        }
    }

