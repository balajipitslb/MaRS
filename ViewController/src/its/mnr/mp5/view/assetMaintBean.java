package its.mnr.mp5.view;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.output.RichInlineFrame;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.LaunchPopupEvent;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.AttributeDescriptor;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;


public class assetMaintBean implements Serializable{

    @SuppressWarnings("compatibility:6029191428249787068")
    private static final long serialVersionUID = 1L;
    private RichInputText txtFileDesc;
    private RichInputFile uploadFileInput;
    private RichPanelFormLayout uploadPhotoForm;
    private RichPopup pendingChangePop;
    private RichPopup popAddSpare;
    private RichTable spareTable;
    private RichTable custAttribsTable;
    private Integer[] selectedIndxs;
    private ArrayList<Integer> selectedIndxList;
    private RichInputFile uploadAssetFileInput;
    private RichInputText txtAssetFileDesc;
    private RichSelectOneChoice txtAssetDocType;
    private RichPopup popAssetDocs;
    private RichTable assetDocsTable;
    private RichInputListOfValues txtAssetDocId;
    private RichInlineFrame inlineframeDocId;
    private RichTable assetReadingTable;
    private RichShowDetailItem tabReading;
    private RichShowDetailItem tabRecord;
    private RichPanelCollection assetsReadingTableContainer;
    private RichPopup popCopyAsset;
    private RichInputText txtCopyAssetOrg;
    private RichInputText txtCopyAssetAsset;
    private RichSelectBooleanCheckbox assetUploadNewPhotoChk;
    private RichShowDetailItem tabAssetPhotoUpload;
    private RichShowDetailItem tabAssetPhotoEdit;
    private String selectedItem="";
    private RichPopup popAssetPhoto;

    public assetMaintBean() {
    }

    /* Helper methods */

    public oracle.binding.BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public static BindingContext getBindingContext() {
        return BindingContext.getCurrent();
    }

