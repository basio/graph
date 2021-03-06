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

package org.apache.giraph.comm.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.giraph.utils.VertexIdMessages;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

/**
 * Message store
 *
 * @param <I> Vertex id
 * @param <M> Message data
 */
public interface MessageStore<I extends WritableComparable,
    M extends Writable> {
  /**
   * True if this message-store encodes messages as a list of long pointers
   * to compact serialized messages
   *
   * @return true if we encode messages as a list of pointers
   */
  boolean isPointerListEncoding();

  /**
   * Gets messages for a vertex.  The lifetime of every message is only
   * guaranteed until the iterator's next() method is called. Do not hold
   * references to objects returned by this iterator.
   *
   * @param vertexId Vertex id for which we want to get messages
   * @return Iterable of messages for a vertex id
   * @throws java.io.IOException
   */
  Iterable<M> getVertexMessages(I vertexId) throws IOException;

  /**
   * Clears messages for a vertex.
   *
   * @param vertexId Vertex id for which we want to clear messages
   * @throws IOException
   */
  void clearVertexMessages(I vertexId) throws IOException;

  /**
   * Clears all resources used by this store.
   *
   * @throws IOException
   */
  void clearAll() throws IOException;

  /**
   * Check if we have messages for some vertex
   *
   * @param vertexId Id of vertex which we want to check
   * @return True iff we have messages for vertex with required id
   */
  boolean hasMessagesForVertex(I vertexId);

  /**
   * Adds messages for partition
   *
   * @param partitionId Id of partition
   * @param messages    Collection of vertex ids and messages we want to add
   * @throws IOException
   */
  void addPartitionMessages(
      int partitionId, VertexIdMessages<I, M> messages)
    throws IOException;

  /**
   * Called before start of computation in bspworker
   * Since it is run from a single thread while the store is not being
   * accessed by any other thread - this is ensured to be thread-safe
   */
  void finalizeStore();

  /**
   * Gets vertex ids from selected partition which we have messages for
   *
   * @param partitionId Id of partition
   * @return Iterable over vertex ids which we have messages for
   */
  Iterable<I> getPartitionDestinationVertices(int partitionId);

  /**
   * Clears messages for a partition.
   *
   * @param partitionId Partition id for which we want to clear messages
   * @throws IOException
   */
  void clearPartition(int partitionId) throws IOException;

  /**
   * Serialize messages for one partition.
   *
   * @param out         {@link DataOutput} to serialize this object into
   * @param partitionId Id of partition
   * @throws IOException
   */
  void writePartition(DataOutput out, int partitionId) throws IOException;

  /**
   * Deserialize messages for one partition
   *
   * @param in          {@link DataInput} to deserialize this object
   *                    from.
   * @param partitionId Id of partition
   * @throws IOException
   */
  void readFieldsForPartition(DataInput in,
      int partitionId) throws IOException;


  /**
   * ASYNC: Check if we have any unprocessed messages for a partition.
   *
   * @param partitionId Id of partition
   * @return True if we have unprocessed messages for the partition
   */
  //boolean hasMessagesForPartition(int partitionId);

  /**
   * ASYNC: Check if we have any unprocessed messages.
   *
   * @return True if we have unprocessed messages
   */
  boolean hasMessages();

  /**
   * Gets messages for a vertex and removes it from the underlying store.
   * The lifetime of every message is only guaranteed until the iterator's
   * next() method is called. Do not hold references to objects returned
   * by this iterator.
   *
   * Similar to getVertexMessages() followed by clearVertexMessages(),
   * but this is "atomic" and returns an iterable after clearing.
   *
   * @param vertexId Vertex id for which we want to get messages
   * @return Iterable of messages for a vertex id
   * @throws java.io.IOException
   */
  Iterable<M> removeVertexMessages(I vertexId) throws IOException;
  /**
   * ASYNC: Adds an unserialized message for partition.
   * Caller can invalidate destVertexId or message after the call.
   *
   * @param partitionId Id of partition
   * @param destVertexId Target vertex id (must be local to worker)
   * @param message Unserialized message to add
   * @throws IOException
   */
  //void addPartitionMessage(int partitionId, I destVertexId, M message)
//          throws IOException;


}
