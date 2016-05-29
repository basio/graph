/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.giraph.examples;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Demonstrates the basic Pregel shortest paths implementation.
 */
@Algorithm(
        name = "NoN",
        description = "test sending to non neighbour"
)

/**
 * Computation in which both incoming and outgoing message types are the same.
 *
 * @param <I> Vertex id
 * @param <V> Vertex data
 * @param <E> Edge data
 * @param <M> Message type
 */
public class NoNF extends        BasicComputation<
        LongWritable, DoubleWritable, DoubleWritable, FloatWritable> {

{
    /** Class logger */
    private static final Logger LOG =
            Logger.getLogger(NoNF.class);

    @Override
    public void compute ( Vertex<LongWritable, DoubleWritable, DoubleWritable> vertex,
    Iterable<FloatWritable> messages)  throws IOException {
        if (getSuperstep() >= 1) {
            double i=0;
            for (FloatWritable message : messages) {
                i++;
            }
            vertex.setValue(new DoubleWritable(i));
        }

        else {
            sendMessage(new LongWritable(1), new DoubleWritable(1));
            vertex.voteToHalt();
        }
    }
}