    public static BindingContainer getBindingContainer() {
        return getBindingContext().getCurrentBindingsEntry();
    }

    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindingContainer();
    }

    public static OperationBinding getOperationBinding(String operationName) {
        return getBindingContainer().getOperationBinding(operationName);
    }

    public String userModificationMsg(String scope, String summary, String detail) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        //FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, text);

        FacesMessage fm = new FacesMessage();
        if (scope == "INFO") {
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (scope == "WARN") {
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        if (scope == "ERROR") {
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        if (scope == "FATAL") {
            fm.setSeverity(FacesMessage.SEVERITY_FATAL);
        }
        //fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fm.setSummary(summary);
        fm.setDetail(detail);
        ctx.addMessage(null, fm);
        return null;
    }
    
    public void goToControlFlow (String asaction, String soutcome){
            //navigate to controlcase_to_follow"
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler nh = context.getApplication().getNavigationHandler();
            nh.handleNavigation(context, asaction, soutcome);
        }

    public String doCommit() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "";
    }

    public boolean isDirty() {
        ApplicationModule am = getDCBindingContainer().getDataControl().getApplicationModule();
        Boolean transDirty = am.getTransaction().isDirty();

        BindingContext bc = getBindingContext();
        String dataControl = getBindingContext().getCurrentDataControlFrame();
        Boolean bindingsChanged = bc.findDataControlFrame(dataControl).isTransactionDirty();

        if (transDirty || bindingsChanged) {
            return true;
        }
        return false;
    }

    public String createInsertSparePart() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertSpare");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return "";
        }
        return "";
    }

    public String doRollback() {
        System.out.println("rolling back");
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        BindingContainer bindings = getBindings();

        if (StringUtils.isBlank(sAsset)) {
            // perform rollback operation on new record
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            // get current rows
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("MrltAssetViewIterator");
            Row rowKey = iter.getCurrentRow();
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //set current rows
            if (StringUtils.isNotBlank(sAsset)) {
                if (rowKey != null) {
                    Key parentKey = rowKey.getKey();
                    try {
                        iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        //System.out.println("Setting Orders");
                    } catch (RowNotFoundException ex) {
                        iter.getViewObject().applyViewCriteria(null);
                        iter.executeQuery();
                        //iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }

        }
        return "";
    }


    public void callAddSparePart(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            pendingChangePop.show(hints);

        } else {
            createInsertSparePart();
            //onLastRowCreate("POrderlinesViewIterator");
            //call popup
            //poLinePop.show(hints);
            popAddSpare.show(hints);
        }
    }

    public String doCancelSparePop() {
        doRollback();
        //RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popAddSpare.hide();
        return "";
    }

    public String doApplySparePop() {
        doCommit();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.spareTable);
        popAddSpare.hide();
        return "";
    }

    public void addSpareDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            //System.out.println("DialogEvent.Outcome.yes");
            //apply_action();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.spareTable);
        }
    }

    public void setAssetPhoto() {
        System.out.println("setAssetPhoto()");
        // get the current roew from the ImagesView2Iterator via the binding
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltDocumentsViewIterator");
        //System.out.println("display name: " + dciter.getDisplayName());
        Row newRow = dciter.getCurrentRow();
        // set the file name
        Long lDocId = (Long)newRow.getAttribute("Docid");
        Long lAssetDocId = (Long)ADFUtil.evaluateEL("#{bindings.Assetdocid.inputValue}");
        //        System.out.println("iDocId= " + lDocId);
        //        System.out.println("iAssetDocId= " + lAssetDocId);

        if ((lAssetDocId == null) || ((lAssetDocId != null) && (!lAssetDocId.equals(lDocId)))) {
            //System.out.println("Setting Asset Photo Id");
            ADFUtil.setEL("#{bindings.Assetdocid.inputValue}", lDocId);
        }


    }

    public void AssetPhotoDiagLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            if (isDirty()) {
                System.out.println("Asset Photo Diag Dirty");
                //call popup
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                pendingChangePop.show(hints);
            }
        }

    }

    public void setSelectedIndxs(Integer[] selectedIndxs) {
        this.selectedIndxs = selectedIndxs;
    }
    //method referenced from the value property of the Select
    //Many Shuttle component. Note that a related setter method is
    //contained in this managed bean as well

    public Integer[] getSelectedIndxs() {
        selectedIndxs = null;
        selectedIndxList = new ArrayList<Integer>();
        //        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        //get all doc for the current selected asset
        System.out.println("before MrltAssetdocumentsViewIterator");
        DCIteratorBinding docIter = (DCIteratorBinding)bindings.get("MrltAssetdocumentsViewIterator");
        RowSetIterator rsi = docIter.getRowSetIterator();
        //get access to the "allEmployees" list binding
        System.out.println("before MrltDocumentsView");
        JUCtrlListBinding docsList = (JUCtrlListBinding)bindings.get("MrltDocumentsView");
        DCIteratorBinding docsListIter = docsList.getDCIteratorBinding();
        while (rsi.hasNext()) {
            //iterate over the employees in the selected department and find
            //the employee record index in the allEmployees iterator. Note
            //that the employee in the allEmployees iterator has a different
            //index number than the same employee record in the department's
            //dependent View Object
            Row rw = (Row)rsi.next();
            System.out.println("setCurrentRowWithKey");
            //            docsListIter.setCurrentRowWithKey(rw.getKey().toStringFormat(true));

            Long sDoc = (Long)docsListIter.getCurrentRow().getAttribute("Docid");
            System.out.println("sDoc: " + sDoc);

            int indx = docsListIter.getCurrentRowIndexInRange();
            selectedIndxList.add(indx);
        }
        selectedIndxs = selectedIndxList.toArray(new Integer[selectedIndxList.size()]);
        return selectedIndxs;
    }


    public void onAssignDocShuttleChange(ValueChangeEvent valueChangeEvent) {
        //get all items left as selected after a change
        Integer[] employeesInDepartment = (Integer[])valueChangeEvent.getNewValue();
        //get the DepartmentId
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding allDepartmentsIterator = (DCIteratorBinding)bindings.get("MrltDocumentsViewIterator");
        //get current row
        Row currentRow = allDepartmentsIterator.getCurrentRow();
        Long docId = (Long)currentRow.getAttribute("Docid");
        //compare the selected values in the list and update all rows that
        //don't yet have the department Id of the selected department
        JUCtrlListBinding docsList = (JUCtrlListBinding)bindings.get("MrltAssetdocumentsView");
        DCIteratorBinding docsListIter = docsList.getDCIteratorBinding();
        if (employeesInDepartment.length > 0) {
            for (int index : employeesInDepartment) {
                Row rowToUpdate = docsListIter.getRowAtRangeIndex(index);
                //check if the department Id of the selected employee is the
                //same as the selected department. If not, update the selected
                //employee record
                if (!((Number)rowToUpdate.getAttribute("DepartmentId")).equals(docId)) {
                    rowToUpdate.setAttribute("DepartmentId", docId);
                }
            }
            //Note that this sample does not commit the changes. I do this on
            //purpose not to mess with your database.
        }
    }

    public void processSelection(ActionEvent actionEvent) {
        BindingContainer bc = this.getBindings();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bc.get("MrltDocumentsView");
        //        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        Object[] docIds = listBinding.getSelectedValues();
        ArrayList<Long> stringList = new ArrayList<Long>();
        String ret = null;
        for (int i = 0; i < docIds.length; i++) {
            System.out.println(docIds[i]);
            stringList.add((Long)docIds[i]);
        }
        //System.out.println("stringList.size() = " + stringList.size());
        if (stringList.size() > 0) {
            // ret = createCCPartStores(iListid, stringList);
        }
        if (ret == null) {
            //System.out.println("ret is null");
            //            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfStoreroom", true);
            //
            //            this.storeShuttle.setDisabled(true);
            //            this.storeProcessBtn.setDisabled(true);
            //            this.storeClearBtn.setDisabled(true);
            //            this.storeCreateMoreStoreBtn.setDisabled(true);
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeShuttle);
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeProcessBtn);
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeClearBtn);
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeCreateMoreStoreBtn);
        }
    }

    public void uploadAssetFileLsnr(ValueChangeEvent valueChangeEvent) {
        {
            String mimeAllowed = "DEFAULT";
            String photoDocType = "PHTO";
            //System.out.println("uploadFileLsnr");
            // The event give access to an Uploade dFile which contains data about the file and its content

            UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
            // Get the original file name
            String sDocName = file.getFilename();
            //System.out.println("fileName: " + sDocName);
            //            Long longFileSz = file.getLength();
            //            Integer intFileSz = longFileSz.intValue();
            // get the mime type
            String sfileType = ContentTypes.get(sDocName);

            //System.out.println("mime type allowed? " + ContentTypes.mimeTypeAllowed(extAllowed,sfileType));

            if (!ContentTypes.mimeTypeAllowed(mimeAllowed, sfileType)) {
                String msg = "File type '" + sfileType + "' is not allowed";
                userModificationMsg("ERROR", "Error", msg);
                this.uploadAssetFileInput.resetValue();
                return;
            }


            // System.out.println("sfileType: " + sfileType);
            String sDocDesc = (String)this.txtAssetFileDesc.getValue();
            String sDocType = (String)this.txtAssetDocType.getValue();

            // get the current row from the docs iterator via the binding
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltDocumentsViewIterator");
            //System.out.println("display name: " + dciter.getDisplayName());
            Row newRow = dciter.getCurrentRow();
            // set the file name
            newRow.setAttribute("Docname", sDocName);
            newRow.setAttribute("Docdesc", sDocDesc);
            newRow.setAttribute("Doctype", sDocType);
            // create the BlobDomain and set it into the row
            //System.out.println("b");
            newRow.setAttribute("Docfile", FileUtil.createBlobDomain(file));
            // set the mime type
            newRow.setAttribute("Filetype", sfileType);
            //            newRow.setAttribute("Filesize;", intFileSz);

        }
    }

    public String createInsertAssetDoc() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertAssetDoc");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "";
    }
    
    public String addNewAssetDoc() {
        createInsertAssetDoc();
        //call popup
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popAssetDocs.show(hints);
        return null;
    }
    
    public String saveNewAssetDoc() {
        doCommit();
        popAssetDocs.hide();
//        this.txtAssetDocType.setDisabled(true);
//        this.txtAssetFileDesc.setDisabled(true);
//        this.uploadAssetFileInput.setDisabled(true);
        return null;
    }
    
    
    public String cancelNewAssetDoc() {
        // Add event code here...   
//        System.out.println("cancelNewAssetDoc");
        
//        System.out.println("Doc Id 2:" + ADFUtil.evaluateEL("#{bindings.Docid.inputValue"));
        doRollback();
        popAssetDocs.hide();
        return null;
    }

    public void onAssetDocIdLovLaunch(LaunchPopupEvent launchPopupEvent) {
//        System.out.println("onAssetDocIdLovLaunch");
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        String sOrg = (String)ADFUtil.evaluateEL("#{bindings.AssetOrg.inputValue}");
        BindingContext bctx = BindingContext.getCurrent();
         BindingContainer bindings = bctx.getCurrentBindingsEntry();
         JUCtrlListBinding lov =(JUCtrlListBinding)bindings.get("Docid");
         lov.getListIterBinding().getViewObject().setNamedWhereClauseParam("BASSET",sAsset);
        lov.getListIterBinding().getViewObject().setNamedWhereClauseParam("BASSETORG",sOrg);

    }
    
    
    public void assetDocDiagLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            //System.out.println("DialogEvent.Outcome.yes");
            //System.out.println("assetDocDiagLsnr ok");

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.popAssetDocs);
            
        } 
    }
    
    public void crAssetFileRec() {
        System.out.println("creating new Asset Doc record");
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        String sOrg = (String)ADFUtil.evaluateEL("#{bindings.AssetOrg.inputValue}");
        //Get Doc ID
        Long lDocId = (Long)ADFUtil.evaluateEL("#{bindings.Docid.inputValue}");
        //Create new AssetDoc record
        createInsertAssetDoc();
            
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltAssetdocumentsViewIterator");
        //System.out.println("display name: " + dciter.getDisplayName());
        Row newRow = dciter.getCurrentRow();
        // set the file name
        newRow.setAttribute("Assetdocitem", sAsset);
        newRow.setAttribute("Org", sOrg);
        newRow.setAttribute("Docid", lDocId);
    }

    public void crAssetFileRec_bak() {
        System.out.println("creating new Asset Doc record");
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        String sOrg = (String)ADFUtil.evaluateEL("#{bindings.AssetOrg.inputValue}");
        //Get Doc ID
        Long lDocId = (Long)ADFUtil.evaluateEL("#{bindings.Docid.inputValue}");
        //Create new AssetDoc record
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltAssetdocumentsViewIterator");
        //System.out.println("display name: " + dciter.getDisplayName());
        Row newRow = dciter.getCurrentRow();
        // set the file name
        newRow.setAttribute("Assetdocitem", sAsset);
        newRow.setAttribute("Org", sOrg);
        newRow.setAttribute("Docid", lDocId);
    }
    public void uploadFileLsnr(ValueChangeEvent valueChangeEvent) {
        {
            String mimeAllowed = "ASSETPHOTO";
            String photoDocType = "PHTO";
            //System.out.println("uploadFileLsnr");
            // The event give access to an Uploade dFile which contains data about the file and its content

            UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
            // Get the original file name
            String sDocName = file.getFilename();
            //System.out.println("fileName: " + sDocName);
            //            Long longFileSz = file.getLength();
            //            Integer intFileSz = longFileSz.intValue();
            // get the mime type
            String sfileType = ContentTypes.get(sDocName);

            //System.out.println("mime type allowed? " + ContentTypes.mimeTypeAllowed(extAllowed,sfileType));

            if (!ContentTypes.mimeTypeAllowed(mimeAllowed, sfileType)) {
                String msg = "File type '" + sfileType + "' is not allowed";
                userModificationMsg("ERROR", "Error", msg);
                this.uploadFileInput.resetValue();
                return;
            }


            // System.out.println("sfileType: " + sfileType);
            String sDocDesc = (String)this.txtFileDesc.getValue();
            //String sDocType = (String)this.txtDocType.getValue();

            // get the current roew from the ImagesView2Iterator via the binding
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltDocumentsViewIterator");
            //System.out.println("display name: " + dciter.getDisplayName());
            Row newRow = dciter.getCurrentRow();
            // set the file name
            newRow.setAttribute("Docname", sDocName);
            newRow.setAttribute("Docdesc", sDocDesc);
            newRow.setAttribute("Doctype", photoDocType);
            // create the BlobDomain and set it into the row
            //System.out.println("b");
            newRow.setAttribute("Docfile", FileUtil.createBlobDomain(file));
            // set the mime type
            newRow.setAttribute("Filetype", sfileType);
            //            newRow.setAttribute("Filesize;", intFileSz);

        }
    }


    public void setTxtFileDesc(RichInputText txtFileDesc) {
        this.txtFileDesc = txtFileDesc;
    }

    public RichInputText getTxtFileDesc() {
        return txtFileDesc;
    }


    public void setUploadFileInput(RichInputFile uploadFileInput) {
        this.uploadFileInput = uploadFileInput;
    }

    public RichInputFile getUploadFileInput() {
        return uploadFileInput;
    }
    
    public Boolean chkRequiredFieldsAssetPhoto() {
        Boolean ret = true;
        BlobDomain bDocfile = (BlobDomain)ADFUtil.evaluateEL("#{bindings.Docfile.inputValue}");
        
        if (this.txtFileDesc.getValue() == null ||
            (this.uploadFileInput.getValue() == null && bDocfile == null)) {
            System.out.println("Required fields missing");
            String msg = "Description and File are required";
            userModificationMsg("INFO", "Info", msg);
            ret = false;

        }
        return ret;

    }

