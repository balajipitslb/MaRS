package its.mnr.mp5.view;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowContext;
import oracle.adf.controller.TaskFlowTrainModel;
import oracle.adf.controller.TaskFlowTrainStopModel;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.view.rich.model.TrainStopModel;


public class TrainControllerBean {
    public TrainControllerBean() {
        super();
    }

    public boolean getPreviousDisabled() {
        TaskFlowTrainModel trainModel = getTrainModel();
        TaskFlowTrainStopModel currentStop = trainModel.getCurrentStop();


        TrainStopModel previoudStopModel = (TrainStopModel)trainModel.getPreviousStop(currentStop);

        if (previoudStopModel == null) {
            return true;
        }

        return previoudStopModel.isDisabled();
    }


    public boolean getNextDisabled() {
        TaskFlowTrainModel trainModel = getTrainModel();
        TaskFlowTrainStopModel currentStop = trainModel.getCurrentStop();

        TrainStopModel nextStopModel = (TrainStopModel)trainModel.getNextStop(currentStop);

        if (nextStopModel == null) {
            return true;
        }

        return nextStopModel.isDisabled();
    }


    private TaskFlowTrainModel getTrainModel() {
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentViewPortCtx = controllerContext.getCurrentViewPort();
        TaskFlowContext taskFlowCtx = currentViewPortCtx.getTaskFlowContext();

        TaskFlowTrainModel trainModel = taskFlowCtx.getTaskFlowTrainModel();
        return trainModel;
    }

    
}
