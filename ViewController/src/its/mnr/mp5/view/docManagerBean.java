package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;

import oracle.jbo.domain.BlobDomain;

import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class docManagerBean {
    private RichInputText txtFileDesc;
    private RichSelectOneChoice txtDocType;
    private RichButton downloadBtn;
    private RichInputFile uploadedFile;

    public docManagerBean() {
    }

    public void setTxtFileDesc(RichInputText txtFileDesc) {
        this.txtFileDesc = txtFileDesc;
    }

    public RichInputText getTxtFileDesc() {
        return txtFileDesc;
    }

    private UploadedFile _file;

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

    public void setFile(UploadedFile _file) {
        this._file = _file;
    }

    public UploadedFile getFile() {
        return _file;
    }

    public String getProfileValue(String sFName) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getProfileValue");
        operationBinding.getParamsMap().put("sFName", sFName);
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return result;
    }

    public String doCommit() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "Return";
    }

    public String doRollback() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return "Return";
    }

    public String cancel_action() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        // get an ADF attributevalue from the ADF page definitions
        AttributeBinding attr = (AttributeBinding)bindings.getControlBinding("Docid");
        Long Docid = (Long)attr.getInputValue();
        OperationBinding opRollback = bindings.getOperationBinding("Rollback");
        opRollback.execute();
        if (!opRollback.getErrors().isEmpty()) {
            List<Throwable> lErrorList = opRollback.getErrors();
            for (Throwable lErr : lErrorList) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, lErr.getMessage(), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return null;
        }

        OperationBinding opSetParent = bindings.getOperationBinding("setCurrentRowWithKeyValue");
        opSetParent.getParamsMap().put("rowKey", Docid);
        opSetParent.execute();
        if (!opSetParent.getErrors().isEmpty()) {
            List<Throwable> lErrorList = opSetParent.getErrors();
            for (Throwable lErr : lErrorList) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, lErr.getMessage(), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
            return null;
        }
        return "cancel";
    }

    public String upload_action() throws IOException {
        UploadedFile myfile = this.getFile();
        Boolean haveFile = false;
        if (myfile != null) {
            haveFile = true;
        }
        //System.out.println("Content Type: " + myfile.getContentType() + " : " + myfile.getLength() + " bytes");

        //if (myfile == null || this.txtDesc.getValue() == null) {
        if (this.txtFileDesc.getValue() == null) {
            String msg = "Description is required";
            userModificationMsg("INFO", "Info", msg);
            return null;
        }


        if (haveFile) {
            if (!"application/pdf".equals(myfile.getContentType())) {
                String msg = "File must be PDF";
                userModificationMsg("ERROR", "Error", msg);
                return null;
            }

            String outFilePath = "";
            //            if ("Terminal Condition".equals(this.reportTypeRadio.getValue())) {
            //                outFilePath = getProfileValue("TCRFileOutput");
            //            } else if ("Special Notice".equals(this.reportTypeRadio.getValue())) {
            //                outFilePath = getProfileValue("SpcNoticeFileOutput");
            //            }
            System.out.println(outFilePath);

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                // read this file into InputStream
                inputStream = myfile.getInputStream();

                outputStream = new FileOutputStream(new File(outFilePath));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) > 0) {
                    System.out.println("Writing");
                    outputStream.write(bytes, 0, read);
                }

                //System.out.println("Done!");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            if (haveFile) {
                String msg =
                    "<html>Successfully uploaded file <b>" + myfile.getFilename() + "</b> (" + myfile.getLength() +
                    " bytes)</html>";
                //String msg = "<html><a href=\"http://itswebdmzuat01/Main/Default.aspx\" target=\"_blank\">View Change</a></html>";
                // msg = "<html>You can not <b>BACK</b> any record <i>Please choose anything else</i></html>";
                // &nbsp;<font color="blue"><a href="http://itswebdmzuat01/Main/Default.aspx" target="_blank">View Change</a></font>
                System.out.println(msg);
                userModificationMsg("INFO", "Info", msg);
                //            FacesContext context = FacesContext.getCurrentInstance();
                //            FacesMessage fm = new FacesMessage(msg);
                //            fm.setSeverity(FacesMessage.SEVERITY_INFO);
                //            context.addMessage(null, fm);

            }
        }


        return null;
    }

    public Boolean chkRequiredFields() {
        Boolean ret = true;
        BlobDomain bDocfile = (BlobDomain)ADFUtil.evaluateEL("#{bindings.Docfile.inputValue}");
        
        if (this.txtFileDesc.getValue() == null || this.txtDocType.getValue() == null ||
            (this.uploadedFile.getValue() == null && bDocfile == null)) {
            String msg = "Description, Document Type, and File are required";
            userModificationMsg("INFO", "Info", msg);
            ret = false;

        }
        return ret;

    }

    public String commitEditDocs() {
        Boolean reqFields = false;
        reqFields = chkRequiredFields();

        if (!reqFields) {
            return "";
        } else {
            doCommit();
        }
        return "";
    }


    public void uploadFileLsnr(ValueChangeEvent valueChangeEvent) {
        {
            Boolean reqFields = false;
            reqFields = chkRequiredFields();

            if (!reqFields) {
                return;
            }
            String mimeAllowed = "DEFAULT";
            //System.out.println("uploadFileLsnr");
            // The event give access to an Uploade dFile which contains data about the file and its content

            UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
            // Get the original file name
            String sDocName = file.getFilename();
            //System.out.println("fileName: " + sDocName);
            Long longFileSz = file.getLength();
            Integer intFileSz = longFileSz.intValue();
            // get the mime type
            String sfileType = ContentTypes.get(sDocName);

            //System.out.println("mime type allowed? " + ContentTypes.mimeTypeAllowed(extAllowed,sfileType));

            if (!ContentTypes.mimeTypeAllowed(mimeAllowed, sfileType)) {
                String msg = "File type '" + sfileType + "' is not allowed";
                userModificationMsg("ERROR", "Error", msg);
                this.uploadedFile.resetValue();
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.uploadedFile);
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
            //System.out.println("a");
            // set the file name
            newRow.setAttribute("Docname", sDocName);
            newRow.setAttribute("Docdesc", sDocDesc);
            // create the BlobDomain and set it into the row
            //System.out.println("b");
            newRow.setAttribute("Docfile", FileUtil.createBlobDomain(file));
            //System.out.println("c");
            // set the mime type
            newRow.setAttribute("Filetype", sfileType);
            newRow.setAttribute("Filesize;", intFileSz);

            //System.out.println("d");
        }
    }


    public void downloadFile(FacesContext facesContext, OutputStream outputStream) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();

        // get an ADF attributevalue from the ADF page definitions
        AttributeBinding attr = (AttributeBinding)bindings.getControlBinding("Docfile");
        if (attr == null) {
            return;
        }

        // the value is a BlobDomain data type
        BlobDomain blob = (BlobDomain)attr.getInputValue();

        try { // copy hte data from the BlobDomain to the output stream
            IOUtils.copy(blob.getInputStream(), outputStream);
            // cloase the blob to release the recources
            blob.closeInputStream();
            // flush the outout stream
            outputStream.flush();
        } catch (IOException e) {
            // handle errors
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void setTxtDocType(RichSelectOneChoice txtDocType) {
        this.txtDocType = txtDocType;
    }

    public RichSelectOneChoice getTxtDocType() {
        return txtDocType;
    }

    public void setDownloadBtn(RichButton downloadBtn) {
        this.downloadBtn = downloadBtn;
    }

    public RichButton getDownloadBtn() {
        return downloadBtn;
    }

    public void setUploadedFile(RichInputFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public RichInputFile getUploadedFile() {
        return uploadedFile;
    }


}
