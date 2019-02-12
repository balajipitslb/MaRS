package its.mnr.mp5.view;

import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTree;

import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.TreeModel;

public class WOSGrpTreeBean {

    public WOSGrpTreeBean() {
    }

    private String selectedSGrpId;
    private String selectedSGrpDesc;

    public void onTreeSelect(SelectionEvent selectionEvent) {
        //original selection listener set by ADF
        //#{bindings.MrlrefWorkordermasterView1.treeModel.makeCurrent}
        String adfSelectionListener = "#{bindings.MrlrefWorkordermasterView1.treeModel.makeCurrent}";
        //make sure the default selection listener functionality is
        //preserved. you don't need to do this for multi select trees
        //as the ADF binding only supports single current row selection
        /* START PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        FacesContext fctx = FacesContext.getCurrentInstance();
        Application ApplicationModule = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = ApplicationModule.getExpressionFactory();
        MethodExpression me = null;
        me =
 exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class, new Class[] { SelectionEvent.class });

        me.invoke(elCtx, new Object[] { selectionEvent });
        /* END PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        RichTree tree = (RichTree)selectionEvent.getSource();
        TreeModel model = (TreeModel)tree.getValue();
        //get selected nodes
        RowKeySet rowKeySet = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeySet.iterator();

        //for single select configurations,this only is called once
        while (rksIterator.hasNext()) {
            List key = (List)rksIterator.next();
            JUCtrlHierBinding treeBinding = null;
            CollectionModel collectionModel = (CollectionModel)tree.getValue();
            treeBinding = (JUCtrlHierBinding)collectionModel.getWrappedData();
            JUCtrlHierNodeBinding nodeBinding = null;
            nodeBinding = treeBinding.findNodeByKeyPath(key);
            Row rw = nodeBinding.getRow();

            // Create binding for ViewObject
            DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.AccountRateTaskViewIterator}");
            ViewObject vo = dc.getViewObject();
            vo.applyViewCriteria(vo.getViewCriteriaManager().getViewCriteria("RateTasksBySGroupId"));

            //print first row attribute. Note that in a tree you have to
            //determine the node type if you want to select node attributes
            //by name and not index
            String rowType = rw.getStructureDef().getDefName();

            if (rowType.equalsIgnoreCase("MrlrefWorkordermasterView")) {
                //System.out.println("This row is a Work Order: " + rw.getAttribute("Wotype"));

            } else if (rowType.equalsIgnoreCase("MrlrefServicegrpView")) {
                selectedSGrpId = (String)rw.getAttribute("Servicegroupid");
                selectedSGrpDesc =
                        (String)rw.getAttribute("Wotype") + " - " + (String)rw.getAttribute("Sgrpdescription");
                //System.out.println("This row is a Service Group " + selectedSGrpId);

                //set Service Group info
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfSGrpId", selectedSGrpId);
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfWOSGrpSelected", selectedSGrpDesc);

                // set bind variable for ViewObject and execute
                vo.ensureVariableManager().setVariableValue("AcctRateTskSGIdBind", selectedSGrpId);
                //System.out.println("AcctRateTskSGIdBind=" + vo.ensureVariableManager().getVariableValue("AcctRateTskSGIdBind"));
                vo.executeQuery();

            } else {
                //System.out.println("Huh????");
            }

            // ... do more useful stuff here
        }


    }

}
