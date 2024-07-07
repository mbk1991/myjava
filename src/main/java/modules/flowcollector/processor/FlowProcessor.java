package modules.flowcollector.processor;

import modules.flowcollector.dao.Netflow;

public interface FlowProcessor{
    public Netflow flowProcess(Netflow input);

}
