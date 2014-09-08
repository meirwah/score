package com.hp.oo.execution.gateways;

import com.hp.score.engine.node.entities.WorkerNode;
import com.hp.score.engine.queue.entities.ExecStatus;
import com.hp.score.engine.queue.entities.ExecutionMessage;
import com.hp.score.engine.queue.entities.ExecutionMessageConverter;
import com.hp.score.engine.queue.entities.Payload;
import com.hp.score.worker.management.services.OutboundBuffer;
import com.hp.oo.internal.sdk.execution.Execution;
import com.hp.oo.internal.sdk.execution.FlowExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: kravtsov
 * Date: 21/11/12
 * Time: 15:19
 */
//todo - remove - non blocking
public class ExecutionGatewayImpl implements ExecutionGateway, SubFlowExecutionGateway {

    @Autowired
    private OutboundBuffer outBuffer;

    @Autowired
    ExecutionMessageConverter executionMessageConverter;

    @Override
    public void addExecution(Execution execution) {
        try {
            ExecutionMessage message = createExecutionMessage(execution);
            outBuffer.put(message);
        } catch (IOException | InterruptedException e) {
           throw new FlowExecutionException("Failed to put new execution object to the gateway...", e);
        }
    }

    private ExecutionMessage createExecutionMessage(Execution execution) throws IOException {
        Payload payload = executionMessageConverter.createPayload(execution);

        //take care of worker group id
        String workerGroupId = execution.getGroupName();
        String workerGroupForMessage;
        if(workerGroupId == null){
	        workerGroupForMessage = WorkerNode.DEFAULT_WORKER_GROUPS[0];
        }
        else{
            workerGroupForMessage = workerGroupId;
        }

           return new ExecutionMessage(ExecutionMessage.EMPTY_EXEC_STATE_ID,
                   ExecutionMessage.EMPTY_WORKER,
                   workerGroupForMessage,
                   String.valueOf(execution.getExecutionId()), //msgId
                   ExecStatus.PENDING,  //it is middle of the flow
                   payload,
                   0).setWorkerKey(UUID.randomUUID().toString());
       }
}
