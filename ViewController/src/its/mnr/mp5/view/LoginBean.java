package its.mnr.mp5.view;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import weblogic.security.SimpleCallbackHandler;
import weblogic.security.services.Authentication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.IOException;

import java.util.Hashtable;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.share.ADFContext;

import oracle.binding.BindingContainer;

import oracle.jbo.ApplicationModule;

import weblogic.security.URLCallbackHandler;
import oracle.jbo.server.TransactionEvent;

public class LoginBean {
    private String _username;
    private String _password;

    public void setUsername(String _username) {
        this._username = _username.toLowerCase();
    }

    public String getUsername() {
        return _username;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public String getPassword() {
        return _password;
    }
    
    public String doLogin() {
        String un = _username;
        byte[] pw =_password.getBytes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        try {
            Subject subject = Authentication.login(new URLCallbackHandler(un, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
           
            //String loginUrl = "/adfAuthentication?success_url=/faces/PO.jsf";
            String loginUrl = "/adfAuthentication?success_url=/faces/home.jspx";
            String loginUrlWO = "/adfAuthentication?success_url=/faces/Content/WorkOrder/workorder.jspx";
            //String loginUrlWO = "/adfAuthentication?success_url=/faces/Content/Asset/asset.jspx";
            
            HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
            sendForward(request, response, loginUrlWO);
        } catch (FailedLoginException fle) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password", "An incorrect Username or Password was specified");
            ctx.addMessage(null, msg);
        } catch (LoginException le) {
            reportUnexpectedLoginError("LoginException", le);
        }
        return null;
    }

    public String doLoginQuick() {
        String un = "r5";
        byte[] pw ="retro666".getBytes();
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        try {
            Subject subject = Authentication.login(new URLCallbackHandler(un, pw));
            weblogic.servlet.security.ServletAuthentication.runAs(subject, request);
           
            //String loginUrl = "/adfAuthentication?success_url=/faces/PO.jsf";
            String loginUrl = "/adfAuthentication?success_url=/faces/home.jspx";
            String loginUrlWO = "/adfAuthentication?success_url=/faces/Content/WorkOrder/workorder.jspx";
            //String loginUrlWO = "/adfAuthentication?success_url=/faces/Content/Asset/asset.jspx";
            
            HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
            sendForward(request, response, loginUrlWO);
        } catch (FailedLoginException fle) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect Username or Password", "An incorrect Username or Password was specified");
            ctx.addMessage(null, msg);
        } catch (LoginException le) {
            reportUnexpectedLoginError("LoginException", le);
        }
        return null;
    }
    
    private String[] getUserRoles(){
        return ADFContext.getCurrent().getSecurityContext().getUserRoles();
        }
    
    public void printRoles() {
        for (String role : getUserRoles()) {
            System.out.println("role " + role);
        }
    }
                
    private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException se) {
            reportUnexpectedLoginError("ServletException", se);
        } catch (IOException ie) {
            reportUnexpectedLoginError("IOException", ie);
        }
        ctx.responseComplete();
    }

    private void reportUnexpectedLoginError(String errType, Exception e) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unexpected error during login",
                             "Unexpected error during login (" + errType + "), please consult logs for detail");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        e.printStackTrace();
    }
}  