//    public void saveNewPhoto(ActionEvent actionEvent) {
//        System.out.println("saveNewPhoto()");
//        Boolean reqFields = false;
//        reqFields = chkRequiredFieldsAssetPhoto();
//
//        if (reqFields) {
//            {
//                //doCommit();
//                setAssetPhoto();
//                doCommit();
//                AdfFacesContext.getCurrentInstance().addPartialTarget(this.uploadPhotoForm);
//            }
//
//        }
//    }
    public String saveNewPhoto() {
        System.out.println("saveNewPhoto()");
//        Boolean reqFields = false;
//        reqFields = chkRequiredFieldsAssetPhoto();
//
//        if (reqFields) {
//            {
                doCommit();
                setAssetPhoto();
                doCommit();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.uploadPhotoForm);
//            }
//
//        }
        return "";
    }
    
    public void closeAssetPhotoPop(ActionEvent actionEvent) {
        //close popup
        if (isDirty()) {
            doRollback();
        }
        popAssetPhoto.hide();
        
    }

      
    public void assetPhotoPopCancLsnr(PopupCanceledEvent popupCanceledEvent) {
        System.out.println("assetPhotoPopCancelLsnr");
        doRollback();
    }
    
    public void setUploadPhotoForm(RichPanelFormLayout uploadPhotoForm) {
        this.uploadPhotoForm = uploadPhotoForm;
    }

    public RichPanelFormLayout getUploadPhotoForm() {
        return uploadPhotoForm;
    }

    public void setPendingChangePop(RichPopup pendingChangePop) {
        this.pendingChangePop = pendingChangePop;
    }

    public RichPopup getPendingChangePop() {
        return pendingChangePop;
    }

    public void setPopAddSpare(RichPopup popAddSpare) {
        this.popAddSpare = popAddSpare;
    }

    public RichPopup getPopAddSpare() {
        return popAddSpare;
    }


    public void setSpareTable(RichTable spareTable) {
        this.spareTable = spareTable;
    }

    public RichTable getSpareTable() {
        return spareTable;
    }


    public String crCustAttribAsset_action() {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("createAssetCustAttributes");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.custAttribsTable);
        }


        return null;
    }

    public void setCustAttribsTable(RichTable custAttribsTable) {
        this.custAttribsTable = custAttribsTable;
    }

    public RichTable getCustAttribsTable() {
        return custAttribsTable;
    }

    public String delCustAttribsAsset_action() {
        System.out.println("Deleting Custom Attribute(s)");
        RowKeySet selectedEmps = getCustAttribsTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();

        BindingContainer bindings = getBindings();
        DCIteratorBinding iter = (DCIteratorBinding)bindings.get("MrltCustattribvaluesViewIterator");

        RowSetIterator RSIter = iter.getRowSetIterator();
        while (selectedEmpIter.hasNext()) {
            Key key = (Key)((List)selectedEmpIter.next()).get(0);
            Row currentRow = RSIter.getRow(key);
            String sAttrib = (String)currentRow.getAttribute("tCustAttribDesc");

            System.out.println(sAttrib + " selected");

            if ((String)currentRow.getAttribute("Custattribvalue") == null) {
                System.out.println(sAttrib + " is empty");
            }
        }

        //        OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
        //        Object result = operationBinding.execute();
        //        if (!operationBinding.getErrors().isEmpty()) {
        //            return null;
        //        }
        //
        return null;
    }

    public void pendingChangeDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.yes) {
            //System.out.println("DialogEvent.Outcome.yes");
            //apply_action();
            doCommit();
        }
    }

    public String addReadingMenuItm() {
        String msg = "";
        //check if AssetCd exists and is In Service
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        String sAssetStatus = (String)ADFUtil.evaluateEL("#{bindings.Assetstatus.inputValue}");

        if (sAsset == null) {
            msg = "Please select an Asset first. ";
            userModificationMsg("INFO", "Info", msg);
            return "";
        } else if (sAssetStatus == null || "OS".equals(sAssetStatus)) {
            msg = "Asset is not In Service";
            userModificationMsg("INFO", "Info", msg);
            return "";
        } else {
            //Nagivate to Meter Reading
            goToControlFlow(null, "goMeterReading");
        }
        return null;
    }
    
    public String callCopyPop() {
        //call popup
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popCopyAsset.show(hints);
        
        String sSelectedAsset = (String)ADFUtil.evaluateEL("#{bindings.Assetcd.inputValue}");
        String sSelectedOrg = (String)ADFUtil.evaluateEL("#{bindings.Org.inputValue}");
        ADFUtil.setEL("#{bindings.PCOPYASSET.inputValue}", sSelectedAsset != null? sSelectedAsset:"" );
        ADFUtil.setEL("#{bindings.PCOPYORG.inputValue}", sSelectedOrg != null? sSelectedOrg:"1" );
        
//        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtCopyAssetAsset);
//        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtCopyAssetOrg);
        
        return null;
    }
    
    public String createInsertAssetPhoto() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertPhoto");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }


    public void photoActionLsnr(ValueChangeEvent valueChangeEvent) {
        //get current instance of change
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        
//        String sAction = (String)ADFUtil.evaluateEL("#{bindings.tUploadPhoto.inputValue}");
        System.out.println("Photo Action: " + getSelectedItem());

//                    if ("Upload".equals(sAction)) {
//                        createInsertAssetPhoto();
//                    } 
        
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);
        } else {
            if ("Upload".equals(getSelectedItem())) {
                createInsertAssetPhoto();
            } 
        }

    }

    public Boolean isAssetPhotoChanged() {
        Boolean ret = false;
        Long newDocId = (Long)ADFUtil.evaluateEL("#{bindings.Assetdocid.inputValue}");
        Long postedDocId = (Long)ADFUtil.evaluateEL("#{bindings.tPostedAssetDocId.inputValue}");
        ret = newDocId.equals(postedDocId) ? true : false;
        return ret;
    }

    public void tabDisclosed(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded() == true) {
            // tabDisclosed = (String)disclosureEvent.getComponent().getAttributes().get("disclosedTab");
            //setwhichTab((String)disclosureEvent.getComponent().getAttributes().get("disclosedTab"));
            //System.out.println(whichTab);


            String sTab = (String)disclosureEvent.getComponent().getAttributes().get("disclosedTab");
            System.out.println(sTab);

            if (isDirty()) {
                //System.out.println("Dirty");
                //call popup
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                pendingChangePop.show(hints);
                this.tabAssetPhotoUpload.setDisclosed(false);
                this.tabAssetPhotoEdit.setDisclosed(true);
                return;
            } else {
                if ("Upload".equals(sTab)) {
                    createInsertAssetPhoto();
                }
            }
        }
    }
    
    public void setUploadAssetFileInput(RichInputFile uploadAssetFileInput) {
        this.uploadAssetFileInput = uploadAssetFileInput;
    }

    public RichInputFile getUploadAssetFileInput() {
        return uploadAssetFileInput;
    }

    public void setTxtAssetFileDesc(RichInputText txtAssetFileDesc) {
        this.txtAssetFileDesc = txtAssetFileDesc;
    }

    public RichInputText getTxtAssetFileDesc() {
        return txtAssetFileDesc;
    }

    public void setTxtAssetDocType(RichSelectOneChoice txtAssetDocType) {
        this.txtAssetDocType = txtAssetDocType;
    }

    public RichSelectOneChoice getTxtAssetDocType() {
        return txtAssetDocType;
    }

    public void setPopAssetDocs(RichPopup popAssetDocs) {
        this.popAssetDocs = popAssetDocs;
    }

    public RichPopup getPopAssetDocs() {
        return popAssetDocs;
    }

    public void setAssetDocsTable(RichTable assetDocsTable) {
        this.assetDocsTable = assetDocsTable;
    }

    public RichTable getAssetDocsTable() {
        return assetDocsTable;
    }


    public void setTxtAssetDocId(RichInputListOfValues txtAssetDocId) {
        this.txtAssetDocId = txtAssetDocId;
    }

    public RichInputListOfValues getTxtAssetDocId() {
        return txtAssetDocId;
    }

    public void setInlineframeDocId(RichInlineFrame inlineframeDocId) {
        this.inlineframeDocId = inlineframeDocId;
    }

    public RichInlineFrame getInlineframeDocId() {
        return inlineframeDocId;
    }



    public void setAssetReadingTable(RichTable assetReadingTable) {
        this.assetReadingTable = assetReadingTable;
    }

    public RichTable getAssetReadingTable() {
        return assetReadingTable;
    }

    public void setTabReading(RichShowDetailItem tabReading) {
        this.tabReading = tabReading;
    }

    public RichShowDetailItem getTabReading() {
        return tabReading;
    }

    public void setTabRecord(RichShowDetailItem tabRecord) {
        this.tabRecord = tabRecord;
    }

    public RichShowDetailItem getTabRecord() {
        return tabRecord;
    }

    public void setAssetsReadingTableContainer(RichPanelCollection assetsReadingTableContainer) {
        this.assetsReadingTableContainer = assetsReadingTableContainer;
    }

    public RichPanelCollection getAssetsReadingTableContainer() {
        return assetsReadingTableContainer;
    }


    public void setPopCopyAsset(RichPopup popCopyAsset) {
        this.popCopyAsset = popCopyAsset;
    }

    public RichPopup getPopCopyAsset() {
        return popCopyAsset;
    }

    public void setTxtCopyAssetOrg(RichInputText txtCopyAssetOrg) {
        this.txtCopyAssetOrg = txtCopyAssetOrg;
    }

    public RichInputText getTxtCopyAssetOrg() {
        return txtCopyAssetOrg;
    }

    public void setTxtCopyAssetAsset(RichInputText txtCopyAssetAsset) {
        this.txtCopyAssetAsset = txtCopyAssetAsset;
    }

    public RichInputText getTxtCopyAssetAsset() {
        return txtCopyAssetAsset;
    }

    public void setAssetUploadNewPhotoChk(RichSelectBooleanCheckbox assetUploadNewPhotoChk) {
        this.assetUploadNewPhotoChk = assetUploadNewPhotoChk;
    }

    public RichSelectBooleanCheckbox getAssetUploadNewPhotoChk() {
        return assetUploadNewPhotoChk;
    }


    public void setTabAssetPhotoUpload(RichShowDetailItem tabAssetPhotoUpload) {
        this.tabAssetPhotoUpload = tabAssetPhotoUpload;
    }

    public RichShowDetailItem getTabAssetPhotoUpload() {
        return tabAssetPhotoUpload;
    }

    public void setTabAssetPhotoEdit(RichShowDetailItem tabAssetPhotoEdit) {
        this.tabAssetPhotoEdit = tabAssetPhotoEdit;
    }

    public RichShowDetailItem getTabAssetPhotoEdit() {
        return tabAssetPhotoEdit;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedItem() {
        return selectedItem;
    }


    public void setPopAssetPhoto(RichPopup popAssetPhoto) {
        this.popAssetPhoto = popAssetPhoto;
    }

    public RichPopup getPopAssetPhoto() {
        return popAssetPhoto;
    }



}
