package its.mnr.mp5.view;

import oracle.adf.controller.TaskFlowId;

public class Region {
    private String taskFlowId = "/WEB-INF/Taskflows/WorkOrder/WorkOrder-WorkOrderEntry.xml#WorkOrder-WorkOrderEntry";

    public Region() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public String workOrderWorkOrderEntry() {
        taskFlowId = "/WEB-INF/Taskflows/WorkOrder/WorkOrder-WorkOrderEntry.xml#WorkOrder-WorkOrderEntry";
        return null;
    }
}
