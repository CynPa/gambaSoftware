/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Asiento;
/*   4:    */ import com.asinfo.as2.entities.CuentaContable;
/*   5:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   6:    */ import com.asinfo.as2.entities.TipoAsiento;
/*   7:    */ import com.asinfo.as2.finaciero.contabilidad.reportes.controller.dataSource.DiarioGeneralDataSource;
/*   8:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.faces.bean.ManagedBean;
/*  14:    */ import javax.faces.bean.ManagedProperty;
/*  15:    */ import javax.faces.bean.RequestScoped;
/*  16:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @RequestScoped
/*  20:    */ public class ReporteDiarioGeneralBean
/*  21:    */   extends AbstractBaseReportBean
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 5724455275422667475L;
/*  24:    */   @ManagedProperty("#{diarioGeneralBean}")
/*  25:    */   private DiarioGeneralBean diarioGeneralBean;
/*  26: 46 */   private final String COMPILE_FILE_NAME = "diarioGeneral";
/*  27: 48 */   private DiarioGeneralDataSource diarioGeneralDataSource = new DiarioGeneralDataSource();
/*  28:    */   
/*  29:    */   public String execute()
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 53 */       this.diarioGeneralBean.setListaDetalleAsiento(new ArrayList());
/*  34: 55 */       for (Iterator localIterator1 = this.diarioGeneralBean.getListaAsiento().iterator(); localIterator1.hasNext();)
/*  35:    */       {
/*  36: 55 */         asiento = (Asiento)localIterator1.next();
/*  37: 56 */         for (DetalleAsiento detalleAsiento : asiento.getListaDetalleAsiento())
/*  38:    */         {
/*  39: 57 */           detalleAsiento.setCodigoCuenta(detalleAsiento.getCuentaContable().getCodigo());
/*  40: 58 */           detalleAsiento.setNombreCuentaContable(detalleAsiento.getCuentaContable().getNombre());
/*  41: 59 */           detalleAsiento.setNumero(asiento.getNumero());
/*  42: 60 */           detalleAsiento.setFecha(asiento.getFecha());
/*  43: 61 */           detalleAsiento.setConcepto(asiento.getConcepto());
/*  44: 62 */           detalleAsiento.setTipoAsiento(asiento.getTipoAsiento().getNombre());
/*  45: 63 */           this.diarioGeneralBean.getListaDetalleAsiento().add(detalleAsiento);
/*  46:    */         }
/*  47:    */       }
/*  48:    */       Asiento asiento;
/*  49: 67 */       this.diarioGeneralDataSource.setListaDetalleAsiento(this.diarioGeneralBean.getListaDetalleAsiento());
/*  50: 68 */       setJRDataSource(this.diarioGeneralDataSource);
/*  51: 69 */       setExportOption(getExportOption());
/*  52:    */       
/*  53: 71 */       super.prepareReport();
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 74 */       e.printStackTrace();
/*  58:    */     }
/*  59: 77 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 87 */     return this.diarioGeneralDataSource;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setJRDataSource(DiarioGeneralDataSource diarioGeneralDataSource)
/*  68:    */   {
/*  69: 91 */     this.diarioGeneralDataSource = diarioGeneralDataSource;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected String getCompileFileName()
/*  73:    */   {
/*  74:101 */     return "diarioGeneral";
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Map<String, Object> getReportParameters()
/*  78:    */   {
/*  79:106 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  80:    */     
/*  81:108 */     reportParameters.put("ReportTitle", "Diario General");
/*  82:109 */     return reportParameters;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public DiarioGeneralBean getDiarioGeneralBean()
/*  86:    */   {
/*  87:118 */     return this.diarioGeneralBean;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setDiarioGeneralBean(DiarioGeneralBean diarioGeneralBean)
/*  91:    */   {
/*  92:128 */     this.diarioGeneralBean = diarioGeneralBean;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getCOMPILE_FILE_NAME()
/*  96:    */   {
/*  97:137 */     return "diarioGeneral";
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.controller.ReporteDiarioGeneralBean
 * JD-Core Version:    0.7.0.1
 */