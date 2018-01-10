/*   1:    */ package com.asinfo.as2.web;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Organizacion;
/*   4:    */ import com.asinfo.as2.enumeraciones.Accion;
/*   5:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   6:    */ import com.asinfo.as2.util.SecurityUtil;
/*   7:    */ import com.asinfo.as2.utils.ServiceLocator;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.security.Permission;
/*  10:    */ import java.util.Set;
/*  11:    */ import javax.security.auth.Subject;
/*  12:    */ import javax.servlet.Filter;
/*  13:    */ import javax.servlet.FilterChain;
/*  14:    */ import javax.servlet.FilterConfig;
/*  15:    */ import javax.servlet.ServletException;
/*  16:    */ import javax.servlet.ServletRequest;
/*  17:    */ import javax.servlet.ServletResponse;
/*  18:    */ import javax.servlet.http.HttpServletRequest;
/*  19:    */ import javax.servlet.http.HttpServletResponse;
/*  20:    */ import javax.servlet.http.HttpSession;
/*  21:    */ 
/*  22:    */ public class SecurityFilterListener
/*  23:    */   implements Filter
/*  24:    */ {
/*  25:    */   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
/*  26:    */     throws IOException, ServletException
/*  27:    */   {
/*  28: 42 */     HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
/*  29: 43 */     HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
/*  30:    */     
/*  31: 45 */     Subject subject = (Subject)httpServletRequest.getSession().getAttribute("javax.security.auth.subject");
/*  32: 46 */     Organizacion organizacion = (Organizacion)httpServletRequest.getSession().getAttribute("com.asinfo.as2.organizacion");
/*  33: 47 */     Usuario usuario = (Usuario)httpServletRequest.getSession().getAttribute("com.asinfo.as2.usuario");
/*  34:    */     
/*  35: 49 */     httpServletRequest.getSession().setAttribute("direccion_remota", httpServletRequest.getRemoteAddr());
/*  36: 50 */     httpServletRequest.getSession().setAttribute("host_remoro", httpServletRequest.getRemoteHost());
/*  37: 51 */     httpServletRequest.getSession().setAttribute("servlet_path", httpServletRequest.getServletPath());
/*  38:    */     
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46: 60 */     String urlRedireccionar = null;
/*  47: 62 */     if ((subject != null) && ((usuario.isIndicadorSuperAdministrador()) || (!subject.getPrincipals().isEmpty())))
/*  48:    */     {
/*  49: 63 */       if (organizacion != null)
/*  50:    */       {
/*  51: 64 */         String viewId = httpServletRequest.getServletPath();
/*  52: 65 */         viewId = viewId.replace("jsf", "xhtml");
/*  53: 67 */         if ((usuario.isIndicadorSuperAdministrador()) || (viewId.contains("seleccionarCuentaContable")) || (SecurityUtil.hasPermissionToAccessViewId(subject, viewId, Accion.READ.toString()))) {
/*  54: 68 */           filterChain.doFilter(servletRequest, servletResponse);
/*  55:    */         } else {
/*  56: 70 */           urlRedireccionar = "/" + ServiceLocator.APP_NAME + "/error_page.jsf";
/*  57:    */         }
/*  58:    */       }
/*  59:    */       else
/*  60:    */       {
/*  61: 73 */         urlRedireccionar = "/" + ServiceLocator.APP_NAME + "/seleccionarOrganizacion.jsf";
/*  62:    */       }
/*  63:    */     }
/*  64:    */     else {
/*  65: 77 */       urlRedireccionar = "/" + ServiceLocator.APP_NAME + "/login.jsf";
/*  66:    */     }
/*  67: 80 */     if (urlRedireccionar != null)
/*  68:    */     {
/*  69: 81 */       httpServletResponse.sendRedirect(urlRedireccionar);
/*  70: 82 */       urlRedireccionar = null;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void init(FilterConfig filterConfig)
/*  75:    */     throws ServletException
/*  76:    */   {}
/*  77:    */   
/*  78:    */   public void destroy() {}
/*  79:    */   
/*  80:    */   public static boolean hasPermission(Permission p)
/*  81:    */   {
/*  82:112 */     SecurityManager sm = System.getSecurityManager();
/*  83:    */     try
/*  84:    */     {
/*  85:114 */       sm.checkPermission(p);
/*  86:115 */       return true;
/*  87:    */     }
/*  88:    */     catch (Exception e) {}
/*  89:117 */     return false;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.web.SecurityFilterListener
 * JD-Core Version:    0.7.0.1
 */