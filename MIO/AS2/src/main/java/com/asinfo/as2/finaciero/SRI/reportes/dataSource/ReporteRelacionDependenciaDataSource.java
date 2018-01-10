/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes.dataSource;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.RelacionDependencia;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import net.sf.jasperreports.engine.JRDataSource;
/*   8:    */ import net.sf.jasperreports.engine.JRException;
/*   9:    */ import net.sf.jasperreports.engine.JRField;
/*  10:    */ 
/*  11:    */ public class ReporteRelacionDependenciaDataSource
/*  12:    */   implements JRDataSource
/*  13:    */ {
/*  14: 31 */   private List<RelacionDependencia> listaRelacionDependencia = new ArrayList();
/*  15: 33 */   private int index = -1;
/*  16:    */   
/*  17:    */   public Object getFieldValue(JRField field)
/*  18:    */     throws JRException
/*  19:    */   {
/*  20: 48 */     Object value = null;
/*  21:    */     
/*  22: 50 */     String fieldName = field.getName();
/*  23: 52 */     if (fieldName.equals("f_cedula")) {
/*  24: 53 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getCedula();
/*  25: 54 */     } else if (fieldName.equals("f_nombre")) {
/*  26: 55 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getNombre();
/*  27: 56 */     } else if (fieldName.equals("f_sueldo")) {
/*  28: 57 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getSueldoSalario();
/*  29: 58 */     } else if (fieldName.equals("f_sobreSueldo")) {
/*  30: 59 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getSobreSueldo();
/*  31: 60 */     } else if (fieldName.equals("f_decimoTercero")) {
/*  32: 61 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDecimoTercero();
/*  33: 62 */     } else if (fieldName.equals("f_decimoCuarto")) {
/*  34: 63 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDecimoCuarto();
/*  35: 64 */     } else if (fieldName.equals("f_fondoReserva")) {
/*  36: 65 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getFondoReserva();
/*  37: 66 */     } else if (fieldName.equals("f_utilidades")) {
/*  38: 67 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getUtilidades();
/*  39: 68 */     } else if (fieldName.equals("f_aportePersonal")) {
/*  40: 69 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getAportePersonalIess();
/*  41: 70 */     } else if (fieldName.equals("f_vivienda")) {
/*  42: 71 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDeducibleVivienda();
/*  43: 72 */     } else if (fieldName.equals("f_salud")) {
/*  44: 73 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDeducibleSalud();
/*  45: 74 */     } else if (fieldName.equals("f_educacion")) {
/*  46: 75 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDeducibleEducacion();
/*  47: 76 */     } else if (fieldName.equals("f_alimentacion")) {
/*  48: 77 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDeducibleAlimentacion();
/*  49: 78 */     } else if (fieldName.equals("f_vestimenta")) {
/*  50: 79 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getDeducibleVestimenta();
/*  51: 80 */     } else if (fieldName.equals("f_numeroRetenciones")) {
/*  52: 81 */       value = Long.valueOf(((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getNumeroRetenciones());
/*  53: 82 */     } else if (fieldName.equals("f_impuestoRentaCausado")) {
/*  54: 83 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getImpuestoRentaCausado();
/*  55: 84 */     } else if (fieldName.equals("f_valorRetenido")) {
/*  56: 85 */       value = ((RelacionDependencia)this.listaRelacionDependencia.get(this.index)).getValorRetenido();
/*  57: 86 */     } else if (fieldName.equals("f_desahucio")) {
/*  58: 87 */       value = BigDecimal.ZERO;
/*  59: 88 */     } else if (fieldName.equals("f_rebajaDiscapacidad")) {
/*  60: 89 */       value = BigDecimal.ZERO;
/*  61: 90 */     } else if (fieldName.equals("f_rebajaTerceraEdad")) {
/*  62: 91 */       value = BigDecimal.ZERO;
/*  63: 92 */     } else if (fieldName.equals("f_impuestoRentaAsumido")) {
/*  64: 93 */       value = BigDecimal.ZERO;
/*  65: 94 */     } else if (fieldName.equals("f_ingresosOtrosEmpleadores")) {
/*  66: 95 */       value = BigDecimal.ZERO;
/*  67: 96 */     } else if (fieldName.equals("f_deducibleOtrosEmpleadores")) {
/*  68: 97 */       value = BigDecimal.ZERO;
/*  69: 98 */     } else if (fieldName.equals("f_valorRetenidoOtrosEmpleadores")) {
/*  70: 99 */       value = BigDecimal.ZERO;
/*  71:100 */     } else if (fieldName.equals("f_rebajasOtrosEmpleadores")) {
/*  72:101 */       value = BigDecimal.ZERO;
/*  73:    */     }
/*  74:104 */     return value;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean next()
/*  78:    */     throws JRException
/*  79:    */   {
/*  80:114 */     this.index += 1;
/*  81:115 */     return this.index < this.listaRelacionDependencia.size();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<RelacionDependencia> getListaRelacionDependencia()
/*  85:    */   {
/*  86:119 */     return this.listaRelacionDependencia;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setListaRelacionDependencia(List<RelacionDependencia> listaRelacionDependencia)
/*  90:    */   {
/*  91:123 */     this.listaRelacionDependencia = listaRelacionDependencia;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.dataSource.ReporteRelacionDependenciaDataSource
 * JD-Core Version:    0.7.0.1
 */