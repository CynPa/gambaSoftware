/*   1:    */ package com.asinfo.as2.seguridad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Organizacion;
/*   5:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   6:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*   7:    */ import com.asinfo.as2.util.AppUtil;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  18:    */ import net.sf.jasperreports.engine.JRException;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class ReportePermisosUsuarioBean
/*  24:    */   extends AbstractBaseReportBean
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private transient ServicioUsuario servicioUsuario;
/*  28:    */   private EntidadUsuario entidadUsuario;
/*  29:    */   private String nombreUsuario;
/*  30:    */   
/*  31:    */   protected JRDataSource getJRDataSource()
/*  32:    */   {
/*  33: 59 */     JRDataSource ds = null;
/*  34: 60 */     if (getEntidadUsuario().getId() > 0)
/*  35:    */     {
/*  36: 62 */       List<Object[]> listaPermisosUsuario = new ArrayList();
/*  37:    */       
/*  38:    */ 
/*  39:    */ 
/*  40: 66 */       this.entidadUsuario = this.servicioUsuario.cargarDetalle(this.entidadUsuario.getId(), AppUtil.getOrganizacion().getId());
/*  41: 67 */       listaPermisosUsuario = this.servicioUsuario.getListaPermisosUsuario(this.entidadUsuario.getId());
/*  42:    */       
/*  43: 69 */       String[] fields = { "f_usuario", "f_nombresUsuario", "f_permisos", "f_accion", "f_modulo", "f_submodulo" };
/*  44: 70 */       ds = new QueryResultDataSource(listaPermisosUsuario, fields);
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 72 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  49:    */     }
/*  50: 75 */     return ds;
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected String getCompileFileName()
/*  54:    */   {
/*  55: 87 */     return "reportePermisosUsuario";
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected Map<String, Object> getReportParameters()
/*  59:    */   {
/*  60: 99 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  61:100 */     return reportParameters;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void crearUsuario()
/*  65:    */   {
/*  66:104 */     this.entidadUsuario = new EntidadUsuario();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String execute()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:115 */       super.prepareReport();
/*  74:    */     }
/*  75:    */     catch (JRException e)
/*  76:    */     {
/*  77:117 */       LOG.info("Error JRException");
/*  78:118 */       e.printStackTrace();
/*  79:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  80:    */     }
/*  81:    */     catch (IOException e)
/*  82:    */     {
/*  83:122 */       LOG.info("Error IOException");
/*  84:123 */       e.printStackTrace();
/*  85:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  86:    */     }
/*  87:128 */     return null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public EntidadUsuario getEntidadUsuario()
/*  91:    */   {
/*  92:132 */     if (this.entidadUsuario == null) {
/*  93:133 */       crearUsuario();
/*  94:    */     }
/*  95:135 */     return this.entidadUsuario;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  99:    */   {
/* 100:139 */     this.entidadUsuario = entidadUsuario;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombreUsuario()
/* 104:    */   {
/* 105:146 */     return this.nombreUsuario;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombreUsuario(String nombreUsuario)
/* 109:    */   {
/* 110:153 */     this.nombreUsuario = nombreUsuario;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.reportes.controller.ReportePermisosUsuarioBean
 * JD-Core Version:    0.7.0.1
 */