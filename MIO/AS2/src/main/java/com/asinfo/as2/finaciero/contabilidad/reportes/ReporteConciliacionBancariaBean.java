/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Banco;
/*   5:    */ import com.asinfo.as2.entities.ConciliacionBancaria;
/*   6:    */ import com.asinfo.as2.entities.CuentaBancaria;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.CuentaContable;
/*   9:    */ import com.asinfo.as2.enumeraciones.TipoCalculo;
/*  10:    */ import com.asinfo.as2.enumeraciones.ValoresCalculo;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioConciliacionBancaria;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import java.io.IOException;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.RequestScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @RequestScoped
/*  31:    */ public class ReporteConciliacionBancariaBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 6873041975491015990L;
/*  35:    */   @EJB
/*  36:    */   private ServicioConciliacionBancaria servicioConciliacionBancaria;
/*  37:    */   @EJB
/*  38:    */   private ServicioCuentaContable servicioCuentaContable;
/*  39:    */   private ConciliacionBancaria conciliacionBancaria;
/*  40: 59 */   private final String COMPILE_FILE_NAME = "reporteConciliacionBancaria";
/*  41:    */   
/*  42:    */   protected JRDataSource getJRDataSource()
/*  43:    */   {
/*  44: 69 */     List listaDatosReporte = new ArrayList();
/*  45: 70 */     JRDataSource ds = null;
/*  46:    */     try
/*  47:    */     {
/*  48: 73 */       listaDatosReporte = this.servicioConciliacionBancaria.getReporteConciliacionBancaria(this.conciliacionBancaria.getFecha(), this.conciliacionBancaria
/*  49: 74 */         .getCuentaBancariaOrganizacion().getId());
/*  50: 76 */       if (listaDatosReporte.isEmpty())
/*  51:    */       {
/*  52: 77 */         Object[] datoAuxuliar = new Object[11];
/*  53: 78 */         datoAuxuliar[0] = null;
/*  54: 79 */         datoAuxuliar[1] = null;
/*  55: 80 */         datoAuxuliar[2] = getLanguageController().getMensaje("msg_no_hay_datos");
/*  56: 81 */         datoAuxuliar[3] = null;
/*  57: 82 */         datoAuxuliar[4] = null;
/*  58: 83 */         datoAuxuliar[5] = null;
/*  59: 84 */         datoAuxuliar[6] = null;
/*  60: 85 */         datoAuxuliar[7] = this.conciliacionBancaria.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero();
/*  61: 86 */         datoAuxuliar[8] = this.conciliacionBancaria.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco().getNombre();
/*  62: 87 */         datoAuxuliar[9] = null;
/*  63: 88 */         datoAuxuliar[10] = null;
/*  64:    */         
/*  65: 90 */         listaDatosReporte.add(datoAuxuliar);
/*  66:    */       }
/*  67: 93 */       String[] fields = { "f_fecha", "f_numeroDocumento", "f_concepto", "f_debe", "f_haber", "f_tipo_asiento", "f_numeroReferencia", "f_cuentaBancaria", "f_banco", "f_formaPago", "f_beneficiario" };
/*  68:    */       
/*  69:    */ 
/*  70: 96 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  71:    */     }
/*  72:    */     catch (ExcepcionAS2 e)
/*  73:    */     {
/*  74: 99 */       LOG.info("Error " + e);
/*  75:100 */       e.printStackTrace();
/*  76:    */     }
/*  77:102 */     return ds;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected String getCompileFileName()
/*  81:    */   {
/*  82:112 */     return "reporteConciliacionBancaria";
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Map<String, Object> getReportParameters()
/*  86:    */   {
/*  87:123 */     BigDecimal saldoContable = this.servicioCuentaContable.obtenerSaldo(FuncionesUtiles.obtenerFechaInicial(), this.conciliacionBancaria.getFecha(), this.conciliacionBancaria
/*  88:124 */       .getCuentaBancariaOrganizacion().getCuentaContableBanco().getIdCuentaContable(), ValoresCalculo.DEBE_HABER, TipoCalculo.SALDO_FINAL, false, -1);
/*  89:    */     
/*  90:126 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  91:    */     
/*  92:128 */     reportParameters.put("p_saldoContable", saldoContable);
/*  93:129 */     reportParameters.put("ReportTitle", "Conciliacion Bancaria");
/*  94:    */     
/*  95:131 */     reportParameters.put("p_fechaConciliacion", FuncionesUtiles.dateToString(this.conciliacionBancaria.getFecha()));
/*  96:132 */     reportParameters.put("p_saldoBanco", this.conciliacionBancaria.getSaldoBancos());
/*  97:133 */     reportParameters.put("p_totalDebitosConciliados", this.servicioConciliacionBancaria.totalDebitoConciliado(this.conciliacionBancaria));
/*  98:134 */     reportParameters.put("p_totalCreditosConciliados", this.servicioConciliacionBancaria.totalCreditoConciliado(this.conciliacionBancaria));
/*  99:    */     
/* 100:136 */     ConciliacionBancaria conciliacionBancariaDetalle = this.servicioConciliacionBancaria.cargarDetalle(this.conciliacionBancaria
/* 101:137 */       .getIdConciliacionBancaria());
/* 102:    */     
/* 103:139 */     String bancoCuenta = conciliacionBancariaDetalle.getCuentaBancariaOrganizacion().getCuentaBancaria().getBanco() + " | " + conciliacionBancariaDetalle.getCuentaBancariaOrganizacion().getCuentaBancaria().getNumero();
/* 104:140 */     reportParameters.put("p_bancoCuenta", bancoCuenta);
/* 105:    */     
/* 106:142 */     return reportParameters;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String execute()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:152 */       super.prepareReport();
/* 114:    */     }
/* 115:    */     catch (JRException e)
/* 116:    */     {
/* 117:154 */       LOG.info("Error JRException");
/* 118:155 */       e.printStackTrace();
/* 119:156 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 120:    */     }
/* 121:    */     catch (IOException e)
/* 122:    */     {
/* 123:158 */       LOG.info("Error IOException");
/* 124:159 */       e.printStackTrace();
/* 125:160 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 126:    */     }
/* 127:162 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ServicioConciliacionBancaria getServicioConciliacionBancaria()
/* 131:    */   {
/* 132:166 */     return this.servicioConciliacionBancaria;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setServicioConciliacionBancaria(ServicioConciliacionBancaria servicioConciliacionBancaria)
/* 136:    */   {
/* 137:170 */     this.servicioConciliacionBancaria = servicioConciliacionBancaria;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ConciliacionBancaria getConciliacionBancaria()
/* 141:    */   {
/* 142:174 */     return this.conciliacionBancaria;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setConciliacionBancaria(ConciliacionBancaria conciliacionBancaria)
/* 146:    */   {
/* 147:178 */     this.conciliacionBancaria = conciliacionBancaria;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getCOMPILE_FILE_NAME()
/* 151:    */   {
/* 152:182 */     return "reporteConciliacionBancaria";
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteConciliacionBancariaBean
 * JD-Core Version:    0.7.0.1
 */