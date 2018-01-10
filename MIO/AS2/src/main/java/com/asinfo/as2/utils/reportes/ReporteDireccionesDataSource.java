/*  1:   */ package com.asinfo.as2.utils.reportes;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  4:   */ import com.asinfo.as2.entities.DireccionEmpresa;
/*  5:   */ import com.asinfo.as2.entities.Empresa;
/*  6:   */ import com.asinfo.as2.entities.Ubicacion;
/*  7:   */ import com.asinfo.as2.utils.ServiceLocator;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.naming.NamingException;
/* 12:   */ import net.sf.jasperreports.engine.JRDataSource;
/* 13:   */ import net.sf.jasperreports.engine.JRException;
/* 14:   */ import net.sf.jasperreports.engine.JRField;
/* 15:   */ 
/* 16:   */ public class ReporteDireccionesDataSource
/* 17:   */   implements JRDataSource
/* 18:   */ {
/* 19:35 */   private List<DireccionEmpresa> listaDireccionEmpresa = new ArrayList();
/* 20:37 */   private Empresa empresa = new Empresa();
/* 21:   */   
/* 22:   */   public void cargarDirecciones()
/* 23:   */   {
/* 24:41 */     ServicioEmpresa servicioEmpresa = null;
/* 25:   */     try
/* 26:   */     {
/* 27:43 */       ServiceLocator serviceLocator = ServiceLocator.getInstance();
/* 28:   */       
/* 29:45 */       servicioEmpresa = (ServicioEmpresa)serviceLocator.getEJB("ServicioEmpresaImpl/local");
/* 30:   */     }
/* 31:   */     catch (NamingException e)
/* 32:   */     {
/* 33:48 */       e.printStackTrace();
/* 34:   */     }
/* 35:51 */     System.out.println("Ingreso en cargarDirecciones");
/* 36:   */     
/* 37:53 */     System.out.println("Empresa en cargar direcciones" + this.empresa
/* 38:54 */       .getNombreFiscal());
/* 39:55 */     this.listaDireccionEmpresa = this.empresa.getDirecciones();
/* 40:   */   }
/* 41:   */   
/* 42:58 */   private int index = -1;
/* 43:   */   
/* 44:   */   public ReporteDireccionesDataSource()
/* 45:   */   {
/* 46:62 */     cargarDirecciones();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean next()
/* 50:   */     throws JRException
/* 51:   */   {
/* 52:66 */     System.out.println("Ingreso en next");
/* 53:67 */     System.out.println("---- Tamanio de la lista ----" + this.listaDireccionEmpresa
/* 54:68 */       .size());
/* 55:69 */     this.index += 1;
/* 56:70 */     return this.index < this.listaDireccionEmpresa.size();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Object getFieldValue(JRField field)
/* 60:   */     throws JRException
/* 61:   */   {
/* 62:74 */     System.out.println("Ingreso en getFieldValue");
/* 63:   */     
/* 64:76 */     Object value = null;
/* 65:   */     
/* 66:78 */     String fieldName = field.getName();
/* 67:   */     
/* 68:80 */     System.out.println("Antes de if del FOR");
/* 69:81 */     if (fieldName.equals("Direccion1")) {
/* 70:83 */       value = ((DireccionEmpresa)this.listaDireccionEmpresa.get(this.index)).getUbicacion().getDireccion1();
/* 71:84 */     } else if (fieldName.equals("Direccion2")) {
/* 72:86 */       value = ((DireccionEmpresa)this.listaDireccionEmpresa.get(this.index)).getUbicacion().getDireccion2();
/* 73:87 */     } else if (fieldName.equals("Telefono")) {
/* 74:88 */       value = ((DireccionEmpresa)this.listaDireccionEmpresa.get(this.index)).getTelefono1();
/* 75:89 */     } else if (fieldName.equals("Descripcion")) {
/* 76:90 */       value = ((DireccionEmpresa)this.listaDireccionEmpresa.get(this.index)).getDescripcion();
/* 77:   */     }
/* 78:93 */     return value;
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.ReporteDireccionesDataSource
 * JD-Core Version:    0.7.0.1
 */