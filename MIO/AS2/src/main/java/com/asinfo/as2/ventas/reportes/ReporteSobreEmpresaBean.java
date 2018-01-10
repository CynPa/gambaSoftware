/*  1:   */ package com.asinfo.as2.ventas.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  5:   */ import com.asinfo.as2.entities.Ciudad;
/*  6:   */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  7:   */ import com.asinfo.as2.entities.Empresa;
/*  8:   */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  9:   */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/* 10:   */ import java.io.IOException;
/* 11:   */ import java.util.ArrayList;
/* 12:   */ import java.util.List;
/* 13:   */ import javax.ejb.EJB;
/* 14:   */ import javax.faces.bean.ManagedBean;
/* 15:   */ import javax.faces.bean.ViewScoped;
/* 16:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 17:   */ import net.sf.jasperreports.engine.JRException;
/* 18:   */ import org.apache.log4j.Logger;
/* 19:   */ 
/* 20:   */ @ManagedBean
/* 21:   */ @ViewScoped
/* 22:   */ public class ReporteSobreEmpresaBean
/* 23:   */   extends AbstractBaseReportBean
/* 24:   */ {
/* 25:   */   private static final long serialVersionUID = -3029723362152385033L;
/* 26:   */   @EJB
/* 27:   */   private transient ServicioEmpresa servicioEmpresa;
/* 28:   */   private Empresa empresa;
/* 29:   */   private DireccionEmpresa direccionEmpresa;
/* 30:32 */   private final String COMPILE_FILE_NAME = "reporteSobreEmpresa";
/* 31:   */   
/* 32:   */   protected JRDataSource getJRDataSource()
/* 33:   */   {
/* 34:36 */     JRDataSource ds = null;
/* 35:   */     try
/* 36:   */     {
/* 37:38 */       String[] fields = { "f_identificacion", "f_nombreFiscal", "f_nombreComercial", "f_ciudad", "f_telefono", "f_direccion" };
/* 38:39 */       Object[] sobre = new Object[6];
/* 39:40 */       sobre[0] = this.direccionEmpresa.getEmpresa().getIdentificacion();
/* 40:41 */       sobre[1] = this.direccionEmpresa.getEmpresa().getNombreFiscal();
/* 41:42 */       sobre[2] = this.direccionEmpresa.getEmpresa().getNombreComercial();
/* 42:43 */       sobre[3] = this.direccionEmpresa.getCiudad().getNombre();
/* 43:44 */       sobre[4] = (this.direccionEmpresa.getTelefono1() + "/" + this.direccionEmpresa.getTelefono2());
/* 44:45 */       sobre[5] = this.direccionEmpresa.getDireccionCompleta();
/* 45:46 */       List<Object[]> lista = new ArrayList();
/* 46:47 */       lista.add(sobre);
/* 47:48 */       ds = new QueryResultDataSource(lista, fields);
/* 48:   */     }
/* 49:   */     catch (Exception e)
/* 50:   */     {
/* 51:50 */       LOG.info("Error " + e);
/* 52:51 */       e.printStackTrace();
/* 53:   */     }
/* 54:53 */     return ds;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String execute()
/* 58:   */   {
/* 59:   */     try
/* 60:   */     {
/* 61:63 */       super.prepareReport();
/* 62:   */     }
/* 63:   */     catch (JRException e)
/* 64:   */     {
/* 65:65 */       LOG.info("Error JRException");
/* 66:66 */       e.printStackTrace();
/* 67:67 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 68:   */     }
/* 69:   */     catch (IOException e)
/* 70:   */     {
/* 71:69 */       LOG.info("Error IOException");
/* 72:70 */       e.printStackTrace();
/* 73:71 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 74:   */     }
/* 75:74 */     return "";
/* 76:   */   }
/* 77:   */   
/* 78:   */   protected String getCompileFileName()
/* 79:   */   {
/* 80:79 */     return "reporteSobreEmpresa";
/* 81:   */   }
/* 82:   */   
/* 83:   */   public Empresa getEmpresa()
/* 84:   */   {
/* 85:83 */     return this.empresa;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public void setEmpresa(Empresa empresa)
/* 89:   */   {
/* 90:87 */     this.empresa = empresa;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public DireccionEmpresa getDireccionEmpresa()
/* 94:   */   {
/* 95:91 */     return this.direccionEmpresa;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public void setDireccionEmpresa(DireccionEmpresa direccionEmpresa)
/* 99:   */   {
/* :0:95 */     direccionEmpresa = this.servicioEmpresa.buscarDireccionEmpresaPorId(direccionEmpresa.getIdDireccionEmpresa());
/* :1:96 */     this.direccionEmpresa = direccionEmpresa;
/* :2:   */   }
/* :3:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteSobreEmpresaBean
 * JD-Core Version:    0.7.0.1
 */